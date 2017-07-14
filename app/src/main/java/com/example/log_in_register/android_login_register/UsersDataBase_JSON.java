package com.example.log_in_register.android_login_register;


// Created By Ayoub EL Khatab

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.EnumMap;

public class UsersDataBase_JSON {



    // this class is not mainly used to store users, users are stored in SQLite
    // but you can store users in this JSON too, if u want.


    // JSON tags
    public String FirstName;
    public String LastName;
    public String UserName;
    public String Email;
    public String PhoneNumber;
    public String Password;
    private JSONArray UsersDetails;


// register new user
    public boolean RegisterUserInJSON(User UserToCreate) {


        UsersDetails = new JSONArray();
        JSONObject user = new JSONObject();
        // success
        try {
            user.put(FirstName,UserToCreate.FirstName);
            user.put(LastName,UserToCreate.LastName);
            user.put(UserName,UserToCreate.UserName);
            user.put(Email,UserToCreate.Email);
            user.put(PhoneNumber,UserToCreate.PhoneNumber);
            user.put(Password,UserToCreate.Password);

            UsersDetails.put(user);
            return true;
        }
        // error
        catch (JSONException exc){
            return false;
        }

    }
// get All Users
    public ArrayList<User> getAllUsersInfo(){

        JSONArray AllUsersDetails = UsersDetails;

        ArrayList<User> AllUsers = new ArrayList<>();
        // get all data successfully
        try {

            for (int i = 0; i < AllUsersDetails.length(); i++) {

                JSONObject Temp_User = UsersDetails.getJSONObject(i);

                String userFirstName = Temp_User.getString(FirstName);
                String userLastName = Temp_User.getString(LastName);
                String userUserName = Temp_User.getString(UserName);
                String userEmail = Temp_User.getString(Email);
                String userPhoneNumber = Temp_User.getString(PhoneNumber);
                String userPassword = Temp_User.getString(Password);

                User TMP_USER = new User(userFirstName,
                        userLastName,
                        userUserName,
                        userEmail,
                        userPassword,
                        userPhoneNumber);
                AllUsers.add(TMP_USER);
            }
            return AllUsers;
        }
        // error
        catch (JSONException exc){
            return null;
        }
    }

}
