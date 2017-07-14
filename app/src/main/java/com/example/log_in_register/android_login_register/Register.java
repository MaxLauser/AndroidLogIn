package com.example.log_in_register.android_login_register;

// Created By Ayoub EL Khatab

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private UsersDataBase_SQLite UsersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText FirstName = (EditText) findViewById(R.id.reg_FirstName);
        final EditText LastName = (EditText) findViewById(R.id.reg_LastName);
        final EditText UserName = (EditText) findViewById(R.id.reg_UserName);
        final EditText Email = (EditText) findViewById(R.id.reg_Email);
        final EditText Password = (EditText) findViewById(R.id.reg_Password);
        final EditText ConfirmPassword = (EditText) findViewById(R.id.reg_ConfirmPassword);
        final EditText PhoneNumber = (EditText) findViewById(R.id.reg_PhoneNumber);

        Button Register = (Button) findViewById(R.id.reg_Register);
        Button LogInExistedAcc = (Button) findViewById(R.id.reg_LogIn);

// log in an existed account
        LogInExistedAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                startActivity(intent);
            }
        });
// register new account
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User new_User = new User(
                        FirstName.getText().toString(),
                        LastName.getText().toString(),
                        UserName.getText().toString(),
                        Email.getText().toString(),
                        Password.getText().toString(),
                        PhoneNumber.getText().toString()
                );
    // Password is confirmed correctly
                if (Password.getText().toString().equals(ConfirmPassword.getText().toString())) {
                    UsersDB = new UsersDataBase_SQLite(getApplicationContext());

                    boolean UserExistence = UsersDB.CheckUserExistence(new_User);
                    if (!UserExistence) {
                // user does not exist, so create it
                        long UserIsCreated = UsersDB.RegisterNewUser(new_User);

                        /*      optional  ------ create user in JSON
                        UsersDataBase_JSON UsersJSON = new UsersDataBase_JSON();
                        boolean UserIsCreated_InJSON = UsersJSON.RegisterUserInJSON(new_User);
                        // if UserIsCreated_InJSON == true ==> user created successfully
                        */

                        // error in db while creating this user
                        if (UserIsCreated == -1) {
                            Toast.makeText(getApplicationContext(),
                                    "Error Creating User,(Related to db)",
                                    Toast.LENGTH_LONG).show();
                        }
                        // user created successefully
                        else {
                            Toast.makeText(getApplicationContext(), "User Created, Please log in",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);
                        }

                    }
                // user already exist
                    else {
                        FirstName.setText("");
                        LastName.setText("");
                        UserName.setText("");
                        PhoneNumber.setText("");
                        Email.setText("");
                        Password.setText("");
                        ConfirmPassword.setText("");
                        Toast.makeText(getApplicationContext(), "User Already exist in db, please register new one.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
    // Password is not Confirmed
                else{
                    Password.setText("");
                    ConfirmPassword.setText("");
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
