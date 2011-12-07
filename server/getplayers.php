<?php
//Database connection
$con = mysql_connect("localhost","martin3","pickup");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("martin3_pug", $con);

//Get inputed game
$game = $_GET["game"];

//Select all players in that game
$result = mysql_query("Select player from playing where game = ".$game."")
  or die ('Error: '.mysql_error ());

//Loop through and create JSONObject
$i = 0;
$arr = array();
while($row = mysql_fetch_array($result))
{
  $result2 = mysql_query("Select * from users where id = ".$row[0]."");
  while($row2 = mysql_fetch_array($result2)){
    $arr[$i] = array("id"=> $row2[0], 
    	"name"=>$row2[1], 
    	"age"=>$row2[2], 
    	"gender"=>$row2[3],
       	"favorite"=>$row2[4]
     );
  }
  $i++;
}

//Encode and output JSONObject
$earr = json_encode($arr);
echo $earr;

//Close DB connection
mysql_close($con);
?> 