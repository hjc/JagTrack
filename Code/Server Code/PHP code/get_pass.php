<?php

	$bus_id = $_GET['b'];
	
	$envjson = json_decode(file_get_contents("/home/dotcloud/environment.json"),true);

	// Create MySQL Connection
    $mysqli = new mysqli($envjson['DOTCLOUD_DB_MYSQL_HOST'],
                     'hjc1710',         # username
                     's1lw2y44',        # password
                     'jagtrack',        # db name
                     $envjson['DOTCLOUD_DB_MYSQL_PORT']);
	
	$q_bus = sprintf("SELECT * FROM jt_bus WHERE current_route_id = '%s'",
      $route_id);
	
	//echo $q_bus;
	
	$res = $mysqli->query($q_bus);
	
	
	if ($bus = $res->fetch_object()) {
		$pass = $bus->avg_time_to_next_stop;
		print(json_encode($pass));
	}
	else {
		//throw error
		print('error!!!');
	}		

?>
