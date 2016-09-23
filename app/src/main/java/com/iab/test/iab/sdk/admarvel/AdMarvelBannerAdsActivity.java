package com.iab.test.iab.sdk.admarvel;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admarvel.android.ads.AdMarvelUtils;
import com.admarvel.android.ads.AdMarvelUtils.ErrorReason;
import com.admarvel.android.ads.AdMarvelUtils.SDKAdNetwork;
import com.admarvel.android.ads.AdMarvelView;
import com.admarvel.android.ads.AdMarvelView.AdMarvelViewListener;
import com.admarvel.android.util.Logging;


import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import com.iab.test.iab.R;
import com.iab.test.iab.utility.HelperMessage;
import com.iab.test.iab.utility.HelperMethods;
import com.iab.test.iab.utility.IntentKey;

public class AdMarvelBannerAdsActivity extends Activity implements
	 OnCheckedChangeListener
    {

	private String TAG = "AdMarvelBannerAdsDemoActivity";
	private String _siteId = "125546";// "38722";//"35961";
	private String _partnerId = "1dd21b33bd603c95";
	private static final String KEYWORDS = "KEYWORDS";
	private static final String FANTASY = "fantasy";
	private static final String POSTAL_CODE = "POSTAL_CODE";
	private static final String POSTAL_CODE_VAL = "64106";
	private static final String AGE = "AGE";
	private static final String AGE_VAL = "12";

	private final int REQUEST_INTERVAL = 20000;
	private Timer requestIntervalTimer;

	private TextView header_text;
	

	private void scheduleTimeThread()
	{
	    requestIntervalTimer = new Timer();
	    requestIntervalTimer.schedule( new TimerTask()
		{
		    public void run()
		    {
			runOnUiThread( TimerRunnable );

		    }
		} , 0 , REQUEST_INTERVAL );
	}

	private Runnable TimerRunnable = new Runnable()
	    {
		public void run()
		{

		    getAd();

		}
	    };
	private AdMarvelView adMarvelView;




	/** Called when the activity is first created. */
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
	    super.onCreate( savedInstanceState );
	    setContentView(R.layout.test_ad_screen);


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



	    /* AdMarvelView adMarvelView = null;*/
		try {
		    adMarvelView = ( AdMarvelView ) findViewById( R.id.ad );

		}

	    catch ( Exception e1 )
		{
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		} // (AdMarvelView) findViewById(R.id.ad);

	    adMarvelView.setEnableClickRedirect(true);
	    adMarvelView.setDisableAnimation(false);

	    adMarvelView.setEnableAutoScaling(true);
	    adMarvelView.setVisibility( View.GONE );


	       AdMarvelUtils.enableLogging(true);

	    try
		{
		    // Initialized SDKs that need to; pass in publisher ids
		    Map < SDKAdNetwork , String > publisherIds = new HashMap < SDKAdNetwork , String >();

		    AdMarvelUtils.initialize( this , publisherIds );

			getAd();

		}
	    catch ( Exception e )
		{
		    Logging.log( e.getStackTrace().toString() );
		}


		setResult();

		adMarvelView.setListener(new AdMarvelViewListener() {
			public void onReceiveAd( AdMarvelView adMarvelView )
			{
				Logging.log("onReceiveAd");
				// adMarvelView.focus();
				// loadingLayout.setVisibility( View.INVISIBLE );
				// ll. addView ( adMarvelView );
				adMarvelView.setVisibility(View.VISIBLE );

			}



			public void onFailedToReceiveAd( AdMarvelView adMarvelView ,
											 int errorCode , ErrorReason errorReason )
			{
				Logging.log("onFailedToReceiveAd" );
				// loadingLayout.setVisibility(View.INVISIBLE );

				Log.e("errorCode", "errorCode");
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.MESSAGE_AD_LOAD_FAILED, AdMarvelBannerAdsActivity.this);
					}
				});

			}

			public void onClickAd( AdMarvelView adMarvelView , String clickUrl )
			{
				Logging.log( "onClickAd: " + clickUrl );

			}

			public void onRequestAd( AdMarvelView adMarvelView )
			{
				Logging.log( "onRequestAd" );

				LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						100);
				adMarvelView.setLayoutParams(rlp);

			}

			public void onExpand()
			{
				Logging.log( "onExpand" );

			}

			public void onClose()
			{
				Log.e( TAG , "onClose" );


			}
		});
	}

		private void setResult() {
			setResult(RESULT_OK);
		}



	private void getAd()
	{

	    try
		{
		    Map < String , Object > targetParams = new HashMap < String , Object >();
		 /*   targetParams.put( KEYWORDS , FANTASY );
		    targetParams.put( POSTAL_CODE , POSTAL_CODE_VAL );
		    targetParams.put( AGE , AGE_VAL );
		    targetParams.put( "case" , "21" );
		    targetParams.put( "case" , "105" );
		*/    targetParams.put( "AD_HTML" , getIntent().getStringExtra(IntentKey.SCRIPT));




		    adMarvelView.setDisableAnimation( false );

		    adMarvelView.requestNewAd(targetParams, _partnerId,
					_siteId, AdMarvelBannerAdsActivity.this);
		    
		   
		    
		/*    adMarvelView.requestNewAd( targetParams , "1dd21b33bd603c95" ,
				    "125546" , AdMarvelBannerAdsDemoActivity.this );
		    */
		    
		    
			

		}
	    catch ( Exception e )
		{
		    Logging.log( e.getStackTrace().toString() );
		}

	}

	

	
	public void getAd( View v )
	{
	    getAd();
	}

	@Override
	protected void onResume()
	{
	    // TODO Auto-generated method stub
	    super.onResume();

	    try
		{
		     adMarvelView = ( AdMarvelView ) findViewById( R.id.ad );
		     adMarvelView.resume(this);

		}
	    catch ( Exception e )
		{
		    Logging.log( e.getStackTrace().toString() );
		}
	}

	@Override
	protected void onPause()
	{
	    // TODO Auto-generated method stub
	    super.onPause();

	    try
		{


		     adMarvelView.pause(this);

		}
	    catch ( Exception e )
		{
		    Logging.log( e.getStackTrace().toString() );
		}
	}

	@Override
	public void onStart()
	{
	    super.onStart();

	    // AdMarvelView adMarvelView = (AdMarvelView) findViewById(R.id.ad);
	     adMarvelView.start(this);
	}

	@Override
	public void onStop()
	{
	    super.onStop();

	  //  AdMarvelView adMarvelView = ( AdMarvelView ) findViewById( R.id.ad );
	     adMarvelView.stop(this);

	    // your code
	}

	@Override
	protected void onDestroy()
	{
	
	   // AdMarvelView adMarvelView = ( AdMarvelView ) findViewById( R.id.ad );
	     if (adMarvelView != null) {
	     adMarvelView.destroy();
	     }
	    if ( requestIntervalTimer != null )
		{
		    requestIntervalTimer.cancel();
		    requestIntervalTimer.purge();
		    requestIntervalTimer = null;
		}
	    super.onDestroy();
	}



		@Override
	public void onCheckedChanged( CompoundButton buttonView ,
		boolean isChecked )
	{
	    if ( isChecked )
		{
		 //   autoRefreshTV.setVisibility( View.VISIBLE );
		    scheduleTimeThread();
		    //getAdButton.setEnabled( false );
		}
	    else
		{
		   // getAdButton.setEnabled( true );
		  //  autoRefreshTV.setVisibility( View.INVISIBLE );
		    if ( requestIntervalTimer != null )
			{
			    requestIntervalTimer.cancel();
			    requestIntervalTimer.purge();
			    requestIntervalTimer = null;
			}

		}

	}

    }