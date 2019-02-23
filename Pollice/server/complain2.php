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
	$email=$_POST['email'];
	//$lat=$_POST['latitude'];
	// $lon=$_POST['longitude'];
	
	$complainAddress=$_POST['complainAddress'];
	$complainCuse=$_POST['complainCuse'];
	$complainDescription=$_POST['complainDescription'];
	$currentTime=$_POST['currentTime'];
	
	/*
	$email="raihanafroz9@gmail.com";
	$name="";
	$complainAddress="Dhaka";
	$complainCuse="Nothing";
	$complainDescription="1234567890";
	$currentTime="12-12-2019 12:12:12";
	*/
	
	
	$lat=23.743210;
	
	$lon= 90.393531;
	
	$nearableThana=array("","","","","","");
	$sql_check= "SELECT `thanaName`, `latitude`, `longitude`, `phoneNo` ,`thanaId` FROM `tbl_station_thana`";
	
	$query=mysqli_query($connect, $sql_check);
	if($query){	
		while($row=mysqli_fetch_array($query)){
			$distence=calculateDistanceInMeter($lat, $lon, $row["latitude"], $row["longitude"]);
            if($nearableThana[0]==""){
                $nearableThana[0]= $distence; 
                $nearableThana[1]= $row["thanaName"];
                $nearableThana[2]= $row["latitude"];
                $nearableThana[3]= $row["longitude"];
                $nearableThana[4]= $row["phoneNo"];
                $nearableThana[5]= $row["thanaId"];
            }else{
                if($distence<$nearableThana[0]){
                    $nearableThana[0]= $distence; 
					$nearableThana[1]= $row["thanaName"];
					$nearableThana[2]= $row["latitude"];
					$nearableThana[3]= $row["longitude"];
					$nearableThana[4]= $row["phoneNo"];
					$nearableThana[5]= $row["thanaId"];
                }
            }
        }
    }
	
	//echo "<Br>".$nearableThana[0]." / ".$nearableThana[1]." / ".$nearableThana[2]." / ".$nearableThana[3]." / ".$nearableThana[4]."<br>";
	//echo "<br>";echo "<br>";echo "<br>";echo "<br>";
	
	
	
	
	// getting user name 
	
	$sql_get_name= "SELECT `first_name`, `last_name`FROM `user_info` WHERE `e-mail`='$email'";
	
	$query=mysqli_query($connect, $sql_get_name);
	if($query){	
		while($row=mysqli_fetch_array($query)){
			$name=$row['first_name']." ".$row['last_name'];
		}
	}
	
	//database date formate
	if($currentTime==""){
		$complainTime="0000-00-00 00:00:00";
	}else{
		$complainTime = date ("Y-m-d h:i:s a", strtotime($currentTime));
	}
	
	
	
	
	
	//insert complain2 into database
	
	
	/*$sql_check="SELECT COUNT(`email`) FROM `tbl_complain1` WHERE `email` LIKE '$email';";
	
	$query=mysqli_query($connect, $sql_check);
	if($query)
    {	
		$complainNo=0;
		
		while ($row=mysqli_fetch_row($query)){
			$complainNo=$row[0];
		}
		
		$complainNo+=1;
		
		$sql_insert="INSERT INTO `tbl_complain1` (`username`, `email`, `latitude`, `longitude`, `usercomplainNo`,`thanaId`, `complainTime`) VALUES ('$name', '$email', '$lat', '$lon', '$complainNo', '$nearableThana[5]', '$complainTime');";
		
		//echo $sql_insert;
		
		if(mysqli_query($connect, $sql_insert)){
			echo "Successfully Complained.";
		}
    }
	
	
	*/
	
	
	
	
	
	
	
	
	
	echo "Name: ".$name." <==> Email: ".$email." <==> Address: ".$complainAddress." <==> Cause: ".$complainCuse." <==> Description: ".$complainDescription." <==> DateTime: ".$complainTime;
	

	
	
	mysqli_close($connect);
	
	?>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	