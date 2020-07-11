package com.example.notes_of_dolphi.Validators;

import android.widget.EditText;

import com.example.notes_of_dolphi.model.User;

public class LogInValidator {

    public static boolean check_please_log_in_data(EditText email_login, EditText password_login)
    {
        String email = email_login.getText().toString().trim();
        String password = password_login.getText().toString().trim();

        if(email.equals("Banan") && password.equals("Banan"))
        return true;

        return false;
    }

    public static boolean check_please_registration_data(User user) {
        //there is check;
        return true;
    }
}
