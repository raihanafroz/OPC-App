<?php

    function calculateDistanceInMeter($userLat, $userLng, $venueLat, $venueLng) {
		$earthRadius= 6371;
        $latDistance = deg2rad($userLat - $venueLat);
		$lngDistance = deg2rad($userLng - $venueLng);
        $a = sin($latDistance / 2) * sin($latDistance / 2) + cos(deg2rad($userLat)) * cos(deg2rad($venueLat))  * sin($lngDistance / 2) * sin($lngDistance / 2);
        $c = (double) (atan2(sqrt($a), sqrt(1 - $a)) * 2);
        $dist= (double) ($earthRadius * $c);
        return (double)($dist*1000);
    }
	
	
	//echo calculateDistanceInMeter(23.748791, 90.407925, 23.745631, 90.406094);
	 
	require 'init.php';
	
	
	
	$name="";
 	$thanaId="";
	$email=$_POST['email'];
	$thanaName=$_POST['thanaName'];
	$lat=$_POST['latitude'];
	$lon=$_POST['longitude'];
	$complainAddress=$_POST['complainAddress'];
	$complainCuse=$_POST['complainCuse'];
	$complainDescription=$_POST['complainDescription'];
	$currentTime=$_POST['currentTime'];
	

	// $email="raihanafroz9@gmail.com";
	// $name="";
	// $thanaId="";
	// $thanaName="Jatrabari Thana";
	// $complainAddress="Dhaka";
	// $complainCuse="Nothing";
	// $complainDescription="1234567890";
	// $currentTime="12-12-2019 12:12:12";

	
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
			$name=$row['first_name']." ".$row['last_name'];
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
	
	
	$sql_check="SELECT COUNT(`email`) FROM `tbl_complain2` WHERE `email` LIKE '$email';";
	
	$query=mysqli_query($connect, $sql_check);
	// echo $query;
	if($query)
    {	
		$complainNo=0;
		
		while ($row=mysqli_fetch_row($query)){
			$complainNo=$row[0];
		}
		
		$complainNo+=1;
		
		$sql_insert="INSERT INTO `tbl_complain2` (`userName`, `email`, `cause`, `description`, `currentAddress`, `usercomplainNo`, `latitude`, `longitude`, `thanaId`, `complainTime`) VALUES ('$name', '$email', '$complainCuse', '$complainDescription', '$complainAddress', '$complainNo', '$lat', '$lon', '$thanaId', '$complainTime');";
		
		// echo $sql_insert;
		
		if(mysqli_query($connect, $sql_insert)){
			$output = "Successfully Complained";
		}else{$output = "Error";}
    }

	mysqli_close($connect);
	echo $output;
	?>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	