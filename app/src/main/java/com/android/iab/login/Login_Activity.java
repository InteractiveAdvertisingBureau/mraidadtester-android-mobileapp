package com.android.iab.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.iab.R;
import com.android.iab.helper.HelperMessage;
import com.android.iab.helper.HelperMethods;
import com.android.iab.main.MainActivity;
import com.android.iab.sqlitehelper.DatabaseHandler;
import com.android.iab.sqlitehelper.User_Details;
import com.android.iab.utility.ApiList;
import com.android.iab.utility.AsyncTaskListner;
import com.android.iab.utility.Helper;
import com.android.iab.utility.MyAsyncTask;
import com.android.iab.welcome.Welcome_Activity;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Login_Activity for loading Ad Tag fragmnet in main layouts
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
 */

public class Login_Activity extends Activity implements OnClickListener, AsyncTaskListner {


    private static final String LOG_TAG = "LoginActivity" ;
    LinearLayout back_layout;
    TextView policy_textView,logged_user_name, skip_Button;
    EditText user_name_editText, company_name_editText, user_email_editText;
    Button login_Button;

    JSONObject jsonObject_request;      // JSONObject for storing request which'll send over the server.
    JSONObject jsonObject_response;     // JSONObject for retrieving response from server.

    public static String PLATEFORM_ANDROID = "android";

    int count = 1; //user count for db

    String user_nameString, company_nameString,user_emailString;   // string for storing user credentials
    SharedPreferences mPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //Hiding default app header
        setContentView(R.layout.login);

        mPrefs = getSharedPreferences(HelperMethods.MODE_TYPE, MODE_PRIVATE);

        findViewById(R.id.back_layout).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();

            }
        });



        getUIobjects();

        addClickListner();

        policy_textView.setMovementMethod(LinkMovementMethod.getInstance());
