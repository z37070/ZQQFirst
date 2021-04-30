package com.example.teamwork.Main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.teamwork.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTextDialog extends Dialog {
    private Context context_text;
    private int layoutID_text;
    public TextView cancel_text, sure_text;
    public EditText edittext_text;



    public MainTextDialog(Context context_text, int layoutID_text) {
        super(context_text, R.style.dialog_main);
        this.context_text = context_text;
        this.layoutID_text = layoutID_text;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.dialog_main);
        setContentView(layoutID_text);


        WindowManager windowManager = ((Activity) context_text).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth()*5/7;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);
        cancel_text =findViewById(R.id.textview_dialog_text1);
        sure_text =findViewById(R.id.textview_dialog_text2);
        edittext_text=findViewById(R.id.edittext_dialog_text0);
        sure_text.setEnabled(false);

        edittext_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Pattern p = Pattern.compile("^[A-Za-z0-9]{4,8}$");
                Matcher m = p.matcher(s.toString());
                if(m.matches()) {
                    sure_text.setEnabled(true);
                    sure_text.setText("确认");
                }
                else if(s.toString().length()==0) {
                    sure_text.setEnabled(false);
                    sure_text.setText("请按要求填写");
                }
                else{
                    sure_text.setEnabled(false);
                    sure_text.setText("请按要求填写");
                }
            }
        });

    }

}
