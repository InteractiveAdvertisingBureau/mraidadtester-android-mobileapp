package com.android.iab.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.android.iab.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class GetPostDataFromServer {
Activity mActivity;

String response_data,response_data_line;

String data;
String api_name;
int timeout;

static PerformBackgroundtask mtask;

private AsyncTaskListner callback;

public ProgressDialog dialog;
	String url;
	private int serverRequestResponse;

	public GetPostDataFromServer(Activity activity) {
		mActivity=activity;
		this.callback=(AsyncTaskListner)activity;
	}

	public String getResponse(String url,String data , String api_name)  {
	try {
		//this.jsonObject=jsonObject;
	   this.url=url;
		 this.data=data;
		this.api_name=api_name;
		Log.e("data",url+data);
		loadData();
			return response_data;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return "";
	}
	
	
class PerformBackgroundtask extends AsyncTask<Void, Void, Void> {
	
		protected void onPreExecute() {
			 try {
				// MainActivity.getInstance().menuTextView.setVisibility(View.GONE);
				dialog = new ProgressDialog(mActivity);
				    dialog.setMessage("Loading...");
				    //dialog.setIndeterminate(true);				
				    	  dialog.show();
				    dialog.setCancelable(false);
				   
				super.onPreExecute();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			
				getData(data, url);
								//getData(getData);
				//getData(nameValuePairs, url);
			return null;
		}
	
		@Override
		protected void onPostExecute(Void result)  {
			try {
				dialog.dismiss();
				// MainActivity.getInstance().menuTextView.setVisibility(View.VISIBLE);
				
                  Log.e("response",response_data_line+"\n"+response_data+"\n"+api_name+"\n"+serverRequestResponse);
					callback.onTaskComplete(response_data, api_name, serverRequestResponse);


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	}

    public String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
    
   	private void getData(String  data ,String url) {
   		// TODO Auto-generated method stub
   		try {
   			timeout=0;
   			HttpClient client = new DefaultHttpClient();
   			HttpParams httpParams = client.getParams();
   			HttpConnectionParams.setConnectionTimeout(httpParams, GlobalInstance.CONNECTION_TIME_OUT);
			HttpConnectionParams.setSoTimeout(httpParams, GlobalInstance.CONNECTION_TIME_OUT);
   			HttpPost post = new HttpPost(url);
             Log.e("data_length()",""+data.length());
   				StringEntity se = new StringEntity(data);
   				//se.setContentEncoding("UTF-8");
   				se.setContentType("application/x-www-form-urlencoded");
   				post.setEntity(se);


//   			  UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);
//   			 entity.setContentType("application/x-www-form-urlencoded");
//
//   			 post.setEntity(entity);

   			HttpResponse response;
   			response = client.execute(post);
   			response_data_line=response.getStatusLine().toString();
   		 //  Log.e("response",response.getStatusLine().toString());
   			response_data=convertStreamToString(response.getEntity().getContent());
			serverRequestResponse=GlobalInstance.IS_SERVER_REQUEST_TRUE;
   			Log.e("result", response_data_line+response_data);
   		}

		catch (ConnectTimeoutException e) {
			//Here Connection TimeOut excepion
			serverRequestResponse=GlobalInstance.IS_SERVER_REQUEST_TIME_OUT;
			//   Log.e("time out", "time out");
		}
		catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			serverRequestResponse=GlobalInstance.IS_SERVER_REQUEST_ERROR;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			serverRequestResponse=GlobalInstance.IS_SERVER_REQUEST_ERROR;
		}
   		
   	}
	
	private void loadData() {
		// TODO Auto-generated method stub
		if(isNetworkAvailable()){
		
         mtask=new PerformBackgroundtask();
	   mtask.execute();
		}
		
	}
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) mActivity.getSystemService(mActivity.getApplicationContext().CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
