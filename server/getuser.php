<?php

//Connect to the database
$con = mysql_connect("localhost","martin3","pickup");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }
mysql_select_db("martin3_pug", $con);

//Get variable passed in representing device
$phone = $_GET["phone"];

//Get user information from the device owner
$query = "Select u.* from users u, phones p where p.phone_id = '".$phone."' and p.user_id = u.id;";
$result = mysql_query($query) or die(mysql_error());

//If the device does not exist, create new user, rerun selection
if (mysql_num_rows($result) == 0) { 
   $query2 = "Insert into users(name, sport, age) values ('New User', 'Soccer', 20)";
   mysql_query($query2) or die(mysql_error());
   $query3 = "Select max(id) from users";
   $result = mysql_query($query3) or die(mysql_error());
   $row = mysql_fetch_array($result);
   $query4 = "Insert into phones values ('".$phone."',".$row[0]." )";
   mysql_query($query4) or die(mysql_error());
   $result = mysql_query($query) or die(mysql_error());
} 

//Create JSONObject with user information
while($row = mysql_fetch_array($result))
  {
  $arr = array("id" =>$row[0], "name" => $row[1], "favorite" => $row[2], "age"=>$row[3],"gender"=>$row[4]);
  }
  
//Encode the object
$earr = json_encode($arr);
echo $earr;

//Close the database connection
mysql_close($con);

?>