package com.example.teamwork.Main.MainMine;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamwork.Main.MainActivity;
import com.example.teamwork.Main.MainMine.MainMinedrawer.MainMinedrawerFragment;
import com.example.teamwork.Main.MainRedDialog;
import com.example.teamwork.R;
import com.example.teamwork.Start.StartActivity;

import java.util.PrimitiveIterator;

import cn.bmob.v3.util.V;

public class MainMineFragment extends Fragment  {
    private ImageView imageview_main_mine0;
    private ImageView imageview_main_mine1;
    private ImageView imageview_main_mine2;
    private ImageView imageview_main_mine3;
    private ImageView imageview_main_mine4;
    private TextView textview_main_mine6;
    private TextView textview_main_mine7;
    private Button button_main_mine1;
    private Button button_main_mine2;
    private Button button_main_mine3;
    private Button button_main_mine4;

    private GestureDetector detector;
    private SoundPool soundpool_main_mine;
    private boolean fine=false;
    private int music_main_mine;
    private final static int COUNTS = 6;//点击次数
    private final static long DURATION = 1200;//规定有效时间
    private long[] mHits = new long[COUNTS];

    String id;
    String phone;
    private MainRedDialog dialog_mine;

    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;

    private MainMineViewModel mViewModel;

    public static MainMineFragment newInstance() {
        return new MainMineFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_mine, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainMineViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textview_main_mine6=view.findViewById(R.id.textview_main_mine6);
        textview_main_mine7=view.findViewById(R.id.textview_main_mine7);
        button_main_mine1=view.findViewById(R.id.button_main_mine1);
        button_main_mine2=view.findViewById(R.id.button_main_mine2);
        button_main_mine3=view.findViewById(R.id.button_main_mine3);
        button_main_mine4=view.findViewById(R.id.button_main_mine4);
        imageview_main_mine0=view.findViewById(R.id.imageview_main_mine0);
        imageview_main_mine1=view.findViewById(R.id.imageview_main_mine1);
        imageview_main_mine2=view.findViewById(R.id.imageview_main_mine2);
        imageview_main_mine3=view.findViewById(R.id.imageview_main_mine3);
        imageview_main_mine4=view.findViewById(R.id.imageview_main_mine4);

        ((MainActivity)getActivity()).getMydrawerlayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        soundpool_main_mine =new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        music_main_mine = soundpool_main_mine.load(this.getContext(),R.raw.sound2,1);//回答正确！ 许可:CC0 作者:Nathaniel 来源:耳聆网 https://www.ear0.com/sound/13122
        imageview_main_mine0.setImageResource(R.drawable.image_main_mine0);
        imageview_main_mine1.setImageResource(R.drawable.image_main_mine1);
        imageview_main_mine2.setImageResource(R.drawable.image_main_mine2);
        imageview_main_mine3.setImageResource(R.drawable.image_main_mine3);
        imageview_main_mine4.setImageResource(R.drawable.image_main_mine4);
        MainActivity.back=false;
        dialog_mine = new MainRedDialog(this.getContext(), R.layout.dialog_main_red,"确认退出登录?");
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        id=sharedPreferences.getString("id","");
        phone=sharedPreferences.getString("phone","");
        textview_main_mine6.setText(id);
        textview_main_mine7.setText(phone);
        imageview_main_mine0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);//每次点击时，数组向前移动一位[1]
                    mHits[mHits.length - 1] = SystemClock.uptimeMillis();//为数组最后一位赋值

                if ((SystemClock.uptimeMillis() - mHits[0]) <= DURATION&&imageview_main_mine0.getTag().toString().equals("before")) {//检查是否是在我们规定的时间内
                    Vibrator vibrator = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100);
                    soundpool_main_mine.play(music_main_mine,1,1,0,0,1);
                    Toast.makeText(getContext(),"恭喜你发现了河海小彩蛋",Toast.LENGTH_LONG).show();
                    imageview_main_mine0.setImageResource(R.drawable.logo);
                    imageview_main_mine0.setTag(R.string.image_after);
                    fine=true;
                }
                if (fine){
                    Animation egg = AnimationUtils.loadAnimation(getContext(), R.anim.layout_animator_eggs);
                    imageview_main_mine0.startAnimation(egg);
                }
            }
        });
        button_main_mine1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {    //我的发布
                NavController navcontroller_toout= Navigation.findNavController(v);
                navcontroller_toout.navigate(R.id.action_menu_main_mine_to_main_mineout);
            }
        });

        button_main_mine2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //我的接单
                NavController navcontroller_toin=Navigation.findNavController(v);
                navcontroller_toin.navigate(R.id.action_menu_main_mine_to_main_minein);
            }
        });

        button_main_mine3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //退出登录
                Vibrator vibrator = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(40);
                dialog_mine.show();
                dialog_mine.cancel_red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_mine.dismiss();
                        Animation shake= AnimationUtils.loadAnimation(getContext(),R.anim.layout_animator_buttonx);
                        button_main_mine3.startAnimation(shake);
                    }
                });

                dialog_mine.sure_red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //推出登陆时，先改变Boolean值，再跳转
                        sprfMain= PreferenceManager.getDefaultSharedPreferences(getActivity());
                        editorMain=sprfMain.edit();
                        editorMain.putBoolean("main",false);
                        editorMain.commit();
                        Intent intent=new Intent(getActivity(), StartActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }
        });
        button_main_mine4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getMydrawerlayout().openDrawer(Gravity.RIGHT);
            }
        });

    }



}
