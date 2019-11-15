<?php
	require 'init.php';
	
	$email=$_POST["user_email"];
	$pass=$_POST["user_password"];
	
	//$email="a@a.com";
	//$pass="0cc175b9c0f1b6a831c399e269772661";
	//$pass="a";
	
	$sql_insert="SELECT * FROM `user_info` WHERE `e-mail` LIKE '$email' AND `password` LIKE '$pass';";
	$query=mysqli_query($connect,$sql_insert);
	if($query)
    {
		$row=mysqli_fetch_array($query);
        
		if($row==null){
			print("Failed");
		}else{
			$flag[]=$row;
			print(json_encode($flag));
		}
    }else{
        print("Failed");
    }
	
	mysqli_close($connect);
?>