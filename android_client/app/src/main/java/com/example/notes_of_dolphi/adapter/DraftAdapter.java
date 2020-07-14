package com.example.notes_of_dolphi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes_of_dolphi.R;
import com.example.notes_of_dolphi.controllers.MainMenuActivity;
import com.example.notes_of_dolphi.crud.DiaryCRUD;
import com.example.notes_of_dolphi.crud.DraftCRUD;
import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.util.Generators;

import java.text.ParseException;
import java.util.List;

public class DraftAdapter  extends ArrayAdapter<Draft> {

    Context mCtx;
    int listLayoutRes;
    List<Draft> draftList;
    SQLiteDatabase mDatabase;

    public DraftAdapter(@NonNull Context mCtx, Integer listLayoutRes, List<Draft> draftList, SQLiteDatabase mDatabase) {
        super(mCtx, listLayoutRes, draftList);
        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.draftList = draftList;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        //getting employee of the specified position
        final Draft draft = draftList.get(position);
        //get by id;


        TextView textViewTitle = view.findViewById(R.id.textViewTitle);
        TextView textViewNote = view.findViewById(R.id.textViewNote);
        TextView textViewJoiningDate = view.findViewById(R.id.textViewJoiningDate);

        //adding data to views
        textViewTitle.setText(draft.getTitle());
        textViewNote.setText(draft.getNote());
        textViewJoiningDate.setText(String.valueOf(draft.getDate_of_note()));

        //we will use these buttons later for update and delete operation
        Button buttonDelete = view.findViewById(R.id.buttonDeleteNote);
        Button buttonEdit = view.findViewById(R.id.buttonEditNote);

        //adding a clicklistener to button
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDraft(draft);
            }
        });

        //the delete operation
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDraft(draft);
            }
        });

        return view;
    }


    private void deleteDraft(final Draft draft)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DraftCRUD server_diary = new DraftCRUD();

                server_diary.delete(draft, mDatabase);
                //reload notes from database

                Bundle mBundle = new Bundle();
                Intent intent = new Intent(mCtx, MainMenuActivity.class);

                mBundle.putString("current_page","draft_tab");
                intent.putExtras(mBundle);
                mCtx.startActivity(intent);
                //..

                Toast.makeText(mCtx, "Note was deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateDraft(final Draft note_class) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        final View view = inflater.inflate(R.layout.activity_update_note, null);
        builder.setView(view);

        final EditText update_title = view.findViewById(R.id.title_update);
        final EditText update_note = view.findViewById(R.id.note_update);

        update_title.setText(note_class.getTitle());
        update_note.setText(String.valueOf(note_class.getNote()));

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.update_note_button).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                final EditText update_title = v.getRootView().findViewById(R.id.title_update);
                final EditText update_note = v.getRootView().findViewById(R.id.note_update);

                String title = update_title.getText().toString().trim();
                String note = update_note.getText().toString().trim();

                if (title.isEmpty()) {
                    update_title.setError("Title can't be blank");
                    update_title.requestFocus();
                    return;
                }

                if (note.isEmpty()) {
                    update_note.setError("Note can't be blank");
                    update_note.requestFocus();
                    return;
                }

                DraftCRUD diary_crud = new DraftCRUD();

                Draft model = new Draft();
                model.setTitle(title);
                model.setNote(note);
                model.setId(note_class.getId());
                model.setDate_of_note(note_class.getDate_of_note());
                model.setOwner(note_class.getOwner());
                model.setDeleted(note_class.getDeleted());
                model.setSynchronized_server(false);

                diary_crud.update(model, mDatabase);

                Toast.makeText(view.getRootView().getContext(), "Note Updated", Toast.LENGTH_SHORT).show();

                try {
                    reloadNotesFromDatabase();
                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println("Error---"+e);
                }
                dialog.dismiss();

                //reload notes from database

                Bundle mBundle = new Bundle();
                Intent intent = new Intent(mCtx, MainMenuActivity.class);

                mBundle.putString("current_page","draft_tab");
                intent.putExtras(mBundle);
                mCtx.startActivity(intent);
                //..

            }
        });

    }

    public void reloadNotesFromDatabase() throws ParseException {
        this.draftList = Generators.generate_list_of_draft(draftList);
        notifyDataSetChanged();
    }

}
