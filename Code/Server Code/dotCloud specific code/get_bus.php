<?php
    mysql_connect("host","username","password");
    mysql_select_db("PeopleData");

    # Create MySQL Connection
    $mysqli = new mysqli($envjson['DOTCLOUD_DB_MYSQL_HOST'],
                     'hjc1710',         # username
                     's1lw2y44',   # password
                     'jagtrack',       # db name
                     $envjson['DOTCLOUD_DB_MYSQL_PORT']);

     
    $q = $mysqli->query("SELECT * FROM people WHERE birthyear>'".$_REQUEST['year']."'");
    while($e = mysql_fetch_assoc($q))
            $output[] = $e;
     
    print(json_encode($output));
     
   # mysql_close();
?>

