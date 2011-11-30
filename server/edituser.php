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
$name = $jsonObj["name"];
$age = $jsonObj["age"];
$gender = $jsonObj["gender"];
$sport = $jsonObj["favorite"];

mysql_query("Update users set name='".$name."',age=".$age.",sport='".$sport."',
	gender='".$gender."' where id = ".$id."") or die ('Error: '.mysql_error ());

mysql_close($con);
?> 