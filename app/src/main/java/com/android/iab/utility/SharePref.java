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

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This is simple class to declare Shared Preference Fields which can access globally
 */
public class SharePref {
    public static String MODE_TYPE = "iab";
    public static String USER_NAME = "name";
    public static String USER_EMAIL = "email";
    public static String USER_COMPANY = "company";
    public static String USER_IS_LOGIN = "isLogin";
    public static String USER_CREATIVE_LIST = "creative_list";
    public static String AD_TYPE_TAG = "Ad_Type";
    public static String DEFAULT_ACCESS_KEY = "default_access_key";
    public static String USER_ACCESS_KEY = "user_access_key";
    public static String USER_TYPE = "user_type";
    /**
     * Method to get Default Access Key
     * */
    public static String getDefaultAccessKey(){
      return GlobalInstance.DEFAULT_ACCESS_KEY;
    }

    public static String getUserAccessKey(Context context){
        SharedPreferences sharedPreferences=context.getApplicationContext().getSharedPreferences(SharePref.MODE_TYPE, Context.MODE_PRIVATE);
      return sharedPreferences.getString(USER_ACCESS_KEY,"");
       // return "3uvzhCVxX44ulX3Cfk6rYg";

    }
    /**
     * Method to check User is Login or not
     * */
    public static boolean isUserLogin(Context context){
        SharedPreferences sharedPreferences=context.getApplicationContext().getSharedPreferences(SharePref.MODE_TYPE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(USER_IS_LOGIN, false);
    }

    /**
     * Method to get User Name
     * */
    public static String getUserName(Context context){
        SharedPreferences sharedPreferences=context.getApplicationContext().getSharedPreferences(SharePref.MODE_TYPE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, GlobalInstance.DEFAULT_GUEST);
    }

    /**
     * Method to set User Info Locally using Shared Preference
     * */
    public static void setUserInfo(Context context,String user_name, String user_email, String company_name, String access_key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharePref.MODE_TYPE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, user_name);
        editor.putString(USER_EMAIL, user_email);
        editor.putString(USER_COMPANY, company_name);
        editor.putBoolean(USER_IS_LOGIN, true);
        editor.putString(USER_ACCESS_KEY, access_key);
        editor.putString(USER_TYPE, "");
        editor.commit();
    }
    /**
     * Method to set Skip Access Key Locally using Shared Preference
     * */
    public static void setSkipAccessKey(Context context, String access_key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharePref.MODE_TYPE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ACCESS_KEY, access_key);
        editor.putBoolean(USER_IS_LOGIN, true);
        editor.commit();
    }
}
