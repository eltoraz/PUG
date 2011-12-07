<?php


$con = mysql_connect("localhost","martin3","pickup");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("martin3_pug", $con);

$game = $_GET["game"];

$result = mysql_query("Select player from playing where game = ".$game."")
  or die ('Error: '.mysql_error ());

//Loop through and create JSONObject
$i = 0;
$arr = array();
while($row = mysql_fetch_array($result))
{
  $arr[$i] = $row[0];
}

$earr = json_encode($arr);
echo $earr;

mysql_close($con);
?> 