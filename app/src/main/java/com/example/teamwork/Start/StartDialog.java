package com.example.teamwork.Start;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.teamwork.R;

public class StartDialog extends Dialog {
    private Context context_start;
    private int layoutID_start;
    public TextView cancel_start,sure_start1,sure_start2;

    public StartDialog(Context context_start, int layoutID_start) {
        super(context_start, R.style.dialog_main);
        this.context_start = context_start;
        this.layoutID_start = layoutID_start;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.dialog_main);
        setContentView(layoutID_start);


        WindowManager windowManager = ((Activity) context_start).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth()*5/7;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);
        cancel_start=findViewById(R.id.textview_dialog_start1);
        sure_start1=findViewById(R.id.textview_dialog_start2);
        sure_start2=findViewById(R.id.textview_dialog_start3);
    }
}
