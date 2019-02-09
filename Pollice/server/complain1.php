<?php
	require 'init.php';
	
	/*$firseName="Raihan";
	$lastName="Topu";
	$email="raihan@gmail.com";
	$passrord="1111";
	$address="Dhaka";
	$gender="Male";
	$contactNmuber="01797325129";*/
	
	$name=$_POST['name'];
	$email=$_POST['email'];
	$lat=$_POST['latitude'];
	$lon=$_POST['longitude'];
	
	//$email=1;
	
	$sql_check="SELECT COUNT(`email`) FROM `tbl_complain1` WHERE `email` LIKE '$email';";
	
	$query=mysqli_query($connect, $sql_check);
	if($query)
    {	
		$complainNo=0;
		
		while ($row=mysqli_fetch_row($query)){
			$complainNo=$row[0];
		}
		
		$complainNo+=1;
		
		$sql_insert="INSERT INTO `tbl_complain1` (`name`, `email`, `latitude`, `longitude`, `complainNo`) VALUES ('$name', '$email', '$lat', '$lon', '$complainNo');";
		
		//echo $sql_insert;
		
		if(mysqli_query($connect, $sql_insert)){
			echo "Successfully Complained.";
		}
    }
	
	mysqli_close($connect);
	
	?>