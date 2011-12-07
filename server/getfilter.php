<?php

//Database connection
$con = mysql_connect("localhost","martin3","pickup");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("martin3_pug", $con);

//Get variables passed in
$lat = $_GET["lat"];
$lon = $_GET["lon"];
$dist = $_GET["dist"];
$sport = $_GET["sport"];
$user = $_GET["user"];

//Create base query
$query = "Select g.*, l.*, u.* 
from games g, locations l, users u 
where g.location = l.id and g.creator = u.id";

//Check for distance filter
if(isset($dist)){
	$query = $query . " and ((l.lat + ($dist * 19618)) >".$lat." AND (l.lat - ($dist * 19618))<".$lat.")";
	$query = $query . " and ((l.lon + ($dist * 19618)) >".$lon." AND (l.lon - ($dist * 19618))<".$lon.")";
}

//Check for user filter
if(isset($user)){
	$query = $query . " and u.name = '".$user."'";
}

//Check for sport filter
if(isset($sport)){
	$query = $query . " and lower(g.sport) like lower('".$sport."')";
}

//Get result set of full query
$result = mysql_query($query) or die(mysql_error());

//Loop through and create JSONObject
$i = 0;
$arr = array();
while($row = mysql_fetch_array($result))
  {
  $arr[$i] = array("id" => $row[0],
    "sport" => $row[1], 
    "descr" => $row[2], 
    "creator"=>array("id"=>$row[3],
    	"name"=>$row[13], 
    	"favorite"=>$row[14], 
    	"age"=>$row[15], 
    	"gender"=>$row[16]), 
    "owner"=>array("id"=>$row[3],
    	"name"=>$row[13], 
    	"favorite"=>$row[14], 
    	"age"=>$row[15], 
    	"gender"=>$row[16]),
    "location"=>array("id"=>$row[4],
    	"name"=>$row[9],
    	"lat"=>$row[10], 
    	"lon"=>$row[11]),
    "datetime"=>$row[5], 
    "playercount"=>$row[6], 
    "private"=>$row[7]);
  $i++;
  }

//Encode and output JSONObject
$earr = json_encode($arr);
echo $earr;

//Close DB connection
mysql_close($con);
?>