package cordova.hms.map.kit;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.Circle;
import com.huawei.hms.maps.model.CircleOptions;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.util.LogM;

import androidx.core.app.ActivityCompat;


/**
 * This class echoes a string called from JavaScript.
 */
public class HMSMapKit extends CordovaPlugin {
  private static final String[] RUNTIME_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};
    private static final int REQUEST_CODE = 100;
    private static final LatLng LAT_LNG = new LatLng(31.2304, 121.4737);
    private static final String TAG = "MapViewDemoActivity";
  private Marker mMarker;
  private Circle mCircle;
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }else if(action.equals("addTwoNumbers")) {
            Double first = args.getDouble(0);
            Double second = args.getDouble(1);
            this.addTwoNumbers(first,second, callbackContext);
            return true;
        }else if(action.equals("loadMap")) {
            this.loadMap();
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
    private void addTwoNumbers(Double first,Double second, CallbackContext callbackContext) {
        if (first != null && second != null) {
            callbackContext.success(""+(first+second));
        } else {
            callbackContext.error("Expected two non-empty double argument.");
        }
    }
    /*private void loadMap() {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Context context = cordova.getActivity().getApplicationContext();
                Intent intent = new Intent(context, NewActivity.class);
                cordova.getActivity().startActivity(intent);
            }
        });

    }*/
    private synchronized void loadMap(){
      final CordovaInterface cordova=this.cordova;
      Runnable runnable=new Runnable() {
        @Override
        public void run() {
          AlertDialog.Builder dlg = new AlertDialog.Builder(cordova.getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
          Application app=cordova.getActivity().getApplication();
          String package_name=app.getPackageName();
          Resources resources=app.getResources();
          int layout =resources.getIdentifier("activity_new","layout",package_name);
          int map=resources.getIdentifier("mapView","id",package_name);
          LayoutInflater inflater=cordova.getActivity().getLayoutInflater();
          View customView=inflater.inflate(layout, null);
          dlg.setView(customView);
          if (!this.hasPermissions(cordova.getActivity(), RUNTIME_PERMISSIONS)) {
            ActivityCompat.requestPermissions(cordova.getActivity(), RUNTIME_PERMISSIONS, REQUEST_CODE);
          }
          MapView mMapView = (MapView) customView.findViewById(map);
          Bundle mapViewBundle = null;
          /*if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
          }*/
          mMapView.onCreate(mapViewBundle);

          // get map by async method
          mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(HuaweiMap huaweiMap) {
              Log.d(TAG, "onMapReady: ");

              // after call getMapAsync method ,we can get HuaweiMap instance in this call back method
              HuaweiMap hmap = huaweiMap;
              hmap.setMyLocationEnabled(true);

              // move camera by CameraPosition param ,latlag and zoom params can set here
              CameraPosition build = new CameraPosition.Builder().target(new LatLng(60, 60)).zoom(5).build();

              CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(build);
              hmap.animateCamera(cameraUpdate);
              hmap.setMaxZoomPreference(5);
              hmap.setMinZoomPreference(2);

              // mark can be add by HuaweiMap
              mMarker = hmap.addMarker(new MarkerOptions().position(LAT_LNG)
                .icon(BitmapDescriptorFactory.fromResource(resources.getIdentifier("badge_ph","drawable",package_name)))
                .clusterable(true));

              mMarker.showInfoWindow();

              // circle can be add by HuaweiMap
              mCircle = hmap.addCircle(new CircleOptions().center(new LatLng(60, 60)).radius(5000).fillColor(Color.GREEN));
            }
          });
          dlg.create();
          dlg.show();
        }
        private boolean hasPermissions(Context context, String... permissions) {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            for (String permission : permissions) {
              if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
              }
            }
          }
          return true;
        }
      };
      this.cordova.getActivity().runOnUiThread(runnable);
    }

}
