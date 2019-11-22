<?php

    require "init.php";

    $result = [
            [
                "immediate_complain" => 0,
                "complain_for_me" => 0,
                "complain_for_others" => 0,
                "total_complain" => 0,
            ]
        ];

    
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $email=$_POST['email'];

        $complain1 = mysqli_query($connect,"SELECT * FROM tbl_complain1 WHERE `email` = '$email';" );
        $immediate_complain=mysqli_num_rows($complain1);

        $complain2 = mysqli_query($connect,"SELECT * FROM tbl_complain2 WHERE `email` = '$email';" );
        $complain_for_me=mysqli_num_rows($complain2);

        $complain3 = mysqli_query($connect,"SELECT * FROM tbl_complain3 WHERE `email` = '$email';" );
        $complain_for_others=mysqli_num_rows($complain3);

        $result = [
            [
                "immediate_complain" => $immediate_complain,
                "complain_for_me" => $complain_for_me,
                "complain_for_others" => $complain_for_others,
                "total_complain" => ($immediate_complain + $complain_for_me + $complain_for_others)
          ]
        ];

        print(json_encode($result));
    }else{
        echo "Access defined.";
    }




?>