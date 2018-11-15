package com.iabtechlab.sdk.admarvel;

/**
 * Created by ayaz on 10/16/2015.
 */
public class AdmarvelAlert {

    public String getErrorByCode(int i) {
        String message;
        switch (i) {
            case 304:
                message = "Next ad is requested while previous one is still processing. There should be at least two seconds gap in between two requests";
                break;
            case 306:
                message = "The XML response returned by the AdMarvel servers produces a parsing error. This is usually a service side configuration issue.";
                break;
            case 307:
                message = "Mediation ad network for which the ad is requested is missing in client library.";
                break;
            case 303:
                message = "The XML response returned by the AdMarvel servers produces a parsing error. This is usually a service side configuration issue.";
                break;
            case 308:
                message = "Ad unit is either not well formed or taking too much time in loading, resulting in exception.";
                break;
            case 305:
                message = "Ad unit is not well formed. Exception occurs while rendering it in Webview.";
                break;
            case 203:
                message = "Device is identified as a bot. Request is rejected to avoid fraud count.";
                break;
            case 302:
                message = "The device has an unreliable internet connection and internet connectivity is disrupted or lost while requesting an ad.";
                break;
            case 205:
                message = "No matching ad is found for this request. The system displays 205 error when an ad network does not send an ad. Hence, the system cannot serve an ad.";
                break;
            case 204:
                message = " No matching ad is found for this request. The system displays 204 error when the following conditions are met:Targeting at banner and that at linked sites do not match.The weight of the banner linked to the site is zero.The banner is not linked to any site.";
                break;
            case 301:
                message = "The device does not have permission or the device does not have network connectivity.";
                break;
            case 206:
                message = "Either user agent is invalid or missing in request.";
                break;
            case 208:
                message = "Partner ID is missing in request.";
                break;
            case 202:
                message = " Either site ID or partner ID for this request is incorrect.";
                break;
            case 207:
                message = "Site ID is missing in request.";
                break;
            case 201:
                message = " Site ID or partner ID is either not valid or missing.";
                break;
            default:
                message = "";
                break;
        }
        return message;
    }
}
