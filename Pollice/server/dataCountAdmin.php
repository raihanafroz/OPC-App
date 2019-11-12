<?php

    require "init.php";

$user_data = mysqli_query($connect,"SELECT * FROM user_info WHERE `type` = 'User';" );

$number_of_user=mysqli_num_rows($user_data);
echo $number_of_user;

?>