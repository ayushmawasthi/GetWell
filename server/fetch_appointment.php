<?php
$prob=filter_input(INPUT_POST,"spec");



$mysqli=new mysqli("fdb28.awardspace.net","3741169_user","Auchitya@1234","3741169_user");
$conn = $mysqli;

if ($conn->connect_error) {
   die("Connection failed: " . $conn->connect_error);
}
 
$sql = "SELECT name from patient WHERE problem LIKE '$prob'";

$result = mysqli_query($conn,$sql);

while($row = mysqli_fetch_array($result))  
  {  

  echo $row[0].","; 
 
  }
  echo "00";


$sql1 = "SELECT phone from patient WHERE problem LIKE '$prob'";

$result1 = mysqli_query($conn,$sql1);

while($row1 = mysqli_fetch_array($result1))  
  {  
       $contact=$row1[0];

       $sql2="SELECT date from appointment WHERE pat_con LIKE '$contact'";
       $result2 = mysqli_query($conn,$sql2);
       while($row2 = mysqli_fetch_array($result2))  
       {  
          echo $row2[0].","; 
       }
  }


$conn->close();
?>