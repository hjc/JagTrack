<?php
	function get_bus_avg($stop_id, $route_id, $mysqli) {
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
			$avg = get_bus_avg($stop_id, $route_id, $mysqli);
			
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
				//here and
				if ($stops[$i]->next_stop_id == $desired_stop_id) {
					//print 'in if';
					$avg += $stops[$i]->avg_time_to_next_stop;
					//print $avg; print '<br>';
					
					//print_r($stops[$i]);
					
					//print '<br>';
					
					for ($j = 0; $j < count($buses); $j++) {
						//print_r($buses[$j]);
						//print "<br>";
						if ($buses[$j]->next_stop_id == $desired_stop_id) {
							//print "breaking<br>";
							//print json_encode($buses[$j]);
							$done = TRUE;
							break;
						}
					}
					
					if ($done) {
						break;
					}
					
					$desired_stop_id = $stops[$i]->stop_id;
				}
			}
		}
		print(json_encode($avg));
	}
	//print 'bad query';
?>
