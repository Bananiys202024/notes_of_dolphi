package com.example.notes_of_dolphi.listeners.CRUD_operations;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes_of_dolphi.R;
import com.example.notes_of_dolphi.controllers.MainMenuActivity;
import com.example.notes_of_dolphi.crud.DiaryCRUD;
import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.Note;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class AddNoteListener  implements View.OnClickListener {

    EditText note;
    EditText title;
    SQLiteDatabase mDatabase;
    Context context;

    public AddNoteListener(SQLiteDatabase mDatabase, Context context) {
    this.mDatabase = mDatabase;
    this.context = context;
    }

    @Override
    public void onClick(View v) {

        this.note = (EditText) v.getRootView().findViewById(R.id.note);
        this.title = (EditText) v.getRootView().findViewById(R.id.title);

        String note_string = note.getText().toString().trim();
        String title_string = title.getText().toString().trim();

        //make record to database
        DiaryCRUD diary_crud = new DiaryCRUD();
        Note note_model = new Note();
        note_model.setTitle(title_string);
        note_model.setNote(note_string);
        note_model.setOwner(Cashe.getLogged_user());

        boolean result = false;

        try {
            result = diary_crud.create(note_model, mDatabase);
        } catch (Exception e) {
            System.out.println("Error---"+e);
        }

        if(result)
        {
            Toast.makeText(context, "Note was created", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this.context, "Unable to save note information", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(v.getContext(), MainMenuActivity.class);
        v.getRootView().getContext().startActivity(intent);

    }
}


