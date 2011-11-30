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
  $arr[$i] = $row[0];
}

echo $arr;

mysql_close($con);
?> 