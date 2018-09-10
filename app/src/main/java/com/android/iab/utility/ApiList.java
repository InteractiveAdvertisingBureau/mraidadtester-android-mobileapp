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

public class ApiList {
    public static final String BASE_URL = "http://50.60.132.101";
    // public static final String BASE_URL ="http://50.60.132.100:8080";
    public static final String IAB_BASE_URL = "https://api.iabtechlab.com";
    /**
     * Url to open Term & Condition Page using WebView
     */
    public static final String URL_TERM_CONDITION = "http://www.iab.net/legal_disclaimer";

    /**
     * Declaration of API Name which are used in Application
     *
     * @param API_URL_GET_CREATIVE      Used for display Creative List
     * @param API_URL_SIGN_UP           Used to  register User
     */
    public static final String API_URL_GET_CREATIVE = "/IABAPI/iab/api/getcreative/";
    public static final String API_URL_GET_ALL_CREATIVE = "/IABAPI/iab/api/getcreativemobile/";
    public static final String API_URL_SIGN_UP = "/IABAPI/iab/api/mobile";
    public static final String API_URL_RIGISTRATION = "/api/mraid/registerform";
    public static final String API_URL_SAVE_CREATIVE = "/IABAPI/iab/api/savecreative?";
    public static final String API_URL_DELETE_CREATIVE = "/IABAPI/iab/api/deletecreative/";
}
