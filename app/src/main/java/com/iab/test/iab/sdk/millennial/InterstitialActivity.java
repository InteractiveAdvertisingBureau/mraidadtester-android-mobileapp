package com.iab.test.iab.sdk.millennial;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.iab.test.iab.R;
import com.iab.test.iab.utility.HelperMessage;
import com.iab.test.iab.utility.HelperMethods;
import com.iab.test.iab.utility.IntentKey;
import com.millennialmedia.AppInfo;
import com.millennialmedia.InterstitialAd;
import com.millennialmedia.MMException;
import com.millennialmedia.MMLog;
import com.millennialmedia.MMSDK;


public class InterstitialActivity extends Activity implements InterstitialAd.InterstitialListener {

	//public static final String PLACEMENT_ID = "203890";  ///Default Placement Id
	public static final String PLACEMENT_ID = "IAB_testposition";  //Placement Id  which is used in App
	public static final String DCN = "9993e2e5945d4bbca5f799177f7cacf5";  //Here DCN is used as a Site Id

	public static final String TAG = InterstitialActivity.class.getSimpleName();

	private InterstitialAd interstitialAd;
	private TextView header_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interstitial);
		
		// pass in current activity instance
		MMSDK.initialize(InterstitialActivity.this);
		//Set Log Level
		MMLog.setLogLevel(2);
		//Adding App Info which having SIte Id
		AppInfo appInfo = new AppInfo();
       // Only applicable if migrating from Nexage
		appInfo.setSiteId(DCN);
		MMSDK.setAppInfo(appInfo);
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

		Log.d("Banner", "Millennial");
		try {
			setResult();
		    //final InterstitialAd interstitialAd = InterstitialAd.createInstance(​<PLACEMENT_ID>​);
			interstitialAd = InterstitialAd.createInstance(PLACEMENT_ID);


			if (interstitialAd != null) {
				try {
					InterstitialAd.InterstitialAdMetadata interstitialAdMetadata =
						new InterstitialAd.InterstitialAdMetadata();
				interstitialAdMetadata.setKeywords(getIntent().getStringExtra(IntentKey.SCRIPT_ID)); // Pass the id value here like I am passing 299

					interstitialAd.load(InterstitialActivity.this, interstitialAdMetadata);
					MMLog.i(TAG,"Loading add");
			}
			catch (Exception e) {
				MMLog.e(TAG, "Exception, Unable to show interstitial" + e.toString());
				e.printStackTrace();
			}
			}

		    interstitialAd.setListener(new InterstitialAd.InterstitialListener() {
		        @Override 
		        public void onLoaded(InterstitialAd interstitialAd) { 
		 
		          //  Log.e(TAG, "Interstitial Ad loaded.");
		            // Show the Ad using the display options you configured. 
		          /*  try { 
		                interstitialAd.show(getApplicationContext()); 
		            } catch (MMException e) { 
		            	Log.e(TAG,"Unable to show interstitial ad content, exception occurred"); 
		                e.printStackTrace(); 
		            } */
		            
		            if (interstitialAd.isReady()) {
		                // Show the Ad using the display options you configured.
		                try {
		                	SharedPreferences sharedPreferences =
		    						PreferenceManager.getDefaultSharedPreferences(InterstitialActivity.this);

		    					InterstitialAd.DisplayOptions displayOptions = new InterstitialAd.DisplayOptions().setImmersive(
		    						sharedPreferences.getBoolean(getResources().getString(R.string.lebel_interstitial), false));
                       
		                    interstitialAd.show(InterstitialActivity.this, displayOptions);
		                } 
		                catch (MMException e) {
		                    Log.e(TAG, "Exception in showing interstitial ad content" + e);

		                    e.printStackTrace();
		                } 
		                catch (Exception e) {
		                    MMLog.e(TAG, "Exception, Unable to show interstitial"+e.toString());
		                    e.printStackTrace();
		                }

		        	} else {
		                if (interstitialAd.isReady()){
							MMLog.e(TAG, "Unable to show interstitial. Ad not loaded.");
		                }    
		            }
		        } 
		 
		 
		        @Override 
		        public void onLoadFailed(InterstitialAd interstitialAd, 
		           InterstitialAd.InterstitialErrorStatus errorStatus) {

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							MMLog.e(TAG, "Interstitial Ad load failed.");
							HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.MESSAGE_AD_LOAD_FAILED, InterstitialActivity.this);

						}
					});

				}
		 
		 
		        @Override 
		        public void onShown(InterstitialAd interstitialAd) { 
		 
		        	MMLog.e(TAG, "Interstitial Ad shown.");
		        } 
		 
		 
		        @Override 
		        public void onShowFailed(InterstitialAd interstitialAd, 
		            InterstitialAd.InterstitialErrorStatus errorStatus) { 
		 
		        	MMLog.e(TAG,"Interstitial Ad show failed.");
		        } 
		 
		 
		        @Override 
		        public void onClosed(InterstitialAd interstitialAd) { 
		 
		        	MMLog.e(TAG,"Interstitial Ad closed.");
		        }  
		 
		        @Override 
		        public void onClicked(InterstitialAd interstitialAd) { 
		 
		            MMLog.e(TAG , "Interstitial Ad clicked.");
		        } 
		 
		 
		        @Override 
		        public void onAdLeftApplication(InterstitialAd interstitialAd) { 
		 
		        	MMLog.e(TAG,"Interstitial Ad left application.");
		        } 
		 
		 
		        @Override 
		        public void onExpired(InterstitialAd interstitialAd) { 
		 
		            MMLog.e(TAG, "Interstitial Ad expired.");
		        } 
		  }); 
		 
		} catch (MMException e) { 
		    MMLog.e(TAG, "Error creating interstitial ad", e);
		    // abort loading ad 
		} 
		
		
		
		
		
	/*	// Create the Ad instance with your placement ID
		try {
			interstitialAd = InterstitialAd.createInstance(PLACEMENT_ID);

			// We will have our Activity implement the listener to receive callbacks for InterstitialAd state
			interstitialAd.setListener(this);

		} catch (MMException e) {
			MMLog.e(TAG, "Error creating interstitial ad", e);
		}*/

	/*	Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.inflateMenu(R.menu.interstitial_toolbar);

		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {

				if (R.id.settings == menuItem.getItemId()) {
					Intent intent = new Intent(InterstitialActivity.this, SettingsActivity.class);
					InterstitialActivity.this.startActivity(intent);

					return true;
				}

				return false;
			}
		});
*/
		final View loadButton = findViewById(R.id.load);
		loadButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (interstitialAd != null) {
					InterstitialAd.InterstitialAdMetadata interstitialAdMetadata =
						new InterstitialAd.InterstitialAdMetadata();

					interstitialAd.load(InterstitialActivity.this, interstitialAdMetadata);

				} else {
					MMLog.e(TAG, "Unable to load interstitial ad content, InterstitialAd instance is null.");
				}
			}
		});

		final View showButton = findViewById(R.id.show);
		showButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (interstitialAd != null) {
					// As a Publisher you can control some aspects of how the ad is displayed.
					SharedPreferences sharedPreferences =
						PreferenceManager.getDefaultSharedPreferences(InterstitialActivity.this);

					InterstitialAd.DisplayOptions displayOptions = new InterstitialAd.DisplayOptions().setImmersive(
						true);

					// Show the Ad using the display options you configured.
					try {
						interstitialAd.show(InterstitialActivity.this, displayOptions);
					} catch (MMException e) {
						MMLog.e(TAG, "Unable to show interstitial ad content, exception occurred");
						e.printStackTrace();
					}

				} else {
					MMLog.e(TAG, "Unable to show interstitial ad content, InterstitialAd instance is null.");
				}
			}
		});

		/*loadButton.setEnabled(!interstitialAd.isReady());
		showButton.setEnabled(interstitialAd.isReady());*/
	}


	@Override
	public void onLoaded(InterstitialAd interstitialAd) {

		Utils.runOnUiThread(new Runnable() {
			@Override
			public void run() {

				findViewById(R.id.show).setEnabled(true);
				findViewById(R.id.load).setEnabled(false);

				MMLog.e(TAG, "Interstitial loaded successfully.");
			}
		});
	}


	@Override
	public void onLoadFailed(InterstitialAd interstitialAd, final InterstitialAd.InterstitialErrorStatus errorStatus) {

		MMLog.e(TAG, errorStatus.toString());
	}


	@Override
	public void onShown(InterstitialAd interstitialAd) {

		Utils.runOnUiThread(new Runnable() {
			@Override
			public void run() {

				findViewById(R.id.load).setEnabled(true);
				findViewById(R.id.show).setEnabled(false);

				MMLog.e(TAG, "Interstitial shown.");
			}
		});
	}


	@Override
	public void onShowFailed(InterstitialAd interstitialAd, final InterstitialAd.InterstitialErrorStatus errorStatus) {

		Utils.runOnUiThread(new Runnable() {
			@Override
			public void run() {

				findViewById(R.id.load).setEnabled(true);
				findViewById(R.id.show).setEnabled(false);

				MMLog.e(TAG, errorStatus.toString());
			}
		});
	}


	@Override
	public void onClosed(InterstitialAd interstitialAd) {

		MMLog.e(TAG, "Interstitial Ad closed.");
	}


	@Override
	public void onClicked(InterstitialAd interstitialAd) {

		MMLog.e(TAG, "Interstitial Ad clicked.");
	}


	@Override
	public void onExpired(InterstitialAd interstitialAd) {

		MMLog.e(TAG, "Interstitial Ad expired.");
	}


	@Override
	public void onAdLeftApplication(InterstitialAd interstitialAd) {

		MMLog.e(TAG, "Interstitial Ad left application.");
	}
	private void setResult() {
		setResult(Activity.RESULT_OK);
	}
}
