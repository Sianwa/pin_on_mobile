package com.interswitchgroup.pinonmobile.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.interswitchgroup.pinonmobile.R;

public class CustomProgressBar {
    AnimationDrawable animationDrawable;
    Dialog dialog;
    Context context;
    String title;

    public void createDialog(Context context, String title){
        this.context = context;
        this.title = title;
    }

    @SuppressLint("SetTextI18n")
    public void showDialog(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View view = inflater.inflate(R.layout.loading_screen, null, false);

        ConstraintLayout cpBgView = view.findViewById(R.id.customProgressBar);
        ImageView loadingAnimationDrawable = view.findViewById(R.id.running_man);
        animationDrawable = (AnimationDrawable) loadingAnimationDrawable.getDrawable();

        cpBgView.setBackgroundColor(Color.parseColor("#60000000"));

        animationDrawable.start();
        this.dialog = new Dialog(context, R.style.Widget);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismissDialog(){
        try {
            animationDrawable.stop();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        dialog.dismiss();
    }
}
