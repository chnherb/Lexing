<!DOCTYPE html>
<html>
<head>
	<!-- <meta charset="utf-8" http-equiv="refresh" content="1"> -->
	<title></title>
	<link rel="stylesheet" type="text/css" href="c05.css">
	<link rel="stylesheet" type="text/css" href="button.css">
</head>
<body>
<?php  
$bike_id = $_GET['bike_id'];
?>
<center>
<img src="<?php echo "$bike_id.png"; ?>">
<br>
<input id="bike" type="hidden" name="bike_id" value="<?php echo $bike_id?>">
<!-- <?php echo date("Y/m/d h:i:sa") ?> -->
<?php
//数据库信息
$servername = "124.207.182.333";
$mysql_username = "root";
$mysql_password = "root";
// $bike_id = $_GET['bike_id'];
// $bike_state = $_GET['bike_state'];
// $bike_repair = $_GET['bike_repair'];
// $bike_command = $_GET['bike_command'];
// echo $bike_id;
// echo $bike_state;
// echo $bike_repair;
try {
	$conn1 = new PDO("mysql:host=124.207.182.333;dbname=shared_bike", $mysql_username, $mysql_password);
	$conn1->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	// echo "Mysql数据库连接成功";
	$sql1 = "SELECT * FROM bike_state WHERE bike_id=$bike_id";
	// echo $sql1;
	echo "<br>";
	$count = $conn1->query($sql1);
	foreach ($count as $row) {
		# code...
		// echo "<li class='complete'>".date("Y/m/d h:i:sa")."</li>";
		echo "<li class='complete'>"."车辆".$bike_id."当前经纬度："."</li>";
		echo "<li class='complete'>".$row['location']."</li>";
		// echo "<li class='complete'>$row['location']<li>";
		if ($row['bike_state'] == 1){
			echo "<li class='hot'><em>车辆已借出-----></em>请您借用其他车量</li>";
		}else{
			echo "<li class='cool'><em>车辆未借出-----></em>您可以借用该车辆</li>";
		}
		if ($row['bike_repair'] == 1) {
			# code...
			echo "<li class='hot'><em>车辆有问题-----></em>建议借用其他车量</li>";
		} else {
			# code...
			echo "<li class='cool'><em>车辆无问题-----></em>可以扫码立即使用</li>";
		}
		// echo "单车是否被借：".$row['bike_state'];
		// $bike_now_state = $row['bike_state'];
		// echo "单车是否有问题".$row['bike_repair'];
		// $bike_now_repair = $row['bike_repair'];		
		// if ($bike_command == 1) {
		// 	# code...
		// 	if ($bike_now_state == 0) {
		// 		# code...
		// 		//将单车状态置为1
		// 		$state_borrow = "1";
		// 		$sqlupdate = "UPDATE bike_state SET bike_state={$state_borrow}";
		// 		$updaterow = $conn1->exec($sqlupdate);
		// 	}
		// } else {
		// 	# code...
		// 	$state_return = '0';
		// 	$sqlupdate = "UPDATE bike_state SET bike_state={$state_return}";
		// 	$updaterow = $conn1->exec($sqlupdate);
		// }		
	}
	// echo ($count->rowCount());
} catch (Exception $e) {
	echo $sql . "<br>" . $e->getMessage();
}

$conn1 = null;
$bike_command = "0";
?>
<div id="test"></div>
<br>
<!--<ul>-->
	<!--<li id="return">点击还车</li>-->
<!--</ul>-->
<!-- <button type="button">点击还车</button> -->
<button class="button button-royal button-box button-giant">还车</button>







<script src="jquery-3.2.1.js"></script>
<script src="return_bike.js"></script>
<script type="text/javascript">
	function myfresh(){
		window.location.reload();
	}
	setTimeout('myfresh()', 3000);
</script>
</center>
</body>
</html>