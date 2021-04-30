package com.example.teamwork.Register;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.teamwork.R;
import com.example.teamwork.Start.StartActivity;
import com.example.teamwork.StartBean;
import com.example.teamwork.Userinfo;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends Activity{

    private EditText editText_register1;
    private EditText editText_register2;
    private EditText editText_register3;
    private EditText editText_register4;

    private Button button_register1;
    private Button button_register2;
    private ImageView imageview_register1;
    private ImageView imageview_register2;
    private ImageView imageview_register3;
    private ImageView imageview_register4;
    private ImageView imageview_register5;
    private ImageView imageview_register6;
    private ImageView imageview_register7;
    private ImageView imageview_register8;

    //SharedPreferences sharedPre;
    private boolean isOK_register1 = false;
    private boolean isOK_register2 = false;
    private boolean isOK_register3 = false;
    private boolean isOK_register4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this,"22859591a0207200359b133208256c5a");//初始化Bmob
        Userinfo userinfo=new Userinfo();
        //StartBean startBean=new StartBean();
        imageview_register1 = findViewById(R.id.imageview_register1);
        imageview_register2 = findViewById(R.id.imageview_register2);
        imageview_register3 = findViewById(R.id.imageview_register3);
        imageview_register4 = findViewById(R.id.imageview_register4);
        imageview_register5 = findViewById(R.id.imageview_register5);
        imageview_register6 = findViewById(R.id.imageview_register6);
        imageview_register7 = findViewById(R.id.imageview_register7);
        imageview_register8 = findViewById(R.id.imageview_register8);


        editText_register1 = findViewById(R.id.edittext_register1);
        editText_register2 = findViewById(R.id.edittext_register2);
        editText_register3 = findViewById(R.id.edittext_register3);
        editText_register4 = findViewById(R.id.edittext_register4);


        button_register1 = findViewById(R.id.button_register1);
        button_register2=findViewById(R.id.button_register2);

        imageview_register1.setImageDrawable(null);
        imageview_register2.setImageDrawable(null);
        imageview_register3.setImageDrawable(null);
        imageview_register4.setImageDrawable(null);
        imageview_register5.setImageDrawable(null);
        imageview_register6.setImageDrawable(null);
        imageview_register7.setImageDrawable(null);
        imageview_register8.setImageDrawable(null);

        button_register1.setEnabled(false);


        editText_register1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                Pattern p = Pattern.compile("^[A-Za-z0-9]{4,8}$");
                Matcher m = p.matcher(s.toString());
                if (m.matches()) {
                    imageview_register1.setImageResource(R.drawable.image_register_ok);
                    imageview_register4.setImageDrawable(null);
                    isOK_register1 = true;
                } else if (s.toString().length() == 0) {
                    imageview_register1.setImageDrawable(null);
                    imageview_register4.setImageDrawable(null);
                    isOK_register1 = false;
                } else {
                    imageview_register1.setImageResource(R.drawable.image_register_wrong);
                    imageview_register4.setImageResource(R.drawable.image_register_warn);
                    isOK_register1 = false;
                }
                if (isOK_register1 && isOK_register2 && isOK_register3 && isOK_register4) {
                    button_register1.setEnabled(true);
                } else {
                    button_register1.setEnabled(false);
                }
            }
        });

        editText_register2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                Pattern p = Pattern.compile("^[A-Za-z0-9]{4,8}$");
                Matcher m = p.matcher(s.toString());
                if (m.matches()) {
                    imageview_register2.setImageResource(R.drawable.image_register_ok);
                    imageview_register5.setImageDrawable(null);
                    isOK_register2 = true;
                } else if (s.toString().length() == 0) {
                    imageview_register2.setImageDrawable(null);
                    imageview_register5.setImageDrawable(null);
                    isOK_register2 = false;
                } else {
                    imageview_register2.setImageResource(R.drawable.image_register_wrong);
                    imageview_register5.setImageResource(R.drawable.image_register_warn);
                    isOK_register2 = false;
                }
                if (!s.toString().equals(editText_register3.getText().toString())) {
                    imageview_register3.setImageResource(R.drawable.image_register_wrong);
                    imageview_register6.setImageResource(R.drawable.image_register_warn);
                    isOK_register3 = false;
                } else {
                    imageview_register3.setImageResource(R.drawable.image_register_ok);
                    imageview_register6.setImageDrawable(null);
                    isOK_register3 = true;
                }
                if (s.toString().length() == 0 && editText_register3.getText().toString().length() == 0) {
                    imageview_register3.setImageDrawable(null);
                    imageview_register6.setImageDrawable(null);
                    isOK_register3 = false;
                }
                if (isOK_register1 && isOK_register2 && isOK_register3 && isOK_register4) {
                    button_register1.setEnabled(true);
                } else {
                    button_register1.setEnabled(false);
                }
            }
        });

        editText_register3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                Pattern p = Pattern.compile(editText_register2.getText().toString());
                Matcher m = p.matcher(s.toString());
                if (m.matches() && s.toString().length() != 0) {
                    imageview_register3.setImageResource(R.drawable.image_register_ok);
                    imageview_register6.setImageDrawable(null);
                    isOK_register3 = true;
                } else if (editText_register2.getText().toString().length() == 0 && s.toString().length() == 0) {
                    imageview_register3.setImageDrawable(null);
                    imageview_register6.setImageDrawable(null);
                    isOK_register3 = false;
                } else {
                    imageview_register3.setImageResource(R.drawable.image_register_wrong);
                    imageview_register6.setImageResource(R.drawable.image_register_warn);
                    isOK_register3 = false;
                }
                if (isOK_register1 && isOK_register2 && isOK_register3 && isOK_register4) {
                    button_register1.setEnabled(true);
                } else {
                    button_register1.setEnabled(false);
                }
            }
        });

        editText_register4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Pattern p = Pattern.compile("^[0-9]{11}$");
                Matcher m = p.matcher(s.toString());
                if (m.matches()) {
                    imageview_register7.setImageResource(R.drawable.image_register_ok);
                    imageview_register8.setImageDrawable(null);
                    isOK_register4 = true;
                } else if (s.toString().length() == 0) {
                    imageview_register7.setImageDrawable(null);
                    imageview_register8.setImageDrawable(null);
                    isOK_register4 = false;
                } else {
                    imageview_register7.setImageResource(R.drawable.image_register_wrong);
                    imageview_register8.setImageResource(R.drawable.image_register_warn);
                    isOK_register4 = false;
                }
                if (isOK_register1 && isOK_register2 && isOK_register3 && isOK_register4) {
                    button_register1.setEnabled(true);
                } else {
                    button_register1.setEnabled(false);
                }


            }
        });


        button_register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id= editText_register1.getText().toString();
                String password=editText_register2.getText().toString();
                String phone= editText_register4.getText().toString();

                BmobQuery<Userinfo> query = new BmobQuery<Userinfo>();
                query.addWhereEqualTo("id",id);
                query.findObjects(new FindListener<Userinfo>() {
                    @Override
                    public void done(List<Userinfo> list, BmobException e) {
                        if(e==null){
                            if (list.size()>0){
                                Toast.makeText(RegisterActivity.this,"注册失败,用户名已存在",Toast.LENGTH_LONG).show();
                            }
                            else{
                                userinfo.setId(id);
                                userinfo.setPassword(password);
                                userinfo.setPhone(phone);
                                userinfo.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e!=null) Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });
                                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });
            }
        });

        button_register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}