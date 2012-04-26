<?php
//# Import environment settings from DotCloud
	$envjson = json_decode(file_get_contents("/home/dotcloud/environment.json"),true);

	$stop_id = $_GET['s'];
	$route_id = $_GET['r'];

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
	
	$done = FALSE;
	
	//print_r(mysql_fetch_assoc($res));
	
	while ($b = $res->fetch_object()) {
		//print 'in';
		if ($b->next_stop_id == $stop_id) {
			$done = TRUE;
			$q_r = sprintf("
				SELECT 
				  avg_time_to_next_stop 
				FROM jt_route_stop
				WHERE next_stop_id = '%s'
				AND route_id = '%s'",
			  $stop_id,
			  $route_id);
			
			$res_avg_time = $mysqli->query($q_r);
			
			if ($route = $res_avg_time->fetch_object()) {
				$avg = $route->avg_time_to_next_stop;
				print(json_encode($avg));
			}
			else {
				//throw error
				print('error!!!');
			}		
		}
	}
	//print 'bad query';
?>
