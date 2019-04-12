package com.iabtechlab.sdk.admarvel;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admarvel.android.ads.AdMarvelUtils;
import com.admarvel.android.ads.AdMarvelUtils.ErrorReason;
import com.admarvel.android.ads.AdMarvelUtils.SDKAdNetwork;
import com.admarvel.android.ads.AdMarvelView;
import com.admarvel.android.ads.AdMarvelView.AdMarvelViewListener;

import com.iabtechlab.R;
import com.iabtechlab.utility.HelperMessage;
import com.iabtechlab.utility.HelperMethods;
import com.iabtechlab.utility.IntentKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AdMarvelBannerAdsActivity extends Activity implements
        OnCheckedChangeListener {


    private final int REQUEST_INTERVAL = 20000;
    private String _siteId = "125546";// "38722";//"35961";
    private String _partnerId = "1dd21b33bd603c95";
    private Timer requestIntervalTimer;

    private TextView header_text;
    private AdMarvelView adMarvelView;
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

    /**
     * Called when the activity is first created.
     */
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
        header_text.setText(getResources().getString(R.string.lebel_banner_view));



        /* AdMarvelView adMarvelView = null;*/
        try {
            adMarvelView = findViewById(R.id.ad);

        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } // (AdMarvelView) findViewById(R.id.ad);

        adMarvelView.setEnableClickRedirect(true);
        adMarvelView.setDisableAnimation(false);

        adMarvelView.setEnableAutoScaling(true);
        adMarvelView.setVisibility(View.GONE);


        AdMarvelUtils.enableLogging(true);

        try {
            // Initialized SDKs that need to; pass in publisher ids
            Map<SDKAdNetwork, String> publisherIds = new HashMap<SDKAdNetwork, String>();

            AdMarvelUtils.initialize(this, publisherIds);

            getAd();

        } catch (Exception e) {
           Log.e("Exception" ,""+e.getMessage());
        }


        setResult();

        adMarvelView.setListener(new AdMarvelViewListener() {
            @Override
            public void onReceiveAd(AdMarvelView adMarvelView) {
                adMarvelView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailedToReceiveAd(AdMarvelView adMarvelView, int i, ErrorReason errorReason) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.MESSAGE_AD_LOAD_FAILED, AdMarvelBannerAdsActivity.this);
                    }
                });
            }

            @Override
            public void onClickAd(AdMarvelView adMarvelView, String s) {

            }

            @Override
            public void onRequestAd(AdMarvelView adMarvelView) {
                LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        100);
                adMarvelView.setLayoutParams(rlp);
            }

            @Override
            public void onExpand(AdMarvelView adMarvelView) {

            }

            @Override
            public void onClose(AdMarvelView adMarvelView) {

            }

            @Override
            public void onAdUnloaded(AdMarvelView adMarvelView) {

            }
        });
    }

    private void setResult() {
        setResult(RESULT_OK);
    }


    private void getAd() {

        try {
            Map<String, Object> targetParams = new HashMap<String, Object>();
            targetParams.put("AD_HTML", getIntent().getStringExtra(IntentKey.SCRIPT));
            adMarvelView.setDisableAnimation(false);
            adMarvelView.requestNewAd(targetParams, _partnerId,
                    _siteId, AdMarvelBannerAdsActivity.this);


        } catch (Exception e) {
            Log.e("errorReason", e.toString());
        }

    }


    public void getAd(View v) {
        getAd();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            adMarvelView = findViewById(R.id.ad);
            adMarvelView.resume(this);

        } catch (Exception e) {
            Log.e("errorReason", e.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {


            adMarvelView.pause(this);

        } catch (Exception e) {
            Log.e("errorReason", e.toString());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // AdMarvelView adMarvelView = (AdMarvelView) findViewById(R.id.ad);
        adMarvelView.start(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        //  AdMarvelView adMarvelView = ( AdMarvelView ) findViewById( R.id.ad );
        adMarvelView.stop(this);

        // your code
    }

    @Override
    protected void onDestroy() {

        // AdMarvelView adMarvelView = ( AdMarvelView ) findViewById( R.id.ad );
        if (adMarvelView != null) {
            adMarvelView.destroy();
        }
        if (requestIntervalTimer != null) {
            requestIntervalTimer.cancel();
            requestIntervalTimer.purge();
            requestIntervalTimer = null;
        }
        super.onDestroy();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView,
                                 boolean isChecked) {
        if (isChecked) {
            //   autoRefreshTV.setVisibility( View.VISIBLE );
            scheduleTimeThread();
            //getAdButton.setEnabled( false );
        } else {
            // getAdButton.setEnabled( true );
            //  autoRefreshTV.setVisibility( View.INVISIBLE );
            if (requestIntervalTimer != null) {
                requestIntervalTimer.cancel();
                requestIntervalTimer.purge();
                requestIntervalTimer = null;
            }

        }

    }

}