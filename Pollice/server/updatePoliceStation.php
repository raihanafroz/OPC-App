<?php
	require 'init.php';
	
	if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $name=$_POST['name'];
        $phone=$_POST['phone'];
        $latitude=$_POST['latitude'];
        $longitude=$_POST['longitude'];
        $id=$_POST['id'];
	
		
		$sql_insert="UPDATE `tbl_station_thana` SET `thanaName` = '$name', `latitude` = '$latitude', `longitude` = '$longitude', `phoneNo` = '$phone' 
					WHERE `thanaId` = '$id';";
		if(mysqli_query($connect, $sql_insert)){
			echo "Successfully Change";
		}else{
			echo "Nothing change";
		}	
	}else{
		echo "Access denied";
	}
	
	mysqli_close($connect);
?>