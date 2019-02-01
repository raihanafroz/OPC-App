<?php
	require 'init.php';
	
	$email=$_POST["user_email"];
	
	/*$email="raihanafroz9@gmail.com";*/
	
	$sql_insert="SELECT * FROM `user_info` WHERE `e-mail` LIKE '$email';";
	$query=mysqli_query($connect,$sql_insert);
	if($query)
    {
        while($row=mysqli_fetch_array($query))
        {
            $flag[]=$row;
        }
		//if($row==null)
        print(json_encode($flag));
    }
	
	mysqli_close($connect);
?>