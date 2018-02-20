<?php
// $username = $_GET['username'];
$bike_id = $_GET['bike_id'];
// $password  = $_GET['password'];
// echo $username;
// echo $password;
$servername = "124.207.182.333";
$mysql_username = "root";
$mysql_password = "root";
$response = array();
// 数据库插入语句
// try {
// 	$conn = new PDO("mysql:host=124.207.182.333;dbname=shared_bike", $mysql_username, $mysql_password);
// 	$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
// 	echo "连接成功";
// 	$sql = "INSERT INTO login (userid, password) VALUES ($username, $password)";
// 	$conn->exec($sql);
// 	echo "新纪录插入成功";
// } catch (PDOException $e) {
// 	echo $sql . "<br>" . $e->getMessage();
// }
// 数据局查询语句
try {
	$conn1 = new PDO("mysql:host=124.207.182.333;dbname=shared_bike", $mysql_username, $mysql_password);
	$conn1->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	// echo "Mysql数据库连接成功";
	$sql1 = "SELECT * FROM bike_state WHERE bike_id=$bike_id";
	// echo $sql1;
	// echo "<br>";
	$count = $conn1->query($sql1);
	// echo ($count->rowCount());
} catch (Exception $e) {
	// echo $count . "<br>" . $e->getMessage();
}
$conn1 = null;
if ($count->rowCount()==1) {
	# code...
	foreach ($count as $row) {
		# code...
		// echo "您的余额：".$row['money'];
		// echo "<br>";
		$response['bike_state'] = $row['bike_state'];
	}
	// include ("location_test.html");
} else {
	# code...
	// echo "<script>alert('登录失败，请检查您的用户名或密码！');window.history.back();</script>";
}
echo json_encode($response);
?>