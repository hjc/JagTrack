package edu.southalabama.jagtrak;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity
{

    public static LinearLayout root;
    static Handler handler = new Handler();
    public static int startx, starty, x, y;
    public static Random generator = new Random();
    public static TranslateAnimation anim;
    public static ImageView dot;
    public static Context context;
    
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        context = this.getBaseContext();
        
        //ImageView tran = (ImageView)findViewById(R.id.mainline);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        root = (LinearLayout)findViewById(R.id.root);
        
        root.setBackgroundResource(R.drawable.mainline);
        
        Button button = new Button(this);
        button.setText("A button");
        button.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(context, Hello.class);
                startActivity(intent);
            }
        });
        root.addView(button);
        
        dot = new ImageView(context);
        dot.setImageResource(R.drawable.reddot);
        
        dot.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        root.addView(dot);
        
        handler.post(animate);
    }
    
    public static Runnable animate = new Runnable()
    {
        public void run()
        {
            x = generator.nextInt(300);
            y = generator.nextInt(500);
            anim = new TranslateAnimation(startx, x, starty, y);
            startx = x;
            starty = y;
            anim.setInterpolator(new AccelerateDecelerateInterpolator());// makes a more realistic sword movement
            anim.setDuration(100);// 200 milliseconds
            anim.setRepeatCount(0);// repeats one time
//          anim.setRepeatMode(Animation.REVERSE);// repeats the one time in reverse
            dot.setAnimation(anim);
            dot.startAnimation(anim);
            handler.postDelayed(animate, (anim.getDuration()));
//          handler.post(animate);
        }
    };
    
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
}