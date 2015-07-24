package com.android.iab.bean;

import java.io.Serializable;

/**
 * Creatives_List  Bean  for storing Creatives List in drawer
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
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
