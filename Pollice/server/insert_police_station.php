<?php
    require 'init.php';

    //if(true){
    // $email="raihanafroz@gmail.com";
    // $thanaName="Raihan";
    // $latitude="Topu";
    // $longitude="1111";
    // $phoneNo="Dhaka";

    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $email=$_POST['email'];
        $thanaName=$_POST['thanaName'];
        $latitude=$_POST['thanaLatitude'];
        $longitude=$_POST['thanaLongitude'];
        $phoneNo=$_POST['thanaNo'];

        $sql_check = "SELECT * FROM `user_info` WHERE `e-mail` = '$email' AND `type` = 'Admin';";

        $query = mysqli_query($connect, $sql_check);
        $row = mysqli_fetch_array($query);

        if ($row != null) {

            $sql_insert = "INSERT INTO `tbl_station_thana`(`thanaName`, `latitude`, `longitude`, `phoneNo`) VALUES ('$thanaName', '$latitude', '$longitude', '$phoneNo');";

            if (mysqli_query($connect, $sql_insert)) {
                echo "Data added";
            } else {
                echo "Failed to add data";
            }
        } else {
            echo "No access to add data";
        }
    }else{
        echo "Access denied";
    }

    mysqli_close($connect);

?>