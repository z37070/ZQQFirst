package com.example.teamwork.Start;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;

import com.example.teamwork.Main.MainActivity;
import com.example.teamwork.R;
import com.example.teamwork.Register.RegisterActivity;
import com.example.teamwork.Userinfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.util.V;

public class StartActivity extends Activity {
    private ImageView image_start;
    private EditText edittext_start1;
    private EditText edittext_start2;
    private Button button_start1;
    private TextView textview_start1;
    private TextView textview_start2;
    private Boolean b=false;
    private StartDialog dialog_start;
    private boolean isOK_start1=false;
    private boolean isOK_start2=false;

    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //在加载布局文件前判断是否登陆过
        sprfMain= PreferenceManager.getDefaultSharedPreferences(this);
        editorMain=sprfMain.edit();

        //如果调试时，不想跳过登录界面（比如调试注册界面时），运行应用并退出登录，再把以下if块注释掉即可
        if(sprfMain.getBoolean("main",false)) {
            //.getBoolean("main",false)；当找不到"main"所对应的键值是默认返回false
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            StartActivity.this.finish();
        }

        setContentView(R.layout.activity_start);
        Bmob.initialize(this,"22859591a0207200359b133208256c5a");//初始化Bmob
        image_start=findViewById(R.id.image_start);
        image_start.setImageResource(R.drawable.image_start);
        edittext_start1=findViewById(R.id.edittext_start1);
        edittext_start2=findViewById(R.id.edittext_start2);
        button_start1=findViewById(R.id.button_start1);
        textview_start1=findViewById(R.id.textview_start1);
        textview_start2=findViewById(R.id.textview_start2);
        dialog_start=new StartDialog(this,R.layout.dialog_start);

        button_start1.setEnabled(false);

        edittext_start1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    edittext_start1.setHint("");
                }
                else {
                    edittext_start1.setHint(R.string.edittext_start1_hint);
                }
            }
        });
        edittext_start2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    edittext_start2.setHint("");
                }
                else {
                    edittext_start2.setHint(R.string.edittext_strat2_hint);
                }
            }
        });

        edittext_start1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length()==0) {
                    isOK_start1=false;
                }
                else {
                    isOK_start1=true;
                }
                if (isOK_start1&&isOK_start2) {
                    button_start1.setEnabled(true);
                }
                else {
                    button_start1.setEnabled(false);
                }
            }
        });

        edittext_start2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length()==0) {
                    isOK_start2=false;
                }
                else {
                    isOK_start2=true;
                }
                if (isOK_start1&&isOK_start2) {
                    button_start1.setEnabled(true);
                }
                else {
                    button_start1.setEnabled(false);
                }
            }
        });

        button_start1.setOnClickListener(new View.OnClickListener() {  //登录
            @Override
            public void onClick(View v) {
                String id=edittext_start1.getText().toString();
                String password=edittext_start2.getText().toString();
                BmobQuery<Userinfo> eq1=new BmobQuery<>();
                BmobQuery<Userinfo> eq2=new BmobQuery<>();
                eq1.addWhereEqualTo("id", id);
                eq2.addWhereEqualTo("password", password);
                List<BmobQuery<Userinfo>> andQuerys = new ArrayList<BmobQuery<Userinfo>>();
                andQuerys.add(eq1);
                andQuerys.add(eq2);
                BmobQuery<Userinfo> query = new BmobQuery<Userinfo>();
                query.and(andQuerys);
                String str;
                query.findObjects(new FindListener<Userinfo>() {
                    @Override
                    public void done(List<Userinfo> list, BmobException e) {
                            if(e==null){
                                if(list.size()!=0) {
                                    Userinfo userinfo=list.get(0);
                                    String phone=userinfo.getPhone();
                                    SharedPreferences mySharedPreferences= getSharedPreferences("user_info", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=mySharedPreferences.edit();
                                    editor.putString("id",id);
                                    editor.putString("phone",phone);
                                    editor.apply();
                                    //登陆时，先存入Boolean值，再跳转
                                    editorMain.putBoolean("main",true);
                                    editorMain.commit();
                                    Intent intent=new Intent(StartActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Animation shake= AnimationUtils.loadAnimation(StartActivity.this,R.anim.layout_animator_buttonx);
                                    edittext_start1.startAnimation(shake);
                                    edittext_start2.startAnimation(shake);
                                    Toast.makeText(StartActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
                                }
                            }
                    }
                });
            }
        });

        textview_start1.setOnClickListener(new View.OnClickListener() {   //注册
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textview_start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_start.show();
                dialog_start.cancel_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_start.dismiss();
                    }
                });

                dialog_start.sure_start1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String url = "mqqwpa://im/chat?chat_type=wpa&uin=1511029720";//uin是发送过去的qq号码
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(StartActivity.this,"请先安装手机QQ",Toast.LENGTH_LONG).show();
                        }
                        dialog_start.dismiss();
                    }
                });

                dialog_start.sure_start2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_start=new Intent();
                        intent_start.setAction(Intent.ACTION_DIAL);
                        intent_start.setData(Uri.parse("tel:"+"15295785521"));
                        startActivity(intent_start);
                        dialog_start.dismiss();
                    }
                });
            }
        });

    }
}