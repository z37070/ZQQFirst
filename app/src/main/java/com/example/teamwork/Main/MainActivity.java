package com.example.teamwork.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.teamwork.Main.MainMine.MainMinedrawer.MainMinedrawerFragment;
import com.example.teamwork.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomnavigationview_main;
    private DrawerLayout mydrawerlayout;
    private long firstPressedTime;
    public static boolean back=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_main);
        Bmob.initialize(this,"22859591a0207200359b133208256c5a");
        bottomnavigationview_main = findViewById(R.id.bottomnavigationview_main);
        NavController navcontroller_main= Navigation.findNavController(this,R.id.fragment_main);
        AppBarConfiguration configuration_main=new AppBarConfiguration.Builder(bottomnavigationview_main.getMenu()).build();
        NavigationUI.setupActionBarWithNavController(this,navcontroller_main,configuration_main);
        NavigationUI.setupWithNavController(bottomnavigationview_main,navcontroller_main);
        bottomnavigationview_main.setItemIconTintList(null);
        mydrawerlayout=findViewById(R.id.drawerlayout);


        mydrawerlayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                Animation shake= AnimationUtils.loadAnimation(drawerView.getContext(),R.anim.layout_animator_imageclock);
                MainMinedrawerFragment.imageview_main_minedrawer2.startAnimation(shake);
                MainMinedrawerFragment.imageview_main_minedrawer3.startAnimation(shake);
                MainMinedrawerFragment.imageview_main_minedrawer4.startAnimation(shake);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (back) {
            if (System.currentTimeMillis() - firstPressedTime < 2000) {
                finish();
            } else {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstPressedTime = System.currentTimeMillis();
            }
        }
        else {
            super.onBackPressed();
        }
    }



    public DrawerLayout getMydrawerlayout() {
        return mydrawerlayout;
    }
}
