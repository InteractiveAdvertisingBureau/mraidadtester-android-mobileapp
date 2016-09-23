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

public class GlobalInstance {
    /**
    * @param SDK_NAME_ARRAY                         This is an Array of String which is used for SDK Name
    * @param SDK_VERSION_ARRAY                      This is an Array of String which is used for SDK Version
    * @param AD_TYPE_ARRAY                          This is an Array of String which is used for AD Type
    */
    public static String[] SDK_NAME_ARRAY = {"Ad Marvel", "PubMatic","Millennial","InMobi","AdForm","SmartAdServer"};
    public static String[] SDK_VERSION_ARRAY = {"v3.2.6 ", "v2.1", "v6.1.0","V5.1.1","V2.5.0","V6.3"};
    public static String[] AD_TYPE_ARRAY = {"Banner ", "Interstitial"};
    public static String AD_TYPE_BANNER = "Banner";
    public static String AD_TYPE_INTERSTITIAL = "Interstitial";
    public static String SDK_TYPE_AD_MARVEL = "Ad Marvel";
    public static String SDK_TYPE_PUB_MATIC = "PubMatic";
    public static String SDK_TYPE_MILLENNIAL = "Millennial";
    public static String SDK_TYPE_INMOBI = "InMobi";
    public static String SDK_TYPE_ADFORM="AdForm";
    public static String SDK_TYPE_SMART_AD_SERVER="SmartAdServer";
    public static String TYPE_MY_CREATIVE = "1";
    public static String TYPE_DEFAULT_CREATIVE = "10";
    public static String DEFAULT_ACCESS_KEY = "MbgyNQA2nFBn9ybhwLBzww";
    public static int  CONNECTION_TIME_OUT = 10000;
    public static int  IS_SERVER_REQUEST_TRUE = 1;
    public static int IS_SERVER_REQUEST_ERROR = 0;
    public static int  IS_SERVER_REQUEST_TIME_OUT = 2;
    public static String  SUBJECT_ACEESS_KEY = "IAB Registration Key";
    public static String  ACEESS_KEY="access_key" ;
    public static String  DEFAULT_GUEST ="";
    public static String  EMAIL_CONTENT_FOR_ACCESS_KEY = "To access IAB API use this key  "+ACEESS_KEY+" .\n\n\nDo not share the key. \n\nRegards, \nTeam @ IAB Tech Lab";
    public static String  DEFAULT_SCRIPT_NAME ="demotest";
    public static int USER_CREATIVE_MANUAL =0;
    public static int USER_CREATIVE_AUTO=1;
    }