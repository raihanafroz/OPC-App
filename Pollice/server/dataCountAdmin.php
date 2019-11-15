<?php

    require "init.php";

    $user_data = mysqli_query($connect,"SELECT * FROM user_info WHERE `type` = 'User';" );
    $number_of_user=mysqli_num_rows($user_data);

    $stations = mysqli_query($connect,"SELECT * FROM tbl_station_thana;" );
    $number_of_station=mysqli_num_rows($stations);

    $complain1 = mysqli_query($connect,"SELECT * FROM tbl_complain1;" );
    $immediate_complain=mysqli_num_rows($complain1);

    $complain2 = mysqli_query($connect,"SELECT * FROM tbl_complain2;" );
    $complain_for_me=mysqli_num_rows($complain2);

    $complain3 = mysqli_query($connect,"SELECT * FROM tbl_complain3;" );
    $complain_for__others=mysqli_num_rows($complain3);

    $result = [
        [
            "users" => $number_of_user,
            "police_station" => $number_of_station,
            "immediate_complain" => $immediate_complain,
            "complain_for_me" => $complain_for_me,
            "complain_for__others" => $complain_for__others,
      ]
    ];

print(json_encode($result));


?>