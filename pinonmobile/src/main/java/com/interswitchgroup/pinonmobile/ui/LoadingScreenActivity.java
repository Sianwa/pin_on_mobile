package com.interswitchgroup.pinonmobile.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.interswitchgroup.pinonmobile.PinOnMobile;
import com.interswitchgroup.pinonmobile.models.Account;
import com.interswitchgroup.pinonmobile.models.Institution;

public class LoadingScreenActivity extends AppCompatActivity {
    private Handler mHandler;
    Institution institution;
    Account account;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        //get extras
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            this.institution =(Institution) extras.getSerializable("Institution");
            this.account = (Account) extras.getSerializable("Account");
        }

        mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        //todo: initialize library
                        startPinService();
                    }
                }
        );
    }

    private void startPinService() {
        try {
            PinOnMobile pinOnMobile = PinOnMobile.getInstance(LoadingScreenActivity.this, institution, account);
            pinOnMobile.setPin(null, null);
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
