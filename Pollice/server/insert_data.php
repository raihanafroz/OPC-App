<?php
	require 'init.php';
	
	// $firseName="Raihan";
	// $lastName="Topu";
	// $email="raihan1@gmail.com";
	// $passrord="1111";
	// $address="Dhaka";
	// $gender="Male";
	// $contactNmuber="01797325129";
	$currentTime = date('Y-m-d H:i:s', (time() /*+ (	6 * 3600)*/));
	// echo $currentTime;
	
	$firseName=$_POST['fname'];
	$lastName=$_POST['lname'];
	$email=$_POST['email'];
	$passrord=$_POST['pass'];
	$address=$_POST['address'];
	$gender=$_POST['gender'];
	$contactNmuber=$_POST['contactNumber'];
	$image=$_POST['Image'];
	$path="Profile_Pic/$email.jpg";
	// $currentTime=$_POST['currentTime'];
	
	
	
	$sql_check="SELECT * FROM `user_info` WHERE `e-mail` LIKE '$email';";
	
	$query=mysqli_query($connect, $sql_check);
	if($query)
    {	
		$row=mysqli_fetch_array($query);
		if($row!=null){
			echo "Already have an account.";
		}else{
			// $sql_insert="INSERT INTO user_info VALUES ('$firseName', '$lastName', '$email', '$passrord', '$address', '$gender', '$contactNmuber','$currentTime');";

			$sql_insert="INSERT INTO `user_info`(`first_name`, `last_name`, `e-mail`, `password`, `address`, `gender`, `contact_number`, `createTime`) VALUES  ('$firseName', '$lastName', '$email', '$passrord', '$address', '$gender', '$contactNmuber','$currentTime');";
			// return;
			if(mysqli_query($connect, $sql_insert)){
				file_put_contents($path, base64_decode($image));
				echo "Data Added.";
			}else{
				// echo "Problem detected!!!Try again later.";
			echo $sql_insert;

			}
		}
    }else{
    	echo $query;
    }
	
	
	
	
	mysqli_close($connect);
	
	?>