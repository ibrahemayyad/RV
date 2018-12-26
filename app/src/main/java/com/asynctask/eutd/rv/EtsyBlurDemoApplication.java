package com.asynctask.eutd.rv;

import android.app.Application;

public class EtsyBlurDemoApplication extends Application {

    private static final String TAG = EtsyBlurDemoApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        SmartAsyncPolicyHolder.INSTANCE.init(getApplicationContext());
    }
}
