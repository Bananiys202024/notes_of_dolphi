package com.example.notes_of_dolphi.controllers;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.notes_of_dolphi.R;
import com.example.notes_of_dolphi.listeners.LoginListener;
import com.example.notes_of_dolphi.listeners.RegistrationListener;
import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    EditText phone_registration;
    EditText email_registration;
    EditText username_registration;
    EditText password_registration;
    Button create_new_user;
    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.create_new_user = (Button) findViewById(R.id.create_new_user);
        this.phone_registration = (EditText) findViewById(R.id.phone_registration);
        this.email_registration = (EditText) findViewById(R.id.email_registration);
        this.username_registration = (EditText) findViewById(R.id.username_registration);
        this.password_registration = (EditText) findViewById(R.id.password_registration);

        this.mDatabase = openOrCreateDatabase(Constants.getDatabaseName(), MODE_PRIVATE, null);

        create_new_user.setOnClickListener((View.OnClickListener) new RegistrationListener(this.password_registration, this.email_registration, this.username_registration, this.phone_registration, mDatabase));
    }

}
