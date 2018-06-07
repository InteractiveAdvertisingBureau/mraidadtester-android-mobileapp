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

package com.android.iab.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DBHelper extends SQLiteOpenHelper {
    /**Database Version*/
    private static final int DATABASE_VERSION = 2;
    /**Database Path*/
    static String path = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/Android/data/com.android.iab";

    /**Database Name with Path*/
    private static final String DATABASE_NAME = path + "/script.db";

    /**Table Name*/
    public static final String TABLE_SAVE_SCRIPT = "savedScript";

    /**Column which are used in  TABLE_SAVE_SCRIPT*/
    public static final String CREATIVE_ROW_ID_KEY = "p_id";
    public static final String CREATIVE_UUID = "p_udid";
    public static final String CREATIVE_TYPE = "p_BannerType";
    public static final String CREATIVE_NAME = "p_CreativeName";
    public static final String CREATIVE_DESCRIPTION = "p_Des";
    public static final String CREATIVE_SDK_NAME = "p_SdkName";
    public static final String CREATIVE_IS_DELETED = "p_Is_Delete";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase Db) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase Db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

}
