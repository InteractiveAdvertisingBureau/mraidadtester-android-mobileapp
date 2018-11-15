package com.iabtechlab.sdk.openx;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iabtechlab.R;
import com.iabtechlab.utility.IntentKey;
import com.openx.view.plugplay.errors.AdException;
import com.openx.view.plugplay.listeners.AdEventsListener;
import com.openx.view.plugplay.models.AdConfiguration;
import com.openx.view.plugplay.models.AdDetails;
import com.openx.view.plugplay.models.openrtb.BidRequest;
import com.openx.view.plugplay.models.openrtb.bidRequests.Imp;
import com.openx.view.plugplay.models.openrtb.bidRequests.User;
import com.openx.view.plugplay.models.openrtb.bidRequests.imps.Banner;
import com.openx.view.plugplay.networking.parameters.UserParameters;
import com.openx.view.plugplay.views.AdView;

import java.util.ArrayList;

public class OpenXBannerActivity extends Activity implements AdEventsListener {
    String scriptId;
    private AdView adView;
    private TextView header_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_xbanner);

        header_text = findViewById(R.id.header_text);

        //Back button clicked on Header to go Back
        header_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (getIntent() != null) {
            scriptId = getIntent().getStringExtra(IntentKey.SCRIPT_ID);
        }
        Log.e("script Id", scriptId);
        LinearLayout adContainer = findViewById(R.id.openx_ad_container);
        startLoadingAnAd();
        adContainer.addView(adView);
    }

    @Override
    public void adDidLoad(AdView adView, AdDetails adDetails) {
        Log.e("Banner loaded", "Banner successfully loaded.");

    }

    @Override
    public void adDidFailToLoad(AdView adView, AdException e) {
        Log.e("Banner Failed to load", e.toString());

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

    private void startLoadingAnAd() {
        try {
            adView = new AdView(this, "mobile-d.openx.net", "537454411", AdConfiguration.AdUnitIdentifierType.BANNER);
            // adView = new AdView(this, "mobile-d.openx.net", scriptId, AdConfiguration.AdUnitIdentifierType.BANNER);
        } catch (Exception e) {
            Log.e("Adview", "AdView failed");
            return;
        }
        //placement: Specify the position in the view, where you want the banner to be placed
        RelativeLayout.LayoutParams BannerPosition = new RelativeLayout.LayoutParams(RelativeLayout
                .LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        BannerPosition.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        BannerPosition.addRule(RelativeLayout.CENTER_HORIZONTAL);

        if (adView != null) {
            adView.addAdEventListener(this);

            adView.setLayoutParams(BannerPosition);

            adView.setAutoDisplayOnLoad(true);

            UserParameters userParameters = new UserParameters();

            userParameters.setCustomParameter("keywords", "social-networking");

            //Sample ORTB object for banner
            BidRequest bidRequest = new BidRequest();
            User user = new User();
            user.gender = "F";
            user.yob = 1984;
            bidRequest.setUser(user);

            Imp imp = new Imp();
            Banner banner = new Banner();
            banner.api = new int[]{3, 5};
            imp.banner = banner;
            ArrayList<Imp> imps = new ArrayList<>();
            imps.add(imp);
            bidRequest.setImp(imps);
            userParameters.setOpenRtbParameters(bidRequest);

            //Set flex adsize, if you would like ads of different sizes
            adView.setFlexAdSize(AdConfiguration.OXMAdSize.BANNER_320x50);
            adView.setUserParameters(userParameters);

            //Set an interval at which this banner should refresh
            adView.setAutoRefreshDelay(20);
            adView.setAutoRefreshMax(3);

            adView.load();
            //call just adView.setAutoDisplayOnLoad(true); and NO adView.show();
            //or call adView.setAutoDisplayOnLoad(false); and adView.show();
        }
    }

}
