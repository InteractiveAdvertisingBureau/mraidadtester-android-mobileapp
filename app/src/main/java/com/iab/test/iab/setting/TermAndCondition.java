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

package com.iab.test.iab.setting;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.iab.test.iab.R;
import com.iab.test.iab.font.SetFont;
import com.iab.test.iab.utility.HelperMessage;
import com.iab.test.iab.utility.HelperMethods;
import com.iab.test.iab.utility.ApiList;

/**
 * This activity is used to display Term & Condition on WebView Page
 */
public class TermAndCondition extends Activity implements View.OnClickListener {
    /**
     * Declaration of  widget which are used in This Page Globally
     *
     * @param header_text                        This is a TextView which is used to press Back Button as well as Display Header Title
     * @param webView                            This is a WebView which is used to Display Term & Condition Page
     * */
    private TextView header_text;
    private WebView webView;

    /**
     * Fields which are used in this Class
     *
     * @param url                                This is a String which is used as a Url when Load on WebView
     * @param dialog                             This is a Progress Dialog to display Loader when Load WebView
     **/

    private String url;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_condition_screen);
        getUIobjects();
        setOnClickEventListner();
        new SetFont(this);
        setHeaderTitle();
        displayTermAndCoditionData();
    }

    /**
     * Methoed is used to Set Header Title
     * */
    private void setHeaderTitle() {
        header_text.setText(getResources().getString(R.string.lebel_term_condition));
    }

    /**
     * Methoed is display Term & Condition Content on WebView
     * */
    private void displayTermAndCoditionData() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        dialog.setCancelable(true);
        webView = (WebView) findViewById(R.id.webView1);
        url = ApiList.URL_TERM_CONDITION;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.requestFocus(View.FOCUS_DOWN);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setScrollContainer(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(1);
        webView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }
        });
        webView.setWebViewClient(new myWebClient());
        if (HelperMethods.isNetworkAvailable(this)) {
            dialog.show();
            webView.loadUrl(url);
        } else {
            dialog.hide();
            ;
            HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.NETWORK_ERROR_MESSAGE, this);
        }
    }

    /**
     * Methoed to add Click Event on UI Object
     */
    private void setOnClickEventListner() {
        header_text.setOnClickListener(this);
    }

    /**
     * Methoed to create UI Object from XML Layout
     */
    private void getUIobjects() {
        header_text = (TextView) findViewById(R.id.header_text);
        webView = (WebView) findViewById(R.id.webView1);
    }

    /**
     * Interface called after Click Event on UI Object
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Back button clicked on Header to go Back
            case R.id.header_text:
                finish();
                break;
        }
    }

    /**
     * Calling WebView Client to Open Url on WebView Page
     */
    public class myWebClient extends WebViewClient {

        boolean loadingFinished = true;
        boolean redirect = false;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            loadingFinished = false;
            if (!dialog.isShowing())
                dialog.show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            if (!dialog.isShowing())
                dialog.show();
            if (!loadingFinished) {
                redirect = true;
            }
            loadingFinished = false;
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            try {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                if (!redirect) {
                    loadingFinished = true;
                }
                if (loadingFinished && !redirect) {
                    dialog.hide();
                } else {
                    redirect = false;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
