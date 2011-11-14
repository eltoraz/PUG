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
$query = "Select * from phones where phone_id = '".$phone."';";
$result = mysql_query($query) or die(mysql_error());

//If the device does not exist, create new user, rerun selection
if (mysql_num_rows($result) == 0) { 
  $arr = array("exists" => "false");
} 
else{
  $arr = array("exists"=>"true");
}
  
//Encode the object
$earr = json_encode($arr);
echo $earr;

//Close the database connection
mysql_close($con);

?>