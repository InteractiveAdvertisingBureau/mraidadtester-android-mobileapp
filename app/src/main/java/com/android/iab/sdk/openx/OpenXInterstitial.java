package com.android.iab.sdk.openx;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.iab.R;
import com.openx.view.plugplay.errors.AdException;
import com.openx.view.plugplay.interstitial.InterstitialManager;
import com.openx.view.plugplay.listeners.AdEventsListener;
import com.openx.view.plugplay.models.AdConfiguration;
import com.openx.view.plugplay.models.AdDetails;
import com.openx.view.plugplay.networking.parameters.UserParameters;
import com.openx.view.plugplay.utils.logger.OXLog;
import com.openx.view.plugplay.views.AdView;

import java.util.Iterator;

public class OpenXInterstitial extends Activity implements AdEventsListener {
    private String AD_ID_INTERSTITIAL = "538528672";
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_xinterstitial);

        createInterstitialAD();

        //setAd(adView);
    }


    private void createInterstitialAD() {

        try {
            //Create an interstitial ad
            adView = new AdView(this, "mobile-d.openx.net", AD_ID_INTERSTITIAL, AdConfiguration.AdUnitIdentifierType.INTERSTITIAL);
        } catch (Exception e) {
            Log.e("AdView", "AdView creation failed");
        }

        if (adView != null) {
            //Set auto display false, so interstitial ad is prefetched & can be used later.
            adView.setAutoDisplayOnLoad(false);
            //Listener to get the life cycle events of an ad
            adView.addAdEventListener(this);

            //Properties to fix the position & an image for a custom close view
            adView.interstitialProperties.customClosePosition = InterstitialManager.InterstitialClosePosition.TOP_RIGHT;
            adView.interstitialProperties.customCloseDrawable = getResources().getDrawable(android.R.drawable.ic_delete);

            //Sets the opacity of the ad. Recommended 1.0f.
            adView.interstitialProperties.setPubBackGroundOpacity(1.0f);

            UserParameters userParameters = new UserParameters();
            userParameters.setUserGender(UserParameters.OXMGender.FEMALE);
            //Set extra params for data enrichment
            adView.setUserParameters(userParameters);

            //Load an ad
            adView.load();
        }
    }

    @Override
    public void adDidLoad(AdView adView, AdDetails adDetails) {
        Log.e("Interstitial loaded","Interstitial successfully loaded.");
        if(!(OpenXInterstitial.this).isFinishing())
        {
            adView.showAsInterstitialFromRoot();
        }

    }

    @Override
    public void adDidFailToLoad(AdView adView, AdException e) {
        Log.e("Interstitial failed",e.toString());
    }

    @Override
    public void adDidDisplay(AdView adView) {

    }

    @Override
    public void adDidComplete(AdView adView) {

    }

    @Override
    public void adWasClicked(AdView adView) {

    }

    @Override
    public void adClickThroughDidClose(AdView adView) {

    }

    @Override
    public void adInterstitialDidClose(AdView adView) {

    }

    @Override
    public void adDidExpand(AdView adView) {

    }

    @Override
    public void adDidCollapse(AdView adView) {

    }


}
