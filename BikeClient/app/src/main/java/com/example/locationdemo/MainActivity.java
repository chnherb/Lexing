package com.example.locationdemo;



import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

public class MainActivity extends Activity {


	private WebView wb;
	private WebView sendlocation;
	//amap
	private LocationManagerProxy aMapManager;
    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		wb = (WebView) findViewById(R.id.showbike);
		wb.loadUrl("http://wuchout.cn/shared_bike/show_bike_state.php?bike_id=000002");
		wb.setWebViewClient(new WebViewClient());
		//得到webview设置
		WebSettings webSettings = wb.getSettings();
		//允许使用javascript
		webSettings.setJavaScriptEnabled(true);
        sendlocation = (WebView) findViewById(R.id.sendlocation);
        startAmap();
	}
	


	private void startAmap() {
		aMapManager = LocationManagerProxy.getInstance(this);
		/*
		 * mAMapLocManager.setGpsEnable(false);
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
		 * API定位采用GPS和网络混合定位方式
		 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		 */
		aMapManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork, 2000, 10, mAMapLocationListener);
	}

	private void stopAmap() {
		if (aMapManager != null) {
			aMapManager.removeUpdates(mAMapLocationListener);
			aMapManager.destory();
		}
		aMapManager = null;
	}


	
	private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			
		}
		
		@Override
		public void onLocationChanged(AMapLocation location) {
			if (location != null) {
				Double geoLat = location.getLatitude();
				Double geoLng = location.getLongitude();
                double[] loc = gcj02_To_Bd09(geoLat, geoLng);
                sendlocation.loadUrl("http://wuchout.cn/shared_bike/change_location.php?bike_id=\"000002\"&location=\""+ loc[1]+ "," + loc[0] +"\"");

			}
		}
	};
	//坐标系转换
	public static double[] gcj02_To_Bd09(double gg_lat, double gg_lon) {
		double x = gg_lon, y = gg_lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
		double bd_lon = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		double[] gps = {bd_lat,bd_lon};
		return gps;
	}
}
