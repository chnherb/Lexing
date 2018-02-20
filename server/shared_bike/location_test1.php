<!DOCTYPE html>  
<html>  
<head>  
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
	<title>乐行单车</title>
	<link rel="stylesheet" type="text/css" href="button.css">
	<style type="text/css">  
		html{height:100%}  
		body{height:100%;margin:0px;padding:0px}  
		#container{height:100%}  
	</style>  
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=j7rplXDXdW9oLM86ADMPZhIFCoO8EUM7">
//v2.0版本的引用方式：src="http://api.map.baidu.com/api?v=2.0&ak=您的密钥"
//v1.4版本及以前版本的引用方式：src="http://api.map.baidu.com/api?v=1.4&key=您的密钥&callback=initialize"
	</script>
</head>  
<?php  
$servername = "124.207.182.333";
$mysql_username = "root";
$mysql_password = "root";
$location_array = array();
try {
	$conn1 = new PDO("mysql:host=124.207.182.333;dbname=shared_bike", $mysql_username, $mysql_password);
	$conn1->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	// echo "Mysql数据库连接成功";
	$sql1 = "SELECT * FROM bike_state";
	// echo $sql1;
	// echo "<br>";
	$count = $conn1->query($sql1);
	// echo ($count->rowCount());
} catch (Exception $e) {
	// echo $count . "<br>" . $e->getMessage();
}
$conn1 = null;
if ($count->rowCount()>=1) {
	# code...
	foreach ($count as $row) {
		# code...
		array_push($location_array, $row['location']);
		// $$row['location'];
				
		// echo "您的余额：".$row['money'];
		// echo "<br>";
		// echo "<tr>";
		// echo "<td>{$row['userid']}</td>";
		// echo "<td>{$row['password']}</td>";
		// echo "<td>{$row['money']}</td>";
		// echo "</tr>";
	}
	// include ("location_test.html");
} else {
	# code...
	// echo "<script>alert('登录失败，请检查您的用户名或密码！');window.history.back();</script>";
}
// print_r($location_array);
// echo json_encode($location_array);
?>
<body>  
	<button id='test'>刷新</button>
	<div id="container"></div>
	<script src="jquery-3.2.1.js"></script>
	<script type="text/javascript"> 
		// 接收php的位置信息
		var readPHPPoint = <?php  echo json_encode($location_array)?>;
		// alert(readPHPPoint[0]);
		var bike0 = readPHPPoint[0];
		var bike1 = readPHPPoint[1];
		var bike00 = readPHPPoint[0].split(',')[0];
		var bike01 = readPHPPoint[0].split(',')[1];
		var bike10 = readPHPPoint[1].split(',')[0];
		var bike11 = readPHPPoint[1].split(',')[1];
		// alert(bike01);
		var bikePoint0 = new BMap.Point(bike00,bike01);
		var bikePoint1 = new BMap.Point(bike10,bike11);
		// alert(typeof(bike0));
		// var bikePoint0 = new BMap.Point(118.85560174461628,32.019030150189394);
		// var bikePoint1 = new BMap.Point(118.85555979804263,32.01901697484176);
		// alert(bikePoint0);
		var testPoint1 = new BMap.Point(118.864271,32.03059);  //第三教学楼
		var testPoint2 = new BMap.Point(118.7840660043,32.0394747449);
	    var SbPoint1 = new BMap.Point(118.864261,32.03091);   // 创建Mark图标
	    var SbPoint2 = new BMap.Point(118.865372,32.03092);   // 创建Mark图标
	    var SbPoint3 = new BMap.Point(118.864483,32.03013);   // 创建Mark图标
	    var SbPoint4 = new BMap.Point(118.865594,32.03024);   // 创建Mark图标
	    var redPoint1 = new BMap.Point(118.864871,32.03089);
	    var redPoint2 = new BMap.Point(118.864871,32.03099);
	    var redPoint3 = new BMap.Point(118.864871,32.03079);
	    var redPoint4 = new BMap.Point(118.864871,32.03069);

		var map = new BMap.Map("container");          		   // 创建地图实例  
		var point = new BMap.Point(116.404, 39.915);           // 创建点坐标  
		map.centerAndZoom(point, 20);                          // 初始化地图，设置中心点坐标和地图级别
		map.addControl(new BMap.NavigationControl());    
		map.addControl(new BMap.ScaleControl());    
		map.addControl(new BMap.OverviewMapControl());    
		map.addControl(new BMap.MapTypeControl());    
		map.setCurrentCity("北京"); // 仅当设置城市信息时，MapTypeControl的切换功能才能可用

	    // bm.addControl(new BMap.NavigationControl());
	    var markerSb1 = new BMap.Marker(SbPoint1);
	    var markerSb2 = new BMap.Marker(SbPoint2);
	    var markerSb3 = new BMap.Marker(SbPoint3);
	    var markerSb4 = new BMap.Marker(SbPoint4);
	    var markerBike0 = new BMap.Marker(bikePoint0);
	    var markerBike1 = new BMap.Marker(bikePoint1);
	    //坐标转换完之后的回调函数
    // 	translateCallback = function (data){
	   //    	if(data.status === 0) {
	   //      var marker = new BMap.Marker(data.points[0]);
	   //      map.addOverlay(marker);
	   //      var label = new BMap.Label("转换后的百度坐标（正确）",{offset:new BMap.Size(20,-10)});
	   //      marker.setLabel(label); //添加百度label
	   //      map.setCenter(data.points[0]);
    //   }
    // }

    // 	setTimeout(function(){
	   //      var convertor = new BMap.Convertor();
	   //      var pointArr = [];
	   //      pointArr.push(testPoint1);
	   //      convertor.translate(pointArr, 3, 5, translateCallback)
    // }, 1000);

		window.setTimeout(function(){  
			map.panTo(testPoint1);    
		}, 2000);
	    // map.addOverlay(markerSb1); 					     //添加GPS marker
	    // map.addOverlay(markerSb2); 					     //添加GPS marker
	    // map.addOverlay(markerSb3); 					     //添加GPS marker
	    // map.addOverlay(markerSb4); 					     //添加GPS marker
	    map.addOverlay(markerBike0);
	    map.addOverlay(markerBike1);
		var myIcon1 = new BMap.Icon("Error.png", new BMap.Size(34,34)); //红包车
		var myIcon2 = new BMap.Icon("Location2.png", new BMap.Size(48,48)); //定位
		var markerRed1 = new BMap.Marker(redPoint1,{icon:myIcon1});   
		// var markerRed2 = new BMap.Marker(redPoint2,{icon:myIcon1});  
		// var markerRed3 = new BMap.Marker(redPoint3,{icon:myIcon1});  
		// var markerRed4 = new BMap.Marker(redPoint4,{icon:myIcon1});  
		var markerLocation = new BMap.Marker(testPoint1,{icon:myIcon2});  // 创建标注

		map.addOverlay(markerRed1); 
		// map.addOverlay(markerRed2); 
		// map.addOverlay(markerRed3); 
		// map.addOverlay(markerRed4); 
		map.addOverlay(markerLocation);

	</script>
<script src="refresh.js"></script>
</body>  
</html>