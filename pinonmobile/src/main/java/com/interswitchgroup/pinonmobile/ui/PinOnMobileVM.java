package com.interswitchgroup.pinonmobile.ui;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.interswitchgroup.pinonmobile.PinOnMobile;
import com.interswitchgroup.pinonmobile.interfaces.FailureCallback;
import com.interswitchgroup.pinonmobile.interfaces.SuccessCallback;

public class PinOnMobileVM extends ViewModel {

    private String LOG_TAG = this.getClass().getSimpleName();
    private PinOnMobile pinOnMobile;
    private ObservableBoolean loading = new ObservableBoolean(false);
    private SuccessCallback successCallback;
    private FailureCallback failureCallback;


    public void setPinOnMobile(String pin, String otp) throws Exception {
        loading.set(true);
        pinOnMobile.sendPin(pin,otp,successCallback,failureCallback);
    }

    public PinOnMobile getPinOnMobile() {
        return pinOnMobile;
    }

    public void setPinOnMobile(PinOnMobile pinOnMobile) {
        this.pinOnMobile = pinOnMobile;
    }

    public ObservableBoolean getLoading() {
        return loading;
    }

    public void setLoading(ObservableBoolean loading) {
        this.loading = loading;
    }

    public SuccessCallback getSuccessCallback() {
        return successCallback;
    }

    public void setSuccessCallback(SuccessCallback successCallback) {
        this.successCallback = successCallback;
    }

    public FailureCallback getFailureCallback() {
        return failureCallback;
    }

    public void setFailureCallback(FailureCallback failureCallback) {
        this.failureCallback = failureCallback;
    }
}
