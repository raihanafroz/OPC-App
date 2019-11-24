<?php
	 
	require 'init.php';
	
	$userName="";
	$thanaId="";
 	$thanaId="";
	$email=$_POST['email'];
	$thanaName=$_POST['thanaName'];
	$name=$_POST['name'];
	$phone=$_POST['phone'];
	$address=$_POST['address'];
	$complainCuse=$_POST['complainCuse'];
	$complainAddress=$_POST['complainAddress'];
	$complainDescription=$_POST['complainDescription'];
	$currentTime=$_POST['currentTime'];
	$lat=$_POST['latitude'];
	$lon=$_POST['longitude'];
	

	// $email="raihanafroz9@gmail.com";
	// $thanaName="Jatrabari Thana";
	// $name="abcd";
	// $phone="12345678900";
	// $address="Dhaka, Bangladesh";
	// $complainCuse="Nothing";
	// $complainAddress="Dhaka";
	// $complainDescription="1234567890";
	// $currentTime="12-12-2019 12:12:12";

	// $userName="";
	// $thanaId="";
	

	// echo $thanaName;
	// return 0;
	// $lat=23.743210;
	
	// $lon= 90.393531;
	
	// $nearableThana=array("","","","","","");


	$sql_check= "SELECT `thanaId` FROM `tbl_station_thana` WHERE `thanaName` = '$thanaName'";
	
	$query=mysqli_query($connect, $sql_check);
	if($query){	
		while($row=mysqli_fetch_array($query)){
			// print_r($row);
			$thanaId = $row['thanaId'];
        }
    }
	// echo $thanaId;
	
	// getting user name 
	
	$sql_get_name= "SELECT `first_name`, `last_name`FROM `user_info` WHERE `e-mail`='$email'";
	
	$query=mysqli_query($connect, $sql_get_name);
	if($query){	
		while($row=mysqli_fetch_array($query)){
			$userName=$row['first_name']." ".$row['last_name'];
		}
	}
	// echo $name;
	
	//database date formate
	if($currentTime==""){
		$complainTime="0000-00-00 00:00:00";
	}else{
		$complainTime = date ("Y-m-d H:i:s", strtotime($currentTime));
	}
	
	
	
	$output = "";
	
	//insert complain2 into database
	
	
	$sql_check="SELECT COUNT(`email`) FROM `tbl_complain3` WHERE `email` LIKE '$email';";
	
	$query=mysqli_query($connect, $sql_check);
	// echo $query;
	if($query)
    {	
		$complainNo=0;
		
		while ($row=mysqli_fetch_row($query)){
			$complainNo=$row[0];
		}
		
		$complainNo+=1;
		
		$sql_insert="INSERT INTO `tbl_complain3`(`name`, `phone`, `address`, `cause`, `description`, `complainAddress`, `thanaId`, `email`, `userName`, `complainTime`, `usercomplainNo`, `latitude`, `longitude`) VALUES ('$name','$phone', '$address', '$complainCuse', '$complainDescription', '$complainAddress', '$thanaId', '$email', '$userName', '$complainTime', '$complainNo', '$lat', '$lon');";
		
		// echo $sql_insert;
		// var_dump(mysqli_query($connect, $sql_insert));
		if(mysqli_query($connect, $sql_insert)){
			$output = "Successfully Complained";
		}else{$output = "Error";}
    }

	mysqli_close($connect);
	echo $output;
	?>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	