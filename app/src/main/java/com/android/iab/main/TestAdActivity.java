package com.android.iab.main;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.iab.R;

/**
 * TestAdActivity for loading banner & Interstitial
 *
 * This activity is used to display banner & Interstitial
 *
 * @author Syed
 * @version 2015
 * @since 1.0
 */
    public class TestAdActivity extends Activity implements View.OnClickListener{

    LinearLayout ad_interstitial, ad_banner;
    TextView header_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_ad_screen);

        getUIobjects();
        setOnClickEventListner();

    }


    private void setOnClickEventListner() {

        header_text.setOnClickListener(this);

    }

    private void getUIobjects() {

        ad_interstitial = (LinearLayout) findViewById(R.id.ad_interstitial);
        ad_banner = (LinearLayout)findViewById(R.id.ad_banner);

        header_text = (TextView) findViewById(R.id.header_text);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.header_text:

                finish();
                break;


        }
    }
}
