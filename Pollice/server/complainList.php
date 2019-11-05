<?php
	require 'init.php';

    // $email="raihanafroz9@gmail.com";

    $email=$_POST['email'];

    $complain1;
    $complain2;
    $complain3;
    
    $sql_insert="SELECT `userName`, `email`, `latitude`, `longitude`, `usercomplainNo`, `thanaId`, `complainTime` FROM `tbl_complain1` WHERE `email`='$email';";
	$query=mysqli_query($connect,$sql_insert);
	if($query)
    {
        while($row=mysqli_fetch_array($query))
        {
            $flag[]=$row;
        }
        $complain1 = $flag;
        // echo json_encode($flag);
//        return $flag;
    }



    $sql_insert1="SELECT `id`, `userName`, `email`, `cause`, `description`, `currentAddress`, `usercomplainNo`, `thanaId`, `complainTime` FROM `tbl_complain2` WHERE `email`='$email';";
    $query1=mysqli_query($connect,$sql_insert1);
    if($query1)
    {
        while($row1=mysqli_fetch_array($query1))
        {
            $flag1[]=$row1;
        }
        $complain2 = $flag1;
        // echo json_encode($flag1);
//        return $flag;
    }


    $sql_insert2="SELECT `id`, `name`, `phone`, `address`, `cause`, `description`, `complainAddress`, `thanaId`, `email`, `userName`, `usercomplainNo`, `complainTime` FROM `tbl_complain3` WHERE `email`='$email';";
    $query2=mysqli_query($connect,$sql_insert2);
    if($query2)
    {
        while($row2=mysqli_fetch_array($query2))
        {
            $flag2[]=$row2;
        }
        $complain3 = $flag2;
        // echo json_encode($flag2);
//        return $flag;
    }

    $newArray = [];
    foreach ($complain1 as $key => $value) {
        $rowItem = [
            'type' => 'Immediate Complain',
            'email' => $value['email'],
            'cause' => '',
            'time' => $value['complainTime'],
            'complainNo' => $value['usercomplainNo'],
        ];
        $newArray[]=$rowItem;
    }


    foreach ($complain2 as $key => $value) {
        $rowItem1 = [
            'type' => 'My Complain',
            'email' => $value['email'],
            'cause' => $value['cause'],
            'time' => $value['complainTime'],
            'complainNo' => $value['usercomplainNo'],
        ];
        $newArray[]=$rowItem1;
    }


    foreach ($complain3 as $key => $value) {
        $rowItem2 = [
            'type' => 'Complain For Other',
            'email' => $value['email'],
            'cause' => $value['cause'],
            'time' => $value['complainTime'],
            'complainNo' => $value['usercomplainNo'],
        ];
        $newArray[]=$rowItem2;
    }

    echo json_encode($newArray);
	
	mysqli_close($connect);
?>