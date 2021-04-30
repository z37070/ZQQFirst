package com.example.teamwork.Main.MainMine.MainMinedrawer;

import androidx.appcompat.widget.ButtonBarLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.teamwork.Main.MainActivity;
import com.example.teamwork.Main.MainTextDialog;
import com.example.teamwork.R;
import com.example.teamwork.Userinfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainMinedrawerFragment extends Fragment {

    private MainMinedrawerViewModel mViewModel;
    private Button button_main_minedrawer1;
    private MainTextDialog dialog_drawer;
    private String newPassword;
    private  String objid;
    @SuppressLint("StaticFieldLeak")
    public static ImageView imageview_main_minedrawer2;
    @SuppressLint("StaticFieldLeak")
    public static ImageView imageview_main_minedrawer3;
    @SuppressLint("StaticFieldLeak")
    public static ImageView imageview_main_minedrawer4;

    public static MainMinedrawerFragment newInstance() {
        return new MainMinedrawerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bmob.initialize(this.getContext(),"22859591a0207200359b133208256c5a");
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        String id=sharedPreferences.getString("id","");
        BmobQuery<Userinfo> query=new BmobQuery<>();
        query.addWhereEqualTo("id",id);
        query.findObjects(new FindListener<Userinfo>() {
            @Override
            public void done(List<Userinfo> list, BmobException e) {
                if(e==null){
                    Userinfo userinfo=list.get(0);
                    objid=userinfo.getObjectId();
                }
            }
        });
        return inflater.inflate(R.layout.fragment_main_minedrawer, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainMinedrawerViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog_drawer=new MainTextDialog(getContext(),R.layout.dialog_main_text);
        button_main_minedrawer1=view.findViewById(R.id.button_main_minedrawer1);
        imageview_main_minedrawer2=view.findViewById(R.id.imageview_main_minedrawer2);
        imageview_main_minedrawer3=view.findViewById(R.id.imageview_main_minedrawer3);
        imageview_main_minedrawer4=view.findViewById(R.id.imageview_main_minedrawer4);
        MainActivity.back=false;



        button_main_minedrawer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_drawer.show();
                dialog_drawer.cancel_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_drawer.dismiss();
                        dialog_drawer=new MainTextDialog(getContext(),R.layout.dialog_main_text);
                        Animation shake= AnimationUtils.loadAnimation(getContext(),R.anim.layout_animator_buttonclock);
                        button_main_minedrawer1.startAnimation(shake);
                    }
                });

                dialog_drawer.sure_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newPassword=dialog_drawer.edittext_text.getText().toString();
                        Userinfo userinfo=new Userinfo();
                        userinfo.setPassword(newPassword);
                        userinfo.update(objid,new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(getActivity(),"密码修改成功",Toast.LENGTH_LONG).show();
                            }
                            }
                        });
                        dialog_drawer.dismiss();
                        dialog_drawer=new MainTextDialog(getContext(),R.layout.dialog_main_text);
                        Animation shake= AnimationUtils.loadAnimation(getContext(),R.anim.layout_animator_imageclock);
                        imageview_main_minedrawer2.startAnimation(shake);
                        imageview_main_minedrawer3.startAnimation(shake);
                        imageview_main_minedrawer4.startAnimation(shake);

                    }
                });
            }
        });







    }


}