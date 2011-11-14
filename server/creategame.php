<?php


$con = mysql_connect("localhost","martin3","pickup");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("martin3_pug", $con);

$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);

$date = $jsonObj['datetime'];
$sport = $jsonObj['sport'];
$descr = $jsonObj['descr'];
$loc = $jsonObj['location'];
$user = $jsonObj['creator'];
$count = $jsonObj['playercount'];
$private = $jsonObj['private'];

$query = "Select l.id from locations l where l.lat = ".$loc['lat']." and l.lon=".$loc['lon']."";
$result = mysql_query($query)  or die ('Error: '.mysql_error ());

while($row = mysql_fetch_array($result))
{ 
  $loc_id = $row[0];
}
if($loc_id == null){
  $query = "Insert into locations(name, lat, lon) values ('".$loc['name']."',".$loc['lat'].",".$loc['lon'].")";
  mysql_query($query) or die ('Error: '.mysql_error ());
  
  $query = "Select l.id from locations l where l.lat = ".$loc['lat']." and l.lon=".$loc['lon']."";
  $loc_id_rsc = mysql_query($query) or die ('Error: '.mysql_error ());
  $loc_id = mysql_result($loc_id_rsc, 0);
}

$query = "Select u.id from users u where u.name = '".$user['name']."'";
$result2 = mysql_query($query) or die ('Error: '.mysql_error ());

while($row2 = mysql_fetch_array($result2))
{ 
  $user_id = $row2[0];
}
if($user_id == null){
  $query = "Insert into users(name) values ('".$user['name']."')";
  mysql_query($query) or die ('Error: '.mysql_error ());
  
  $query = "Select u.id from users u where u.name = '".$user['name']."'";
  $user_id_rsc = mysql_query($query)  or die ('Error: '.mysql_error ());
  $user_id = mysql_result($user_id_rsc, 0);
}

$query = "Insert into games(sport, descr, creator, location, datetime, count, private) values";
$query = $query . "('".$sport."','".$descr."',".$user_id.",".$loc_id.",'".$date."',".$count.",".$private.")";
mysql_query($query)  or die ('Error: '.mysql_error ());

mysql_close($con);
?> 
