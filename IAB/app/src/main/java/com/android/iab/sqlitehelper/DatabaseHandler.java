package com.android.iab.sqlitehelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseHandler for loading layouts resources
 *
 * This activity is used to manag all the db related act
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    // All Static variables

    Activity mActivity;
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "userLoginCredentilas";

    // Contacts table name
    private static final String TABLE_USER_DATA = "login";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_COMPANY = "companyName";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT" + KEY_COMPANY + " TEXT " +
                ")";

        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        // Drop older table if already existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DATA);

        // Create tables again
        onCreate(db);

    }



    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

      // Adding new user
    public void addUser(User_Details user_Details) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, user_Details.getId());  //user id
        values.put(KEY_NAME, user_Details.getName()); // User_Details Name
        values.put(KEY_EMAIL, user_Details.getEmail()); // User_Details Email
        values.put(KEY_COMPANY, user_Details.getCompany()); // User_Details Company


        // Inserting Row
        db.insert(TABLE_USER_DATA, null, values);
        db.close(); // Closing database connection
    }

    // Getting single user data
    User_Details getDetails(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER_DATA, new String[] { KEY_ID,
                KEY_NAME, KEY_EMAIL, KEY_COMPANY }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        User_Details user_Details = new User_Details(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3));
        // return contact
        return user_Details;
    }


    // Getting All USer Details
    public List<User_Details> getAllUser() {

        List<User_Details> user_DetailsList = new ArrayList<User_Details>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER_DATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User_Details user_Details = new User_Details();
                user_Details.setId(Integer.parseInt(cursor.getString(0)));
                user_Details.setName(cursor.getString(1));
                user_Details.setEmail(cursor.getString(2));
                user_Details.setCompany(cursor.getString(3));
                // Adding contact to list
                user_DetailsList.add(user_Details);
            } while (cursor.moveToNext());
        }

        // return contact list
        return user_DetailsList;
    }

    // Updating user data
    public int updateUser(User_Details user_Details) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user_Details.getName());
        values.put(KEY_EMAIL, user_Details.getEmail());
        values.put(KEY_COMPANY, user_Details.getCompany());


        // updating row
        return db.update(TABLE_USER_DATA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(user_Details.getId()) });
    }

    // Deleting single user
    public void deleteUser(User_Details user_Details) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER_DATA, KEY_ID + " = ?",
                new String[] { String.valueOf(user_Details.getId()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USER_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }






}
