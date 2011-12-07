<?php
//Database connection
$con = mysql_connect("localhost","martin3","pickup");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("martin3_pug", $con);

//Get inputed user and game
$user = $_GET["user"];
$game = $_GET["game"];

//Add pair to DB, joining player to game
$query = "Insert into playing values(".$game.", ".$user.")";
mysql_query($query)  or die ('Error: '.mysql_error ());

//Close DB connection
mysql_close($con);
?>