package com.example.notes_of_dolphi.listeners;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes_of_dolphi.Validators.LogInValidator;
import com.example.notes_of_dolphi.controllers.MainMenuActivity;
import com.example.notes_of_dolphi.crud.UsersCRUD;
import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.User;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;


public class LoginListener implements View.OnClickListener {

    EditText email_login;
    EditText password_login;
    Button log_in;
    SQLiteDatabase mDatabase;

    public LoginListener(EditText email_login, EditText password_login, SQLiteDatabase mDatabase)
    {
        this.email_login = email_login;
        this.password_login = password_login;
        this.mDatabase = mDatabase;
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;
        boolean input_data_all_right = LogInValidator.check_please_log_in_data(email_login, password_login);
        boolean does_login_exist_in_database = true;
        boolean does_password_right = true;
        boolean result = input_data_all_right && does_login_exist_in_database && does_password_right;

        User user = new User();
        user.setEmail(this.email_login.getText().toString());
        user.setPassword(this.password_login.getText().toString());

        UsersCRUD users_server = new UsersCRUD();
        boolean does_it_okay_for_user = false;
        try {
            does_it_okay_for_user = users_server.login(user, mDatabase);
        } catch (SQLException e) {
            System.out.println("Error---"+e);
        }

        if(does_it_okay_for_user)
        {
            String logged_user = this.email_login.getText().toString();
            Cashe.setLogged_user(logged_user);
            intent = new Intent(v.getContext(), MainMenuActivity.class);
            v.getContext().startActivity(intent);
        }
        else
        {
            Toast.makeText(v.getContext(), "That user doesn't exist, maaaan", Toast.LENGTH_SHORT).show();
        }

    }

}
