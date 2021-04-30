package com.example.teamwork.Welcome;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.teamwork.R;
import com.example.teamwork.Start.StartActivity;

import java.util.Timer;
import java.util.TimerTask;


public class WelcomeActivity extends Activity {
    private ProgressBar progressbar;
    private ImageView image_welcome;
    private TextView[] textview_welcome=new TextView[6];
    private int[] textid={R.id.textview_welcome1,R.id.textview_welcome2,R.id.textview_welcome3,R.id.textview_welcome4,R.id.textview_welcome5,R.id.textview_welcome6};
    private int[] textvalue={R.string.textview_welcome1_value,R.string.textview_welcome2_value,R.string.textview_welcome3_value,R.string.textview_welcome4_value,R.string.textview_welcome5_value,R.string.textview_welcome6_value};
    private final int WaitTime=3800;
    //避免调试时浪费时间，可将WaitTime改为0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        progressbar=findViewById(R.id.progressbar_welcome);
        image_welcome=findViewById(R.id.image_welcome);
        image_welcome.setImageResource(R.drawable.image_welcome);
        for(int i=0;i<2;i++) {
            textview_welcome[i]=findViewById(textid[i]);
            textview_welcome[i].setText(textvalue[i]);
            AnimationSet animationSet = new AnimationSet(true);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setDuration(2000);
            animationSet.addAnimation(alphaAnimation);
            textview_welcome[i].startAnimation(animationSet);
        }
        for(int i=2;i<6;i++) {
            textview_welcome[i]=findViewById(textid[i]);
            textview_welcome[i].setText(textvalue[i]);
            AnimationSet animationSet = new AnimationSet(true);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setDuration(4000);
            animationSet.addAnimation(alphaAnimation);
            textview_welcome[i].startAnimation(animationSet);
        }
        Timer time=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {

                Intent intent = new Intent(WelcomeActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
                ////////////////////////////////
                ////////////////////////////////
            }
        };
        time.schedule(task,WaitTime);
    }
}
