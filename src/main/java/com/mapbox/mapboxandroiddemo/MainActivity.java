package com.mapbox.mapboxandroiddemo;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mapbox.mapboxsdk.overlay.UserLocationOverlay;
import com.mapbox.mapboxsdk.tileprovider.tilesource.ITileLayer;
import com.mapbox.mapboxsdk.tileprovider.tilesource.MBTilesLayer;

import com.mapbox.mapboxsdk.views.MapView;

import java.util.List;

import butterknife.ButterKnife;


public class MainActivity extends Activity {

    private MapView mv;
    private UserLocationOverlay myLocationOverlay;
    private LocationManager locationManager;
   // @BindView(R.id.mapview)
   // Button mBtnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mv = (MapView) findViewById(R.id.mapview);
        mv.setUseDataConnection(false);
    //    MBTilesLayer mbTileLayer = new MBTilesLayer("/mnt/sdcard/simaoliushun16_19.mbtiles");
   //     mv.setTileSource(new ITileLayer[]{mbTileLayer});
    //    mv.setScrollableAreaLimit(mbTileLayer.getBoundingBox());
    //    String path = Utils.getExtSDCardPath().get(2)+"/openstreetmap-hongdae.mbtiles";

   //   Toast.makeText(this, path+new File(path).exists(), Toast.LENGTH_SHORT).show();
   //   TileLayer mbTileLayer = new MBTilesLayer(this, "/mnt/sdcard/hongdae.mbtiles");
        MBTilesLayer mbTileLayer = new MBTilesLayer("/mnt/sdcard/hongdae.mbtiles");
        mv.setTileSource(new ITileLayer[] {mbTileLayer});
        mv.setScrollableAreaLimit(mbTileLayer.getBoundingBox());
        mv.setMinZoomLevel(mv.getTileProvider().getMinimumZoomLevel());
        mv.setMaxZoomLevel(mv.getTileProvider().getMaximumZoomLevel());
        mv.setCenter(mv.getTileProvider().getCenterCoordinate());
        mv.setUseSafeCanvas(true);
    }

    public void getMyLocation(){
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // 获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        String provider;
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            // 当没有可用的位置提供器时,弹出Toast提示用户
            Toast.makeText(this, "No location provider to use",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("location", provider);
/*
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            // 显示当前设备的位置信息
            showLocation(location);
        }
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);*/
    }

    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            // 关闭程序时将监听器移除
         //   locationManager.removeUpdates(locationListener);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle
                extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
        @Override
        public void onLocationChanged(Location location) {
            // 更新当前设备的位置信息
            showLocation(location);
        }
    };

    private void showLocation(Location location) {
        String currentPosition = "latitude is " + location.getLatitude() + "\n"
                + "longitude is " + location.getLongitude();
        Log.e("location",currentPosition);
    }
}
