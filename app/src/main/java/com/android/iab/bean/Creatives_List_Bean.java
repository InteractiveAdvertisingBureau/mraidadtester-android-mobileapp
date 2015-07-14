package com.android.iab.bean;

import java.io.Serializable;

/**
 * Created by syed on
 */
public class Creatives_List_Bean  implements Serializable{

    private String id;
    private String creativeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreativeName() {
        return creativeName;
    }

    public void setCreativeName(String creativeName) {
        this.creativeName = creativeName;
    }
}
