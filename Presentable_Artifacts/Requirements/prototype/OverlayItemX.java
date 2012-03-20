package edu.southalabama.jagtrak;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Projection;

public class OverlayItemX extends com.google.android.maps.Overlay
{
    Context context;
    Resources r;

    OverlayItemX(Context context, Resources r)
    {
        this.context = context;
        this.r = r;
    }

    public boolean onTouchEvent(MotionEvent event, MapView mapview)
    {

        if (event.getAction() == 1)
        {
            // GeoPoint p = mapview.getProjection().fromPixels((int)
            // event.getX(), (int) event.getY());
            // Toast.makeText(context, p.getLatitudeE6() / 1E6 + "," +
            // p.getLongitudeE6() / 1E6, Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        Projection projection = mapView.getProjection();

        GeoPoint myLocationGeoPoint = new GeoPoint(123, 123);

        Point myPoint = new Point();
        projection.toPixels(myLocationGeoPoint, myPoint);

//        int radiusPixel = (int) projection.metersToEquatorPixels(300);

//        canvas.drawCircle(myPoint.x, myPoint.y, radiusPixel, new Paint());
//        canvas.drawCircle(myPoint.x, myPoint.y, radiusPixel, new Paint());
        canvas.drawBitmap(BitmapFactory.decodeResource(r, R.drawable.popup), myPoint.x, myPoint.y, new Paint());


        super.draw(canvas, mapView, shadow);
    }
}
