package com.android.iab.sdk.inmobi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.iab.R;
import com.android.iab.utility.HelperMessage;
import com.android.iab.utility.HelperMethods;
import com.android.iab.utility.IntentKey;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.sdk.InMobiSdk;

import java.util.HashMap;
import java.util.Map;

public class BannerAdsActivity extends Activity {

    private static final String TAG = BannerAdsActivity.class.getSimpleName();
    private static final long YOUR_PLACEMENT_ID = 1449190430700l;
    private static final String YOUR_PROPERTY_ID = "0c4dad25ba0640029aac28f51070b163";


    private InMobiBanner mBannerAd;
    private TextView header_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmobi_banner_ads);
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        InMobiSdk.init(this, YOUR_PROPERTY_ID);

        header_text = (TextView) findViewById(R.id.header_text);

        //Back button clicked on Header to go Back
        header_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Set Screen Title
        header_text.setText(getResources().getString(R.string.lebel_banner_view));

        mBannerAd = new InMobiBanner(BannerAdsActivity.this, YOUR_PLACEMENT_ID);
        RelativeLayout fragmentContainer = (RelativeLayout) findViewById(R.id.fragment_container);
        RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.ad_container);
        if (fragmentContainer != null) {
            if (savedInstanceState != null) {
                return;
            }
            setResult();
            mBannerAd.setAnimationType(InMobiBanner.AnimationType.ROTATE_HORIZONTAL_AXIS);
            mBannerAd.setListener(new InMobiBanner.BannerAdListener() {
                @Override
                public void onAdLoadSucceeded(InMobiBanner inMobiBanner) {
                }

                @Override
                public void onAdLoadFailed(InMobiBanner inMobiBanner,
                                           InMobiAdRequestStatus inMobiAdRequestStatus) {
                    Log.e(TAG, "Banner ad failed to load with error: " +
                            inMobiAdRequestStatus.getMessage());
                    HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.MESSAGE_AD_LOAD_FAILED, BannerAdsActivity.this);

                }

                @Override
                public void onAdDisplayed(InMobiBanner inMobiBanner) {
                    Log.e(TAG, "Banner ad onAdDisplayed"
                    );
                }

                @Override
                public void onAdDismissed(InMobiBanner inMobiBanner) {
                    Log.e(TAG, "Banner ad onAdDismissed"
                    );
                }

                @Override
                public void onAdInteraction(InMobiBanner inMobiBanner, Map<Object, Object> map) {
                    Log.e(TAG, "Banner ad onAdInteraction"
                    );
                }

                @Override
                public void onUserLeftApplication(InMobiBanner inMobiBanner) {
                    Log.e(TAG, "Banner ad onUserLeftApplication"
                    );
                }

                @Override
                public void onAdRewardActionCompleted(InMobiBanner inMobiBanner, Map<Object, Object> map) {
                    Log.e(TAG, "Banner ad onAdRewardActionCompleted"
                    );
                }
            });


            int width = toPixelUnits(320);
            int height = toPixelUnits(50);
            RelativeLayout.LayoutParams bannerLayoutParams =
                    new RelativeLayout.LayoutParams(width, height);
            bannerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            bannerLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            String iab_creative = getIntent().getStringExtra(IntentKey.SCRIPT);

            Map<String, String> targetParams = new HashMap<String, String>();
            String base64 = Base64.encodeToString(iab_creative.getBytes(), Base64.DEFAULT);
       /*     Log.d("Script", iab_creative);
            Log.d("base64", base64);

            Log.d("base64", "***********************");*/
            targetParams.put("iab_creative", base64);
            mBannerAd.setExtras(targetParams);
            adContainer.addView(mBannerAd, bannerLayoutParams);
            mBannerAd.load();
        }
    }

    private int toPixelUnits(int dipUnit) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dipUnit * density);
    }

    private void setResult() {
        setResult(Activity.RESULT_OK);
    }
}
