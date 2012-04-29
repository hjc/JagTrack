<?php
	/**
	 *@file Gets average arrival time for a given stop (in minutes). If there is
	 *   no bus that is going to the given stop right away, calculates when the 
	 *   next will arrive (by going through all the stops). 
	 * 
	 * @param $_GET['s']
	 *   The s parameter should be passed through the HTTP request in the URL (in
	 *   a GET fashion). It should contain the stop id we want to find an average
	 *   time for.
	 * @param $_GET['r']
	 *   The s parameter should be passed through the HTTP request in the URL (in
	 *   a GET fashion). It should contain the route id that relates this stop to
	 *   a route (a stop could have multiple routes).
	 * 
	 * @return JSON Average Time
	 *   Returns the average time in a json object or the string "Error".
	 */

	/**
	 * Queries the database and gets the average time it takes to get to a stop,
	 *   assuming the bus is coming from the stop directly before this one.
	 * 
	 * @param int $stop_id 
	 *   ID for the stop we want the average time of, if we want to know how long
	 *   it takes for a bus to go from stop 2 to stop 3 on route 1, we would pass
	 *   in 3 (average time to get to stop 3).
	 * 
	 * @param int $route_id
	 *   Stops can have multiple routes, use this to ensure we pull right time.
	 * 
	 * @param mysqli $mysqli
	 *   Already made mysqli object that is connected to dotCloud DB (making it
	 *   is a mild pain).
	 * 
	 * @return null|avg_time_to_next_stop
	 *   Returns either an integer which represents the average time it will
	 *   take for a bus, on the specified route, to get to the specified stop (in
	 *   minutes) or returns NULL if query failed.
	 **/
	function get_avg_time($stop_id, $route_id, $mysqli) {
		//figure out avg time for this piece of the route
		$q_r = sprintf("
			SELECT 
				avg_time_to_next_stop 
			FROM jt_route_stop
			WHERE next_stop_id = '%s'
			AND route_id = '%s'",
			$stop_id,
			$route_id);
		$res_avg_time = $mysqli->query($q_r);

		//see if query worked
		if ($route = $res_avg_time->fetch_object()) {
			return $route->avg_time_to_next_stop;
		}
		else {
			return NULL;
		}
	}

//# Import environment settings from DotCloud
	$envjson = json_decode(file_get_contents("/home/dotcloud/environment.json"),true);

	//get http vars
	$stop_id = $_GET['s'];
	$route_id = $_GET['r'];

	// Create MySQL Connection
    $mysqli = new mysqli($envjson['DOTCLOUD_DB_MYSQL_HOST'],
                     'hjc1710',         # username
                     's1lw2y44',        # password
                     'jagtrack',        # db name
                     $envjson['DOTCLOUD_DB_MYSQL_PORT']);
	
	//query for buses on this route
	$q_bus = sprintf("SELECT * FROM jt_bus WHERE current_route_id = '%s'",
      $route_id);
	$res = $mysqli->query($q_bus);
	
	
	$buses = array();
	
	//bool for later logic
	$done = FALSE;
	
	//get those buses!
	while ($b = $res->fetch_object()) {
		//if this bus is going to the next stop, then we're done!
		if ($b->next_stop_id == $stop_id) {
			//indicate we're done
			$done = TRUE;
			
			//figure out avg time for this piece of the route
			$avg = get_avg_time($stop_id, $route_id, $mysqli);
			
			//see if query worked
			if ($avg !== NULL) {
				print(json_encode($avg));
			}
			else {
				//throw error
				print('error!!!');
			}		
		}
		
		//add bus to array
		$buses[] = $b;
	}
	
	//if not done, "climb the bridge table" until we can get an accurate avg
	//time
	if (! $done) {
		
		//get every stop on this route
		$get_bridge = sprintf("SELECT * FROM jt_route_stop WHERE route_id = '%s'",
		  $route_id);
		$bridge_res = $mysqli->query($get_bridge);
		
		//store stops in an array
		$stops = array();
		while ($stop = $bridge_res->fetch_object()) {
			$stops[] = $stop;
		}
		
		//set stop we want
		$desired_stop_id = $stop_id;
		$avg = 0;
		
		$done = FALSE;
		
		//do logic
		while (! $done) {
			
			//see what 
			for ($i = 0; $i < count($stops); $i++) {
				
				//this is the previous stop in the chain! look for a bus going
				//here and stop 
				if ($stops[$i]->next_stop_id == $desired_stop_id) {
					//increment average
					$avg += $stops[$i]->avg_time_to_next_stop;
					
					//go through every bus
					for ($j = 0; $j < count($buses); $j++) {
						
						//if bus' next stop id is the one we want, we can stop
						//after this
						if ($buses[$j]->next_stop_id == $desired_stop_id) {
							$done = TRUE;
							break;
						}
					}
					if ($done) {
						break;
					}
					
					//change the desired stop id
					$desired_stop_id = $stops[$i]->stop_id;
				}
			}
		}
		//print the data
		print(json_encode($avg));
	}
?>
