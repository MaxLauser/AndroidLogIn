package com.example.log_in_register.android_login_register;

// Created By Ayoub EL Khatab

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DashBoard extends AppCompatActivity {

    private UsersDataBase_SQLite UsersDB;
    private User CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        final TextView FullName = (TextView) findViewById(R.id.dash_FullName);
        final TextView UserName = (TextView) findViewById(R.id.dash_UserName);
        final TextView Email = (TextView) findViewById(R.id.dash_Email);
        final TextView PhoneNumber = (TextView) findViewById(R.id.dash_PhoneNumber);
        final TextView Password = (TextView) findViewById(R.id.dash_Password);

        // get incoming data
        Bundle bundle = getIntent().getExtras();
        String LoggedUser_FirstName = bundle.getString(Home.FirstName_key);
        String LoggedUser_LastName = bundle.getString(Home.LastName_key);
        String LoggedUser_UserName = bundle.getString(Home.UserName_key);
        String LoggedUser_Email = bundle.getString(Home.Email_key);
        String LoggedUser_PhoneNumber = bundle.getString(Home.PhoneNumber_key);
        String LoggedUser_Password = bundle.getString(Home.Password_key);


        CurrentUser = new User(LoggedUser_FirstName,
                LoggedUser_LastName,
                LoggedUser_UserName,
                LoggedUser_Email,
                LoggedUser_Password,
                LoggedUser_PhoneNumber);


        FullName.setText(LoggedUser_FirstName + " " + LoggedUser_LastName);
        UserName.setText(LoggedUser_UserName);
        Email.setText(LoggedUser_Email);
        PhoneNumber.setText(LoggedUser_PhoneNumber);
        Password.setText(LoggedUser_Password);

        Button LogOut = (Button) findViewById(R.id.dash_logout);
        Button DeleteAccount = (Button) findViewById(R.id.dash_DeleteAccount);

        // Delete Account
        DeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UsersDB = new UsersDataBase_SQLite(getApplicationContext());
                UsersDB.DeleteUser(CurrentUser.UserName);
                Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_SHORT).show();
                // back to Home
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        // Log out
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                startActivity(intent);

            }
        });
    }
}
