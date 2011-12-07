<?php
//Database connection
$con = mysql_connect("localhost","martin3","pickup");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("martin3_pug", $con);

//Get input and decode
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);

//Get variables from input
$id = $jsonObj["id"];
$name = $jsonObj["name"];
$age = $jsonObj["age"];
$gender = $jsonObj["gender"];
$sport = $jsonObj["favorite"];

//Update user information with input
mysql_query("Update users set name='".$name."',age=".$age.",sport='".$sport."',
	gender='".$gender."' where id = ".$id."") or die ('Error: '.mysql_error ());

//Close DB connection
mysql_close($con);
?> 