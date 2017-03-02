package com.example.ideafoundation.meetaapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ideafoundation.meetaapp.model.User;

/**
 * Created by ideafoundation on 01/03/17.
 */

public class LoginHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USEREMAIL = "useremail";

    private static final String TABLE_CONTACTS = "userlogin";
    // Database Name
    private static final String DATABASE_NAME = "login";
    public LoginHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_USERNAME + " TEXT NOT NULL,"
                + KEY_USEREMAIL + " VARCHAR NOT NULL);";
        db.execSQL(CREATE_CONTACTS_TABLE);
         }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        onCreate(db);
    }

    public void insertEntry(User usr)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put(KEY_USERNAME, usr.getName());
        newValues.put(KEY_USEREMAIL,usr.getPassword());

        // Insert the row into your table
        db.insert(TABLE_CONTACTS, null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String UserName)
    {
        //String id=String.valueOf(ID);
        SQLiteDatabase db = this.getWritableDatabase();
        String where=KEY_USERNAME+" =?";
        int numberOFEntriesDeleted= db.delete(TABLE_CONTACTS, where, new String[]{UserName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public String getSinlgeEntry(String userName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.query(TABLE_CONTACTS, null, KEY_USERNAME+" =?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex(KEY_USEREMAIL));
        cursor.close();
        return password;
    }
    public void  updateEntry(String userName,String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put(KEY_USERNAME, userName);
        updatedValues.put(KEY_USEREMAIL,email);

        String where=KEY_USERNAME+" =?";
        db.update(TABLE_CONTACTS,updatedValues, where, new String[]{userName});
    }
}
