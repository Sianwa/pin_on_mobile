package com.interswitchgroup.pinonmobile.ui;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Toast;

import com.interswitchgroup.pinonmobile.PinOnMobile;
import com.interswitchgroup.pinonmobile.databinding.ActivityPinOnMobileBinding;
import com.interswitchgroup.pinonmobile.R;
import com.interswitchgroup.pinonmobile.models.Account;
import com.interswitchgroup.pinonmobile.models.Institution;

import java.util.ArrayList;
import java.util.List;

import co.paystack.android.design.widget.PinPadView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PinOnMobileActivity extends AppCompatActivity {
    private ActivityPinOnMobileBinding binding;
    List<String> pages = new ArrayList<>();
    PinOnMobile pinOnMobile;
    Institution institution;
    Account account;
    String otp = "";
    String newPin = "";
    String confirmNewPin = "";
    Integer currentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPinOnMobileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();
        pages.add("Please enter your otp");
        pages.add("please enter your pin");
        pages.add("Please confirm your pin");
        if (extras != null) {
            this.institution = (Institution) extras.getSerializable("Institution");
            this.account = (Account) extras.getSerializable("Account");
            //The key argument here must match that used in the other activity
        }
        try {
            this.pinOnMobile = PinOnMobile.getInstance(this, this.institution, this.account);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
        // loading dialogue + progrees bar
        // enum of steps
        // change pin
        // 1. give me your otp , set new pin , confirm new pin
        // hii ni ya first pin
        binding.pinpadView.setOnSubmitListener(new PinPadView.OnSubmitListener() {
            @Override
            public void onCompleted(String pin) {
                // listen for when the "done" button is clicked
                // and the pin is complete
                if (currentPage == 0){
                    otp = pin;
                }
                if (currentPage == 1){
                    newPin = pin;
                }
                if (currentPage == 2){
                    confirmNewPin = pin;
                }
                currentPage++;
                binding.pinpadView.clear();
                binding.pinpadView.setPromptText(pages.get(currentPage));
            }
            // block back button
            @Override
            public void onIncompleteSubmit(String pin) {
                // listen for when the "done" button is clicked
                // and the pin is incomplete
                System.out.println("incomplete otp");
            }
            });



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        // check current page
        // if page == 0
    }




}