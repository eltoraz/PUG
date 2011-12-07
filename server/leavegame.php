<?php
//Database connection
$con = mysql_connect("localhost","martin3","pickup");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("martin3_pug", $con);

//Get inputed player and game
$user = $_GET["user"];
$game = $_GET["game"];

//Remove pair from DB, removing player from the game
$query = "Delete from playing where game=".$game." and player=".$user;
mysql_query($query)  or die ('Error: '.mysql_error ());

//Close DB connection
mysql_close($con);
?>