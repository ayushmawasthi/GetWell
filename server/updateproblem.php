<?php
$prob=filter_input(INPUT_POST,"prob");
$phone=filter_input(INPUT_POST,"phone");


$mysqli=new mysqli("fdb28.awardspace.net","3741169_user","Auchitya@1234","3741169_user");
$conn = $mysqli;

if ($conn->connect_error) {
   die("Connection failed: " . $conn->connect_error);
}
 
$sql = "UPDATE patient SET problem='".$prob."' WHERE phone='".$phone."'";
if ($conn->query($sql) === TRUE) {
  echo "1";
} else {
  echo "0" . $conn->error;
}



 
$conn->close();
?>