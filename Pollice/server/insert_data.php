<?php
	require 'init.php';
	
	/*$firseName="Raihan";
	$lastName="Topu";
	$email="raihan@gmail.com";
	$passrord="1111";
	$address="Dhaka";
	$gender="Male";
	$contactNmuber="01797325129";*/
	
	$firseName=$_POST['fname'];
	$lastName=$_POST['lname'];
	$email=$_POST['email'];
	$passrord=$_POST['pass'];
	$address=$_POST['address'];
	$gender=$_POST['gender'];
	$contactNmuber=$_POST['contactNumber'];
	$image=$_POST['Image'];
	$path="Profile_Pic/$email.jpg";
	
	$sql_insert="INSERT INTO user_info VALUES ('$firseName', '$lastName', '$email', '$passrord', '$address', '$gender', '$contactNmuber');";
	
	$sql_check="SELECT * FROM `user_info` WHERE `e-mail` LIKE '$email';";
	
	$query=mysqli_query($connect, $sql_check);
	if($query)
    {
		$row=mysqli_fetch_array($query);
		if($row==null){ //checking that e-mail used before or not
			if(mysqli_query($connect, $sql_insert)){
				file_put_contents($path, base64_decode($image));
				echo "Data Added.";
			}
		}else{
			echo "Duplicate e-mail";
		}
    }
	
	mysqli_close($connect);
	
	?>