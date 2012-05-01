package edu.southalabama.jagtrak;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class Hello extends MapActivity
{

    static List<Overlay> mapOverlays;
    static Drawable drawable;
    LocationManager locationManager;
    static Location loc;
    static MyLocationOverlay myLocationOverlay;
    static Context context;
    static MapView mapView;
    static List<Overlay> overlays;
    static HelloItemizedOverlay itemizedOverlay;
    private final static Handler handler = new Handler();

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        context = this;

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.popup);
        itemizedOverlay = new HelloItemizedOverlay(drawable, this);

        overlays = mapView.getOverlays();
        
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        overlays.add(myLocationOverlay);
        myLocationOverlay.enableMyLocation();
        OverlayItemX itemX = new OverlayItemX(context, getResources());
        overlays.add(itemX);
        handler.postDelayed(checkAcc, 10000);
        
        mapView.bringToFront();
        
//        mapView.setOnLongClickListener(new OnLongClickListener()
//        {
//            public boolean onLongClick(View v)
//            {
//                Canvas canvas = new Canvas();
//                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.popup), v.getLeft() + v.getRight() - v.getRight()/2, v.getTop(), new Paint());
//                Toast.makeText(context, "blah", 1).show();
//                return false;
//            }
//        });
//         Toast.makeText(context, "You're too inaccurate!", Toast.LENGTH_LONG);
        // showDialog(1);

        // locationManager = (LocationManager)
        // getSystemService(Context.LOCATION_SERVICE);
        // Criteria criteria = new Criteria();
        // criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // criteria.setAltitudeRequired(false);
        // criteria.setBearingRequired(false);
        // criteria.setCostAllowed(true);
        // criteria.setPowerRequirement(Criteria.POWER_LOW);
        //
        // String provider = locationManager.getBestProvider(criteria, true);
        // Location location = locationManager.getLastKnownLocation(provider);
        // locationManager.requestLocationUpdates(provider, 60000, // 1min
        // 100, // 1km
        // locationListener);

    }

    // private final LocationListener locationListener = new LocationListener()
    // {
    // public void onLocationChanged(Location location)
    // {
    // updateWithNewLocation(location);
    // }
    //
    // public void onProviderDisabled(String provider)
    // {
    // }
    //
    // public void onProviderEnabled(String provider)
    // {
    // }
    //
    // public void onStatusChanged(String provider, int status, Bundle extras)
    // {
    // }
    // };
    //
    // private void updateWithNewLocation(Location location)
    // {
    // currentLocation = location;
    //
    // String latLongStirng = "";
    // String addressString = "You are not at an address!";
    //
    // if (location != null)
    // {
    // dobule lat = location.getLatitude();
    // double lng = location.getLongitude();
    // latLongString = "Lat:" + lat + "\nLong:" + lng;
    //
    // Geocoder geo = new Geocoder(this, Locale.getDefault());
    // try
    // {
    // List<Address> addre
    // }
    // }
    // }

    private final static Runnable checkAcc = new Runnable()
    {
        public void run()
        {
            loc = myLocationOverlay.getLastFix();
//            GeoPoint g = new GeoPoint((int)loc.getLatitude(), (int)loc.getLongitude());
//            OverlayItem oi = new OverlayItem(g, "Hey!", "Cheese!");
//            itemizedOverlay.addOverlay(oi);
//            mapOverlays.add(itemizedOverlay);
            // Canvas canvas = new Canvas();
            if (loc != null)
            {
                float acc = loc.getAccuracy();
                if (acc > 60)
                    ((Activity) context).showDialog(1);
            }
//            handler.postDelayed(checkAcc, 10000);
        }
    };

    public boolean onTouchEvent(MotionEvent me, Overlay myLocationOverlay)
    {
        super.onTouchEvent(me);
//        Canvas canvas = new Canvas();
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.popup), me.getX(), me.getY(), new Paint());
        Toast.makeText(context, "blah", 1).show();
        showDialog(1);
        return true;
    }
//    
//    public boolean onTap(GeoPoint p, MapView mapView)
//    {
////        Canvas canvas = new Canvas();
////        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.popup), p.getLatitudeE6(), p.getLongitudeE6(), new Paint());
//        Toast.makeText(context, "blah", 1).show();
//        showDialog(1);
//        return false;
//    }

    protected android.app.Dialog onCreateDialog(int id)
    {
        switch (id)
        {
        case 1:
            AlertDialog.Builder builder = new Builder(this);// builds an alert
            return builder.setTitle("Test")// sets title as about
                    .setMessage("Your location is too inaccurate. You may not be able to use the application.")// sets
                                                                                                               // the
                                                                                                               // message?
                    .create();// creates it
        }

        return null;// in case 0 isn't called. Neat trick.
    }

    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }

    public void onStop()
    {
        // locationManager.removeUpdates(locationListener);
        super.onStop();
    }

}