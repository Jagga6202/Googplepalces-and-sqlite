package com.example.ideafoundation.meetaapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ideafoundation.meetaapp.model.DefaultNeed;

import java.util.ArrayList;

/**
 * Created by ideafoundation on 02/03/17.
 */

public class NewUserNeedHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "selcteduserneed";

    private static final String TABLE_CONTACTS = "selectneed";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PLACE = "place";



    public NewUserNeedHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_PLACE + " VARCHAR(1000) NOT NULL);";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    public void allpalces(ArrayList<DefaultNeed> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (DefaultNeed city : list) {
                values.put(KEY_ID, city.getId());
                values.put(KEY_PLACE, city.getPlaces_default());
                db.insert(TABLE_CONTACTS, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    // Getting All Contacts
    public ArrayList<DefaultNeed> getAllContacts() {
        ArrayList<DefaultNeed> contactList = new ArrayList<DefaultNeed>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DefaultNeed contact = new DefaultNeed();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setPlaces_default(cursor.getString(1));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    public void deleteone(ArrayList<DefaultNeed> arrayList){
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i=0;i<arrayList.size();i++)
        {
            db.delete(TABLE_CONTACTS,KEY_ID +" ='"+arrayList.get(i).getId()+"'",null);
        }
    }
}
