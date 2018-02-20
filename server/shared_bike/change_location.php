<?php  
$servername = "124.207.182.333";
$mysql_username = "root";
$mysql_password = "root";
$bike_id = $_GET['bike_id'];
$location = $_GET['location'];
try {
	$conn1 = new PDO("mysql:host=124.207.182.333;dbname=shared_bike", $mysql_username, $mysql_password);
	$conn1->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	# code...
	// 发送单车位置
	// echo "1";
	// $state_borrow = 1;
	$sqlupdate = "UPDATE bike_state SET location=$location WHERE bike_id=$bike_id";
	$updaterow = $conn1->exec($sqlupdate);	
	// echo $updaterow;	
} catch (Exception $e) {
	echo $sqlupdate . "<br>" . $e->getMessage();
}

?>