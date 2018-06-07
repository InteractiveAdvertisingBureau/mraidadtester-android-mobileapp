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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.android.iab.bean.CreativesListBean;
import com.android.iab.utility.GlobalInstance;
import com.android.iab.utility.HelperMethods;

import java.util.ArrayList;

public class DataSource {

    private DBHelper Db_helper;
    private SQLiteDatabase Db;

    /**
     * Create DataSource Constructor
     */
    public DataSource(Context context) {
        Db_helper = new DBHelper(context);
    }

    /**
     * Open Database
     */
    public void open() throws SQLException {
        Db = Db_helper.getWritableDatabase();
    }

    /**
     * Close DataBase
     */
    public void close() {
        Db_helper.close();
    }

    /**
     * Adding Creative Into Database
     */
    public long insertCreativeIntoDb(String CREATIVE_ID, String CREATIVE_TYPE, String CREATIVE_NAME, String CREATIVE_DESCRIPTION, String CREATIVE_SDK_NAME, String CREATIVE_IS_DELETED) {
        long rowId=-1;

        try {
            ContentValues cv = new ContentValues(7);
            cv.put(DBHelper.CREATIVE_ROW_ID_KEY, CREATIVE_ID);
            cv.put(DBHelper.CREATIVE_UUID, "ANDROID");
            cv.put(DBHelper.CREATIVE_TYPE, CREATIVE_TYPE);
            cv.put(DBHelper.CREATIVE_NAME, CREATIVE_NAME);
            cv.put(DBHelper.CREATIVE_DESCRIPTION, HelperMethods.encode(CREATIVE_DESCRIPTION));
            cv.put(DBHelper.CREATIVE_SDK_NAME, CREATIVE_SDK_NAME);
            cv.put(DBHelper.CREATIVE_IS_DELETED, CREATIVE_IS_DELETED);
            rowId = Db.insert(Db_helper.TABLE_SAVE_SCRIPT, null, cv);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return rowId;
        }

    }

    /**
     * Getting Creative List from Database
     */
    public ArrayList<CreativesListBean> getCreativeListFromDb() {
        // TODO Auto-generated method stub
        ArrayList<CreativesListBean> creativeList = new ArrayList<CreativesListBean>();
        try {
            String sql = "select * from  " + DBHelper.TABLE_SAVE_SCRIPT + ";";
            open();
            Cursor cursor = Db.rawQuery(sql, null);
            if (cursor.moveToFirst())
                do {
                    String id = cursor.getString(cursor.getColumnIndex(DBHelper.CREATIVE_ROW_ID_KEY));
                    String creativeName = cursor.getString(cursor.getColumnIndex(DBHelper.CREATIVE_NAME));
                    String addTag = cursor.getString(cursor.getColumnIndex(DBHelper.CREATIVE_DESCRIPTION));
                    String sdkId = cursor.getString(cursor.getColumnIndex(DBHelper.CREATIVE_SDK_NAME));
                    String sdkName = cursor.getString(cursor.getColumnIndex(DBHelper.CREATIVE_SDK_NAME));
                    String sdkversion = cursor.getString(cursor.getColumnIndex(DBHelper.CREATIVE_SDK_NAME));
                    String addTypeIdId = cursor.getString(cursor.getColumnIndex(DBHelper.CREATIVE_TYPE));
                    String isDeleted = cursor.getString(cursor.getColumnIndex(DBHelper.CREATIVE_IS_DELETED));

                    CreativesListBean creatives_list_bean = new CreativesListBean();
                    creatives_list_bean.setId(id);
                    creatives_list_bean.setCreativeName(creativeName);
                    creatives_list_bean.setSdkName(sdkName);
                    creatives_list_bean.setAddTag(HelperMethods.decode(addTag));
                    creatives_list_bean.setAddType(addTypeIdId);
                    creatives_list_bean.setSdkId(sdkId);
                    creatives_list_bean.setSdkversion(sdkversion);
                    creatives_list_bean.setIsDeleted(isDeleted);
                    creativeList.add(creatives_list_bean);
                } while (cursor.moveToNext());
            cursor.close();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return creativeList;
        }

    }

    /**
     * Checking whether Creative is Already Added into Database or Not.
     */
    public boolean isCreativeAddedIntoDb(String creativeName) {
        // TODO Auto-generated method stub
        boolean isAdded = false;
        try {

           // Log.e("Creative Name", creativeName);
            String sql = "select count(*) from  " + DBHelper.TABLE_SAVE_SCRIPT + " where " + DBHelper.CREATIVE_NAME + "='" + creativeName + "';";
            Cursor cursor = Db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {

                int coulumCount = cursor.getInt(0);
                if (coulumCount > 0)
                    isAdded = true;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return isAdded;
        }
    }

    /**
     * Checking whether Creative is Already Added into Database or Not.
     */
    public int isCreativeAlreadySavedIntoDb(String creativeName) {
        // TODO Auto-generated method stub
        int isAdded = 0;
        try {

         //   Log.e("Creative Name", creativeName);
            String sql = "select * from  " + DBHelper.TABLE_SAVE_SCRIPT + " where " + DBHelper.CREATIVE_DESCRIPTION + "='" + HelperMethods.encode(creativeName) + "';";
            Cursor cursor = Db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                String id = cursor.getString(cursor.getColumnIndex(DBHelper.CREATIVE_ROW_ID_KEY));
                    isAdded = Integer.parseInt(id);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return isAdded;
        }
    }

    /**
     * Remove Creative Info from Database after Edit Mode
     */
    public long removeCreativeFromDb(String creativeName) {
        long rowId = -1;
        try {
            open();
            rowId = Db.delete(DBHelper.TABLE_SAVE_SCRIPT, DBHelper.CREATIVE_ROW_ID_KEY + "='" + creativeName + "'", null);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return rowId;
        }
    }
    /**
     * Delete All Data from DataBase
     */
    public void deleteAllPreviousData(String creativeType) {
        try {
            String sql = " DELETE FROM " + DBHelper.TABLE_SAVE_SCRIPT+" where "+DBHelper.CREATIVE_IS_DELETED+"="+creativeType;
            Db.execSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete All Data from DataBase
     */
    public void deleteAllPreviousData() {
        try {
            String sql = " DELETE FROM " + DBHelper.TABLE_SAVE_SCRIPT;
            Db.execSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Get User Creative Name automatically in the case of Millennial
     */
    public String getTempScriptName() {
        String scriptName="";
        {
            // TODO Auto-generated method stub

            try {
             //   String sql = "select * from  savedScript where p_CreativeName like "+"'"+ GlobalInstance.DEFAULT_SCRIPT_NAME +"%' order by  p_CreativeName desc limit 1";
           String sql="select max(cast(substr(p_CreativeName,9) as integer)) from  savedScript where p_CreativeName like "+"'"+ GlobalInstance.DEFAULT_SCRIPT_NAME +"%'  and cast(substr(p_CreativeName,9) as integer) > 0";

           // Log.e("sql",sql);
                Cursor cursor = Db.rawQuery(sql, null);
                if (cursor.moveToFirst())
                    do {
                       int count= cursor.getInt(0)+1;
                        scriptName=GlobalInstance.DEFAULT_SCRIPT_NAME+count;
                       // Log.e("scriptName",GlobalInstance.DEFAULT_SCRIPT_NAME+count);

                    } while (cursor.moveToNext());
                cursor.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                return scriptName;
            }

        }

    }

}