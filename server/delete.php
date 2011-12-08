<?php

$con = mysql_connect("localhost","martin3","pickup");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("martin3_pug", $con);

// Current timestamp, factoring in milliseconds
$timestamp = time() * 1000;

$result = mysql_query("select id from games where datetime < ".$timestamp)or
	die(mysql_error());

// Delete Games from the DB that have already passed.
while($row = mysql_fetch_array($result)){
	mysql_query("delete from playing where game = ".$row[0]) or
		 die("playing delete:".mysql_error());
	mysql_query("delete from games where id = ".$row[0]) or 
		die("games delete:".mysql_error());
}

mysql_close($con);

?>
