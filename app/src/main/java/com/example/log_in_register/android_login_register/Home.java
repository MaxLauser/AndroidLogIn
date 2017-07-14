package com.example.log_in_register.android_login_register;

// Created By Ayoub EL Khatab

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    // keys for user details thats gonna be sent to DashBoard
    public static final String FirstName_key = "FirstName";
    public static final String LastName_key = "LastName";
    public static final String UserName_key = "UserName";
    public static final String Email_key = "Email";
    public static final String PhoneNumber_key = "PhoneNumber";
    public static final String Password_key = "Password";

    // user details to be sent to DashBoard activity
    private String FirstNameToDashBoard;
    private String LastNameToDashBoard;
    private String UserNameToDashBoard;
    private String EmailToDashBoard;
    private String PhoneNumberToDashBoard;
    private String PasswordToDashBoard;

    private boolean PasswordState;
    private boolean UserExistance;
    private int WrongPasswordCounter;


    /*
    User exist + enter the correct details ={
            UserExistance = true;
            WrongPassword = false;
            }
    User exist + enter the  wrong password ={
            UserExistence =true;
            WrongPassword = true;
            }
    User does not exist ={
            UserExistance = false;
            }

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize password tries
        WrongPasswordCounter = 0;

        final Button login_Button = (Button) findViewById(R.id.LogIn);
        Button register_Button = (Button) findViewById(R.id.Register);

        final EditText UserName_Email = (EditText) findViewById(R.id.UserName_Email);
        final EditText Password = (EditText) findViewById(R.id.Password);

// log in event
        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Pass = Password.getText().toString();
                String Mail = UserName_Email.getText().toString();
                // clear Email field
                Password.setText("");

                LogInAuthentication(Mail, Pass);
                // user exists
                if (UserExistance) {
                    // password is correct
                    if (PasswordState) {
                        // clear Password field
                        UserName_Email.setText("");

                        Toast.makeText(getApplicationContext(),
                                "Logging in ...", Toast.LENGTH_SHORT).show();
                        Intent DashBoard = new Intent(getApplicationContext(), DashBoard.class);
                        // sending user FirstName + LastName to DashBoard activity
                        Bundle LoggedInUserDetails = new Bundle();

                        LoggedInUserDetails.putString(FirstName_key, FirstNameToDashBoard);
                        LoggedInUserDetails.putString(LastName_key, LastNameToDashBoard);
                        LoggedInUserDetails.putString(UserName_key, UserNameToDashBoard);
                        LoggedInUserDetails.putString(Email_key, EmailToDashBoard);
                        LoggedInUserDetails.putString(PhoneNumber_key, PhoneNumberToDashBoard);
                        LoggedInUserDetails.putString(Password_key, PasswordToDashBoard);
                        DashBoard.putExtras(LoggedInUserDetails);

                        startActivity(DashBoard);
                    }
                    // password is wrong
                    else {
                        // user entered wrong password 3 times
                        if (WrongPasswordCounter >= 0 & WrongPasswordCounter <= 3)
                            Toast.makeText(getApplicationContext(),
                                    "Wrong Password. Try again", Toast.LENGTH_SHORT).show();
                            // user entered wrong password 6 times
                        else if (WrongPasswordCounter <= 6) {
                            // display hint and number of tries left before blocking this account
                            switch (WrongPasswordCounter) {
                                case 4:
                                    Toast.makeText(getApplicationContext(),
                                            "Wrong Password. 3 tries left", Toast.LENGTH_SHORT).show();
                                    break;
                                case 5:
                                    Toast.makeText(getApplicationContext(),
                                            "Wrong Password. 2 tries left", Toast.LENGTH_SHORT).show();
                                    break;
                                case 6:
                                    Toast.makeText(getApplicationContext(),
                                            "Wrong Password. Last try!", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                        // user passed the allowed tries to log in ==> account will be blocked
                        else if (WrongPasswordCounter > 6) {
                            Toast.makeText(getApplicationContext(),
                                    "This Account is blocked!",
                                    Toast.LENGTH_LONG).show();
                            // update AccountBlockingState from 0 To 1
                        }
                    }
                }
                //user does not exist in db
                else {
                    Toast.makeText(getApplicationContext(),
                            "This user dos not exist",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
// register event
        register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }

    // Authenticate a user
    private void LogInAuthentication(String UserNameOrEmail, String PasswordToVerify) {

        UsersDataBase_SQLite uDB = new UsersDataBase_SQLite(getApplicationContext());

        Cursor AllUsers = uDB.getAllUsersFromDB();

        /*  optional ---- you can get users from JSON if u choose to save them in it

        UsersDataBase_JSON UsersJSON = new UsersDataBase_JSON();
        ArrayList<User> AllUsersInfo = UsersJSON.getAllUsersInfo();

         */

        // UsersName in db will be stored in UsersName
        ArrayList<String> db_UsersNames = new ArrayList<>();
        // Passwords in db will be stored in db_Passwords
        ArrayList<String> db_Passwords = new ArrayList<>();
        // Passwords in db will be stored in db_Emails
        ArrayList<String> db_Emails = new ArrayList<>();
        // FirstNames in db will be stored in db_FirstNames
        ArrayList<String> db_FirstNames = new ArrayList<>();
        // LastNames in db will be stored in db_LastNames
        ArrayList<String> db_LastNames = new ArrayList<>();
        // PhoneNumbers in db will be stored in db_PhoneNumbers
        ArrayList<String> db_PhoneNumbers = new ArrayList<>();

        // indexes
        int FirstNames_index = AllUsers.getColumnIndex(UsersDataBase_SQLite.COLUMN2_REAL_First_NAME);
        int LastNames_index = AllUsers.getColumnIndex(UsersDataBase_SQLite.COLUMN3_REAL_LAST_NAME);
        int UserNames_index = AllUsers.getColumnIndex(UsersDataBase_SQLite.COLUMN4_USER_NAME);
        int Emails_index = AllUsers.getColumnIndex(UsersDataBase_SQLite.COLUMN5_EMAIL);
        int Passwords_index = AllUsers.getColumnIndex(UsersDataBase_SQLite.COLUMN6_PASSWORD);
        int PhoneNumbers_index = AllUsers.getColumnIndex(UsersDataBase_SQLite.COLUMN7_PHONE_NUMBER);

        //AllUsers.moveToNext();
        while (AllUsers.moveToNext()) {

            db_UsersNames.add(AllUsers.getString(UserNames_index));
            db_Passwords.add(AllUsers.getString(Passwords_index));
            db_Emails.add(AllUsers.getString(Emails_index));
            db_FirstNames.add(AllUsers.getString(FirstNames_index));
            db_LastNames.add(AllUsers.getString(LastNames_index));
            db_PhoneNumbers.add(AllUsers.getString(PhoneNumbers_index));
        }

        // User exists in db
        if (db_UsersNames.contains(UserNameOrEmail)) {
            UserExistance = true;

            // Correct Password
            if (db_Passwords.contains(PasswordToVerify)) {

                PasswordState = true;

                FirstNameToDashBoard = db_FirstNames.get(db_UsersNames.indexOf(UserNameOrEmail));
                LastNameToDashBoard = db_LastNames.get(db_UsersNames.indexOf(UserNameOrEmail));
                UserNameToDashBoard = db_UsersNames.get(db_UsersNames.indexOf(UserNameOrEmail));
                EmailToDashBoard = db_Emails.get(db_UsersNames.indexOf(UserNameOrEmail));
                PhoneNumberToDashBoard = db_PhoneNumbers.get(db_UsersNames.indexOf(UserNameOrEmail));
                PasswordToDashBoard = db_Passwords.get(db_UsersNames.indexOf(UserNameOrEmail));
            }
            // Wrong Password
            else {

                WrongPasswordCounter++;
                PasswordState = false;
            }

        }
        // User doesn't exist in db
        else {
            UserExistance = false;
        }
    }
}
