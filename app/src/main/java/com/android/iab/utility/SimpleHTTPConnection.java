package com.android.iab.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class SimpleHTTPConnection {
	private static final String TAG = "SimpleHTTPConnection";
	private static final String ERROR = "Simple HTTP Connection Error";
	private Context _context;

	public SimpleHTTPConnection(Context context) {
		this._context = context;
	}

	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	public static String sendByGET(String url) {
		InputStream is;
		StringBuilder sb;
		String result = ERROR;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 20);
			sb = new StringBuilder();
			sb.append(reader.readLine());
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);

			}
			is.close();
			result = sb.toString();
		} catch (UnsupportedEncodingException e) {
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
			System.out.println("UnsupportedEncodingException>>" + e);
		} catch (ClientProtocolException e) {
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
			System.out.println("ClientProtocolException>>" + e);
		} catch (IOException e) {
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
			result = AppConstants.ConnectionTimeOut;
			System.out.println("IOException>>" + e);
		}
		// System.out.println("$$result$$"+result);
		return result;
	}

	// With image upload using post
	public static String sendByPOST(HttpPost httppost,Context context)
	{
		InputStream is;
		StringBuilder sb;
		String result = ERROR;
		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			is = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 20);
			sb = new StringBuilder();
			sb.append(reader.readLine());
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);

			}
			is.close();
			result = sb.toString();
			
		} catch (UnsupportedEncodingException e) {
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
		} catch(Error e){
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	// With image upload using put
	public static String sendByPUT(HttpPut httpput)
	{
		InputStream is;
		StringBuilder sb;
		String result = ERROR;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(httpput);
			HttpEntity entity = response.getEntity();

			is = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 20);
			sb = new StringBuilder();
			sb.append(reader.readLine());
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);

			}
			is.close();
			result = sb.toString();
		} catch (UnsupportedEncodingException e) {
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	public static String sendByPOST(String url, ArrayList<NameValuePair> data,Context context) {
		
		InputStream is;
		StringBuilder sb;
		String result = ERROR;
		try {
			HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is
			// established.
			// The default value is zero, that means the timeout is not used.
			int timeoutConnection = 30000;
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			// Set the default socket timeout (SO_TIMEOUT)
			// in milliseconds which is the timeout for waiting for data.
			int timeoutSocket = 30000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			// System.out.println("sendByPOST URL="+url);

			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(data));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			is = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 20);
			sb = new StringBuilder();
			sb.append(reader.readLine());
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);

			}
			is.close();
			result = sb.toString();
			
		} catch (UnsupportedEncodingException e) {
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
			System.out.println("UnsupportedEncodingException>>" + e);
		} catch (ClientProtocolException e) {
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
			System.out.println("ClientProtocolException>>" + e);
		} catch (IOException e) {
			// Log.i (TAG, e.getMessage ());
			e.printStackTrace();
			result = AppConstants.ConnectionTimeOut;
			System.out.println("IOException>>" + e);
		} catch(OutOfMemoryError error){
			error.printStackTrace();
//			result = AppConstants.ConnectionTimeOut;
			System.out.println("IOException>>" + error);
		}

		// System.out.println("This result in http post>>"+result);
		return result;
	}



	public static String sendByPUT(String url, ArrayList<NameValuePair> data) {
		InputStream is;
		StringBuilder sb;
		String result = ERROR;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPut httpput = new HttpPut(url);
			httpput.setEntity(new UrlEncodedFormEntity(data));
			HttpResponse response = httpclient.execute(httpput);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 20);
			sb = new StringBuilder();
			sb.append(reader.readLine());
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);

			}
			is.close();
			result = sb.toString();

		} catch (UnsupportedEncodingException e) {
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
		}

		return result;

	}

	public static ArrayList<NameValuePair> generateParams(String[] keys,
			String[] values) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		for (int i = 0; i < keys.length; i++) {
			params.add(new BasicNameValuePair(keys[i], values[i]));
		}

		return params;
	}

	public static String buildGetUrl(String url, String[] keys, String[] values) {
		if (!url.endsWith("?"))
			url += "?";
		url += URLEncodedUtils.format(generateParams(keys, values), "utf-8");

		return url;
	}

}
