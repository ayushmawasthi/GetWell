<?php
$lat=filter_input(INPUT_POST,"lat");
$long=filter_input(INPUT_POST,"long");
$phone=filter_input(INPUT_POST,"phone");
$local=filter_input(INPUT_POST,"locality");
$add=filter_input(INPUT_POST,"address");

$mysqli=new mysqli("fdb28.awardspace.net","3741169_user","Auchitya@1234","3741169_user");
$conn = $mysqli;

if ($conn->connect_error) {
   die("Connection failed: " . $conn->connect_error);
}
 
$sql = "UPDATE doctor SET locx='".$lat."' WHERE contact='".$phone."'";
if ($conn->query($sql) === TRUE) {
  echo "1";
} else {
  echo "0" . $conn->error;
}
$sql = "UPDATE doctor SET locy='".$long."' WHERE contact='".$phone."'";
if ($conn->query($sql) === TRUE) {
  echo "1";
} else {
  echo "0" . $conn->error;
}

$sql = "UPDATE doctor SET locality='".$local."' WHERE contact='".$phone."'";
if ($conn->query($sql) === TRUE) {
  echo "1";
} else {
  echo "0" . $conn->error;
}
$sql = "UPDATE doctor SET address='".$add."' WHERE contact='".$phone."'";
if ($conn->query($sql) === TRUE) {
  echo "1";
} else {
  echo "0" . $conn->error;
}

/*
$sql = "UPDATE doctor SET specialty='".$spec."' WHERE contact='".$phone."'";
if ($conn->query($sql) === TRUE) {
  echo "1";
} else {
  echo "0" . $conn->error;
}
*/
 
$conn->close();
?>