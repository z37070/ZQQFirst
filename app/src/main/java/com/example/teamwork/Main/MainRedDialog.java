package com.example.teamwork.Main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.teamwork.R;

public class MainRedDialog extends Dialog {
    private Context context_red;
    private int layoutID_red;
    public TextView cancel_red, sure_red;
    private TextView head_red;
    private String title_red;



    public MainRedDialog(Context context_red, int layoutID_red, String title_red) {
        super(context_red, R.style.dialog_main);
        this.context_red = context_red;
        this.layoutID_red = layoutID_red;
        this.title_red = title_red;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.dialog_main);
        setContentView(layoutID_red);


        WindowManager windowManager = ((Activity) context_red).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth()*5/7;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);
        cancel_red =findViewById(R.id.textview_dialog_red1);
        sure_red =findViewById(R.id.textview_dialog_red2);
        head_red =findViewById(R.id.textview_dialog_red0);
        head_red.setText(title_red);


    }







}
