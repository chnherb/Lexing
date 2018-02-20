<?php  
$servername = "124.207.182.333";
$mysql_username = "root";
$mysql_password = "root";
$bike_id = $_GET['bike_id'];
try {
	$conn1 = new PDO("mysql:host=124.207.182.333;dbname=shared_bike", $mysql_username, $mysql_password);
	$conn1->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	# code...
	// 发送单车位置
	// echo "1";
	// $state_borrow = 1;
	$sqlselect = "SELECT * FROM bike_state WHERE bike_id=$bike_id";
	// echo $sql1;
	// echo "<br>";
	$count = $conn1->query($sqlselect);
	foreach ($count as $row) {
		# code...
		// echo "您的余额：".$row['money'];
		// echo "<br>";
		$report_times_now = $row['report_times'];
	}
	$next_report_times = $report_times_now+1;
	echo $next_report_times;
	// echo $report_times_now;
	if ($next_report_times <= 5) {
		# code...
		
		$sqlupdate = "UPDATE bike_state SET report_times=$next_report_times WHERE bike_id=$bike_id";
		$updaterow = $conn1->exec($sqlupdate);	
		$sqlupdate_state = "UPDATE bike_state SET bike_repair=0 WHERE bike_id=$bike_id";
		$updaterow_state = $conn1->exec($sqlupdate_state);
	} else {
		# code...
		$sqlupdate = "UPDATE bike_state SET report_times=$next_report_times WHERE bike_id=$bike_id";
		$updaterow = $conn1->exec($sqlupdate);	
		$sqlupdate_state = "UPDATE bike_state SET bike_repair=1 WHERE bike_id=$bike_id";
		$updaterow_state = $conn1->exec($sqlupdate_state);
	}
	
	// $sqlupdate = "UPDATE bike_state SET location=$location WHERE bike_id=$bike_id";
	// $updaterow = $conn1->exec($sqlupdate);	
	// echo $updaterow;	
} catch (Exception $e) {
	echo $sqlselect . "<br>" . $e->getMessage();
}

?>