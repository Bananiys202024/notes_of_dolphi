package com.example.notes_of_dolphi.listeners.CRUD_operations;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes_of_dolphi.R;
import com.example.notes_of_dolphi.controllers.DraftTabFragment;
import com.example.notes_of_dolphi.controllers.MainMenuActivity;
import com.example.notes_of_dolphi.crud.DiaryCRUD;
import com.example.notes_of_dolphi.crud.DraftCRUD;
import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.model.Note;

import java.util.Date;

public class AddDraftListener implements View.OnClickListener  {

    EditText draft;
    EditText title;
    SQLiteDatabase mDatabase;
    Context context;

    public AddDraftListener(SQLiteDatabase mDatabase, Context context) {
        this.mDatabase = mDatabase;
        this.context = context;
    }

    @Override
    public void onClick(View v) {

        this.draft = (EditText) v.getRootView().findViewById(R.id.draft);
        this.title = (EditText) v.getRootView().findViewById(R.id.title);

        String draft_string = draft.getText().toString().trim();
        String title_string = title.getText().toString().trim();

        //make record to database
        DraftCRUD draft_crud = new DraftCRUD();
        Draft draft_model = new Draft();
        draft_model.setTitle(title_string);
        draft_model.setNote(draft_string);
        draft_model.setOwner(Cashe.getLogged_user());

        boolean result = false;

        try {
            result = draft_crud.create(draft_model, mDatabase);
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

        Bundle mBundle = new Bundle();
        Intent intent = new Intent(v.getRootView().getContext(), MainMenuActivity.class);

        mBundle.putString("current_page","draft_tab");
        intent.putExtras(mBundle);
        v.getRootView().getContext().startActivity(intent);

    }





}
