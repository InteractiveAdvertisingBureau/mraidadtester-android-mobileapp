package com.android.iab.sdk.pabmatic;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.iab.R;
import com.android.iab.utility.IntentKey;
import com.moceanmobile.mast.MASTAdView;
import com.moceanmobile.mast.MASTAdView.LogLevel;
import com.moceanmobile.mast.MASTAdViewInterstitial;

public class PubmaticInterstitialActivity extends Activity {

	private MASTAdView mAdView;
	private TextView header_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		mAdView = (MASTAdViewInterstitial) findViewById(R.id.adView);
		mAdView.setZone(350030); // Can also be set in layout XML



		// Add listeners (optional)
	/*	mAdView.setRequestListener(new AdRequestListener());
		mAdView.setActivityListener(new AdActivityListener());
		mAdView.setRichMediaListener(new AdRichMediaListener());
		mAdView.setInternalBrowserListener(new AdInternalBrowserListener());
*/
		// Set log level (Optional)
		mAdView.setLogLevel(LogLevel.Debug);

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

		setResult();

		// Mraid Banner ad creative
		// Use any one of the creative to test

		// mAdView.setCreativeCode("<img src=\"data:image/gif;base64,TlND\" onerror=\"{if(typeof _caf==='undefined')_caf=[];var m=Math,t=this,i=t.id+'-'+m.floor(m.random()*99),c=_caf,h=location.protocol,f,s;if(h!=='https:')h='http:';c[i]={};f=c[i].pp={};f.cs=h+'//cdn2.crispadvertising.com/CDNbanners/DEFAULT/';f.cp=t.id.split(/-\\w/)[1].replace(/\\D/g,'/')+'/';f.vu='%VEW%';f.cu='%CLK%';f.au='%ACT%';f.eu='%ENG%';f.xp='%PRM%';t.id=i;s=document.createElement('script');s.setAttribute('type','text/javascript');s.setAttribute('src',f.cs+f.cp+'pgclient.js');t.appendChild(s)}(this)\" width=\"5\" height=\"5\" style=\"display:none\" onload=\"this.onerror();\" alt=\".\" id=\"crisp-a7p647z21757\"/>");
		// mAdView.setCreativeCode("<img src=\"data:image/gif;base64,TlND\" onerror=\"{if(typeof _caf==='undefined')_caf=[];var m=Math,t=this,i=t.id+'-'+m.floor(m.random()*99),c=_caf,h=location.protocol,f,s;if(h!=='https:')h='http:';c[i]={};f=c[i].pp={};f.cs=h+'//cdn2.crispadvertising.com/CDNbanners/DEFAULT/';f.cp=t.id.split(/-\\w/)[1].replace(/\\D/g,'/')+'/';f.vu='%VEW%';f.cu='%CLK%';f.au='%ACT%';f.eu='%ENG%';f.xp='%PRM%';t.id=i;s=document.createElement('script');s.setAttribute('type','text/javascript');s.setAttribute('src',f.cs+f.cp+'pgclient.js');t.appendChild(s)}(this)\" width=\"5\" height=\"5\" style=\"display:none\" onload=\"this.onerror();\" alt=\".\" id=\"crisp-a7p647z21756\"/>");
		// mAdView.setCreativeCode("<div class=\"celtra-ad-v3\"> <img src=\"data:image/png,celtra\" style=\"display: none\" onerror=\" (function(img) {  var params = {'placementId':'a790ec04','clickUrl':'[4INFO_click]','clickEvent':'advertiser','externalAdServer':'FOURINFO'};  var req = document.createElement('script');  req.id = params.scriptId = 'celtra-script-' + (window.celtraScriptIndex = (window.celtraScriptIndex||0)+1);  params.clientTimestamp = new Date/1000;  var src = (window.location.protocol == 'https:' ? 'https' : 'http') + '://ads.celtra.com/3ac0e7c5/mraid-ad.js?';  for (var k in params) { src += '&amp;' + encodeURIComponent(k) + '=' + encodeURIComponent(params[k]);  }  req.src = src;  img.parentNode.insertBefore(req, img.nextSibling); })(this); \"/></div>");
		// mAdView.setCreativeCode("<div class=\"celtra-ad-v3\"> <img src=\"data:image/png,celtra\" style=\"display: none\" onerror=\" (function(img) { var params = {'placementId':'90336858','clickUrl':'','clickEvent':'advertiser','externalAdServer':'Custom'}; var req = document.createElement('script'); req.id = params.scriptId = 'celtra-script-' + (window.celtraScriptIndex = (window.celtraScriptIndex||0)+1); params.clientTimestamp = new Date/1000; var src = (window.location.protocol == 'https:' ? 'https' : 'http') + '://ads.celtra.com/3ac0e7c5/mraid-ad.js?'; for (var k in params) { src += '&amp;' + encodeURIComponent(k) + '=' + encodeURIComponent(params[k]); } req.src = src; img.parentNode.insertBefore(req, img.nextSibling); })(this); \"/> </div>");

