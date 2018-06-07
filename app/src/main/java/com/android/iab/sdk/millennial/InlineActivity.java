package com.android.iab.sdk.millennial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.iab.R;
import com.android.iab.utility.HelperMessage;
import com.android.iab.utility.HelperMethods;
import com.android.iab.utility.IntentKey;
import com.millennialmedia.AppInfo;
import com.millennialmedia.InlineAd;
import com.millennialmedia.MMException;
import com.millennialmedia.MMLog;
import com.millennialmedia.MMSDK;

public class InlineActivity extends Activity {

	//public static final String PLACEMENT_ID = "203888";  ///Default Placement Id
	public static final String PLACEMENT_ID = "IAB_testposition";  //Placement Id  which is used in App
	public static final String DCN = "9993e2e5945d4bbca5f799177f7cacf5";  //Here DCN is used as a Site Id

	private static final String TAG = InlineActivity.class.getSimpleName();

	private InlineAd inlineAd;
	private TextView header_text;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inline);

		MMSDK.initialize(InlineActivity.this); // pass in current activity instance
		MMLog.setLogLevel(2);

		AppInfo appInfo = new AppInfo();
        // Only applicable if migrating from Nexage
        appInfo.setSiteId(DCN);
		MMSDK.setAppInfo(appInfo);
		final View adContainer = findViewById(R.id.ad_container);
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
		MMLog.d("Banner", "Millennial");

		// Create a new Inline instance using your placement ID and a reference to the View to fill with the Ad
		// from your layout.  The SDK will attempt to fit the optimal ad size based on the size of your container
		try {
			inlineAd = InlineAd.createInstance(PLACEMENT_ID, (ViewGroup) adContainer);
			setResult();
			requestAd();

			inlineAd.setListener(new InlineAd.InlineListener() {
				@Override
				public void onRequestSucceeded(InlineAd inlineAd) {

					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							findViewById(R.id.request_button).setVisibility(View.GONE);
							adContainer.setVisibility(View.VISIBLE);

							/*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
							toolbar.getMenu().findItem(R.id.inline_refresh).setVisible(true);*/
						}
					});

					MMLog.i(TAG, "Inline Ad loaded.");
				}


				@Override
				public void onRequestFailed(InlineAd inlineAd, InlineAd.InlineErrorStatus errorStatus) {

					MMLog.e(TAG, errorStatus.toString());
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.MESSAGE_AD_LOAD_FAILED, InlineActivity.this);

						}
					});

				}


				@Override
				public void onClicked(InlineAd inlineAd) {

					MMLog.i(TAG, "Inline Ad clicked.");
				}


				@Override
				public void onResize(InlineAd inlineAd, int width, int height) {

					MMLog.i(TAG, "Inline Ad starting resize.");
				}


				@Override
				public void onResized(InlineAd inlineAd, int width, int height, boolean toOriginalSize) {

					MMLog.i(TAG, "Inline Ad resized.");
				}


				@Override
				public void onExpanded(InlineAd inlineAd) {

					MMLog.i(TAG, "Inline Ad expanded.");
				}


				@Override
				public void onCollapsed(InlineAd inlineAd) {

					MMLog.i(TAG, "Inline Ad collapsed.");
				}


				@Override
				public void onAdLeftApplication(InlineAd inlineAd) {

					MMLog.i(TAG, "Inline Ad left application.");
				}
			});

			// uncomment to set a refresh rate of 30 seconds
			// inlineAd.setRefreshInterval(30000);

			/*toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem menuItem) {

					if (menuItem.getItemId() == R.id.inline_refresh) {
						requestAd();

						return true;
					}

					return false;
				}
			});
*/


		} catch (MMException e) {
			MMLog.e(TAG, "Error creating inline ad", e);
		}
	}


	private void requestAd() {

		if (inlineAd != null) {

			//The AdRequest instance is used to pass additional metadata to the server to improve ad selection
			final InlineAd.InlineAdMetadata inlineAdMetadata = new InlineAd.InlineAdMetadata().
				setAdSize(InlineAd.AdSize.BANNER);
			inlineAdMetadata.setKeywords(getIntent().getStringExtra(IntentKey.SCRIPT_ID));
			//Request ads from the server.  If automatic refresh is enabled for your placement new ads will be shown
			//automatically
			inlineAd.request(inlineAdMetadata);
		}
	}
	private void setResult() {
		setResult(Activity.RESULT_OK);
	}
}
