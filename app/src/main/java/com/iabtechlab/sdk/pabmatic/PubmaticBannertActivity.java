package com.iabtechlab.sdk.pabmatic;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.iabtechlab.R;
import com.iabtechlab.utility.HelperMessage;
import com.iabtechlab.utility.HelperMethods;
import com.iabtechlab.utility.IntentKey;
import com.pubmatic.sdk.banner.PMBannerAdView;
import com.pubmatic.sdk.banner.pubmatic.PMBannerAdRequest;
import com.pubmatic.sdk.common.AdRequest;
import com.pubmatic.sdk.common.PMAdSize;
import com.pubmatic.sdk.common.PMError;
import com.pubmatic.sdk.common.PMLogger;
import com.pubmatic.sdk.common.PubMaticSDK;

public class PubmaticBannertActivity extends Activity {

    private PMBannerAdView mAdView;
    private TextView header_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubmatic_banner);

        mAdView = findViewById(R.id.adView);
        mAdView.setUseInternalBrowser(true);//Optional
        setupMraidFeatureListeners(mAdView);

        AdRequest request = PMBannerAdRequest.createPMBannerAdRequest("123", "456", "789");
        request.setAdSize(new PMAdSize(320, 50));

        String script = getIntent().getStringExtra(IntentKey.SCRIPT);
        Log.e("Script", script);
        request.setCreativeCode(script);


        mAdView.setRequestListener(new PMBannerAdView.BannerAdViewDelegate.RequestListener() {
            @Override
            public void onFailedToReceiveAd(PMBannerAdView pmBannerAdView, PMError pmError) {
                HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.MESSAGE_AD_LOAD_FAILED, PubmaticBannertActivity.this);
            }

            @Override
            public void onReceivedAd(PMBannerAdView pmBannerAdView) {

            }
        });
        mAdView.loadRequest(request);

        // Set log level (Optional)
        PubMaticSDK.setLogLevel(PMLogger.PMLogLevel.Debug);

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
        setResult();

    }

    private void setResult() {
        setResult(RESULT_OK);
    }


    void setupMraidFeatureListeners(PMBannerAdView adView) {


        PMBannerAdView.BannerAdViewDelegate.FeatureSupportHandler featureHandler = new PMBannerAdView.BannerAdViewDelegate.FeatureSupportHandler() {

            @Override
            public Boolean shouldSupportSMS(PMBannerAdView adView) {
                return true;
            }

            @Override
            public Boolean shouldSupportPhone(PMBannerAdView adView) {
                return true;
            }

            @Override
            public Boolean shouldSupportCalendar(PMBannerAdView adView) {
                return true;
            }

            @Override
            public Boolean shouldSupportStorePicture(PMBannerAdView adView) {
                return true;
            }

            @Override
            public boolean shouldStorePicture(PMBannerAdView sender, String url) {
                return true;
            }

            @Override
            public boolean shouldAddCalendarEntry(PMBannerAdView sender, String calendarProperties) {
                return true;
            }
        };

        adView.setFeatureSupportHandler(featureHandler);

    }

    @Override
    protected void onResume() {        // TODO Auto-generated method stub
        super.onResume();

    }

    @Override
    protected void onPause() {        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

        // your code
    }

    @Override
    protected void onDestroy() {
        try {
            if (mAdView != null) {
                mAdView.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }


}
