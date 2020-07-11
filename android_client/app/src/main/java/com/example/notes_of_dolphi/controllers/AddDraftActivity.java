package com.example.notes_of_dolphi.controllers;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.notes_of_dolphi.listeners.CRUD_operations.AddDraftListener;
import com.example.notes_of_dolphi.listeners.CRUD_operations.AddNoteListener;
import com.example.notes_of_dolphi.model.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.notes_of_dolphi.R;

public class AddDraftActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_draft);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLiteDatabase mDatabase = openOrCreateDatabase(Constants.getDatabaseName(), MODE_PRIVATE, null);

        findViewById(R.id.create_draft_button).setOnClickListener((View.OnClickListener) new AddDraftListener(mDatabase, this));

    }

}
