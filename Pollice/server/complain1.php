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
	
	/*$firseName="Raihan";
	$lastName="Topu";
	$email="raihan@gmail.com";
	$passrord="1111";
	$address="Dhaka";
	$gender="Male";
	$contactNmuber="01797325129";*/
	
	$name=$_POST['userName'];
	$email=$_POST['email'];
	$lat=$_POST['latitude'];
	$lon=$_POST['longitude'];
	
	
	
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
		
		$sql_insert="INSERT INTO `tbl_complain1` (`username`, `email`, `latitude`, `longitude`, `usercomplainNo`,`thanaId`) VALUES ('$name', '$email', '$lat', '$lon', '$complainNo', '$nearableThana[5]');";
		
		//echo $sql_insert;
		
		if(mysqli_query($connect, $sql_insert)){
			echo "Successfully Complained.";
		}
    }
	
	mysqli_close($connect);
	
	?>