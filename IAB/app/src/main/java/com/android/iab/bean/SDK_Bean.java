package com.android.iab.bean;

import java.io.Serializable;

/**
 * Created by syed on
 */
public class SDK_Bean implements Serializable{

    private String id;
    private String sdkName;
    private String sdkversion;

    private boolean isSelected;



    public String getId() {
        return id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSdkName() {
        return sdkName;
    }

    public void setSdkName(String sdkName) {
        this.sdkName = sdkName;
    }

    public String getSdkversion() {
        return sdkversion;
    }

    public void setSdkversion(String sdkversion) {
        this.sdkversion = sdkversion;
    }
}