		// Non Mraid basic banner test creative
		// mAdView.setCreativeCode("<script>var width=320;var height=50;var borderWidth=1;function get_random_color() { var letters = '0123456789ABCDEF'.split(''); var color = '#'; for (var i = 0; i < 6; i++ ) { color += letters[Math.round(Math.random() * 9)]; } return color;}document.write('<div align=\"center\"><a target=\"_blank\" href=\"http://www.pubmatic.com\"><div id=ad\" style=\"height:'+(height-2*borderWidth)+'px;width:'+(width-2*borderWidth)+'px;background-color:'+get_random_color()+';color:#FFF;border:'+borderWidth+'px solid black\" align=\"center\"> <img src=\"http://apps.pubmatic.com/AdGainMgmt/images/logo_pubmatic_new.png\" height=\"20\" width=\"80\" alt=\"PubMatic\"><br>Test Ad: '+width+'x'+height+'</div></a></div>')</script> ");

		// IAB test ad - expandable 1
		// mAdView.setCreativeCode("<script src=\"http://mraid.iab.net/compliance/units/expand.js\"></script><div id=\"aroniabtestad\"></div>");

		// IAB test ad - two-part
		mAdView.setCreativeCode(getIntent().getStringExtra(IntentKey.SCRIPT));

						// IAB test ad - resize
		// mAdView.setCreativeCode("<script src=\"mraid.js\" type=\"text/javascript\"></script><div id=\"adContainer\" style=\"width:320px;margin:0px;padding:0px;background-color:#ffffff\"><div id=\"normal\" style=\"display:none;width:318px;height:48px;margin:auto;position:relative;top:0px;left:0px;background-color:#ffffff;border-style:solid;border-width:1px;border-color:rgb(238,50,36)\" onclick=\"javascript:resize()\"><img width=\"97\" height=\"48\" style=\"position:absolute;top:0px;left:0px\" src=\"http://mraid.iab.net/compliance/units/iab-logo.gif\" /><div style=\"position:absolute;top:0px;left:97px;background-color:#fffffff\"><div style=\"width:221px;height:48px;display:table-cell;text-align:center;vertical-align:middle;font-family:Arial,Helvetica,sans-serif\">Click to Resize</div></div></div><div id=\"resized\" style=\"display:none;width:318px;height:248px;margin:auto;position:relative;top:0px;left:0px;background-color:#ffffff;border-style:solid;border-width:1px;border-color:rgb(238,50,36)\"><img width=\"97\" height=\"50\" style=\"position:absolute;top:0px;left:0px\" src=\"http://mraid.iab.net/compliance/units/iab-logo.gif\"/><div style=\"position:absolute;top:5px;right:5px;background-color:rgb(238,50,36)\"><div style=\"width:20px;height:20px;display:table-cell;text-align:center;vertical-align:middle;font-family:Arial,Helvetica,sans-serif\">X</div></div><div style=\"position:absolute;top:90px;left:56px;background-color:rgb(238,50,36)\"><div style=\"width:37px;height:37px;display:table-cell;text-align:center;vertical-align:middle;font-family:Arial,Helvetica,sans-serif;font-size:8px\" onclick=\"javascript:openSite('http://www.iab.net/iab_products_and_industry_services/508676/mobile_guidance/mraid')\">Open URL</div></div><div style=\"position:absolute;top:90px;left:113px;background-color:rgb(238,50,36)\"><div style=\"width:37px;height:37px;display:table-cell;text-align:center;vertical-align:middle;font-family:Arial,Helvetica,sans-serif;font-size:8px\" onclick=\"javascript:openSite('https://maps.google.com/maps?q=Interactive+Advertising+Bureau+116+East+27th+Street,+7th+Floor+New+York,+New+York+10016&hl=en&sll=37.556536,-122.31563&sspn=0.315717,0.463142&t=m&hq=Interactive+Advertising+Bureau+116+East+27th+Street,+7th+Floor+New+York,+New+York+10016&z=15')\">Click to Map</div></div><div style=\"position:absolute;top:90px;left:170px;background-color:rgb(238,50,36)\"><div style=\"width:37px;height:37px;display:table-cell;text-align:center;vertical-align:middle;font-family:Arial,Helvetica,sans-serif;font-size:8px\" onclick=\"javascript:clickToAppStore()\">Click to App</div></div><div style=\"position:absolute;top:90px;left:227px;background-color:rgb(238,50,36)\"><div style=\"width:37px;height:37px;display:table-cell;text-align:center;vertical-align:middle;font-family:Arial,Helvetica,sans-serif;font-size:8px\" onclick=\"javascript:playVideo('http://mraid.iab.net/compliance/units/iab-video-small.mp4')\">Play Video</div></div><div style=\"position:absolute;top:147px;left:56px;background-color:rgb(238,50,36)\"><div style=\"width:37px;height:37px;display:table-cell;text-align:center;vertical-align:middle;font-family:Arial,Helvetica,sans-serif;font-size:8px\" onclick=\"javascript:sendSMS('12123804700')\">SMS</div></div><div style=\"position:absolute;top:147px;left:113px;background-color:rgb(238,50,36)\"><div style=\"width:37px;height:37px;display:table-cell;text-align:center;vertical-align:middle;font-family:Arial,Helvetica,sans-serif;font-size:8px\" onclick=\"javascript:storePicture('http://mraid.iab.net/compliance/units/iab-logo.gif')\">Store Picture</div></div><div style=\"position:absolute;top:147px;left:170px;background-color:rgb(238,50,36)\"><div style=\"width:37px;height:37px;display:table-cell;text-align:center;vertical-align:middle;font-family:Arial,Helvetica,sans-serif;font-size:8px\" onclick=\"javascript:createCalendarEvent()\">Create Calendar Event</div></div><div style=\"position:absolute;top:147px;left:227px;background-color:rgb(238,50,36)\"><div style=\"width:37px;height:37px;display:table-cell;text-align:center;vertical-align:middle;font-family:Arial,Helvetica,sans-serif;font-size:8px\" onclick=\"javascript:callNumber('12123804700')\">Click to Call</div></div></div></div><style type=\"text/css\">body{background-color:#fff}</style><script>function openSite(a){mraid.open(a)}function sendSMS(a){if(!mraid.supports(\"sms\")){logMRAIDMessage(\"SMS is not supported on this device.\")}else{mraid.open(\"sms://\"+a)}}function callNumber(a){if(!mraid.supports(\"tel\")){logMRAIDMessage(\"Calling is not supported on this device.\")}else{mraid.open(\"tel://\"+a)}}function playVideo(a){mraid.playVideo(a)}function storePicture(a){if(!mraid.supports(\"storePicture\")){logMRAIDMessage(\"storePicture is not supported on this device.\")}else{mraid.storePicture(a)}}function createCalendarEvent(){if(!mraid.supports(\"calendar\")){logMRAIDMessage(\"Calendar is not supported on this device.\")}else{var a={description:\"Mayan Apocalypse/End of World\",location:\"everywhere\",start:\"2013-12-21T00:00-05:00\",end:\"2013-12-22T00:00-05:00\"};mraid.createCalendarEvent(a)}}function clickToAppStore(){if(isIOSDevice()){openSite(\"https://itunes.apple.com/us/app/iab-interactive-advertising/id795405931?mt=8\")}else{if(isAndroidDevice()){openSite(\"https://play.google.com/store/apps/details?id=com.coreapps.android.followme.iabevents\")}else{logMRAIDMessage(\"There is no app strong link available for this device.\")}}}function isIOSDevice(){return(detectDeviceType(\"ipod\")||detectDeviceType(\"iphone\")||detectDeviceType(\"ipad\"))}function isAndroidDevice(){return detectDeviceType(\"android\")}function detectDeviceType(b){var c=navigator.userAgent.toLowerCase();var a=c.indexOf(b);return(a>-1)}function collapse(){mraid.close()}function mraidIsReady(){mraid.removeEventListener(\"ready\",mraidIsReady);showMyAd()}function showMyAd(){var a=document.getElementById(\"normal\");a.style.display=\"\";mraid.addEventListener(\"stateChange\",updateAd);mraid.addEventListener(\"sizeChange\",sizeChangeHandler);mraid.addEventListener(\"error\",errorHandler)}function resize(){var b=mraid.getDefaultPosition();var a=b.x+\",\"+b.y+\",\"+b.width+\",\"+b.height;logMRAIDMessage(\"Default position of banner: \"+a);mraid.setResizeProperties({width:320,height:250,offsetX:0,offsetY:0,allowOffscreen:false});mraid.resize()}function updateAd(a){if(a==\"resized\"){toggleLayer(\"normal\",\"resized\")}else{if(a==\"default\"){toggleLayer(\"resized\",\"normal\")}}}function sizeChangeHandler(b,a){logMRAIDMessage(\"sizeChange event fired!: width = \"+b+\", height = \"+a)}function errorHandler(a,b){logMRAIDMessage(\"error event fired!: message = \"+a+\", action = \"+b);if(b==\"createCalendarEvent\"){logMRAIDMessage(\"User canceled createCalendEvent action!\")}else{if(b==\"storePicture\"){logMRAIDMessage(\"User canceled storePicture action!\")}}}function logMRAIDMessage(a){console.log(\"MRAID: \"+a)}function toggleLayer(b,d){var c=document.getElementById(b);c.style.display=\"none\";var a=document.getElementById(d);a.style.display=\"\"}window.addEventListener(\"orientationchange\",function(){if(mraid.getState()==\"resized\"){collapse()}});function setupViewport(b){var a=document.querySelector(\"meta[name=viewport]\");if(!a){a=document.createElement(\"meta\");a.name=\"viewport\";a.content=\"width=\"+b+\", user-scalable=no\";document.getElementsByTagName(\"head\")[0].appendChild(a)}else{a.content=\"width=\"+b+\", user-scalable=no\"}}setupViewport(320);function doReadyCheck(){if(mraid.getState()==\"loading\"){mraid.addEventListener(\"ready\",mraidIsReady)}else{showMyAd()}}doReadyCheck();</script>");


	}
	private void setResult() {
		setResult(RESULT_OK);
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
			Log.e("Error",e.toString());
		}
		super.onDestroy();
	}

}
