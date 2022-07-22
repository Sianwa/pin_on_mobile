package com.interswitchgroup.pinonmobile.ui;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.interswitchgroup.pinonmobile.PinOnMobile;
import com.interswitchgroup.pinonmobile.databinding.ActivityPinOnMobileBinding;
import com.interswitchgroup.pinonmobile.R;
import com.interswitchgroup.pinonmobile.interfaces.FailureCallback;
import com.interswitchgroup.pinonmobile.interfaces.SuccessCallback;
import com.interswitchgroup.pinonmobile.models.Account;
import com.interswitchgroup.pinonmobile.models.Institution;
import com.interswitchgroup.pinonmobile.utils.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

import co.paystack.android.design.widget.PinPadView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PinOnMobileActivity extends AppCompatActivity {
    private ActivityPinOnMobileBinding binding;
    private final String LOG_TAG = this.getClass().getSimpleName();
    List<String> pages = new ArrayList<>();
    private ObservableBoolean loading = new ObservableBoolean(false);
    PinOnMobile pinOnMobile;
    Institution institution;
    Account account;
    private String otp = "";
    private String newPin = "";
    String confirmNewPin = "";
    Integer currentPage = 0;

    public ObservableBoolean getLoading() {
        return loading;
    }

    public void setLoading(ObservableBoolean loading) {
        this.loading = loading;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pages.add("Please enter your otp");
        pages.add("Please enter your pin");
        pages.add("Please confirm your pin");
        binding = ActivityPinOnMobileBinding.inflate(getLayoutInflater());
        binding.pinpadView.setPromptText(pages.get(currentPage));
        setContentView(binding.getRoot());


        Bundle extras = getIntent().getExtras();
        try {
//            pinOnMobile.setSuccessCallback(response -> {
//                PinOnMobileActivity.this.finish();
//                pinOnMobile.getSuccessCallback().onSuccess(response);
//            });
//            pinOnMobile.setFailureCallback(error -> {
//                PinOnMobileActivity.this.finish();
//                pinOnMobile.getSuccessCallback().onSuccess(error);
//            });
            if (extras != null) {
                this.institution = (Institution) extras.getSerializable("Institution");
                this.account = (Account) extras.getSerializable("Account");
                //The key argument here must match that used in the other activity
            }
            this.pinOnMobile = PinOnMobile.getInstance(this, this.institution, this.account);
            this.pinOnMobile.generateOtp();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            pinOnMobile.getFailureCallback().onError(e);
            PinOnMobileActivity.this.finish();
        }


        // loading dialogue + progress bar
        binding.pinpadView.setOnSubmitListener(new PinPadView.OnSubmitListener() {
            @Override
            public void onCompleted(String pin) {
                // listen for when the "done" button is clicked
                if (currentPage == 0){setOtp(pin);}
                if (currentPage == 1){
                    setNewPin(pin);
                }
                if (currentPage == 2){
                    setConfirmNewPin(pin);
                    setPin();
                }
                if(currentPage < 2){
                    currentPage++;
                }
                binding.pinpadView.clear();
                binding.pinpadView.setPromptText(pages.get(currentPage));
            }
            // block back button
            @Override
            public void onIncompleteSubmit(String pin) {
                Log.i(LOG_TAG, "incomplete otp");
            }
            });



    }

    private void setPin() {
        try {
            Log.d(LOG_TAG, "setting pin");
            if(getNewPin().equalsIgnoreCase(getConfirmNewPin())){
                pinOnMobile.sendPin(getNewPin(),getOtp(),pinOnMobile.getSuccessCallback(), pinOnMobile.getFailureCallback());
                PinOnMobileActivity.this.finish();
            }else {
                currentPage = 0;
                Snackbar.make(binding.pinpadView, "The two pins are not the same", Snackbar.LENGTH_LONG)
                        .show();
            }

        } catch (Exception e) {
            Log.d(LOG_TAG, e.getMessage());
            pinOnMobile.getFailureCallback().onError(e);
            PinOnMobileActivity.this.finish();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if(currentPage == 0){
            pinOnMobile.getFailureCallback().onError(new Exception("User exited before finishing"));
            PinOnMobileActivity.this.finish();
        }else {
            currentPage--;
            binding.pinpadView.clear();
            binding.pinpadView.setPromptText(pages.get(currentPage));
        }
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }

    public String getConfirmNewPin() {
        return confirmNewPin;
    }

    public void setConfirmNewPin(String confirmNewPin) {
        this.confirmNewPin = confirmNewPin;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}