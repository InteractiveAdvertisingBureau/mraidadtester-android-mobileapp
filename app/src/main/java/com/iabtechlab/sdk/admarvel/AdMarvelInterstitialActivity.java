package com.iabtechlab.sdk.admarvel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.admarvel.android.ads.AdMarvelActivity;
import com.admarvel.android.ads.AdMarvelAd;
import com.admarvel.android.ads.AdMarvelInterstitialAds;
import com.admarvel.android.ads.AdMarvelInterstitialAds.AdMarvelInterstitialAdListener;
import com.admarvel.android.ads.AdMarvelUtils;
import com.admarvel.android.ads.AdMarvelUtils.ErrorReason;
import com.admarvel.android.ads.AdMarvelUtils.SDKAdNetwork;
import com.iabtechlab.R;
import com.iabtechlab.utility.IntentKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AdMarvelInterstitialActivity extends Activity implements
        AdMarvelInterstitialAdListener {

    private final int REQUEST_INTERVAL = 20000;
    private String _siteId = "125546";
    private String _partnerId = "1dd21b33bd603c95";
    private AdMarvelInterstitialAds adMarvelInterstitialAds;
    private AdMarvelActivity adMarvelActivity = null;

    private Timer requestIntervalTimer;

    private TextView header_text;
    private Runnable TimerRunnable = new Runnable() {
        public void run() {

            getAd();

        }
    };

    private void scheduleTimeThread() {
        requestIntervalTimer = new Timer();
        requestIntervalTimer.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(TimerRunnable);

            }
        }, 0, REQUEST_INTERVAL);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    /**
     * Called when the activity is first created.
     */
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_ad_screen);


        header_text = findViewById(R.id.header_text);

        //Back button clicked on Header to go Back
        header_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Set Screen Title
        header_text.setText(getResources().getString(R.string.lebel_interstitial));

        // Calling SDK initalize API
        Map<SDKAdNetwork, String> publisherIds = new HashMap<SDKAdNetwork, String>();
        AdMarvelUtils.initialize(this, publisherIds);

        // Enabling logs (optional)
        // AdMarvelUtils.enableLogging(true);

        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        adMarvelInterstitialAds = new AdMarvelInterstitialAds(
                getApplicationContext(), 0, 0x726D6D, 0x00FF00,
                0x000000);


        getAd();

    }

    private void getAd() {

        Map<String, Object> targetParams = new HashMap<String, Object>();


        targetParams.put("AD_HTML", getIntent().getStringExtra(IntentKey.SCRIPT));
        adMarvelInterstitialAds
                .setListener(AdMarvelInterstitialActivity.this);


        adMarvelInterstitialAds.requestNewInterstitialAd(AdMarvelInterstitialActivity.this, targetParams, _partnerId, _siteId);

        adMarvelInterstitialAds.setEnableClickRedirect(true);
        setResult();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setResult() {
        setResult(Activity.RESULT_OK);
    }

    @Override
    public void onRequestInterstitialAd(AdMarvelInterstitialAds adMarvelInterstitialAds) {

    }

    @Override
    public void onReceiveInterstitialAd(SDKAdNetwork sdkAdNetwork, AdMarvelInterstitialAds adMarvelInterstitialAds, AdMarvelAd adMarvelAd) {
        if (adMarvelInterstitialAds.isInterstitialAdAvailable()) {
            boolean isDisplayed = adMarvelInterstitialAds.displayInterstitial(this, sdkAdNetwork, adMarvelAd);
        }

    }

    @Override
    public void onFailedToReceiveInterstitialAd(SDKAdNetwork sdkAdNetwork, AdMarvelInterstitialAds adMarvelInterstitialAds, int i, ErrorReason errorReason) {
        Log.e("admarvel", "onFailedToReceiveInterstitialAd");
    }

    @Override
    public void onCloseInterstitialAd(AdMarvelInterstitialAds adMarvelInterstitialAds) {

        if (this.adMarvelActivity != null) {
            this.adMarvelActivity.finish();
        }
    }

    @Override
    public void onAdmarvelActivityLaunched(AdMarvelActivity adMarvelActivity, AdMarvelInterstitialAds adMarvelInterstitialAds) {

        this.adMarvelActivity = adMarvelActivity;
    }

    @Override
    public void onClickInterstitialAd(String s, AdMarvelInterstitialAds adMarvelInterstitialAds) {

    }

    @Override
    public void onInterstitialDisplayed(AdMarvelInterstitialAds adMarvelInterstitialAds) {

    }

    @Override
    public void onInterstitialAdUnloaded(AdMarvelInterstitialAds adMarvelInterstitialAds) {

    }

}