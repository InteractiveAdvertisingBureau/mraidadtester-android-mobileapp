package com.android.iab.utility;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MyAsyncTask extends AsyncTask<String, Void, String> {

	private Activity activity;
	public static ProgressDialog dialog;
	private AsyncTaskListner callback;
	JSONObject jobject;
	JSONObject jsonObject_response;
	String url="";
	
	SharedPreferences mPrefs;
	
	String methoed_name;

	/*public MyAsyncTask(Activity act,JSONObject jobject) {
		this.activity = act;
		this.callback = (AsyncTaskListner)act;
		this.jobject=jobject;
		url="";
		
		Log.e("request", ""+jobject);
		
		// mPrefs = act.getSharedPreferences("Location", Context.MODE_PRIVATE);
		
	}*/
	// NEw Method for posting ride
	public MyAsyncTask(Activity act,JSONObject jobject, String postUrl) {
		
		try {
			try {
				String token=	act.getSharedPreferences("Location", Context.MODE_PRIVATE).getString("TOKEN", "");
				if(!jobject.has("securityToken"))
				jobject.put("securityToken", token);
			
				this.methoed_name=jobject.getString("methodeName");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			this.activity = act;
			this.callback = (AsyncTaskListner)act;
			this.jobject=jobject;
			this.url = postUrl;
			
			
			Log.e("url	", "" + postUrl);
			Log.e("data", "" + jobject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	

	public MyAsyncTask(Activity act,Fragment fragment,JSONObject jobject) {
		this.activity = act;
		this.callback = (AsyncTaskListner)fragment;
		this.jobject=jobject;
		url="";
		 mPrefs = act.getSharedPreferences("Location", Context.MODE_PRIVATE);
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		dialog = new ProgressDialog(activity);
		dialog.setMessage("Loading...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected String doInBackground(String... params) {
		MyHttpClient http=new MyHttpClient(activity);

		if(url.length()>0)
		{
			return http.GetDataForUrl(jobject,url);
		}
		else
		{
			return http.GetData(jobject);
		}
		
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		dialog.cancel();
//		if (null != dialog && dialog.isShowing()) {
//			dialog.dismiss();
//		}
		//call method from here....
		
		
//		result=result.replaceAll("&gt;", ">");
//		result=result.replaceAll("&lt;", "<");
//		result=result.replaceAll("&amp;", "&");
//		result=result.replaceAll("&#39;", "'");
//		result=result.replaceAll("&quot;", "\"");
		
//		if(mPrefs.getInt("CONNECTION_TIMEOUT", 0)==1)
//		{
//			Toast.makeText(activity, "Connection timeout", Toast.LENGTH_LONG).show();
//		}
		
		
			
			try
			{JSONObject jsonObject_response = new JSONObject(result);

			String status = jsonObject_response.getString("status");
			Log.e("status", " = " + status);
			
			callback.onTaskComplete(result,methoed_name);
			
			
		/*	if (status.equalsIgnoreCase("200")) {
		
				callback.onTaskComplete(result,methoed_name);
			}
			
			else{
				String message = jsonObject_response.getString("message");
				
				HelperMethods.openAlert(HelperMessage.MESSAGE_ALERT_FROM_SERVER, message, activity);
			}*/
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		
		
	}
	
	
}
