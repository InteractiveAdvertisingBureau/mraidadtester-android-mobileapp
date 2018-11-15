package com.iabtechlab.sdk.adform;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.adform.sdk.AdformSDK;
import com.adform.sdk.network.entities.AdformEnum;
import com.adform.sdk.pub.AdOverlay;
import com.iabtechlab.R;
import com.iabtechlab.utility.IntentKey;

/**
 * An ad implementation, that loads an ad, and shows it whenever its needed. It
 * displays a fullscreen ad with a close button.
 */
public class AdOverlayActivity extends Activity {

    private AdOverlay adOverlay;
    private TextView header_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdformSDK.setHttpsEnabled(false);
        // [app related] Initializing additional buttons for user control
        setContentView(R.layout.activity_adoverlay);
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
        String script = getIntent().getStringExtra(IntentKey.SCRIPT);
        /*View loadButton = findViewById(R.id.load_button);
        if (loadButton != null)
            loadButton.setOnClickListener(this);
        View showButton = findViewById(R.id.show_button);
        if (showButton != null)
            showButton.setOnClickListener(this);*/

        // [mandatory] Initializing AdOverlay with its base parameters
        adOverlay = AdOverlay.createInstance(this);

        // [mandatory] Setting master tag. [optional] if set in layout XML
        adOverlay.setMasterTagId(4660165);

        // [optional] Debug mode for testing ad. Can be set in layout XML
        // adOverlay.setDebugMode(true);
        adOverlay.setAdTag(script);

      /*  // to load the ad in the view
        adOverlay.loadAd();*/
        // [optional] Setting base listeners for ad load status
        adOverlay.setListener(new AdOverlay.OverlayLoaderListener() {
            @Override
            public void onLoadSuccess() {
            }

            @Override
            public void onLoadError(String error) {
            }

            @Override
            public void onShowError(String error) {
            }
        });

        // [optional] Setting additional listeners for ad events
        adOverlay.setStateListener(new AdOverlay.OverlayStateListener() {
            @Override
            public void onAdShown() {
            }

            @Override
            public void onAdClose() {
            }
        });
        setResult();
        // [optional] You can set an animation type that could be used when ad is shown
        // adOverlay.setPresentationStyle(AdformEnum.AnimationType.FADE);
        // adOverlay.setPresentationStyle(AdformEnum.AnimationType.NO_ANIMATION);
        adOverlay.setPresentationStyle(AdformEnum.AnimationType.FADE); // default

        // to show the ad in the view
        adOverlay.showAd();
        // [optional] This flag sets if dim should be used in the background when ad shows.
        adOverlay.setDimOverlayEnabled(true); // default
    }

    @Override
    protected void onResume() {
        super.onResume();

        // [mandatory] Sending world event to the view
        if (adOverlay != null)
            adOverlay.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // [mandatory] Sending world event to the view
        if (adOverlay != null)
            adOverlay.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // [mandatory] Sending world event to the view
        if (adOverlay != null)
            adOverlay.destroy();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // [mandatory] Sending world event to the view
        if (adOverlay != null)
            adOverlay.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // [mandatory] Sending world event to the view
        if (adOverlay != null)
            adOverlay.onRestoreInstanceState(savedInstanceState);
    }

    private void setResult() {
        setResult(RESULT_OK);
    }

}
