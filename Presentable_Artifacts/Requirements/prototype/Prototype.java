package edu.southalabama.jagtrak;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Prototype extends Activity
{

    public static LinearLayout root;
    static Handler handler = new Handler();
    public static int startx, starty, x, y;
    public static Random generator = new Random();
    public static TranslateAnimation anim;
    public static ImageView dot;
    public static Context context;
    public static boolean longclk = false;
    public static ImageView iv;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        context = this.getBaseContext();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        root = (LinearLayout) findViewById(R.id.root);

        root.setBackgroundResource(R.drawable.screencap);
        
//        View view = new View(context);
        root.setLongClickable(true);
        
        root.bringToFront();
        iv = new ImageView(context);
        root.setOnLongClickListener(new OnLongClickListener()
        {
            public boolean onLongClick(View v)// onLongClick
            {
//                Canvas canvas = new Canvas();
                if (!longclk)
                {
//                Toast.makeText(context, "bob", Toast.LENGTH_LONG).show();
                v.scrollTo(20, 0);
                iv.setLayoutParams(new LayoutParams(root.getWidth()/2, root.getHeight()/5));
                iv.setBackgroundResource(R.drawable.popup);
                root.addView(iv);
                longclk = true;
                }
//                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.popup), v.getLeft(), v.getTop(), new Paint());
//                root.draw(canvas);
                return false;
            }
        });
        
        iv.setOnClickListener(new OnClickListener()
        {
            public void onClick(View arg0)
            {
                longclk = false;
                root.removeView(iv);
            }
        });
        
//        handler.post(animate);
    }

    public void onPause()
    {
        onStop();
        super.onPause();
    }

    public void onStop()
    {
        System.exit(0);
        super.onStop();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        try
        {
            switch (item.getItemId())
            {
                case R.id.lines:
                {
                    Intent intent = new Intent(context, Preferences.class);
                    startActivity(intent);
                }
            }
        } 
        catch (Exception e)
        {}
        return super.onOptionsItemSelected(item);
    }
    
//    public static Runnable animate = new Runnable()
//    {
//        public void run()
//        {
//            x = generator.nextInt(300);
//            y = generator.nextInt(500);
//            anim = new TranslateAnimation(startx, x, starty, y);
//            startx = x;
//            starty = y;
//            anim.setInterpolator(new AccelerateDecelerateInterpolator());// makes a more realistic sword movement
//            anim.setDuration(100);// 200 milliseconds
//            anim.setRepeatCount(0);// repeats one time
////          anim.setRepeatMode(Animation.REVERSE);// repeats the one time in reverse
//            dot.setAnimation(anim);
//            dot.startAnimation(anim);
//            handler.postDelayed(animate, (anim.getDuration()));
////          handler.post(animate);
//        }
//    };
}