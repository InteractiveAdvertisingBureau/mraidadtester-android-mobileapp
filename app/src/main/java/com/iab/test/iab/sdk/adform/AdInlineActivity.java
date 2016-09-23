package com.iab.test.iab.sdk.adform;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.adform.sdk.interfaces.AdListener;
import com.adform.sdk.interfaces.AdStateListener;
import com.adform.sdk.network.entities.AdformEnum;
import com.adform.sdk.pub.views.AdInline;
import com.iab.test.iab.R;
import com.iab.test.iab.utility.IntentKey;

/**
 * The most basic ad implementation. You pass in mandatory events and parameters,
 * start loading ad, and it handles everything else automatically.
 */
public class AdInlineActivity extends Activity {

    private AdInline adInline;
    private TextView header_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adinline);

        header_text=(TextView)findViewById(R.id.header_text);

        //Back button clicked on Header to go Back
        header_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Set Screen Title
        header_text.setText(getResources().getString(R.string.lebel_banner_view));
        String script = getIntent().getStringExtra(IntentKey.SCRIPT);
        // [mandatory] Getting an instance of the view
        adInline = (AdInline) findViewById(R.id.view_ad_inline);

        // [optional] Setting base listeners for ad load status
        adInline.setListener(new AdListener() {
            @Override
            public void onAdLoadSuccess(AdInline adInline) {
            }

            @Override
            public void onAdLoadFail(AdInline adInline, String s) {
                // Setting fallback image whenever ad fails to load
                // findViewById(R.id.ad_container).setBackgroundResource(R.drawable.ic_launcher);
            }
        });

        // [optional] Setting additional listeners for ad events
        adInline.setStateListener(new AdStateListener() {
            @Override
            public void onAdVisibilityChange(AdInline adInline, boolean b) {

            }

            @Override
            public void onAdOpen(AdInline adInline) {

            }

            @Override
            public void onAdClose(AdInline adInline) {

            }
        });

        // [mandatory] Setting ad size. [optional] if set in layout XML
      //  adInline.setAdSize(new AdSize(320, 50));

//        [optional] You could add to the request additional sizes.
//        adInline.setSupportedSizes(new AdSize(300, 300), new AdSize(320, 160));

//        [optional] If you want to support multiple ad sizes at the same placement without setting them, you could use additional dimensions feature.
//        adInline.setEnabledAdditionalDimensions(true);

//        [optional] If you want to setup that HTML ad would be loaded if video ads fails, you need to set fallback master tag.
//        adInline.setFallbackMasterTagId(1111111);

        // [mandatory] Setting master tag. [optional] if set in layout XML
      //  adInline.setMasterTagId(4016318);

        // [optional] Debug mode for testing ad. Can be set in layout XML
       // adInline.setDebugMode(true);

        // [optional] You can set an animation type that could be used when ad shows in the banner.
        // adInline.setBannerAnimationType(AdformEnum.AnimationType.FADE);
        // adInline.setBannerAnimationType(AdformEnum.AnimationType.NO_ANIMATION);
        adInline.setBannerAnimationType(AdformEnum.AnimationType.SLIDE); // default

        // [optional] You can set an animation type that could be used when ad is expanded ( mraid.expand() ).
        // adInline.setModalPresentationStyle(AdformEnum.AnimationType.FADE);
        // adInline.setModalPresentationStyle(AdformEnum.AnimationType.NO_ANIMATION);
        adInline.setModalPresentationStyle(AdformEnum.AnimationType.SLIDE); // default
        setResult();
        // [optional] This flag sets if dim should be used in the background whenever mraid.expand() is used.
        adInline.setDimOverlayEnabled(true); // default
        adInline.setAdTag(script);
        // [mandatory] Send an event to start loading
        adInline.loadAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // [mandatory] Sending world event to the view
        if (adInline != null)
            adInline.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // [mandatory] Sending world event to the view
        if (adInline != null)
            adInline.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // [mandatory] Sending world event to the view
        if (adInline != null)
            adInline.destroy();
    }
    private void setResult() {
        setResult(RESULT_OK);
    }
}
