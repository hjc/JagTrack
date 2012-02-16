package edu.southalabama.jagtrak;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Hello extends MapActivity
{
    
    List<Overlay> mapOverlays;
    Drawable drawable;
    HelloItemizedOverlay itemizedOverlay;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.reddot);
        itemizedOverlay = new HelloItemizedOverlay(drawable);
        
        GeoPoint point = new GeoPoint(19240000,-99120000);
        OverlayItem overlayitem = new OverlayItem(point, "", "");
        
        itemizedOverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay);
    }
    
    public boolean onTouchEvent(MotionEvent me)
    {
        super.onTouchEvent(me);

        return false;
    }

    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }
}