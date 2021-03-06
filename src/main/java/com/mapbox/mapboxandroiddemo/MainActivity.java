package com.mapbox.mapboxandroiddemo;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.mapbox.mapboxsdk.overlay.UserLocationOverlay;
import com.mapbox.mapboxsdk.tileprovider.tilesource.ITileLayer;
import com.mapbox.mapboxsdk.tileprovider.tilesource.MBTilesLayer;
import com.mapbox.mapboxsdk.views.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends Activity {

    @BindView(R.id.mapview)
    MapView mv;
    @BindView(R.id.textView)
    TextView zoomLevel;

    private UserLocationOverlay myLocationOverlay;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mv.setUseDataConnection(false);

        MBTilesLayer mbTileLayer = new MBTilesLayer("/mnt/sdcard/hongdae.mbtiles");
        mv.setTileSource(new ITileLayer[]{mbTileLayer});
        mv.setScrollableAreaLimit(mbTileLayer.getBoundingBox());
        mv.setMinZoomLevel(mv.getTileProvider().getMinimumZoomLevel());
        mv.setMaxZoomLevel(mv.getTileProvider().getMaximumZoomLevel());
        mv.setCenter(mv.getTileProvider().getCenterCoordinate());
        mv.setUseSafeCanvas(true);
        zoomLevel.setText("当前级别：16级");
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
        Log.e("location", currentPosition);
    }
}
