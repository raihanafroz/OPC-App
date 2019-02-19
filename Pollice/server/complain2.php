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
	
	
	
	/*$name=$_POST['name'];
	$email=$_POST['email'];
	$lat=$_POST['latitude'];
	$lon=$_POST['longitude'];
	*/
	//$email=1;
	
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
	
	echo "<Br>".$nearableThana[0]." / ".$nearableThana[1]." / ".$nearableThana[2]." / ".$nearableThana[3]." / ".$nearableThana[4]."<br>";
	//echo "<br>";echo "<br>";echo "<br>";echo "<br>";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	mysqli_close($connect);
	
	?>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	