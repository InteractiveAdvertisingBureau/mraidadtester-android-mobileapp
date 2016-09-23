package com.android.iab.sdk.inmobi;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.iab.R;
import com.android.iab.utility.HelperMessage;
import com.android.iab.utility.HelperMethods;
import com.android.iab.utility.IntentKey;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.sdk.InMobiSdk;

import java.util.HashMap;
import java.util.Map;

public class InterstitialAdsActivity extends ActionBarActivity {

    private static final String TAG = InterstitialAdsActivity.class.getSimpleName();
    private static final long YOUR_PLACEMENT_ID = 1450223352908l;
    private static final String YOUR_PROPERTY_ID = "3f446d14d5a64ec3a3fadcb789898b04";

    private InMobiInterstitial mInterstitialAd;
    private TextView header_text;

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmobi_interstitial_ads);

        header_text=(TextView)findViewById(R.id.header_text);

        //Back button clicked on Header to go Back
        header_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Set Screen Title
        header_text.setText(getResources().getString(R.string.lebel_interstitial));

        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        InMobiSdk.init(this, YOUR_PROPERTY_ID);
        setResult();

        mInterstitialAd = new InMobiInterstitial(InterstitialAdsActivity.this, YOUR_PLACEMENT_ID,
                new InMobiInterstitial.InterstitialAdListener() {
                    @Override
                    public void onAdRewardActionCompleted(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {

                    }

                    @Override
                    public void onAdDisplayed(InMobiInterstitial inMobiInterstitial) {

                    }

                    @Override
                    public void onAdDismissed(InMobiInterstitial inMobiInterstitial) {

                    }

                    @Override
                    public void onAdInteraction(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {

                    }

                    @Override
                    public void onAdLoadSucceeded(InMobiInterstitial inMobiInterstitial) {
                        if (inMobiInterstitial.isReady()) {
                            mInterstitialAd.show();
                        }
                    }

                    @Override
                    public void onAdLoadFailed(InMobiInterstitial inMobiInterstitial, InMobiAdRequestStatus inMobiAdRequestStatus) {
                        Log.w(TAG, "Unable to load interstitial ad (error message: " +
                                inMobiAdRequestStatus.getMessage() + ")");
                        HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.MESSAGE_AD_LOAD_FAILED, InterstitialAdsActivity.this);

                    }

                    @Override
                    public void onUserLeftApplication(InMobiInterstitial inMobiInterstitial) {

                    }
                });

        String iab_creative=getIntent().getStringExtra(IntentKey.SCRIPT);
        Map < String , String > targetParams = new HashMap< String , String >();
        String base64 = Base64.encodeToString(iab_creative.getBytes(), Base64.DEFAULT);
    /*    Log.d("Script", iab_creative);
        Log.d("base64", base64);*/
        targetParams.put("iab_creative", base64);
        mInterstitialAd.setExtras(targetParams);
        mInterstitialAd.load();
    }

    private void setResult() {
        setResult(Activity.RESULT_OK);
    }
}
