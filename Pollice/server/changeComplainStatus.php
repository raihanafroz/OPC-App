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
        $tableName=$_POST['table_name'];
        $status=$_POST['status'];

        $sql_update = "UPDATE `$tableName` SET `status`= '$status' WHERE `email` = '$email';";

        $query=mysqli_query($connect, $sql_update);
        if($query)
        {
            $row=mysqli_affected_rows($connect);
            if($row!=0){
                echo "Successfully";
            }else{
                echo "Sorry";
            }
        }
    }else{
        echo "Access denied";
    }

    mysqli_close($connect);

?>