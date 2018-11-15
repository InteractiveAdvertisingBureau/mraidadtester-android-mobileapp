package com.iabtechlab.sdk.admarvel;

import android.app.Activity;
import android.widget.Toast;

import com.admarvel.android.ads.AdMarvelActivity;
import com.admarvel.android.ads.AdMarvelAd;
import com.admarvel.android.ads.AdMarvelInterstitialAds;
import com.admarvel.android.ads.AdMarvelUtils;
import com.admarvel.android.ads.AdMarvelVideoActivity;
import com.iabtechlab.utility.IntentKey;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ayaz on 10/9/2015.
 */
public class AdmarvelInterface implements
        AdMarvelInterstitialAds.AdMarvelInterstitialAdListener {

    Activity activity;
    private String _siteId = "125546";
    private String _partnerId = "1dd21b33bd603c95";
    private AdMarvelInterstitialAds adMarvelInterstitialAds;
    private AdMarvelActivity adMarvelActivity = null;

    public AdmarvelInterface(Activity adMarvelInterstitialActivity) {

        activity = adMarvelInterstitialActivity;
      /*  adMarvelInterstitialAds = new AdMarvelInterstitialAds(
                activity , 0 , 0x726D6D , 0x00FF00 ,
                0x000000 );

        AdMarvelUtils.isNetworkAvailable(null);
        AdMarvelUtils.setPreferenceValueBoolean(null, "", "", false);
        AdMarvelUtils.setPreferenceValueInt(null, "", "", 0);
        AdMarvelUtils.setPreferenceValueLong(null, "", "", 0);
        AdMarvelUtils.setPreferenceValueString(null, "", "", "");
        AdMarvelUtils.reportAdMarvelAdHistory(null);
        AdMarvelUtils.reportAdMarvelAdHistory(0, null);
        AdMarvelUtils.isTabletDevice(null);

*/
        //  AdMarvelInterstitialAds.setListener();

        //  AdMarvelInterstitialAds.setListener((AdMarvelInterstitialAds.AdMarvelInterstitialAdListener) activity.getApplicationContext());


        //   getAd();
    }

    public void getAd() {

        Map<String, Object> targetParams = new HashMap<String, Object>();

        targetParams.put("GEOLOCATION",
                "42.253387,-83.6874026");
        targetParams.put("test", "bt_inapp");
        targetParams.put("case", "30");
        targetParams.put("AD_HTML", activity.getIntent().getStringExtra(IntentKey.SCRIPT));


        AdMarvelInterstitialAds.setEnableClickRedirect(true);
        adMarvelInterstitialAds
                .requestNewInterstitialAd(
                        activity,
                        targetParams, _partnerId,
                        _siteId,
                        activity);

    }


    @Override
    public void onRequestInterstitialAd() {

    }

    @Override
    public void onReceiveInterstitialAd(AdMarvelUtils.SDKAdNetwork sdkAdNetwork, String s, AdMarvelAd adMarvelAd) {

    }

    @Override
    public void onFailedToReceiveInterstitialAd(AdMarvelUtils.SDKAdNetwork sdkAdNetwork, String s, int i, AdMarvelUtils.ErrorReason errorReason) {

    }

    @Override
    public void onCloseInterstitialAd() {

    }

    @Override
    public void onAdmarvelActivityLaunched(AdMarvelActivity adMarvelActivity) {
        Toast.makeText(activity, "onAdmarvelActivityLaunched", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdMarvelVideoActivityLaunched(AdMarvelVideoActivity adMarvelVideoActivity) {
        Toast.makeText(activity, "onAdMarvelVideoActivityLaunched", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClickInterstitialAd(String s) {

    }

    @Override
    public void onInterstitialDisplayed() {

    }
}
