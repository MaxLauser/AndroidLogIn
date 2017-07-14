package com.example.log_in_register.android_login_register;

// Created By Ayoub EL Khatab

public class User {

    final String FirstName;
    final String LastName;
    final String UserName;
    final String Password;
    final String PhoneNumber;
    final String Email;

    User(String FirstName,
            String LastName,
            String UserName,
            String Email,
            String Password,
            String PhoneNumber
         ){

        this.Email = Email;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Password =Password;
        this.UserName = UserName;
        this.PhoneNumber = PhoneNumber;
    }
}
