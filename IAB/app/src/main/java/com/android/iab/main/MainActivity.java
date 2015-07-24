package com.android.iab.main;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.iab.R;
import com.android.iab.helper.HelperMethods;

/**
 * Home Activity for loading layouts resources
 *
 * This activity is used to display three different layout.
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
 */

public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener ,View.OnClickListener {


    LinearLayout back_layout;

    /**
     * ACTIVITY_LOG for use logging debug output to LogCat
     */
    String ACTIVITY_LOG = "";


    /**
     * SharedPreferences object to store coomon sharable data.
     */
    SharedPreferences mPrefs;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    //  EditText tab_add_tag;
    TextView tab1, tab2,tab3;
    TextView next_button, previous_button, save_button;
    //  String javaScriptString ="";

    /**
     * TAB_POSITION for managing position of tab. i.e. in which
     * tag in we currently we are.
     */
    int TAB_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TAB_POSITION = 0;
        ACTIVITY_LOG = this.getClass().getSimpleName();

        /**
         * Initializing the SharedPreferences
         */

        mPrefs= getSharedPreferences(HelperMethods.MODE_TYPE, MODE_PRIVATE);

        /**
         * Attaching  toolbar
         */
        mToolbar = (Toolbar) findViewById(R.id.toolbar);            //attaching toolbar

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
     //  getSupportActionBar().setTitle(getResources().getString(R.string.app_actionbar_title));
     //  getSupportActionBar().setIcon(R.drawable.small_logo);

        /**
         * for use setting title text & color in Toolbar.
         */
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);


        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        getUIobjects();
        setOnClickEventListner();
        loadAdFragment();
 }

    /**
     * Method that initialize all the view & widgets
     */

    private void getUIobjects() {

     //   tab_add_tag = (EditText) findViewById(R.id.add_tag_textView);
        next_button = (TextView) findViewById(R.id.next_button);
        previous_button = (TextView) findViewById(R.id.previous_button);
        save_button = (TextView) findViewById(R.id.save_button);


        tab1= (TextView) findViewById(R.id.tab1);
        tab2= (TextView) findViewById(R.id.tab2);
        tab3= (TextView) findViewById(R.id.tab3);

    //    javaScriptString = tab_add_tag.getText().toString();
    }



    /**
     * Method for calling ClickListner() event
     */
    private void setOnClickEventListner() {


        next_button.setOnClickListener(this);

        previous_button.setOnClickListener(this);


    }


    /**
     * Method for Managing click event on drawer item
     */

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }


    /**
     * ClickListner
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.next_button:

                if(TAB_POSITION==0){

                    TAB_POSITION=TAB_POSITION+1;
                    loadSDKFragment();

                }
                else if(TAB_POSITION==1){

                    TAB_POSITION=TAB_POSITION+1;

                    loadTypeFragment();
                    updateUI(TAB_POSITION);

                }

                else if(TAB_POSITION==2){

                    loadTypeFragment();
                    updateUI(TAB_POSITION);
                    TAB_POSITION++;
                }

              break;

            case R.id.previous_button:

                if(TAB_POSITION==1)
                {

                TAB_POSITION=TAB_POSITION-1;

                loadAdFragment();
                updateUI(TAB_POSITION);

                }

            else if(TAB_POSITION==2){

                TAB_POSITION=TAB_POSITION-1;
                loadSDKFragment();
                updateUI(TAB_POSITION);

            }

            break;
        }

        }


    /**
     * Method for Loading Ad Fragment in MainActivity
     */
    private void loadAdFragment() {

        Fragment fragment = new AddTagFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.FrameLayout, fragment)
                .commit();

        updateUI(TAB_POSITION);
    }

    /**
     * Method for Loading Sdk Fragment in MainActivity
     */
    private void loadSDKFragment() {

        Fragment fragment = new SelectSDKFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.FrameLayout, fragment)

                .commit();

        updateUI(TAB_POSITION);
    }


    /**
     * Method for Loading Type Fragment in MainActivity
     */
    private void loadTypeFragment() {

        Fragment fragment = new AddTypeFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.FrameLayout, fragment)

                .commit();

        updateUI(TAB_POSITION);
    }



    /**
     * Method for Manging UI's changes on Navigation
     */
    private void updateUI(int position) {
        switch(position)
        {
            case 0 :previous_button.setVisibility(View.GONE);
                    save_button.setVisibility(View.GONE);
                    next_button.setVisibility(View.VISIBLE);
                tab1.setTextColor(getResources().getColor(R.color.red));
                tab2.setTextColor(getResources().getColor(R.color.grey));
                tab3.setTextColor(getResources().getColor(R.color.grey));


                Log.e("position updateUI 0", "position = " + position);
                    break;

            case 1 :save_button.setVisibility(View.GONE);
                    previous_button.setVisibility(View.VISIBLE);
                    save_button.setVisibility(View.GONE);
                Log.e("position updateUI 1", "position = " + position);


                tab1.setTextColor(getResources().getColor(R.color.grey));
                tab2.setTextColor(getResources().getColor(R.color.red));
                tab3.setTextColor(getResources().getColor(R.color.grey));


                break;

            case 2 :previous_button.setVisibility(View.VISIBLE);
                    save_button.setVisibility(View.GONE);
                    next_button.setVisibility(View.GONE);
                Log.e("position updateUI 2", "position = " + position);


                tab1.setTextColor(getResources().getColor(R.color.grey));
                tab2.setTextColor(getResources().getColor(R.color.grey));
                tab3.setTextColor(getResources().getColor(R.color.red));


                break;


        }



    }



 /*   private void allUnSelected() {

        Log.e("allUnSelected", "allUnSelected called");
        tab1.setTextColor(getResources().getColor(R.color.text_color_grey_one));
        tab2.setTextColor(getResources().getColor(R.color.text_color_grey_one));
        tab3.setTextColor(getResources().getColor(R.color.text_color_grey_one));
    }*/
}
