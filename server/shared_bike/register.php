<?php  
$servername = "124.207.182.333";
$mysql_username = "root";
$mysql_password = "root";
// $bike_command = $_GET['bike_command'];
$username = $_GET['username'];
$password = $_GET['password'];
$response = array();

try {
	$conn1 = new PDO("mysql:host=124.207.182.333;dbname=shared_bike", $mysql_username, $mysql_password);
	$conn1->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	$insert = "INSERT INTO login (userid, password, money) VALUES ($username, $password, 0.0)";
	// echo $insert;
	// echo "<br>";
	$conn1->exec($insert);
	$response['success'] = true;
    // echo "新记录插入成功";
} catch (Exception $e) {
	echo $insert . "<br>" . $e->getMessage();
	$response['success'] = false;
	$response['error_info'] = $e->getMessage();
}

echo json_encode($response);
?>