package com.example.an.pnaandroid.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.an.pnaandroid.R;
import com.example.an.pnaandroid.library.UtilsLog;

public class NotifyConnectionDialog extends Dialog implements View.OnClickListener{
    private NotifyConnectionDialogListener dialogListener;
    private Context mParentContext;

    public NotifyConnectionDialog(Context context, String title, String message) {
        super(context, R.style.AppDialogTheme);
        mParentContext = context;
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_notify);
        if (getWindow() != null) {
            View v = getWindow().getDecorView();
            v.setBackgroundResource(android.R.color.transparent);
        }
        TextView tv = findViewById(R.id.txt_title_dialog_notify);
        tv.setText(title);
        Button btOK = findViewById(R.id.btn_yes_dialog_notify);
        btOK.setOnClickListener(this);
    }

    public void setDialogListener(NotifyConnectionDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    public void dismiss() {
        try {
            Activity act = (Activity) mParentContext;
            if (act != null && !act.isFinishing()) {
                super.dismiss();
                if (dialogListener != null) {
                    dialogListener.onDismissNotifyConnectionDialog();
                }
            }
        } catch (RuntimeException e) {
            UtilsLog.l(e);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_yes_dialog_notify:
                dismiss();
                break;
            default:
                break;
        }
    }
    public interface NotifyConnectionDialogListener {
        void onDismissNotifyConnectionDialog();
    }
}
