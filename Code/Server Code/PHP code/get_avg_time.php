<?php 
//
	$stop_id = $_GET['s'];
	$route_id = $_GET['r'];
	
	print_r($_GET);
	
	print 'in';
	
	echo "<p>in</p>";
	

	// Create MySQL Connection
    $mysqli = new mysqli($envjson['DOTCLOUD_DB_MYSQL_HOST'],
                     'hjc1710',         # username
                     's1lw2y44',        # password
                     'jagtrack',        # db name
                     $envjson['DOTCLOUD_DB_MYSQL_PORT']);
	
	$q_bus = sprintf("SELECT * FROM jt_bus WHERE route_id = '%s'",
      mysql_real_escape_string($route_id));
	
	$res = $mysqli->query($q_bus);
	
	$done = FALSE;
	
	while ($b = mysql_fetch_assoc($res)) {
		if ($b['next_stop_id'] == $stop_id) {
			$done = TRUE;
			$q_r = sprintf("
				SELECT 
				  avg_time_to_next_stop 
				FROM jt_route_stop
				WHERE next_stop_id = '%s'
				AND route_id = '%s'",
			  mysql_real_escape_string($stop_id),
			  mysql_real_escape_string($route_id));
			
			$res_avg_time = $mysqli->query($q_r);
			
			if ($route = mysql_fetch_assoc($res_avg_time)) {
				$avg = $route['avg_time_to_next_stop'];
				print(json_encode($avg));
			}
			else {
				//throw error
				print('error!!!');
			}		
		}
	}
	print 'bad query';
?>
