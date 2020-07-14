package com.example.notes_of_dolphi.listeners;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes_of_dolphi.LoginActivity;
import com.example.notes_of_dolphi.Validators.LogInValidator;
import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.User;
import com.example.notes_of_dolphi.server.crud.UsersCRUD;
import com.example.notes_of_dolphi.server.synchronize.Synchronise;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class RegistrationListener implements View.OnClickListener {

    private EditText email_registration;
    private EditText phone_registration;
    private EditText username_registration;
    private EditText password_registration;
    private SQLiteDatabase mDatabase;

    public RegistrationListener(EditText password_registration, EditText email_registration, EditText username_registration, EditText phone_registration, SQLiteDatabase mDatabase) {

        this.email_registration = email_registration;
        this.phone_registration = phone_registration;
        this.username_registration = username_registration;
        this.password_registration = password_registration;
        this.mDatabase = mDatabase;
    }

    @Override
    public void onClick(View v) {

        User user = new User();

        user.setPassword(this.password_registration.getText().toString());
        user.setUsername(this.username_registration.getText().toString());
        user.setEmail(this.email_registration.getText().toString());

        Intent intent = null;
        boolean input_data_all_right = LogInValidator.check_please_registration_data(user);

        Synchronise synchronize = new Synchronise();

        boolean connection_with_server = false;

        try {
            connection_with_server = synchronize.check_connection_with_server();
        } catch (IOException e) {
            System.out.println("Error---"+e);
        }


        if(connection_with_server) {
            if (input_data_all_right) {

                //send request to server about create login;

                UsersCRUD users_server = new UsersCRUD();

                try {
                    users_server.create(user);
                } catch (InterruptedException e) {
                    System.out.println("Error---"+e);
                } catch (ExecutionException e) {
                    System.out.println("Error---"+e);
                }

                intent = new Intent(v.getContext(), LoginActivity.class);
                v.getContext().startActivity(intent);
            } else {
                Toast.makeText(v.getContext(), "User doesn't created", Toast.LENGTH_SHORT).show();
            }
        }//end if of checking connection_with_server
        else
        {
            Toast.makeText(v.getRootView().getContext(),"Not possible to make registration of new user without connection with server", Toast.LENGTH_SHORT).show();
        }

    }
}
