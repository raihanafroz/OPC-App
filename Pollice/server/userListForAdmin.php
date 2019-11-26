<?php
	require 'init.php';

	$sql_insert="SELECT * FROM `user_info` WHERE `type` = 'User' ORDER BY `e-mail` ASC";
	$query=mysqli_query($connect,$sql_insert);
	if($query)
    {
        while($row=mysqli_fetch_array($query))
        {
            $flag[]=$row;
        }
        echo json_encode($flag);
    }
	
	mysqli_close($connect);
?>