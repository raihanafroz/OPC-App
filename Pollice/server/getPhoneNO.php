<?php
	require 'init.php';
	
	$email=$_POST["user_email"];
	
	/*$email="raihanafroz9@gmail.com";*/
	
	$sql_insert="SELECT `phoneNo` FROM `tbl_station_thana` WHERE `thanaId` LIKE (SELECT `thanaId` FROM `tbl_complain1` WHERE `email`='$email' AND `usercomplainNo` =(SELECT COUNT(`usercomplainNo`) FROM `tbl_complain1` WHERE `email`='$email'));";
	$query=mysqli_query($connect,$sql_insert);
	if($query)
    {
        while($row=mysqli_fetch_array($query))
        {
            if($row['phoneNo']=="" || $row['phoneNo']==null){
				echo "Phone No available";
			}else{
				echo $row['phoneNo'];
			}
        }
    }
	mysqli_close($connect);
?>