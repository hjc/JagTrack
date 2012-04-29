package edu.southalabama.jagtrack;

import java.util.HashMap;
import edu.southalabama.jagtrack.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Driver extends Activity
{
    /* Declare Class Variables */
    static TextView tv3;
    static String loc = "", output = "", route = "";
    static boolean correct;
    static JagPersist persist;
    static Button button;
    private static HashMap<String, String> hm = new HashMap<String, String>();
    private static HashMap<String, BusStop> stop_hm = new HashMap<String, BusStop>();
    JagTran jagTran = new JagTran();
    /* End Declare Class Variables */

    public void onCreate(Bundle savedInstanceState)//Basically Android's 'main'
    {
        super.onCreate(savedInstanceState);//Necessary part of Android Syntax
        setContentView(R.layout.main);//This allows the Activity to know what resources to pull
        tv3 = (TextView) findViewById(R.id.text3);//Simply sets tv3 to correspond with a pre-defined TextView
        persist = new JagPersist();//Creates a persistence layer

        addHash();//Adds a hash map so we can tell which stop has which routes

        Spinner spinner = (Spinner) findViewById(R.id.spinner);//Associates the drop-down box with spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.stops_array, android.R.layout.simple_spinner_item);//Adapts the string array to the drop-down box
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//Identifies the adapter as a drop-down box
        spinner.setAdapter(adapter);//Associates the adapter with the spinner

        spinner.setOnItemSelectedListener(new OnItemSelectedListener()//Lets us know about spinner selection change
        {

            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)//If an item is selected
            {
                loc = arg0.getSelectedItem().toString();//Set that to the new location
                correct = false;//necessary for proper calculation later
//                for (int i = 0; i < hm.get(loc).split(" ").length; i++)//Split the hashMap value and traverse the new array
//                {
//                    if (route.contains(hm.get(loc).split(" ")[i]))//If route is one of the associated routes for the stop
//                        correct = true;//Set correct to true so we can calculate.
//                }
                if (route.equals(hm.get(loc))){
                	correct = true;
                }                
                tv3.setText("");//Set the text to be blank.
            }

            public void onNothingSelected(AdapterView<?> arg0)//Not used, but must be present for Java syntax
            {
            }

        });

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);//Associates the drop-down box with spinner2
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.routes_array, android.R.layout.simple_spinner_item);//Adapts the string array to the drop-down box
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//Identifies the adapter2 as a drop-down box
        spinner2.setAdapter(adapter2);//Associates the adapter2 with the spinner2

        spinner2.setOnItemSelectedListener(new OnItemSelectedListener()//Lets us know about spinner2 selection change
        {

            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)//If an item is selected
            {
                correct = false;//Set this to false so we don't wrongly calculate
                route = arg0.getSelectedItem().toString();//Set the selection to the route
//                for (int i = 0; i < hm.get(loc).split(" ").length; i++)//Split the hashMap value into a String array
//                {
//                    if (route.contains(hm.get(loc).split(" ")[i]))//If the location has this selected route
//                        correct = true;//Set this gate to true
//                }
                if (route.equals(hm.get(loc))){
                	correct = true;
                }
                tv3.setText("");//Clear the text
            }

            public void onNothingSelected(AdapterView<?> arg0)//Syntactical necessity.
            {
            }

        });

        button = (Button) findViewById(R.id.button);//Associate button with a pre-defined button
        button.setOnClickListener(new OnClickListener()//Check for button being clicked
        {

            public void onClick(View v)//If button is clicked
            {
                output = "invalid selection";//Set output
                if (correct)//If correct is true
                	try{
                		output = "dinos";
                	output = persist.getArrivalTime(route, stop_hm.get(loc)).toString();
                    //output = persist.getArrivalTime(route, loc).toString();//reset output
                	}catch(Exception e){
                		System.out.println(e);
                	}
                tv3.setText(output);//Write output
            }

        });
    }

    /**
     * Hard-codes a hash map for the JagStops and their routes. If the JagStops
     * themselves are modified (renamed or a new one is created), be sure to
     * also modify the res/values/strings.xml to match.
     * 
     * @see res/values/strings.xml
     */
    private void addHash()
    {
    	for (int i = 0; i < jagTran.busStops.length; i++){
    		hm.put(jagTran.busStops[i].getLocation(), jagTran.busStops[i].getRoutes());
    		stop_hm.put(jagTran.busStops[i].getLocation(), jagTran.busStops[i]);
    	}

// First 8 Stops
//        hm.put("Student Center", "Red Blue");
//        hm.put("Humanities South", "Red ");
//        hm.put("Humanities North", "Red");
//        hm.put("Engineering", "Blue");
//        hm.put("Dining Hall", "Blue");
//        hm.put("Grove", "Blue");
//        hm.put("Greek Row", "Green");
//        hm.put("Delta/Epsilon", "Green Red");
        
        
//        hm.put("Rec Center", "Green Blue");
//        hm.put("Hospital", "Red");
//        hm.put("Allied Health", "Red");
//        hm.put("Laidlaw/MCOB", "Yellow");
//        hm.put("Shelby Hall", "Yellow");
//        hm.put("UCOM", "Yellow");
//        hm.put("Mitchell Center", "Yellow");
//        hm.put("Library", "Red");
    }
}