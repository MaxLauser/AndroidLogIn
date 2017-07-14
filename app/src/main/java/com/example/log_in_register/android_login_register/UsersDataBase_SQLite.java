package com.example.log_in_register.android_login_register;


// Created By Ayoub EL Khatab

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


class UsersDataBase_SQLite extends SQLiteOpenHelper {

// db info
    private final static String DATABASE_NAME = "UsersDataBase_SQLite.sqlite";
    private final static int DATABASE_VERSON = 1 ;
    private final static String TABLE_NAME = "UsersDetails";
    private final static String COLUMN1_ID = "ID";
    public final static String COLUMN2_REAL_First_NAME = "FirstName";
    public final static String COLUMN3_REAL_LAST_NAME = "LastName";
    public final static String COLUMN4_USER_NAME = "UserName";
    public final static String COLUMN5_EMAIL = "Email";
    public final static String COLUMN6_PASSWORD = "Password";
    public final static String COLUMN7_PHONE_NUMBER = "PhoneNumber";


    public UsersDataBase_SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String init_query = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"("
                +COLUMN1_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "
                +COLUMN2_REAL_First_NAME+" TEXT , "
                +COLUMN3_REAL_LAST_NAME+" TEXT , "
                +COLUMN4_USER_NAME+" TEXT UNIQUE , "
                +COLUMN5_EMAIL+" TEXT , "
                +COLUMN6_PASSWORD+" TEXT , "
                +COLUMN7_PHONE_NUMBER+" TEXT "
                +" ) ";

        db.execSQL(init_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String upgrade_query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(upgrade_query);
    }
// insert USER
    public long RegisterNewUser(User NewUser){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues UserToAdd = new ContentValues();

        UserToAdd.put(COLUMN2_REAL_First_NAME,  NewUser.FirstName);
        UserToAdd.put(COLUMN3_REAL_LAST_NAME,   NewUser.LastName);
        UserToAdd.put(COLUMN4_USER_NAME,    NewUser.UserName);
        UserToAdd.put(COLUMN5_EMAIL,    NewUser.Email);
        UserToAdd.put(COLUMN6_PASSWORD, NewUser.Password);
        UserToAdd.put(COLUMN7_PHONE_NUMBER, NewUser.PhoneNumber);

        return db.insertWithOnConflict(TABLE_NAME, null, UserToAdd, SQLiteDatabase.CONFLICT_REPLACE);
    }
// get all Users from db
    public Cursor getAllUsersFromDB(){

        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
// Delete a user
    public void DeleteUser(String UserNameOfUserToDelete){

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN4_USER_NAME+" = ? ", new String[]{UserNameOfUserToDelete});
    }
// check if a given user is already exist or not
    public boolean CheckUserExistence(User UserToCheck){

        Cursor AllUsers = getAllUsersFromDB();

        // UsersName in db will be stored in UsersName
        ArrayList<String> db_UsersNames = new ArrayList<>();
        // db_Emails in db will be stored in db_Emails
        ArrayList<String> db_Emails = new ArrayList<>();
        // FirstNames in db will be stored in db_FirstNames
        ArrayList<String> db_FirstNames = new ArrayList<>();
        // LastNames in db will be stored in db_LastNames
        ArrayList<String> db_LastNames = new ArrayList<>();
        // PhoneNumbers in db will be stored in db_PhoneNumbers
        ArrayList<String> db_PhoneNumbers = new ArrayList<>();

        // indexes
        int FirstNames_index = AllUsers.getColumnIndex(COLUMN2_REAL_First_NAME);
        int LastNames_index = AllUsers.getColumnIndex(COLUMN3_REAL_LAST_NAME);
        int UserNames_index = AllUsers.getColumnIndex(COLUMN4_USER_NAME);
        int Emails_index = AllUsers.getColumnIndex(COLUMN5_EMAIL);
        int PhoneNumbers_index = AllUsers.getColumnIndex(COLUMN7_PHONE_NUMBER);

        //AllUsers.moveToNext();
        while (AllUsers.moveToNext()) {

            db_UsersNames.add(AllUsers.getString(UserNames_index));
            db_Emails.add(AllUsers.getString(Emails_index));
            db_FirstNames.add(AllUsers.getString(FirstNames_index));
            db_LastNames.add(AllUsers.getString(LastNames_index));
            db_PhoneNumbers.add(AllUsers.getString(PhoneNumbers_index));
        }
    // user exist
        // user does not user
        return db_FirstNames.contains(UserToCheck.FirstName) ||
                db_LastNames.contains(UserToCheck.LastName) ||
                db_UsersNames.contains(UserToCheck.UserName) ||
                db_Emails.contains(UserToCheck.Email) ||
                db_PhoneNumbers.contains(UserToCheck.PhoneNumber);

    }
}
