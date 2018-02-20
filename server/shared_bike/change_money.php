<?php  
$servername = "124.207.182.333";
$mysql_username = "root";
$mysql_password = "root";
$username = $_GET['username'];
// $password = $_GET['password'];
$money = $_GET['money'];
$response = array();
try {
	$conn1 = new PDO("mysql:host=124.207.182.333;dbname=shared_bike", $mysql_username, $mysql_password);
	$conn1->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	# code...
	//将单车状态置为1
	// echo "1";
	$state_borrow = 1;
	$sqlupdate = "UPDATE login SET money=$money WHERE userid=$username";
	$updaterow = $conn1->exec($sqlupdate);	
	// echo $updaterow;	
	$response['success'] = true;
} catch (Exception $e) {
	echo $sqlupdate . "<br>" . $e->getMessage();
	$response['success'] = false;
	$response['error_info'] = $e->getMessage();
}
echo json_encode($response);
?>