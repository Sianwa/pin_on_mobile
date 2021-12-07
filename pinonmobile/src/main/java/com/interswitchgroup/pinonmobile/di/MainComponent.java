package com.interswitchgroup.pinonmobile.di;

import com.interswitchgroup.pinonmobile.PinOnMobile;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                NetModule.class
        })
public interface MainComponent {
    void inject(PinOnMobile pinOnMobile);
}
