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
	
	require 'init.php';
	
	/*$name="";
	$email="1";
	$lat="0000000";
	$lon="0000000";
	$complainDate="";*/
	

	
	$name="";
	$email=$_POST['email'];
	$lat=$_POST['latitude'];
	$lon=$_POST['longitude'];
	$complainDate=$_POST['currentTime'];

	
	
	// getting nearable thana
	
	$nearableThana=array("","","","","","");
	$sql_check_thana= "SELECT `thanaName`, `latitude`, `longitude`, `phoneNo` ,`thanaId` FROM `tbl_station_thana`";
	
	$query=mysqli_query($connect, $sql_check_thana);
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
	
	// getting user name 
	
	$sql_get_name= "SELECT `first_name`, `last_name`FROM `user_info` WHERE `e-mail`='$email'";
	
	$query=mysqli_query($connect, $sql_get_name);
	if($query){	
		while($row=mysqli_fetch_array($query)){
			$name=$row['first_name']." ".$row['last_name'];
		}
	}
	
	//database date formate
	if($complainDate==""){
		$complainTime="0000-00-00 00:00:00";
	}else{
		$complainTime = date ("Y-m-d h:i:s a", strtotime($complainDate));
	}
	
	
	
	
	
	//insert complain into database
	
	
	$sql_check="SELECT COUNT(`email`) FROM `tbl_complain1` WHERE `email` LIKE '$email';";
	
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
	
	mysqli_close($connect);
	
	?>