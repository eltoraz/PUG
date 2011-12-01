<?php

$con = mysql_connect("localhost","martin3","pickup");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("martin3_pug", $con);

$user = $_GET["user"];
$game = $_GET["game"];

$query = "Delete from playing where game=".$game." and player=".$user;
mysql_query($query)  or die ('Error: '.mysql_error ());

mysql_close($con);
?>