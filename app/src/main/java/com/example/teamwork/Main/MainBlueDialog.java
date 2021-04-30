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

public class MainBlueDialog extends Dialog {
    private Context context_blue;
    private int layoutID_blue;
    public TextView cancel_blue,sure_blue;
    private TextView head_blue;
    private String title_blue;



    public MainBlueDialog(Context context_blue, int layoutID_blue, String title_blue) {
        super(context_blue, R.style.dialog_main);
        this.context_blue = context_blue;
        this.layoutID_blue = layoutID_blue;
        this.title_blue = title_blue;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.dialog_main);
        setContentView(layoutID_blue);


        WindowManager windowManager = ((Activity) context_blue).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth()*5/7;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);
        cancel_blue=findViewById(R.id.textview_dialog_blue1);
        sure_blue=findViewById(R.id.textview_dialog_blue2);
        head_blue=findViewById(R.id.textview_dialog_blue0);
        head_blue.setText(title_blue);

    }

}
