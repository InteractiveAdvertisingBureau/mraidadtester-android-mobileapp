package com.android.iab.sdk.pabmatic;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.iab.R;
import com.android.iab.utility.IntentKey;
import com.pubmatic.sdk.banner.PMInterstitialAd;
import com.pubmatic.sdk.banner.pubmatic.PMInterstitialAdRequest;
import com.pubmatic.sdk.common.PMError;
import com.pubmatic.sdk.common.PMLogger;
import com.pubmatic.sdk.common.PubMaticSDK;

public class PubmaticInterstitialActivity extends Activity {

    private PMInterstitialAd interstitialAd;
    private TextView header_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        interstitialAd = new PMInterstitialAd(this);
        interstitialAd.setUseInternalBrowser(true);//Optional
        setupMraidFeatureListeners(interstitialAd);

        interstitialAd.setRequestListener(new PMInterstitialAd.InterstitialAdListener.RequestListener() {
            @Override
            public void onFailedToReceiveAd(PMInterstitialAd pmInterstitialAd, PMError pmError) {

            }

            @Override
            public void onReceivedAd(PMInterstitialAd pmInterstitialAd) {
                if (pmInterstitialAd.isReady()) {
                    pmInterstitialAd.show();
                }
            }
        });


        PMInterstitialAdRequest adRequest = PMInterstitialAdRequest.createPMInterstitialAdRequest(
                "123", "567", "789");
        String script = getIntent().getStringExtra(IntentKey.SCRIPT);
        Log.e("Script", script);
        adRequest.setCreativeCode(script);

        interstitialAd.loadRequest(adRequest);

        // Set log level (Optional)
        PubMaticSDK.setLogLevel(PMLogger.PMLogLevel.Debug);

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

        setResult();
    }

    private void setResult() {
        setResult(RESULT_OK);
    }


    void setupMraidFeatureListeners(PMInterstitialAd pmInterstitialAd) {

        PMInterstitialAd.InterstitialAdListener.FeatureSupportHandler featureHandler = new PMInterstitialAd.InterstitialAdListener.FeatureSupportHandler() {

            @Override
            public Boolean shouldSupportSMS(PMInterstitialAd adView) {
                return true;
            }

            @Override
            public Boolean shouldSupportPhone(PMInterstitialAd adView) {
                return true;
            }

            @Override
            public Boolean shouldSupportCalendar(PMInterstitialAd adView) {
                return true;
            }

            @Override
            public Boolean shouldSupportStorePicture(PMInterstitialAd adView) {
                return true;
            }

            @Override
            public boolean shouldStorePicture(PMInterstitialAd sender, String url) {
                return true;
            }

            @Override
            public boolean shouldAddCalendarEntry(PMInterstitialAd sender, String calendarProperties) {
                return true;
            }
        };

        pmInterstitialAd.setFeatureSupportHandler(featureHandler);
    }

    @Override
    protected void onDestroy() {
        try {

            if (interstitialAd != null) {
                interstitialAd.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.toString());
        }
        super.onDestroy();
    }

}
