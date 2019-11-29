<?php
	require 'init.php';
	
	$email=$_POST["user_email"];
	$oldPass=$_POST["user_oldPass"];
	$newPass=$_POST["user_newPass"];
	$id=$_POST["user_id"];
	
	// $email='topu@gmail.com';
	// $id='2';
	// // $oldPass="e10adc3949ba59abbe56e057f20f883e";
	// $oldPass="cc077e4074d58b5b3afe96921b220364";
	// $newPass='cc077e4074d58b5b3afe96921b220364';
	if($oldPass==null){
		$sql_insert="UPDATE `user_info` SET `password` = '$newPass' WHERE `e-mail` = '$email' AND `id` = '$id' ;";
	}else{
		$sql_insert="UPDATE `user_info` SET `password` = '$newPass' WHERE `e-mail` = '$email' AND `password` = '$oldPass';";

		$query=mysqli_query($connect, "SELECT * FROM `user_info` WHERE `e-mail` = '$email' AND `password` = '$newPass';");
		if($query)
	    {
			while($row=mysqli_fetch_array($query))
	        {
	        	echo "Same";
	        	return;
	        }
	    }
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