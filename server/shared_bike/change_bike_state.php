<?php  
$servername = "124.207.182.333";
$mysql_username = "root";
$mysql_password = "root";
$bike_id = $_GET['bike_id'];
$bike_command = $_GET['bike_command'];
try {
	$conn1 = new PDO("mysql:host=124.207.182.333;dbname=shared_bike", $mysql_username, $mysql_password);
	$conn1->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	if ($bike_command == 1) {
		# code...
		//将单车状态置为1
		echo "1";
		$state_borrow = 1;
		$sqlupdate = "UPDATE bike_state SET bike_state=1 WHERE bike_id=$bike_id";
		$updaterow = $conn1->exec($sqlupdate);	
		echo $updaterow;	
	} else {
		# code...
		echo "0";
		$state_return = 0;
		$sqlupdate = "UPDATE bike_state SET bike_state=0 WHERE bike_id=$bike_id";
		$updaterow = $conn1->exec($sqlupdate);
		echo $updaterow;
	}		
} catch (Exception $e) {
	echo $sql . "<br>" . $e->getMessage();
}

?>