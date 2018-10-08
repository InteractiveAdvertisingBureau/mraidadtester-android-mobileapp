package com.android.iab.sdk.admarvel;

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
import com.admarvel.android.ads.AdMarvelVideoActivity;
import com.android.iab.R;
import com.android.iab.utility.HelperMessage;
import com.android.iab.utility.HelperMethods;
import com.android.iab.utility.IntentKey;

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
    private AdMarvelVideoActivity adMarvelVideoActivity = null;
    private SDKAdNetwork sdkAdNetwork;
    private String pubId;
    private AdMarvelAd adMarvelAd;
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


        header_text = (TextView) findViewById(R.id.header_text);

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
        AdMarvelUtils.enableLogging(true);

        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }


        adMarvelInterstitialAds = new AdMarvelInterstitialAds(
                getApplicationContext(), 0, 0x726D6D, 0x00FF00,
                0x000000);

        AdMarvelUtils.isNetworkAvailable(null);
        AdMarvelUtils.setPreferenceValueBoolean(null, "", "", false);
        AdMarvelUtils.setPreferenceValueInt(null, "", "", 0);
        AdMarvelUtils.setPreferenceValueLong(null, "", "", 0);
        AdMarvelUtils.setPreferenceValueString(null, "", "", "");
        AdMarvelUtils.reportAdMarvelAdHistory(null);
        AdMarvelUtils.reportAdMarvelAdHistory(0, null);
        AdMarvelUtils.isTabletDevice(null);

        setResult();

        getAd();
        AdmarvelInterface admarvelInterface = new AdmarvelInterface(this);


    }

    private void getAd() {

        Map<String, Object> targetParams = new HashMap<String, Object>();

        targetParams.put("GEOLOCATION",
                "42.253387,-83.6874026");
        targetParams.put("test", "bt_inapp");
        targetParams.put("case", "30");
        targetParams.put("AD_HTML", getIntent().getStringExtra(IntentKey.SCRIPT));


        AdMarvelInterstitialAds
                .setListener(AdMarvelInterstitialActivity.this);
        AdMarvelInterstitialAds.setEnableClickRedirect(true);
        AdMarvelInterstitialActivity.this.adMarvelInterstitialAds
                .requestNewInterstitialAd(
                        AdMarvelInterstitialActivity.this,
                        targetParams, _partnerId,
                        _siteId,
                        AdMarvelInterstitialActivity.this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestInterstitialAd() {
        Log.e("admarvel", "onRequestInterstitialAd");

    }

    @Override
    public void onReceiveInterstitialAd(SDKAdNetwork sdkAdNetwork,
                                        String publisherid, AdMarvelAd adMarvelAd) {
        AdMarvelInterstitialAds
                .setListener(null);

        Log.e("admarvel", "onReceiveInterstitialAd");
        this.sdkAdNetwork = sdkAdNetwork;
        this.pubId = publisherid;
        this.adMarvelAd = adMarvelAd;

        // this.displayIntBtn.setEnabled( true );

        displayAdd();
    }

    private void displayAdd() {
        boolean isDisplaySuccesss = adMarvelInterstitialAds.displayInterstitial(
                AdMarvelInterstitialActivity.this,
                sdkAdNetwork, pubId, adMarvelAd);

        Log.e("admarvel", "isDisplaySuccesss : " + isDisplaySuccesss);

    }

    private void setResult() {
        setResult(Activity.RESULT_OK);
    }


    @Override
    public void onFailedToReceiveInterstitialAd(SDKAdNetwork sdkAdNetwork,
                                                String publisherid, int errorCode, ErrorReason errorReason) {
        AdMarvelInterstitialAds
                .setListener(null);
        Log.e("admarvel", "onFailedToReceiveInterstitialAd; errorCode: "
                + errorCode + " errorReason: " + errorReason.toString());
        HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.MESSAGE_AD_LOAD_FAILED, AdMarvelInterstitialActivity.this);

    }

    @Override
    public void onCloseInterstitialAd() {
        Log.e("admarvel", "onCloseInterstitialAd");
        if (this.adMarvelActivity != null) {
            this.adMarvelActivity.finish();
            this.adMarvelActivity = null;
        } else if (this.adMarvelVideoActivity != null) {
            this.adMarvelVideoActivity.finish();
            this.adMarvelVideoActivity = null;
        }

    }

    @Override
    public void onAdmarvelActivityLaunched(AdMarvelActivity a) {
        Log.e("admarvel", "onAdmarvelActivityLaunched");
        this.adMarvelActivity = a;

    }

    @Override
    public void onAdMarvelVideoActivityLaunched(AdMarvelVideoActivity a) {
        Log.e("admarvel", "onAdmarvelVideoActivityLaunched");
        this.adMarvelVideoActivity = a;
    }

    @Override
    public void onClickInterstitialAd(String clickUrl) {
        if (clickUrl != null) {
            Log.e("admarvel", "InterstitialClickUrl: " + clickUrl);
        }


    }

    @Override
    public void onInterstitialDisplayed() {
        Log.e("admarvel", "onInterstitialDisplayed");

    }


}