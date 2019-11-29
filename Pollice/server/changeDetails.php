<?php
	require 'init.php';
	
	$id=$_POST["id"];
	$fname=$_POST["user_firstName"];
	$lname=$_POST["user_lastName"];
	$contactNumber=$_POST["user_contactNumber"];
	$address=$_POST["user_address"];
	/*$email="aa";
	$fname="Rony";
	$lname="Khan";
	$contactNumber="012345678901";
	$address="Dhaka";*/
	

		$sql_insert="UPDATE `user_info` SET `first_name`='$fname',`last_name`='$lname',`address`='$address',`contact_number`='$contactNumber' WHERE  `id` LIKE '$id';";
		
		if(mysqli_query($connect, $sql_insert)){
			echo "Successfully Change";
		}else{
			echo "Nothing change";
		}
	
	mysqli_close($connect);
?>