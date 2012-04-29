<?php

	/**
	 *  @file Gets the number of passengers for a given bus. Pass in the bus id via
	 *   the URL (pass in the database ID) and then get the number of current
	 *   passengers returned via a JSON object.
	 * 
	 * @param $_GET['b']
	 *   The b parameter should be passed through the HTTP request in the URL (in
	 *   a GET fashion). It should contain the bus id we want to find the current
	 *   number of passengers for. This should be the database ID, but can be
	 *   easily adapted to use the bus numbers.
	 * 
	 * @return JSON Current Passengers
	 *   Return the current number of passengers on a given bus.
	 */

	//get bus param
	$bus_id = $_GET['b'];
	
	if (! is_numeric($bus_id)) {
		echo "error!!";
	}
	
	//dotCloud stuff
	$envjson = json_decode(file_get_contents("/home/dotcloud/environment.json"),true);

	// Create MySQL Connection
    $mysqli = new mysqli($envjson['DOTCLOUD_DB_MYSQL_HOST'],
                     'hjc1710',         # username
                     's1lw2y44',        # password
                     'jagtrack',        # db name
                     $envjson['DOTCLOUD_DB_MYSQL_PORT']);
	
	
	//get bus
	$q_bus = sprintf("SELECT * FROM jt_bus WHERE bus_id = '%s'",
      $bus_id);
	$res = $mysqli->query($q_bus);
	
	//try to fetch object from query, return passenger count if we get it, throw
	//error if it failed
	if ($bus = $res->fetch_object()) {
		$pass = $bus->passenger_count;
		
		//JSON it back!
		print(json_encode($pass));
	}
	else {
		//throw error
		print('error!!!');
	}		
?>
