<?php
$phone=filter_input(INPUT_POST,"username");
$password=filter_input(INPUT_POST,"password");

$mysqli=new mysqli("fdb28.awardspace.net","3741169_user","getwell4","3741169_user");
$conn = $mysqli;

if ($conn->connect_error) {
   die("Connection failed: " . $conn->connect_error);
}
 if(isset($phone)) {


    $cell = (isset($phone)?
    $phone : null);
    $query=mysqli_query($mysqli,"SELECT * from Patient where Pat_Phone='$cell' LIMIT 1");
    $find=mysqli_num_rows($query);
    echo $find;
    if($find==0)
    {
    die("2");
    }
}
 


$result=mysqli_query($mysqli,"select * from Patient where Pat_Phone='".$phone."' and Pat_Password='".$password."'");

if($data=mysqli_fetch_array($result))
{
	echo '1';
	echo implode(',', $data);
}
else
{
    echo '0';
} 
$conn->close();
?>