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

package com.android.iab.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.iab.R;
import com.android.iab.adapter.NavigationDrawerAdapter;
import com.android.iab.bean.CreativesListBean;
import com.android.iab.database.DataSource;
import com.android.iab.model.NavDrawerItem;
import com.android.iab.setting.Setting;
import com.android.iab.utility.ApiList;
import com.android.iab.utility.AsyncTaskListner;
import com.android.iab.utility.GetDataFromServer;
import com.android.iab.utility.GlobalInstance;
import com.android.iab.utility.HelperMessage;
import com.android.iab.utility.HelperMethods;
import com.android.iab.utility.OnCreativeListClickListner;
import com.android.iab.utility.SharePref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentDrawer  is used for Left Slider Menu having Creative List,Setting and Edit
 */

public class FragmentDrawer extends Fragment implements View.OnClickListener, AsyncTaskListner {
    ArrayList<CreativesListBean> creativesListBeans = new ArrayList<>();
    List<NavDrawerItem> navDrawerItems = new ArrayList<>();
    /**
     * Declaration of  widget which are used in This Page Globally
     *
     * @param recyclerView                 This is a RecyclerView which is used to Display Recycler View
     * @param isEditText                   This is a TextView which is used to skip This Page
     * @param settingTextView              This is a TextView which is used to Display Setting page
     * @param mDrawerToggle                This is a ActionBarDrawerToggle which is used to Add Slider Menu
     * @param mDrawerLayout                This is a DrawerLayout which is used to Add Slider Menu in Main Layout
     * @param containerView                This is a Slider Menu View
     * @param mSwipeRefreshLayout               This is a SwipeRefresh Layout which is used to refresh Recycler View on pull
     */
    private RecyclerView recyclerView;
    private TextView isEditText;
    private TextView settingTextView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    /**
     * @param adapter                        This is used for RecycleView  Adapter
     * @param drawerListener                 This is used as a Listner where Sliding Drawer is Open or Closed
     * @param creativesListBeans             This is a collection ArrayList which is used for Creative List.
     * @param isEdit                         This is a boolean to check whether Edit option is on/off.
     */
    private NavigationDrawerAdapter adapter;
    private FragmentDrawerListener drawerListener;
    private boolean isEdit;
    private int deletedPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        view.setOnClickListener(null);
        getUIobjects(view);
        setOnClickEventListner();
        displayCreativeList();
        getCreativeTitleNameList();
        //Setting color scheme to be shown while refreshing
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
        return view;
    }

    public void refreshItems() {
        getUserCreativeFromServer();
    }

    void onItemsLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Method that initialize all the view & widgets
     */
    private void getUIobjects(View view) {
        isEditText = (TextView) view.findViewById(R.id.isEditTextView);
        settingTextView = (TextView) view.findViewById(R.id.settingTextView);
        recyclerView = (RecyclerView) view.findViewById(R.id.drawerList);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.contentRefreshView);
    }

    /**
     * Method for calling ClickListner() event
     */
    private void setOnClickEventListner() {
        isEditText.setOnClickListener(this);
        settingTextView.setOnClickListener(this);
    }

    /**
     * ClickListner
     */
    public void onClick(View v) {
        switch (v.getId()) {   //Click to Edit Creative List
            case R.id.isEditTextView:
                if (isEdit) { //If Edit Option  true
                    isEdit = false;
                    isEditText.setText(getActivity().getString(R.string.edit));
                    settingTextView.setVisibility(View.VISIBLE);
                    getCreativeTitleNameList();
                } else {  //If Edit Option  false
                    isEdit = true;
                    getCreativeTitleNameList();
                    isEditText.setText(getActivity().getString(R.string.done));
                    settingTextView.setVisibility(View.INVISIBLE);
                    getCreativeTitleNameList();
                }
                break;
            //After Setting Pressed will go Profile Page
            case R.id.settingTextView:
                if (!isEdit) {
                    mDrawerLayout.closeDrawer(containerView);
                    Intent intent = new Intent(getActivity(), Setting.class);
                    getActivity().startActivity(intent);
                }
                break;
        }
    }

    /**
     * Display Creative List which has been saved
     */
    private void displayCreativeList() {
        adapter = new NavigationDrawerAdapter(getActivity(), navDrawerItems, isEdit);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                if (isEdit && !creativesListBeans.get(position).getIsDeleted().equals(GlobalInstance.TYPE_DEFAULT_CREATIVE)) {//If Edit Option True then delete Creative from Creative List and Refresh Creative Page
                    deleteUserCreativeFromServer(position);
                } else if (!isEdit) {//If Edit Option False then Open Home Page for this Creative
                    mDrawerLayout.closeDrawer(containerView);
                    OnCreativeListClickListner onCreativeListClickListner = (OnCreativeListClickListner) getActivity();
                    onCreativeListClickListner.OnCreativeListItemClickListner(position);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    /**
     * For Infalting MenuDrawer & implement the action on drawer open & close
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
                HelperMethods.hideSoftKeyboard(getActivity());
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public void refreshCreativeList() {
        getCreativeTitleNameList();
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    /**
     * Get Creative List which are Saved.
     */
    public List<NavDrawerItem> getCreativeTitleNameList() {
        if (navDrawerItems.size() > 0)
            navDrawerItems.clear();
        if (creativesListBeans.size() > 0)
            creativesListBeans.clear();
        creativesListBeans = new DataSource(getActivity().getApplicationContext()).getCreativeListFromDb();
        // preparing navigation drawer items
        for (int i = 0; i < creativesListBeans.size(); i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(creativesListBeans.get(i).getCreativeName());
            navItem.setIsDeleted(creativesListBeans.get(i).getIsDeleted());
            navDrawerItems.add(navItem);
        }
        adapter.isEdit = isEdit;
        adapter.setNotifyDataSetChanged(navDrawerItems);
        adapter.notifyDataSetChanged();
        return navDrawerItems;
    }

    /**
     * This Method  is used to getUserCreative List From Sever
     */
    private void deleteUserCreativeFromServer(int position) {
        if (HelperMethods.isNetworkAvailable(getActivity())) {
            String url = ApiList.BASE_URL + ApiList.API_URL_DELETE_CREATIVE + SharePref.getUserAccessKey(getActivity().getApplicationContext()) + "/" + creativesListBeans.get(position).getId();
            GetDataFromServer getDataFromServer = new GetDataFromServer(getActivity(), FragmentDrawer.this);
            getDataFromServer.getResponse(url, ApiList.API_URL_DELETE_CREATIVE);
            deletedPosition = position;
        } else {
            HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.NETWORK_ERROR_MESSAGE, getActivity());
        }
    }

    /**
     * This Method  is used to save User's Creative
     */
    private void getUserCreativeFromServer() {
        if (HelperMethods.isNetworkAvailable(getActivity())) {
            String url = ApiList.BASE_URL + ApiList.API_URL_GET_ALL_CREATIVE + SharePref.getUserAccessKey(getActivity().getApplicationContext());
            GetDataFromServer getDataFromServer = new GetDataFromServer(getActivity(), FragmentDrawer.this);
            getDataFromServer.getResponse(url, ApiList.API_URL_GET_ALL_CREATIVE);
            if (getDataFromServer.dialog.isShowing())
                getDataFromServer.dialog.hide();
        } else {
            onItemsLoadComplete();
            HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.NETWORK_ERROR_MESSAGE, getActivity());
        }
    }

    /**
     * Remove Creative From Creative List
     */
    public void deleteCreative(int position) {
        DataSource dataSource = new DataSource(getActivity().getApplicationContext());
        dataSource.open();
        long rowId = dataSource.removeCreativeFromDb(creativesListBeans.get(position).getId());
        Log.d("rowId", "" + rowId);
        dataSource.close();
        if (rowId != -1)
            refreshCreativeList();
    }

    /**
     * Interface Callback: callback method for track API response
     * used for getting & extracting response from the JSON
     *
     * @param result  Response String getting By Server
     * @param apiName Identify Which API is used in this Request
     */
    @Override
    public void onTaskComplete(String result, String apiName, int serverRequest) {
        Log.d(result, apiName);
        if (serverRequest == GlobalInstance.IS_SERVER_REQUEST_TRUE) {
            if (apiName.equals(ApiList.API_URL_DELETE_CREATIVE)) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("response");
                    if (status.equals("true")) {
                        deleteCreative(deletedPosition);
                        MainActivity.getInstance().getCreativeListFromDb();
                        HelperMethods.openAlert(getResources().getString(R.string.app_name), HelperMessage.CREATIVE_DELETED, getActivity());
                    } else {
                        String response = jsonObject.getString("response");
                        HelperMethods.openAlert(getResources().getString(R.string.app_name), response, getActivity());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (apiName.equals(ApiList.API_URL_GET_ALL_CREATIVE)) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if (status.equals("true")) {
                        JSONArray creativeJsonArray = jsonObject.getJSONArray("response");
                        DataSource dataSource = new DataSource(getActivity().getApplicationContext());
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
                            p_Des = response_data.replace("\\", "");
                            p_SdkName = creativeJsonObject.getString("p_SdkName");
                            dataSource.insertCreativeIntoDb(id, p_BannerType, p_CreativeName, p_Des, p_SdkName, r_id);
                        }
                        onItemsLoadComplete();
                        dataSource.close();
                        getCreativeTitleNameList();
                        MainActivity.getInstance().getCreativeListFromDb();
                    } else {
                        String response = jsonObject.getString("response");
                        HelperMethods.openAlert(getResources().getString(R.string.app_name), response, getActivity());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (serverRequest == GlobalInstance.IS_SERVER_REQUEST_ERROR) {
                onItemsLoadComplete();
                HelperMethods.serverRequestError(getActivity().getApplicationContext());
            } else if (serverRequest == GlobalInstance.IS_SERVER_REQUEST_TIME_OUT) {
                onItemsLoadComplete();
                HelperMethods.serverRequestTimeout(getActivity().getApplicationContext());
            }
        }
    }

    /**
     *Get Creative From Server
     */
    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

       /* @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }*/
    }

}