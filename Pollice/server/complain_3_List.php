<?php
	require 'init.php';
	
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
    	// $email = "topu@gmail.com";
        $email=$_POST['email'];
        $where = "";
        if($email == null || $email == ""){
        	$where = ";";
        }else{
        	$where = "WHERE `m`.`email` = '$email'";
        }
	
		$sql_insert="SELECT `m`.`id`, `m`.`userName`, `m`.`email`, `m`.`latitude`, `m`.`longitude`, `m`.`cause`, `m`.`complainAddress` As `complainAddress`, `m`.`description`, `m`.`name` As `otherName`, `m`.`phone` As `otherPhone`, `m`.`address` As `otherAddress`, `m`.`complainTime`, `m`.`status`, `c`.`thanaName`, `c`.`thanaId`, `d`.`address`, `d`.`gender`, `d`.`contact_number`, `d`.`first_name`, `d`.`last_name` FROM `tbl_complain3` `m` INNER JOIN `tbl_station_thana` `c` ON `c`.`thanaId` = `m`.`thanaId` INNER JOIN `user_info` `d` ON `d`.`e-mail` = `m`.`email`".$where;
		$query=mysqli_query($connect,$sql_insert);
		if($query)
	    {
	        while($row=mysqli_fetch_array($query))
	        {
	            $flag[]=$row;
	        }
	        print(json_encode($flag));
	        // echo $email;
	    }
	}else{
		echo "Access denied";
	}
	mysqli_close($connect);
?>