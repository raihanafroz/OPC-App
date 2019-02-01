<?php
	require 'init.php';
	
	$email=$_POST["user_email"];
	$contactNumber=$_POST["user_contact"];
	
	/*$email="raihanafroz9@gmail.com";
	$contactNumber="0179732512";*/
	
	$sql_insert="SELECT * FROM `user_info` WHERE `e-mail` LIKE '$email' AND `contact_number` LIKE '$contactNumber';";
	$query=mysqli_query($connect, $sql_insert);
	if($query)
    {
		$row=mysqli_fetch_array($query);
		if($row==null){
			echo "Forget User Not Found";
		}else{
			echo "Forget User Found";
		}
    }
	
	mysqli_close($connect);
?>