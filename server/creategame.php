<?php


$con = mysql_connect("localhost","martin3","pickup");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("martin3_pug", $con);

$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);

$id = $jsonObj["id"];
$date = $jsonObj["datetime"];
$sport = $jsonObj['sport'];
$descr = $jsonObj['descr'];
$loc = $jsonObj['location'];
$user = $jsonObj['creator'];
$count = $jsonObj['playercount'];
$private = $jsonObj['private'];

$user_id = $user["id"];

if($id == -1){
	$query = "Select l.id from locations l where l.lat = ".$loc['lat']." and l.lon=".$loc['lon']."";
	$result = mysql_query($query)  or die ('Error in select: '.mysql_error());
	
	while($row = mysql_fetch_array($result))
	{ 
	  $loc_id = $row[0];
	}
	if($loc_id == null){
	  $query = "Insert into locations(name, lat, lon) values ('".$loc['name']."',".$loc['lat'].",".$loc['lon'].")";
	  mysql_query($query) or die ('Error in loc insert: '.mysql_error ());
	  
	  $query = "Select l.id from locations l where l.lat = ".$loc['lat']." and l.lon=".$loc['lon']."";
	  $loc_id_rsc = mysql_query($query) or die ('Error: '.mysql_error ());
	  $loc_id = mysql_result($loc_id_rsc, 0);
	}
	

	
	$query = "Insert into games(sport, descr, creator, location, datetime, count, private) values";
	$query = $query . "('".$sport."','".$descr."',".$user_id.",".$loc_id.",'".$date."',".$count.",'".$private."')";
	mysql_query($query)  or die ('Error in game insert: '.mysql_error ().$query);
	
	$query = "Select max(id) from games where creator = ".$user_id;
	$result2 = mysql_query($query) or die ('Error in game id select: '.mysql_error ());

	$game_id = mysql_result($result2, 0);
	

	$query = "Insert into playing(game, player) values (".$game_id.",".$user_id.")";
	mysql_query($query) or die ('Error: '.mysql_error ());
	
	
}
else{
	$query = "Select l.id from locations l where l.lat = ".$loc['lat']." and l.lon=".$loc['lon']."";
	$result = mysql_query($query)  or die ('Error in select: '.mysql_error());
	
	while($row = mysql_fetch_array($result))
	{ 
	  $loc_id = $row[0];
	}
	if($loc_id == null){
	  $query = "Insert into locations(name, lat, lon) values ('".$loc['name']."',".$loc['lat'].",".$loc['lon'].")";
	  mysql_query($query) or die ('Error in loc insert: '.mysql_error ());
	  
	  $query = "Select l.id from locations l where l.lat = ".$loc['lat']." and l.lon=".$loc['lon']."";
	  $loc_id_rsc = mysql_query($query) or die ('Error: '.mysql_error ());
	  $loc_id = mysql_result($loc_id_rsc, 0);
	}
	
	$query2 = "Update games set sport = '".$sport."', descr = '".$descr."', location =  ".$loc_id.", 
		datetime = '".$date."', count = ".$count." where id = ".$id."";
	mysql_query($query2);
}
	

mysql_close($con);
?> 
