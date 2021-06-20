
<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
$con=mysqli_connect("fdb28.awardspace.net","3741169_user","Auchitya@1234","3741169_user"); //$mysqli=new mysqli("fdb30.awardspace.net","3529874_vendors","FinalYear@4","3529874_vendors");
if ($conn->connect_error) {
   die("Connection failed: " . $conn->connect_error);
}
$pat_con=$_POST['pat_con'];
$phone=$_POST['doc_con'];
$date=$_POST['date'];



	
        
	$sql="INSERT INTO appointment(doc_con,date,pat_con) VALUES ('".$phone."','".$date."','".$pat_con."')";
        
	if(mysqli_query($con,$sql)){
	echo("1");
	}
        else
        {        
	echo("0");
	}




}