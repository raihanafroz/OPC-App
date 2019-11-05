<?php
	require 'init.php';

	$sql_insert="SELECT `thanaName`,`thanaId` FROM `tbl_station_thana` WHERE `phoneNo` != '';";
	$query=mysqli_query($connect,$sql_insert);
	if($query)
    {
        while($row=mysqli_fetch_array($query))
        {
            $flag[]=$row;
        }
        echo json_encode($flag);
//        return $flag;
    }
	
	mysqli_close($connect);
?>