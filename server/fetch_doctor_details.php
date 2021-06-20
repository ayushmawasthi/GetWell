<?php
$prob=filter_input(INPUT_POST,"phone");



$mysqli=new mysqli("fdb28.awardspace.net","3741169_user","Auchitya@1234","3741169_user");
$conn = $mysqli;

if ($conn->connect_error) {
   die("Connection failed: " . $conn->connect_error);
}
 


$sql1 = "SELECT doc_con from appointment WHERE pat_con LIKE '$prob'";

$result1 = mysqli_query($conn,$sql1);

while($row1 = mysqli_fetch_array($result1))  
  {  
       $contact=$row1[0];

       $sql2="SELECT name,locality,address,locx,locy from doctor WHERE contact LIKE '$contact'";
       $result2 = mysqli_query($conn,$sql2);
       while($row2 = mysqli_fetch_array($result2))  
       {  
          echo $row2[0].",";
          echo $row2[1].",";
          echo $row2[2].",";
          echo $row2[3].",";
          echo $row2[4].",";
       }
  }


$conn->close();
?>