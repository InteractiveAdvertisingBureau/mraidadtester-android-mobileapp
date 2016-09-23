package com.android.iab.sdk.pabmatic;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.admarvel.android.ads.AdMarvelView;
import com.admarvel.android.util.Logging;
import com.android.iab.R;
import com.android.iab.utility.HelperMessage;
import com.android.iab.utility.HelperMethods;
import com.android.iab.utility.IntentKey;
import com.moceanmobile.mast.MASTAdView;
import com.moceanmobile.mast.MASTAdView.LogLevel;
import com.moceanmobile.mast.MASTAdViewDelegate;

import java.util.Map;

public class PubmaticBannertActivity extends Activity {

	private MASTAdView mAdView;
	private TextView header_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pubmatic_banner);

		mAdView = (MASTAdView) findViewById(R.id.adView);
		mAdView.setZone(350030); // Can also be set in layout XML


		// Add listeners (optional)
		mAdView.setRequestListener(new AdRequestListener());
		mAdView.setActivityListener(new AdActivityListener());
		mAdView.setRichMediaListener(new AdRichMediaListener());
		mAdView.setInternalBrowserListener(new AdInternalBrowserListener());

		// Set log level (Optional)
		mAdView.setLogLevel(LogLevel.Debug);

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
		setResult();

		String script = getIntent().getStringExtra(IntentKey.SCRIPT);
		Log.e("Script", script);
		mAdView.setCreativeCode(script);

	}
	private void setResult() {
		setResult(RESULT_OK);
	}



	private class AdRequestListener implements
			MASTAdViewDelegate.RequestListener {
		@Override
		public void onFailedToReceiveAd(MASTAdView adView, Exception ex) {

			HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.MESSAGE_AD_LOAD_FAILED, PubmaticBannertActivity.this);

		}

		@Override
		public void onReceivedAd(MASTAdView adView) {

		}

		@Override
		public void onReceivedThirdPartyRequest(MASTAdView adView,
				Map<String, String> properties, Map<String, String> parameters) {

		}
	}

	private class AdActivityListener implements
			MASTAdViewDelegate.ActivityListener {

		@Override
		public boolean onOpenUrl(MASTAdView adView, String url) {

			return false;
		}

		@Override
		public void onLeavingApplication(MASTAdView adView) {

		}

		@Override
		public boolean onCloseButtonClick(MASTAdView adView) {

			return false;
		}
	}

	private class AdInternalBrowserListener implements
			MASTAdViewDelegate.InternalBrowserListener {
		@Override
		public void onInternalBrowserPresented(MASTAdView adView) {

		}

		@Override
		public void onInternalBrowserDismissed(MASTAdView adView) {

		}
	}



	private class AdRichMediaListener implements
			MASTAdViewDelegate.RichMediaListener {
		@Override
		public void onExpanded(MASTAdView adView) {

		}

		@Override
		public void onResized(MASTAdView adView, Rect area) {

		}

		@Override
		public void onCollapsed(MASTAdView adView) {

		}

		@Override
		public boolean onPlayVideo(MASTAdView adView, String url) {

			return false;
		}

		@Override
		public void onEventProcessed(MASTAdView adView, String request) {

		}
	}

	@Override
	protected void onResume()
	{		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onPause()
	{		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onStart()
	{
		super.onStart();
	}

	@Override
	public void onStop()
	{
		super.onStop();

		// your code
	}

	@Override
	protected void onDestroy()
	{
		try {

			if (mAdView != null) {
				mAdView.reset();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}


}