//		String text = "<a href='http://www.claritusconsulting.com.com'> Terms </a> and " +
//				"<a href='http://www.claritusconsulting.com.com'> Privacy Policy. </a>";
//		policy_textView2.setText(Html.fromHtml(text));
        policy_textView.setLinkTextColor(Color.RED);




    }




    private void getUIobjects() {
       // TODO Auto-generated method stub

      //  policy_textView2 = (TextView) findViewById(R.id.policy_textView2);
        policy_textView = (TextView) findViewById(R.id.policy_textView);
        logged_user_name = (TextView) findViewById(R.id.logged_user_name);
        skip_Button = (TextView) findViewById(R.id.skip_Button);

        user_name_editText = (EditText) findViewById(R.id.user_name_editText);
        company_name_editText = (EditText) findViewById(R.id.company_name_editText);
        user_email_editText = (EditText) findViewById(R.id.user_email_editText);

        login_Button =  (Button) findViewById(R.id.login_Button);


    }



    private void addClickListner() {
        // TODO Auto-generated method stub

        //	policy_textView.setOnClickListener(this);
        login_Button.setOnClickListener(this);
        skip_Button.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.login_Button:

                user_nameString = user_name_editText.getText().toString();
                user_emailString = user_email_editText.getText().toString();
                company_nameString = company_name_editText.getText().toString();

                if(validateData())
                {

//                    userLogin();

                    storeData(count, user_nameString,user_emailString,company_nameString);
                    count++;

                    Editor editor = mPrefs.edit();
                    editor.putBoolean("isLogin", true);
                    editor.putString("userName", user_nameString);
                    editor.commit();

                    defaultOneButtonDialog(this, user_nameString+", " + HelperMessage.LOGIN_WELCOME_MESSAGE);



                }

                break;
            case R.id.skip_Button:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
                // close this activity
                finish();
                break;

            default:
                break;
        }
    }


    public void defaultOneButtonDialog(Activity activity, String msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //moving to Main Activity when data is validated
                        Intent intent = new Intent(Login_Activity.this, Welcome_Activity.class);

                        startActivity(intent);
                        // close this activity
                        finish();
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }

  /*  private ArrayList<String> login() {

        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {

            StringBuilder sb = new StringBuilder();
            sb.append("/"+user_nameString);
            sb.append("/"+user_nameString);
            sb.append("/" + company_nameString);
            sb.append("/" + user_emailString);
            sb.append("/" + PLATEFORM_ANDROID);

            String param = sb.toString();

      //      sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet(ApiList.USER_LOGIN);
            request.setParams(param);

            HttpResponse response = httpclient.execute(request);
            response.getStatusLine().getStatusCode();
          //  InputStreamReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            *//*StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String result = sb.toString();
            Log.v("My Response :: ", result);*//*

            URL url = new URL(sb.toString());

            System.out.println("URL: "+url);

            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }




        }
        catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray responseJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(responseJsonArray.length());
            for (int i = 0; i < responseJsonArray.length(); i++)
            {
                System.out.println(responseJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(responseJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;

    }*/


    /**
     * storeData for further use
     */
    private void storeData(int count, String user_name, String user_email, String company_name) {
        // TODO Auto-generated method stub

        DatabaseHandler db = new DatabaseHandler(this);

        // Inserting User data

        db.addUser(new User_Details(count, user_name, user_email, company_name ));

        Log.d("Data Inserted: ", " " + count + " " + user_name + " " + user_email + " " + company_name);

        // Reading all contacts
        Log.d("Reading: ", "Reading all data..");
        List<User_Details> user_Details = db.getAllUser();

        for (User_Details user : user_Details)
        {
            String log = "Id: "+user.getId()+" ,Name: " + user.getName() + " ,Email: " + user.getEmail()
                    +" ,Company: " + user.getCompany();

            // Writing Contacts to log
            Log.d("Name: ", log);

        }
    }




    /**
     * validateData for validating data
     */

    private Boolean validateData() {
        // TODO Auto-generated method stub
        if(user_nameString.length()==0){
            Toast.makeText(getApplicationContext(), HelperMessage.MESSAGE_USER_NAME,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (company_nameString.length()==0) {

            Toast.makeText(getApplicationContext(), HelperMessage.MESSAGE_COMPANY_NAME,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(user_emailString.length()==0)
        {
            Toast.makeText(getApplicationContext(), HelperMessage.MESSAGE_USER_EMAIL,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!HelperMethods.isEmailValid(user_emailString))
        {
            Toast.makeText(getApplicationContext(), HelperMessage.MESSAGE_USER_EMAIL,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        else
            return true;
    }




    /**
     * userLogin() is used for adding user data in JSONObject
     */
    private void userLogin()
    {

        if(HelperMethods.isNetworkAvailable(Login_Activity.this))
        {
            jsonObject_request=new JSONObject();
            try
            {
                jsonObject_request.put("firstname", user_nameString);
                jsonObject_request.put("lastname", user_nameString);
                jsonObject_request.put("company", company_nameString);
                jsonObject_request.put("email", user_emailString);
                jsonObject_request.put("platform", PLATEFORM_ANDROID);

                Log.d("#request#", "" + jsonObject_request.toString());
            }
            catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            /**
             * Calling  AsyncTask CallBack
             */
            MyAsyncTask task = new MyAsyncTask(this, jsonObject_request,ApiList.USER_LOGIN);
            task.execute();

        }

        else
        {

            Helper.defaultOneButtonDialog(this, "Network error");

        }

    }


    /*
	 * callback method for track API response start
	 *  used for getting & extracting response from the JSONObject
	 */


    @Override
    public void onTaskComplete(String result, String url)
    {

        try
        {
            jsonObject_response=new JSONObject(result);

            String response=jsonObject_response.getString("response");
            if(response.equalsIgnoreCase("true"))
            {
            Toast.makeText(getApplicationContext(), "Succesfully Login", Toast.LENGTH_SHORT).show();

            //moving to Main Activity when data is validated
            Intent signIntent = new Intent(getApplicationContext(), Welcome_Activity.class);
            startActivity(signIntent);
            // close this activity
            finish();
        }
            else
            {
                JSONObject data=jsonObject_response.getJSONObject("data");
                String msg=data.getString("message");
                Helper.defaultOneButtonDialog(this,msg);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}

