<?php
	require 'init.php';
	
	$email=$_POST["user_email"];
	$oldPass=$_POST["user_oldPass"];
	$newPass=$_POST["user_newPass"];
	$contactNumber=$_POST["user_contactNumber"];
	
	/*$email='q';
	$contactNumber='55';
	$oldPass='11aaaa';
	$newPass='11';*/
	if($oldPass==null){
		$sql_insert="UPDATE `user_info` SET `password` = '$newPass' WHERE `e-mail` LIKE '$email' AND `contact_number` LIKE '$contactNumber' ;";
	}else{
		$sql_insert="UPDATE `user_info` SET `password` = '$newPass' WHERE `e-mail` LIKE '$email' AND `password` LIKE '$oldPass';";
	}
	$query=mysqli_query($connect, $sql_insert);
	if($query)
	{
		$row=mysqli_affected_rows($connect);
		if($row!=0){
			echo "Successfully";
		}else{
			echo "Sorry";
		}
	}
	
	mysqli_close($connect);
?>