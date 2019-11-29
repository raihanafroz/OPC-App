<?php
	require 'init.php';
	
	$email=$_POST["user_email"];
	$contactNumber=$_POST["user_contact"];
	
	// $email="topu@gmail.com";
	// $contactNumber="01310329575";
	
	$sql_insert="SELECT * FROM `user_info` WHERE `e-mail` LIKE '$email' AND `contact_number` LIKE '$contactNumber';";
	$query=mysqli_query($connect, $sql_insert);
	if($query)
    {
    	$count = 0;
		while($row=mysqli_fetch_array($query))
        {
        	$count = 1;
            $flag[]=$row;
        }
		if($count == 1){
	        print(json_encode($flag));
	    }else{
	    	echo "null";
	    }
    }
	
	mysqli_close($connect);
?>