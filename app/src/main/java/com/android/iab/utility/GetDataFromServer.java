/**
 * ****************************************************************************
 * Copyright (c) 2015, Interactive Advertising Bureau
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p>
 * ****************************************************************************
 */

package com.android.iab.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.iab.R;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;

/**
 * This class is used to Request and get Response from Server
 */
public class GetDataFromServer {
    public ProgressDialog dialog;
    /**
     * Fields which are used in this Class
     *
     * @param mActivity              This is an instnace of Activity which is Pass by Activity
     * @param mFragment              This is an instnace of Fragment which is Pass by Fragment
     * @param response_data          This is a String as a Response getting after Request on Server
     * @param api_name               This is a String which  tells which API is called
     * @param AsyncTaskListner       This is an Interface which is called after getting Response from Server
     * @param ProgressDialog         This is a Progress Dialog which is used to Display Loader when Request is going on on Server
     */
    private Activity mActivity;
    private Fragment mFragment;
    private String response_data;
    private String api_name;
    private AsyncTaskListner callback;
    private String url;
    private int serverRequestResponse;

    public GetDataFromServer(Activity activity) {
        mActivity = activity;
        this.callback = (AsyncTaskListner) activity;
    }

    public GetDataFromServer(Activity activity, Fragment fragment) {
        mActivity = activity;
        mFragment = fragment;
        this.callback = (AsyncTaskListner) mFragment;
        serverRequestResponse = 1;
    }

    public void getResponse(String url, String api_name) {
        this.url = url;
        this.api_name = api_name;
        serverRequestResponse = 1;
        loadData();
    }

    /**
     * This method is Called to Get Response From Server After Passing Request
     *
     * @param url is used to get HTTPGet Data from Server
     */
    private void getData(String url) {
        try {

            Log.e("url", url);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            HttpParams httpParams = client.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, GlobalInstance.CONNECTION_TIME_OUT);
            HttpConnectionParams.setSoTimeout(httpParams, GlobalInstance.CONNECTION_TIME_OUT);
            response_data = client.execute(request, responseHandler);
        } catch (ConnectTimeoutException e) {
            //Here Connection TimeOut excepion
            serverRequestResponse = GlobalInstance.IS_SERVER_REQUEST_TIME_OUT;
            //   Log.e("time out", "time out");
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            serverRequestResponse = GlobalInstance.IS_SERVER_REQUEST_ERROR;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            serverRequestResponse = GlobalInstance.IS_SERVER_REQUEST_ERROR;
        }

    }

    /**
     * This method is Called form AsyncTask to get Response from Server If Internet is Available
     */
    private void loadData() {
        // TODO Auto-generated method stub
        if (HelperMethods.isNetworkAvailable(mActivity)) {
            new PerformBackgroundtask().execute();
        } else {
            HelperMethods.openAlert(mActivity.getResources().getString(R.string.app_name), HelperMessage.NETWORK_ERROR_MESSAGE, mActivity);
        }
    }

    /**
     * AsyncTask
     */
    class PerformBackgroundtask extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            try {
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
            getData(url);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                dialog.dismiss();
                Log.e("RESPONSE", response_data);
                callback.onTaskComplete(response_data, api_name, serverRequestResponse);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
    }

}
