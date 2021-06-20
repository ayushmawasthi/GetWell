
<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
$con=mysqli_connect("fdb28.awardspace.net","3741169_user","getwell4","3741169_user"); //$mysqli=new mysqli("fdb30.awardspace.net","3529874_vendors","FinalYear@4","3529874_vendors");
if ($conn->connect_error) {
   die("Connection failed: " . $conn->connect_error);
}
$name=$_POST['name'];
$phone=$_POST['phone'];
$pass=$_POST['password'];


$sq="SELECT * FROM Patient where Pat_Phone='".$phone."'";
$rj=mysqli_query($con,$sq);
$row = mysqli_fetch_assoc($rj);
	if($row)
        {
        echo("exit");
        }
        else
        {
        
	$sql="INSERT INTO Patient(Pat_Name,Pat_Phone,Pat_Password) VALUES ('".$name."','".$phone."','".$pass."')";
	if(mysqli_query($con,$sql)){
	echo("1");


	}else
        {        
	echo("Error");
	}
}



}