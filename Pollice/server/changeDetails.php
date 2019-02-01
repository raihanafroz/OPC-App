<?php
	require 'init.php';
	
	$email=$_POST["user_email"];
	$fname=$_POST["user_firstName"];
	$lname=$_POST["user_lastName"];
	$contactNumber=$_POST["user_contactNumber"];
	$address=$_POST["user_address"];$email=$_POST["user_email"];
	/*$email="aa";
	$fname="Rony";
	$lname="Khan";
	$contactNumber="012345678901";
	$address="Dhaka";*/
	
	$count=0;
	
	if($fname!=null){
		$sql_insert="UPDATE `user_info` SET `first_name` = '$fname' WHERE `e-mail` LIKE '$email';";
		if(abcd($connect,$sql_insert)){
			$count=$count+1;
		}
	}
	if($lname!=null){
		$sql_insert="UPDATE `user_info` SET `last_name` = '$lname' WHERE `e-mail` LIKE '$email';";
		if(abcd($connect,$sql_insert)){
			$count=$count+1;
		}
	}
	if($contactNumber!=null){
		$sql_insert="UPDATE `user_info` SET `contact_number` = '$contactNumber' WHERE `e-mail` LIKE '$email';";
		if(abcd($connect,$sql_insert)){
			$count=$count+1;
		}
	}
	if($address!=null){
		$sql_insert="UPDATE `user_info` SET `address` = '$address' WHERE `e-mail` LIKE '$email';";
		if(abcd($connect,$sql_insert)){
			$count=$count+1;
		}
	}
	function abcd($connect,$sql){
		if(mysqli_query($connect, $sql)){
				return true;
		}
		return false;
	}
	if($count===0){
		echo "Nothing change";
	}else{
		echo "Successfully Change";
	}
	
	mysqli_close($connect);
?>