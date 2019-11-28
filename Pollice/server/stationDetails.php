<?php
	require 'init.php';
	
//	$email=$_POST["user_email"];
	
	/*$email="raihanafroz9@gmail.com";*/
	
	$sql_insert="SELECT * FROM `tbl_station_thana` ORDER BY `thanaName`;";
	$query=mysqli_query($connect,$sql_insert);
	if($query)
    {
        while($row=mysqli_fetch_array($query))
        {
            $flag[]=$row;
        }
        print(json_encode($flag));
    }
	
	mysqli_close($connect);
?>