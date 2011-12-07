<?php

$con = mysql_connect("localhost","martin3","pickup");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("martin3_pug", $con);

$user = $_GET["user"];

$result = mysql_query("Select game from playing where player = ".$user."")
  or die ('Error: '.mysql_error ());

  //Loop through and create JSONObject
$i = 0;
$arr = array();
while($row = mysql_fetch_array($result))
  {
  $result2 = mysql_query("Select g.*, l.*, u.* from games g, locations l, users u 
			where g.id = ".$row[0]." and l.id = g.location 
			 and u.id = g.creator");
  while($row2 = mysql_fetch_array($result2)){
	  $arr[$i] = array("id" => $row2[0],
	    "sport" => $row2[1], 
	    "descr" => $row2[2], 
	    "creator"=>array("id"=>$row2[3],
	    	"name"=>$row2[13], 
	    	"favorite"=>$row2[14], 
	    	"age"=>$row2[15], 
	    	"gender"=>$row2[16]), 
	    "owner"=>array("id"=>$row2[3],
	    	"name"=>$row2[13], 
	    	"favorite"=>$row2[14], 
	    	"age"=>$row2[15], 
	    	"gender"=>$row2[16]),
	    "location"=>array("id"=>$row2[4],
	    	"name"=>$row2[9],
	    	"lat"=>$row2[10], 
	    	"lon"=>$row2[11]),
	    "datetime"=>$row2[5], 
	    "playercount"=>$row2[6], 
	    "private"=>$row2[7]);    
  }
  $i++;  
}

//Encode and output JSONObject
$earr = json_encode($arr);
echo $earr;

mysql_close($con);
?> 