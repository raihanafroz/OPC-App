<?php
	require 'init.php';
	
	$email=$_POST['user_email'];
	$image=$_POST['Image'];
	//$email="aaaa";
	$file="Profile_Pic/$email.jpg";
	
	if(file_exists($file)){
		unlink($file);
		file_put_contents($file, base64_decode($image));
		echo "File deleted";
	}else{
		echo "Data not found.";
	}
?>