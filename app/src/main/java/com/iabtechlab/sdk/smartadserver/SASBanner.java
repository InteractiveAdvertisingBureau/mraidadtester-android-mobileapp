package com.iabtechlab.sdk.smartadserver;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iabtechlab.R;
import com.iabtechlab.utility.IntentKey;
import com.smartadserver.android.library.SASBannerView;

/**
 * Created by claritus on 14/6/16.
 */
public class SASBanner extends Activity {
    private SASBannerView mBannerView;
    private TextView header_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_ad_banner);
        header_text = findViewById(R.id.header_text);

        //Back button clicked on Header to go Back
        header_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Set Screen Title
        header_text.setText(getResources().getString(R.string.lebel_banner_view));
        mBannerView = findViewById(R.id.banner);
        String pageId = getIntent().getStringExtra(IntentKey.SCRIPT_ID);
        setResult();
        mBannerView.loadAd(100989, "652021", 15048, true, "iab_id=" + pageId, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBannerView != null)
            mBannerView.onDestroy();

    }

    private void setResult() {
        setResult(Activity.RESULT_OK);
    }
}
