package com.example.teamwork.Main.MainFms;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.teamwork.Main.MainActivity;
import com.example.teamwork.Main.MainBlueDialog;
import com.example.teamwork.Order;
import com.example.teamwork.R;

import java.sql.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainFmsFragment extends Fragment {
    private ImageView imageview_main_fms0;
    private RadioButton radiobutton_main_fms1;
    private RadioButton radiobutton_main_fms2;
    private RadioButton radiobutton_main_fms3;
    private RadioButton radiobutton_main_fms4;
    private EditText edittext_main_fms1;
    private EditText edittext_main_fms2;
    private EditText edittext_main_fms3;
    private Button button_main_fms1;
    private SoundPool soundpool_main_fms;
    private int music_main_fms;
    private boolean isOK_main_fms1=false;
    private boolean isOK_main_fms2=false;
    private boolean isOK_main_fms3=false;
    private boolean isOK_main_fms4=false;
    private boolean isOK_main_fms5=false;
    private boolean isOK_main_fms6=false;
    private boolean isOK_main_fms7=false;
    private boolean fine=false;
    private MainBlueDialog dialog_fms;



    private MainFmsViewModel mViewModel;
    public String facility;//取件处
    public  String objectId;//插入数据时生成的id
    private String id;
    private String phone;
    private final static int COUNTS = 6;//点击次数
    private final static long DURATION = 1200;//规定有效时间
    private long[] mHits = new long[COUNTS];
    public static MainFmsFragment newInstance() {
        return new MainFmsFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bmob.initialize(this.getContext(),"22859591a0207200359b133208256c5a");

        return inflater.inflate(R.layout.fragment_main_fms, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(MainFmsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageview_main_fms0=view.findViewById(R.id.imageview_main_fms0);
        imageview_main_fms0.setImageResource(R.drawable.image_main_fms0);
        radiobutton_main_fms1 =view.findViewById(R.id.radiobutton_main_fms1);
        radiobutton_main_fms2 =view.findViewById(R.id.radiobutton_main_fms2);
        radiobutton_main_fms3 =view.findViewById(R.id.radiobutton_main_fms3);
        radiobutton_main_fms4 =view.findViewById(R.id.radiobutton_main_fms4);
        edittext_main_fms1=view.findViewById(R.id.edittext_main_fms1);
        edittext_main_fms2=view.findViewById(R.id.edittext_main_fms2);
        edittext_main_fms3=view.findViewById(R.id.edittext_main_fms3);
        button_main_fms1=view.findViewById(R.id.button_main_fms1);
        MainActivity.back=true;
        soundpool_main_fms =new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        music_main_fms = soundpool_main_fms.load(this.getContext(),R.raw.sound2,1);//回答正确！ 许可:CC0 作者:Nathaniel 来源:耳聆网 https://www.ear0.com/sound/13122
        dialog_fms = new MainBlueDialog(this.getContext(), R.layout.dialog_main_blue,"确认发布?");
        ((MainActivity)getActivity()).getMydrawerlayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);



        button_main_fms1.setEnabled(false);

        imageview_main_fms0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);//每次点击时，数组向前移动一位[1]
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();//为数组最后一位赋值
                if ((SystemClock.uptimeMillis() - mHits[0]) <= DURATION&&imageview_main_fms0.getTag().toString().equals("before")) {//检查是否是在我们规定的时间内
                    Vibrator vibrator = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100);
                    soundpool_main_fms.play(music_main_fms,1,1,0,0,1);
                    Toast.makeText(getContext(),"恭喜你发现了滑稽小彩蛋",Toast.LENGTH_LONG).show();
                    imageview_main_fms0.setImageResource(R.drawable.happy);
                    imageview_main_fms0.setTag(R.string.image_after);
                    fine=true;
                }
                if (fine) {
                    Animation egg = AnimationUtils.loadAnimation(getContext(), R.anim.layout_animator_eggs);
                    imageview_main_fms0.startAnimation(egg);
                }
            }
        });

        radiobutton_main_fms1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    radiobutton_main_fms2.setChecked(false);
                    radiobutton_main_fms3.setChecked(false);
                    radiobutton_main_fms4.setChecked(false);
                    facility=radiobutton_main_fms1.getText().toString();
                    isOK_main_fms4=true;
                }
                else {
                    isOK_main_fms4=false;
                }
                    button_main_fms1.setEnabled(isOK_main_fms1&&isOK_main_fms2&&isOK_main_fms3
                            &&(isOK_main_fms4||isOK_main_fms5||isOK_main_fms6||isOK_main_fms7));
            }
        });
        radiobutton_main_fms2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    radiobutton_main_fms1.setChecked(false);
                    radiobutton_main_fms3.setChecked(false);
                    radiobutton_main_fms4.setChecked(false);
                    facility=radiobutton_main_fms2.getText().toString();
                    isOK_main_fms5=true;
                }
                else {
                    isOK_main_fms5=false;
                }
                button_main_fms1.setEnabled(isOK_main_fms1&&isOK_main_fms2&&isOK_main_fms3
                        &&(isOK_main_fms4||isOK_main_fms5||isOK_main_fms6||isOK_main_fms7));
            }
        });
        radiobutton_main_fms3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    radiobutton_main_fms1.setChecked(false);
                    radiobutton_main_fms2.setChecked(false);
                    radiobutton_main_fms4.setChecked(false);
                    facility=radiobutton_main_fms3.getText().toString();
                    isOK_main_fms6=true;
                }
                else {
                    isOK_main_fms6=false;
                }
                button_main_fms1.setEnabled(isOK_main_fms1&&isOK_main_fms2&&isOK_main_fms3
                        &&(isOK_main_fms4||isOK_main_fms5||isOK_main_fms6||isOK_main_fms7));
            }
        });
        radiobutton_main_fms4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    radiobutton_main_fms1.setChecked(false);
                    radiobutton_main_fms2.setChecked(false);
                    radiobutton_main_fms3.setChecked(false);
                    facility=radiobutton_main_fms4.getText().toString();
                    isOK_main_fms7=true;
                }
                else {
                    isOK_main_fms7=false;
                }
                button_main_fms1.setEnabled(isOK_main_fms1&&isOK_main_fms2&&isOK_main_fms3
                        &&(isOK_main_fms4||isOK_main_fms5||isOK_main_fms6||isOK_main_fms7));
            }
        });

        edittext_main_fms1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==0) {
                    isOK_main_fms1 = false;
                }
                else {
                    isOK_main_fms1=true;
                }
                button_main_fms1.setEnabled(isOK_main_fms1&&isOK_main_fms2&&isOK_main_fms3
                        &&(isOK_main_fms4||isOK_main_fms5||isOK_main_fms6||isOK_main_fms7));
            }
        });

        edittext_main_fms2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==0) {
                    isOK_main_fms2 = false;
                }
                else {
                    isOK_main_fms2=true;
                }
                button_main_fms1.setEnabled(isOK_main_fms1&&isOK_main_fms2&&isOK_main_fms3
                        &&(isOK_main_fms4||isOK_main_fms5||isOK_main_fms6||isOK_main_fms7));
            }
        });

        edittext_main_fms3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==0) {
                    isOK_main_fms3 = false;
                }
                else {
                    isOK_main_fms3=true;
                }
                button_main_fms1.setEnabled(isOK_main_fms1&&isOK_main_fms2&&isOK_main_fms3
                        &&(isOK_main_fms4||isOK_main_fms5||isOK_main_fms6||isOK_main_fms7));
            }
        });


        button_main_fms1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_fms.show();
                dialog_fms.cancel_blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_fms.dismiss();
                        Animation shake= AnimationUtils.loadAnimation(getContext(),R.anim.layout_animator_buttonclock);
                        button_main_fms1.startAnimation(shake);
                    }
                });
                dialog_fms.sure_blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_fms.dismiss();
                        String time;
                        android.icu.text.SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
                        java.sql.Date curDate = new Date(System.currentTimeMillis());
                        time=formatter.format(curDate);
                        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("user_info", Activity.MODE_PRIVATE);
                        id=sharedPreferences.getString("id","");
                        phone=sharedPreferences.getString("phone","");
                        Order order=new Order();
                        order.setExpressNumber(id+"#"+time);
                        order.setFacility(facility);
                        order.setPickupNumber(edittext_main_fms3.getText().toString());
                        order.setReceiveAddress(edittext_main_fms2.getText().toString());
                        order.setReceiveName(edittext_main_fms1.getText().toString());
                        order.setReceivePhone(phone);
                        order.setSubstituteID("");
                        order.setSubstitutePhone("");
                        order.setState("未接单");
                        order.setReceiveID(id);
                        order.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e==null) {
                                    objectId=order.getObjectId();
                                    edittext_main_fms1.setText("");
                                    edittext_main_fms2.setText("");
                                    edittext_main_fms3.setText("");
                                    edittext_main_fms3.clearFocus();
                                    edittext_main_fms1.clearFocus();
                                    edittext_main_fms2.clearFocus();
                                    radiobutton_main_fms1.setChecked(false);
                                    radiobutton_main_fms2.setChecked(false);
                                    radiobutton_main_fms3.setChecked(false);
                                    radiobutton_main_fms4.setChecked(false);
                                    Toast.makeText(getContext(),"发布成功",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }




}
