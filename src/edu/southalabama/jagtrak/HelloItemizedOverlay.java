package edu.southalabama.jagtrak;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("rawtypes")
public class HelloItemizedOverlay extends ItemizedOverlay
{
    Context context;
    private ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();

    public HelloItemizedOverlay(Drawable defaultMarker, Context context)
    {
        super(boundCenterBottom(defaultMarker));
        this.context = context;
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
    
    protected boolean onTap(int x)
    {
        OverlayItem item = overlays.get(x);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(item.getTitle());
        dialog.setMessage(item.getSnippet());
        dialog.show();
        Toast.makeText(context, "Bob", 1).show();
        return true;
    }

}
