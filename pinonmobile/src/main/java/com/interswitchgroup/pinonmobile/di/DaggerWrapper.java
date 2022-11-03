package com.interswitchgroup.pinonmobile.di;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.interswitchgroup.pinonmobile.models.Institution;

public class DaggerWrapper {
    private static MainComponent mComponent;

    public static MainComponent getComponent(Context context, Institution institution) throws PackageManager.NameNotFoundException {
        if (mComponent == null) {
            initComponent(context, institution);
        }
        return mComponent;
    }

    private static void initComponent(Context context, Institution institution) throws PackageManager.NameNotFoundException {
        ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        String baseUrl = String.valueOf(ai.metaData.get("interswitch-kenya-limited.pin_on_mobile.base_url"));

        mComponent = DaggerMainComponent
                .builder()
                .netModule(new NetModule(baseUrl, institution.getClientId(), institution.getClientSecret(),institution.getInstitutionId() ))
                .build();
    }
}
