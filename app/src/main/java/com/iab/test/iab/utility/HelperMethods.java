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

package com.iab.test.iab.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.iab.test.iab.R;
import com.iab.test.iab.database.DataSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HelperMethods extends Application {
    /**
     * Method  to check there is on Internet Connection or not
     */
    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(activity.getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Method  to send Email
     * @param emailTo  is used to whom user want to send Email
     * @param emailSubject is used as a Email Subject
     * @param emailContent is used as a Email Content
     */
    public static void sendEmail(Activity context,String emailTo, String emailSubject, String emailContent) {
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?subject=" + emailSubject + "&body=" + emailContent + "&to=" + emailTo);
        testIntent.setData(data);
        context. startActivity(testIntent);
    }
    /**
     * Method  to Hide Keyboard
     */
    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null && activity.getCurrentFocus() instanceof EditText) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getCurrentFocus()
                    .getWindowToken(), 0);
        }
    }
    /**
     * Method  to check  Email is Valid or not
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    /**
     * Method  to Open Alert/Dialog
     * @param title is used as a Dialog Title
     * @param message is used as a Dialog Message
     */
    public static void openAlert(final String title, final String message, final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                alertDialogBuilder.setTitle(title);
                alertDialogBuilder.setMessage(message);
                // set positive button: Yes message
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show alert
                alertDialog.show();
            }
        });

    }

    /**
     * Method  to open Alert/Dialog when Server Request is TimeOut
     */
    public static void serverRequestTimeout(Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(HelperMessage.MESSAGE_SERVER_ALERT);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }
    /**
     * Method  to open Alert/Dialog if Server Request is failed
     */
    public static void serverRequestError(Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(HelperMessage.MESSAGE_SERVER_ALERT);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }
    /**
     * Method  to check Script/Creative is Already store in Database or not
     */
    public static int isScriptAlreadyStored(Activity activity ,String script) {
        int isAdded;
        DataSource dataSource=new DataSource(activity.getApplicationContext());
        dataSource.open();
        isAdded=dataSource.isCreativeAlreadySavedIntoDb(script);
            dataSource.close();
            return  isAdded;
        }
    /**
     * Method  to get User Creative Name to save Creative on Server & Local Database
     */
    public static String getCreativeName(Activity activity) {
        DataSource dataSource=new DataSource(activity.getApplicationContext());
        dataSource.open();
       String tempScriptName= dataSource.getTempScriptName();
            dataSource.close();
            return  tempScriptName;
        }
    /**
     * Method  to Decode String from Base64
     * @param encodeValue is a String which is used to Decode
     */
    public static String decode(String encodeValue) {
        byte[] decodeValue = Base64.decode(encodeValue, Base64.DEFAULT);
        return new String(decodeValue);
    }
    /**
     * Method  to Encode String into Base64
     * @param testValue is a String which is used to Encode
     */
    public static String encode(String testValue) {
        String base64 = Base64.encodeToString(testValue.getBytes(), Base64.DEFAULT);
        return base64;
    }

}
