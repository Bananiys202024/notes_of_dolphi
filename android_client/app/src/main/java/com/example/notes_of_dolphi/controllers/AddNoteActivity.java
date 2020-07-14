package com.example.notes_of_dolphi.controllers;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.notes_of_dolphi.listeners.CRUD_operations.AddNoteListener;
import com.example.notes_of_dolphi.listeners.go_to_next_activity_page_listeners.showPageAddNoteListener;
import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.notes_of_dolphi.R;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class AddNoteActivity extends AppCompatActivity {

    Button create_note_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLiteDatabase mDatabase = openOrCreateDatabase(Constants.getDatabaseName(), MODE_PRIVATE, null);

        findViewById(R.id.create_note_button).setOnClickListener((View.OnClickListener) new AddNoteListener(mDatabase, this));
        System.out.println("End add_note activity");

    }
}
