/**
 * ****************************************************************************
 * Copyright (c) 2015, Interactive Advertising Bureau
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p/>
 * ****************************************************************************
 */

package com.android.iab.main;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.iab.R;
import com.android.iab.adapter.AdTypeAdapter;
import com.android.iab.adapter.SdkListAdapter;
import com.android.iab.bean.AdTypeBean;
import com.android.iab.bean.CreativesListBean;
import com.android.iab.bean.SDKBean;
import com.android.iab.database.DataSource;
import com.android.iab.font.SetFont;
import com.android.iab.sdk.adform.AdInlineActivity;
import com.android.iab.sdk.adform.AdOverlayActivity;
import com.android.iab.sdk.admarvel.AdMarvelBannerAdsActivity;
import com.android.iab.sdk.admarvel.AdMarvelInterstitialActivity;
import com.android.iab.sdk.inmobi.BannerAdsActivity;
import com.android.iab.sdk.inmobi.InterstitialAdsActivity;
import com.android.iab.sdk.millennial.InlineActivity;
import com.android.iab.sdk.millennial.InterstitialActivity;
import com.android.iab.sdk.openx.OpenXBannerActivity;
import com.android.iab.sdk.openx.OpenXInterstitial;
import com.android.iab.sdk.pabmatic.PubmaticBannertActivity;
import com.android.iab.sdk.pabmatic.PubmaticInterstitialActivity;
import com.android.iab.sdk.smartadserver.SASBanner;
import com.android.iab.sdk.smartadserver.SASInterstitial;
import com.android.iab.utility.ApiList;
import com.android.iab.utility.AsyncTaskListner;
import com.android.iab.utility.GetDataFromServer;
import com.android.iab.utility.GetPostDataFromServer;
import com.android.iab.utility.GlobalInstance;
import com.android.iab.utility.HelperMessage;
import com.android.iab.utility.HelperMethods;
import com.android.iab.utility.IntentKey;
import com.android.iab.utility.OnCreativeListClickListner;
import com.android.iab.utility.SharePref;
import com.android.iab.utility.URLUTF8Encoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener, View.OnClickListener, OnCreativeListClickListner, AsyncTaskListner {
    public static MainActivity mainActivity;
    /**
     * Declaration of  widget which are used in This Page Globally
     *
     * @param mToolbar                              This is a Material Design ToolBar
     * @param drawerFragment                        This is a FragmentDrawer which is used to Slide In/Out
     * @param addTagTextView                        This is a TextView which is used for AD TAG Tab
     * @param selectSdkTextView                     This is a TextView which is used for SELECT SDK Tab
     * @param selectAddTypeTextView                 This is a TextView which is used for  AD TAG  Tab
     * @param nextButton                            This is a TextView which is used to go next Tab
     * @param previousButton                        This is a TextView which is used to go previous Tab
     * @param startButton                           This is a TextView which is used to START New Creative
     * @param saveButton                            This is a TextView which is used to SAVE Creative
     * @param addTagView                            This is a View which shows AD TAG View
     * @param selectSdkView                         This is a View which shows SELECT SDK View
     * @param selectAddTypeView                     This is a View which shows SELECT AD TYPE View
     * @param add_tag_editText                      This is a EditTextView which is used to get AD TAG Script
     * @param selectSdkListView                     This is a ListView which is used to display SDK List
     * @param sdkNameTextView                       This is a TextView which tells which SDK is used for AD
     * @param bannerView                            This is a ListView which is used to display AD TYPE List
     */

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private TextView addTagTextView;
    private TextView selectSdkTextView;
    private TextView selectAddTypeTextView;
    private TextView nextButton; //
    private TextView previousButton;
    private TextView startButton;
    private TextView saveButton;
    private View addTagView;
    private View selectSdkView;
    private View selectAddTypeView;
    private EditText add_tag_editText;
    private ListView selectSdkListView;
    private TextView sdkNameTextView;
    private ListView bannerView;
    /**
     * Fields which are used in this Class
     *
     * @param tab_position                           This is an Integer which tells which Tab is Enable
     * @param ad_tag_position                        This is an Integer which tells AD TAG  Tab is Enable
     * @param select_sdk_position                    This is an Integer which tells SELECT SDK  Tab is Enable
     * @param select_add_type_position               This is an Integer which tells AD TYPE  Tab is Enable
     * @param creativeListBeans                      This is a Collection which is used to display Menu List for slider Menu
     * @param sdkListAdapter                         This is an Adapter to select SDK
     * @param sdk_list                               This is a Collection which is used for SDK List
     * @param selectedSdk                            This is a String to check which SDK is selected
     * @param sdkName                                This is a String which is used for SDK Name
     * @param sdkversion                             This is a String which is used for SDK Version
     * @param adTypeAdapter                          This is an Adapter to select AD Type
     * @param ad_type_list                           This is a Collection which is used for AD Type List
     * @param ad_type_tag                            This is a String.It tells which AD Type is selected to display Ad
     * @param ad_type_position                       This is an Integer .It tells which AD Type is selected to display Ad
     * @param ad_type_bean                           This is a Bean Class for AD Type
     * @param selectedAddType                        This is a String.To which assign AD Type
     * @param test_creative                          This is an Integer for Request Code.
     */


    private int tab_position;
    private int ad_tag_position = 0;
    private int select_sdk_position = 1;
    private int select_add_type_position = 2;
    private ArrayList<CreativesListBean> creativeListBeans = new ArrayList<CreativesListBean>();
    private SdkListAdapter sdkListAdapter;
    private ArrayList<SDKBean> sdk_list = new ArrayList<SDKBean>();
    private String selectedSdk; //  use for sdk unique Id
    private String sdkName;   // use for sdk name
    private String sdkversion; //use for sdk version
    private AdTypeAdapter adTypeAdapter;
    private ArrayList<AdTypeBean> ad_type_list = new ArrayList<AdTypeBean>();
    private AdTypeBean ad_type_bean;
    private String selectedAddType;
    private int test_creative = 1;
    private String add_tag_String;
    private String creativeName;
    private String scriptId;
    private int user_creative_type;

    public MainActivity() {
        mainActivity = this;
    }

    public static MainActivity getInstance() {
        if (mainActivity == null) {
            mainActivity = new MainActivity();
        }
        return mainActivity;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tab_position = 0;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);            //attaching toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        getUIObjects();
        setOnClickEventListner();
        loadAllView();
        updateUI(ad_tag_position);
        new SetFont(this);
        boolean is_creative_loaded = getIntent().getBooleanExtra(IntentKey.IS_LOAD_USER_CREATIVE, false);
        if (is_creative_loaded)
            getAllCreativeFromServer();
        else
            userLogin();
        getCreativeListFromDb();

    }

    /**
     * This Method  is used to getAllCreative List From Sever
     */
    private void getAllCreativeFromServer() {
        if (HelperMethods.isNetworkAvailable(this)) {
            String url = ApiList.BASE_URL + ApiList.API_URL_GET_ALL_CREATIVE + SharePref.getUserAccessKey(getApplicationContext());
            GetDataFromServer getDataFromServer = new GetDataFromServer(MainActivity.this);
            getDataFromServer.getResponse(url, ApiList.API_URL_GET_ALL_CREATIVE);
        } else {
            HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.NETWORK_ERROR_MESSAGE, this);
        }
    }

    /**
     * This Method  is used to save User's Creative
     *
     * @param userCreativeType to check Creative Type
     */
    private void saveUserCreativeFromServer(int userCreativeType) {
        if (HelperMethods.isNetworkAvailable(this)) {
            user_creative_type = userCreativeType;
            String url = ApiList.BASE_URL + ApiList.API_URL_SAVE_CREATIVE;
            String data = "apikey=" + SharePref.getUserAccessKey(getApplicationContext()) + "&name=" + URLUTF8Encoder.encode(creativeName) + "&des=" + URLUTF8Encoder.encode(add_tag_String) + "&sdk=" + URLUTF8Encoder.encode(sdkName) + "&type=" + URLUTF8Encoder.encode(selectedAddType);
            GetPostDataFromServer getPostDataFromServer = new GetPostDataFromServer(this);
            getPostDataFromServer.getResponse(url, data, ApiList.API_URL_SAVE_CREATIVE, false);
        } else {
            HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.NETWORK_ERROR_MESSAGE, this);
        }
    }

    private void userLogin() {
       /* String url = ApiList.BASE_URL + ApiList.API_URL_SIGN_UP + "/" + "demo_first_name" + "/" + "demo_last_name" + "/" + getID() + "/" + "demo_company" + "/android";
        GetDataFromServer getDataFromServer = new GetDataFromServer(MainActivity.this);
        getDataFromServer.getResponse(url, ApiList.API_URL_SIGN_UP);*/
        String data = "firstname=%s&lastname=%s&email=%s&company=%s&platform=android";
        ;
        //data =  URLUTF8Encoder.encode(user_nameString) + "/" +  URLUTF8Encoder.encode(user_nameString) + "/" +  URLUTF8Encoder.encode(user_emailString) + "/" +  URLUTF8Encoder.encode(company_nameString) + "/android";
        data = String.format(data, "demo_first_name", "demo_last_name", getID(), "demo_company");
        String url = ApiList.IAB_BASE_URL + ApiList.API_URL_RIGISTRATION;
        /*
        GetDataFromServer getDataFromServer = new GetDataFromServer(SignUpActivity.this);
        getDataFromServer.getResponse(url, ApiList.API_URL_SIGN_UP);*/
        GetPostDataFromServer getPostDataFromServer = new GetPostDataFromServer(MainActivity.this);
        getPostDataFromServer.getResponse(url, data, ApiList.API_URL_RIGISTRATION, true);

    }


    public String getID() {
        // 10 digits.
        long LIMIT = 10000000000L;
        long last = 0;
        long id = System.currentTimeMillis() % LIMIT;
        if (id <= last) {
            id = (last + 1) % LIMIT;
        }
        return id + "@skipuser.com";
    }

    /**
     * Method that initialize all the view & widgets
     */
    private void getUIObjects() {
        nextButton = (TextView) findViewById(R.id.next_button);
        previousButton = (TextView) findViewById(R.id.previous_button);
        saveButton = (TextView) findViewById(R.id.save_button);
        startButton = (TextView) findViewById(R.id.start_button);
        addTagTextView = (TextView) findViewById(R.id.addTagTextView);
        selectSdkTextView = (TextView) findViewById(R.id.selectSdkTextView);
        selectAddTypeTextView = (TextView) findViewById(R.id.selectAddTypeTextView);
    }

    /**
     * Method for calling ClickListner() event
     */
    private void setOnClickEventListner() {
        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        addTagTextView.setOnClickListener(this);
        selectSdkTextView.setOnClickListener(this);
        selectAddTypeTextView.setOnClickListener(this);
    }

    /**
     * Method for Handle click event on drawer item
     */
    @Override
    public void onDrawerItemSelected(View view, int position) {
    }

    /**
     * ClickListner
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addTagTextView:
                updateUI(ad_tag_position);
                break;
            case R.id.selectSdkTextView:
                updateUI(select_sdk_position);
                break;
            case R.id.selectAddTypeTextView:
                updateUI(select_add_type_position);
                break;
            case R.id.next_button:
                if (tab_position == 0)
                    updateUI(select_sdk_position);
                else if (tab_position == 1)
                    updateUI(select_add_type_position);
                break;
            case R.id.previous_button:
                if (tab_position == 1)
                    updateUI(ad_tag_position);
                else if (tab_position == 2)
                    updateUI(select_sdk_position);
                break;
            case R.id.save_button:
                createDialogForAddCreative();
                break;
            case R.id.start_button:
                // refresh Activity
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
                break;
        }
    }

    /**
     * Method To create Dialog when user want to save Creative
     */
    private void createDialogForAddCreative() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(HelperMessage.CREATIVE_NAME_MASSAGE);
        //Create EditText Object to Enter Creative Name
        final EditText creativeNameEditText = new EditText(this);
        creativeNameEditText.setHint(HelperMessage.CREATIVE_NAME_MASSAGE_HINT);
        creativeNameEditText.setPadding((int) this.getResources().getDimension(R.dimen.default_text_size_small_very), (int) getResources().getDimension(R.dimen.default_text_size_small_very), (int) getResources().getDimension(R.dimen.default_text_size_small_very), (int) getResources().getDimension(R.dimen.default_text_size_small_very));
        creativeNameEditText.setGravity(Gravity.CENTER);
        alertDialogBuilder.setView(creativeNameEditText);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                creativeName = creativeNameEditText.getText().toString().trim();
                if (creativeName.length() > 0)
                    saveUserCreativeFromServer(GlobalInstance.USER_CREATIVE_MANUAL);
                else
                    HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.CREATIVE_NAME_MASSAGE, MainActivity.this);
            }
        });
        // set negative button: No message
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // cancel the alert box and put a Toast to the user
                dialog.dismiss();
            }
        });
        // set neutral button: Exit the app message
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
    }

    /**
     * Method to add Creative Name into CreativeList
     *
     * @param creativeId To save creative into Local Database After successfully save on Server
     */
    private void addCreativeIntoCreativeList(String creativeId) {

        DataSource dataSource = new DataSource(getApplicationContext());
        dataSource.open();
        boolean isAdded = dataSource.isCreativeAddedIntoDb(creativeName);
        if (isAdded == false) {
            Log.d("Save Creative", creativeId);
            dataSource.insertCreativeIntoDb(creativeId, selectedAddType, creativeName, add_tag_String, sdkName, GlobalInstance.TYPE_MY_CREATIVE);
            getCreativeListFromDb();
            drawerFragment.refreshCreativeList();
        } else {
            HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.CREATIVE_NAME_ALREADY_MASSAGE, this);
        }
        dataSource.close();
    }

    /**
     * Method for Managing UI's changes on Navigation
     *
     * @param selectedTab is used to show Tab Position
     */
    private void updateUI(int selectedTab) {
        switch (selectedTab) {
            case 0:
                displayAddTagView();
                tab_position = selectedTab;
                break;
            case 1:
                HelperMethods.hideSoftKeyboard(this);
                if (validateScript()) {
                    displaySelectSdkView();
                    tab_position = selectedTab;
                }
                break;
            case 2:
                HelperMethods.hideSoftKeyboard(this);
                if (validateScript() && isSdkSelected()) {
                    displaySelectAddType();
                    tab_position = selectedTab;
                }
                break;
        }
    }

    /**
     * Method for  ADD TAG validation
     */
    private Boolean validateScript() {
        add_tag_String = add_tag_editText.getText().toString().trim();
        if (add_tag_String.length() > 0)
            return true;
        else {
            HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.AD_TAG_VALIDATION_MESSAGE, this);
        }
        return false;
    }

    /**
     * Method for  SELECT SDK  validation
     */
    private boolean isSdkSelected() {
        SDKBean sdk_bean = sdkListAdapter.getSelectedSdk();
        if (sdk_bean != null) {
            selectedSdk = sdk_bean.getId();
            sdkName = sdk_bean.getSdkName();
            sdkversion = sdk_bean.getSdkversion();
            return true;
        } else {
            HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.SDK_VALIDATION_MESSAGE, this);
        }
        return false;
    }

    /**
     * Method for  SELECT SDK  validation
     */
    private boolean isAddTypeSelected() {
        selectedAddType = adTypeAdapter.getSelectedAddType();
        if (selectedAddType.length() > 0)
            return true;
        else {
            HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.ADD_TYPE_VALIDATION_MESSAGE, this);
        }
        return false;
    }

    /**
     * Method to display ADD TAG Screen
     */
    private void displayAddTagView() {
        addTagView.setVisibility(View.VISIBLE);
        selectSdkView.setVisibility(View.GONE);
        selectAddTypeView.setVisibility(View.GONE);
        previousButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.GONE);
        addTagTextView.setTextColor(getResources().getColor(R.color.red));
        selectSdkTextView.setTextColor(getResources().getColor(R.color.grey));
        selectAddTypeTextView.setTextColor(getResources().getColor(R.color.grey));
    }

    /**
     * Method to display SELECT SDK Screen
     */
    private void displaySelectSdkView() {
        addTagView.setVisibility(View.GONE);
        selectSdkView.setVisibility(View.VISIBLE);
        selectAddTypeView.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        previousButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);
        startButton.setVisibility(View.GONE);
        addTagTextView.setTextColor(getResources().getColor(R.color.grey));
        selectSdkTextView.setTextColor(getResources().getColor(R.color.red));
        selectAddTypeTextView.setTextColor(getResources().getColor(R.color.grey));
    }

    /**
     * Method to display SELECT ADD TYPE  Screen
     */
    private void displaySelectAddType() {
        addTagView.setVisibility(View.GONE);
        selectSdkView.setVisibility(View.GONE);
        selectAddTypeView.setVisibility(View.VISIBLE);
        previousButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
        startButton.setVisibility(View.GONE);
        addTagTextView.setTextColor(getResources().getColor(R.color.grey));
        selectSdkTextView.setTextColor(getResources().getColor(R.color.grey));
        selectAddTypeTextView.setTextColor(getResources().getColor(R.color.red));
        sdkNameTextView.setText(sdkName + " " + sdkversion);
        Log.e("sdk version", sdkversion);
    }

    /**
     * Method to load view and UI Object  for ADD TAG,SELECT SDK, SELCT AD TYPE
     */
    private void loadAllView() {
        addTagView = findViewById(R.id.tabLayout1);
        selectSdkView = findViewById(R.id.tabLayout2);
        selectAddTypeView = findViewById(R.id.tabLayout3);
        addUIObjectForAddTag();
        addUIObjectForSelectSdk();
        addUIObjectForAddType();
    }

    /**
     * Method to add UI of ADD Tag
     */
    private void addUIObjectForAddTag() {
        add_tag_editText = (EditText) addTagView.findViewById(R.id.add_tag_editText);
    }

    /**
     * Method to add UI of SELECT SDK
     */
    private void addUIObjectForSelectSdk() {
        selectSdkListView = (ListView) selectSdkView.findViewById(R.id.sdk_list_item);
        for (int i = 0; i < GlobalInstance.SDK_NAME_ARRAY.length; i++) {
            SDKBean sdk_bean = new SDKBean();
            sdk_bean.setSdkName(GlobalInstance.SDK_NAME_ARRAY[i]);
            sdk_bean.setSdkversion(GlobalInstance.SDK_VERSION_ARRAY[i]);
            sdk_bean.setId("" + i);
            sdk_bean.setIsSelected(false);
            sdk_list.add(sdk_bean);
        }
        sdkListAdapter = new SdkListAdapter(this, sdk_list);
        selectSdkListView.setAdapter(sdkListAdapter);
    }

    /**
     * Method to add UI of ADD TYPE
     */
    private void addUIObjectForAddType() {
        sdkNameTextView = (TextView) selectAddTypeView.findViewById(R.id.sdkNameTextView);
        bannerView = (ListView) selectAddTypeView.findViewById(R.id.ad_type_list_item);
        selectAddTypeView.findViewById(R.id.button_test_creative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testCreative();
            }
        });
        for (int i = 0; i < GlobalInstance.AD_TYPE_ARRAY.length; i++) {
            ad_type_bean = new AdTypeBean();
            ad_type_bean.setAdtype(GlobalInstance.AD_TYPE_ARRAY[i]);
            ad_type_bean.setIsSelected(false);
            ad_type_bean.setId("" + i);
            ad_type_list.add(ad_type_bean);
        }
        adTypeAdapter = new AdTypeAdapter(this, ad_type_list);
        bannerView.setAdapter(adTypeAdapter);
    }

    /**
     * Method to Reset option AdType
     */
    public void ResetAdType() {
        for (int i = 0; i < GlobalInstance.AD_TYPE_ARRAY.length; i++) {
            ad_type_list.get(i).setIsSelected(false);
        }
        adTypeAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == test_creative) {
            saveButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.GONE);
            startButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Interface call when User select Creative from Menu
     *
     * @param position This is Selected Creative Position
     */
    public void OnCreativeListItemClickListner(int position) {
        add_tag_String = creativeListBeans.get(position).getAddTag();
        scriptId = creativeListBeans.get(position).getId();
        selectedSdk = creativeListBeans.get(position).getSdkName();
        sdkName = creativeListBeans.get(position).getSdkName();
        sdkversion = creativeListBeans.get(position).getSdkversion();
        selectedAddType = creativeListBeans.get(position).getAddType();
        add_tag_editText.setText(add_tag_String);
        sdkListAdapter.setSelectedSdk(selectedSdk);
        adTypeAdapter.setSelectedAddType(selectedAddType);
        updateUI(ad_tag_position);
    }

    /**
     * Method  to get Creative List from Local Database
     */
    public void getCreativeListFromDb() {
        DataSource dataSource = new DataSource(getApplicationContext());
        if (creativeListBeans.size() > 0)
            creativeListBeans.clear();
        creativeListBeans = dataSource.getCreativeListFromDb();
        drawerFragment.refreshCreativeList();
    }

    /**
     * Method  to display ad after select Tag,SDK & Ad Type
     */
    public void testCreative() {
        if (isAddTypeSelected()) {
            Intent intent = null;
            String script = null;
            try {
                script = URLDecoder.decode(URLUTF8Encoder.encode(add_tag_editText.getText().toString().trim()), "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (sdkName.equals(GlobalInstance.SDK_TYPE_AD_MARVEL)) {

                if (selectedAddType.equals(GlobalInstance.AD_TYPE_INTERSTITIAL))
                    intent = new Intent(MainActivity.this, AdMarvelInterstitialActivity.class);
                else
                    intent = new Intent(MainActivity.this, AdMarvelBannerAdsActivity.class);
                intent.putExtra(IntentKey.SCRIPT, script);
                startActivityForResult(intent, test_creative);

            } else if (sdkName.equals(GlobalInstance.SDK_TYPE_PUB_MATIC)) {

                if (selectedAddType.equals(GlobalInstance.AD_TYPE_INTERSTITIAL))
                    intent = new Intent(MainActivity.this, PubmaticInterstitialActivity.class);
                else
                    intent = new Intent(MainActivity.this, PubmaticBannertActivity.class);
                intent.putExtra(IntentKey.SCRIPT, script);
                startActivityForResult(intent, test_creative);
            } else if (sdkName.equals(GlobalInstance.SDK_TYPE_MILLENNIAL)) {
                int scriptId = HelperMethods.isScriptAlreadyStored(MainActivity.this, add_tag_editText.getText().toString().trim());
                if (scriptId != 0) {
                    if (selectedAddType.equals(GlobalInstance.AD_TYPE_INTERSTITIAL))
                        intent = new Intent(MainActivity.this, InterstitialActivity.class);
                    else
                        intent = new Intent(MainActivity.this, InlineActivity.class);
                    intent.putExtra(IntentKey.SCRIPT_ID, "" + scriptId);
                    startActivityForResult(intent, test_creative);
                } else {
                    creativeName = HelperMethods.getCreativeName(MainActivity.this);
                    saveUserCreativeFromServer(GlobalInstance.USER_CREATIVE_AUTO);
                    // HelperMethods.openAlert(getResources().getString(R.string.app_name), "Not Saved", MainActivity.this);
                }


            } else if (sdkName.equals(GlobalInstance.SDK_TYPE_INMOBI)) {
                if (selectedAddType.equals(GlobalInstance.AD_TYPE_INTERSTITIAL))
                    intent = new Intent(MainActivity.this, InterstitialAdsActivity.class);
                else
                    intent = new Intent(MainActivity.this, BannerAdsActivity.class);
                intent.putExtra(IntentKey.SCRIPT, script);
                startActivityForResult(intent, test_creative);
            } else if (sdkName.equals(GlobalInstance.SDK_TYPE_ADFORM)) {
                if (selectedAddType.equals(GlobalInstance.AD_TYPE_INTERSTITIAL))
                    intent = new Intent(MainActivity.this, AdOverlayActivity.class);
                else
                    intent = new Intent(MainActivity.this, AdInlineActivity.class);
                intent.putExtra(IntentKey.SCRIPT, script);
                startActivityForResult(intent, test_creative);
            } else if (sdkName.equals(GlobalInstance.SDK_TYPE_OPENX)) {
                int scriptId = HelperMethods.isScriptAlreadyStored(MainActivity.this, add_tag_editText.getText().toString().trim());
                if (selectedAddType.equals(GlobalInstance.AD_TYPE_INTERSTITIAL))
                    intent = new Intent(MainActivity.this, OpenXInterstitial.class);
                else
                    intent = new Intent(MainActivity.this, OpenXBannerActivity.class);
                intent.putExtra(IntentKey.SCRIPT_ID, "" + scriptId);
                startActivityForResult(intent, test_creative);
            } else if (sdkName.equals(GlobalInstance.SDK_TYPE_SMART_AD_SERVER)) {
                int scriptId = HelperMethods.isScriptAlreadyStored(MainActivity.this, add_tag_editText.getText().toString().trim());
                if (scriptId != 0) {
                    if (selectedAddType.equals(GlobalInstance.AD_TYPE_INTERSTITIAL))
                        intent = new Intent(MainActivity.this, SASInterstitial.class);
                    else
                        intent = new Intent(MainActivity.this, SASBanner.class);
                    intent.putExtra(IntentKey.SCRIPT_ID, "" + scriptId);
                    startActivityForResult(intent, test_creative);
                } else {
                    creativeName = HelperMethods.getCreativeName(MainActivity.this);
                    saveUserCreativeFromServer(GlobalInstance.USER_CREATIVE_AUTO);
                }
            }
        }
    }


    /**
     * Interface Callback: callback method for track API response
     * used for getting & extracting response from the JSON
     *
     * @param result        Response String getting By Server
     * @param apiName       Identify Which API is used in this Request
     * @param serverRequest check Request Response Status
     */
    @Override
    public void onTaskComplete(String result, String apiName, int serverRequest) {
        if (serverRequest == GlobalInstance.IS_SERVER_REQUEST_TRUE) {
            if (apiName.equals(ApiList.API_URL_GET_ALL_CREATIVE)) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if (status.equals("true")) {
                        JSONArray creativeJsonArray = jsonObject.getJSONArray("response");
                        DataSource dataSource = new DataSource(getApplicationContext());
                        dataSource.open();
                        String id;
                        String p_BannerType;
                        String p_CreativeName;
                        String p_Des;
                        String p_SdkName;
                        String r_id;
                        dataSource.deleteAllPreviousData();
                        for (int position = 0; position < creativeJsonArray.length(); position++) {
                            JSONObject creativeJsonObject = creativeJsonArray.getJSONObject(position);
                            id = creativeJsonObject.getString("id");
                            r_id = creativeJsonObject.getString("r_id");
                            p_BannerType = creativeJsonObject.getString("p_BannerType");
                            p_CreativeName = creativeJsonObject.getString("p_CreativeName");
                            String response_data = creativeJsonObject.getString("p_Des");
                            //p_Des = response_data.replace("\\", "");
                            p_Des = response_data;
                            p_SdkName = creativeJsonObject.getString("p_SdkName");
                            dataSource.insertCreativeIntoDb(id, p_BannerType, p_CreativeName, p_Des, p_SdkName, r_id);
                        }
                        getCreativeListFromDb();
                        dataSource.close();
                    } else {
                        String response = jsonObject.getString("response");
                        HelperMethods.openAlert(getResources().getString(R.string.app_name), response, this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (apiName.equals(ApiList.API_URL_RIGISTRATION)) {
                try {
                    JSONObject jsonObject_response = new JSONObject(result);
                    String response = jsonObject_response.getString("response");
                    if (response.equalsIgnoreCase("true")) {
                        String access_key = jsonObject_response.getString("accessKey");
                        SharePref.setSkipAccessKey(getApplicationContext(), access_key);
                    }
                    getCreativeListFromDb();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (apiName.equals(ApiList.API_URL_SAVE_CREATIVE)) {
                try {
                    JSONObject jsonObject_response = new JSONObject(result);
                    String status = jsonObject_response.getString("status");
                    if (status.equalsIgnoreCase("true")) {
                        String creative_id = jsonObject_response.getString("response");
                        addCreativeIntoCreativeList(creative_id);
                        if (user_creative_type == GlobalInstance.USER_CREATIVE_MANUAL)
                            HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.CREATIVE_ADDED, this);
                        else {
                            scriptId = creative_id;
                            testCreative();
                        }

                    }
                    getCreativeListFromDb();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (serverRequest == GlobalInstance.IS_SERVER_REQUEST_ERROR) {
                HelperMethods.serverRequestError(getApplicationContext());
            } else if (serverRequest == GlobalInstance.IS_SERVER_REQUEST_TIME_OUT) {
                HelperMethods.serverRequestTimeout(getApplicationContext());
            }
        }
    }

}