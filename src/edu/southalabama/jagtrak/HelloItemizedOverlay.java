package edu.southalabama.jagtrak;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("rawtypes")
public class HelloItemizedOverlay extends ItemizedOverlay
{

    private ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();

    public HelloItemizedOverlay(Drawable defaultMarker)
    {
        super(boundCenterBottom(defaultMarker));
    }

    protected OverlayItem createItem(int i)
    {
        return overlays.get(i);
    }

    public int size()
    {
        return overlays.size();
    }

    public void addOverlay(OverlayItem overlay)
    {
        overlays.add(overlay);
        populate();
    }

}
