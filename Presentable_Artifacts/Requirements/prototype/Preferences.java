package edu.southalabama.jagtrak;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;

public class Preferences extends PreferenceActivity// Hey, a preference activity
{
    public void onCreate(Bundle savedInstanceState)// saved stuff
    {
        super.onCreate(savedInstanceState);// when created
        addPreferencesFromResource(R.xml.preferences);// get preferences
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)// if user presses back button
        {
            Intent intent = new Intent(this.getBaseContext(), Prototype.class);
            startActivity(intent);
            return true;// returns... true
        }
        return super.onKeyDown(keyCode, event);// yeah.
    }

    public void onPause()// when paused
    {
        super.onPause();// call this
    }
}