package com.android.iab.utility;

import android.app.Application;

import com.adform.sdk.utils.AdApplicationService;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by claritus on 22/6/16.
 */
public class MyApplication extends Application implements AdApplicationService.ServiceListener {
    private AdApplicationService adService;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        // [mandatory] Initializes application service
        adService = AdApplicationService.init();
    }

    // [mandatory] A mandatory method for the SDK to work properly
    @Override
    public AdApplicationService getAdService() {
        return adService;
    }
}
