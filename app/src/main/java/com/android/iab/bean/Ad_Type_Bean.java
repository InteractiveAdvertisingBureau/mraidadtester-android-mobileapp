package com.android.iab.bean;

import java.io.Serializable;

/**
 * Ad_Type Bean  for storing ad type data i.e. ad type
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
 */
public class Ad_Type_Bean implements Serializable{

    private String id;
    private String adtype;

    private boolean isSelected;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdtype() {
        return adtype;
    }

    public void setAdtype(String adtype) {
        this.adtype = adtype;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
