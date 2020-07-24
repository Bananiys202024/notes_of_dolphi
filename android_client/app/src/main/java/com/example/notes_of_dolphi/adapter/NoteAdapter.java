package com.example.notes_of_dolphi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.util.Generators;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class NoteAdapter extends ArrayAdapter<Note>
{

    Context mCtx;
    int listLayoutRes;
    List<Note> notesList;
    SQLiteDatabase mDatabase;

    public NoteAdapter(@NonNull Context mCtx, Integer listLayoutRes, List<Note> notesList, SQLiteDatabase mDatabase) {
        super(mCtx, listLayoutRes, notesList);
        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.notesList = notesList;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        //getting employee of the specified position
        final Note note = notesList.get(position);
        //get by id;

        System.out.println("Mark---");
        System.out.println(note.getNote());
        System.out.println(note.getSynchronized_server());
        System.out.println(note.getOwner());
        System.out.println("--------Date of mark and date of note--------");
        System.out.println(note.getDate_of_note());
        System.out.println(note.getId());


        TextView textViewTitle = view.findViewById(R.id.textViewTitle);
        TextView textViewNote = view.findViewById(R.id.textViewNote);
        TextView textViewJoiningDate = view.findViewById(R.id.textViewJoiningDate);

        //adding data to views
        textViewTitle.setText(note.getTitle());
        textViewNote.setText(note.getNote());
        textViewJoiningDate.setText(String.valueOf(note.getDate_of_note()));

        //we will use these buttons later for update and delete operation
        Button buttonDelete = view.findViewById(R.id.buttonDeleteNote);
        Button buttonEdit = view.findViewById(R.id.buttonEditNote);

        //delete buttonEdit
        ViewGroup layout = (ViewGroup) buttonEdit.getParent();
        if(null!=layout)
            layout.removeView(buttonEdit);
        //...


        //adding a clicklistener to button
//        buttonEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateNote(note);
//            }
//        });

        //the delete operation
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              deleteNote(note);
            }
        });

        return view;
    }

    private void deleteNote(final Note note)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DiaryCRUD server_diary = new DiaryCRUD();

                server_diary.delete(note.getId(), mDatabase);
                //reload notes from database
                Intent intent = null;
                intent = new Intent(mCtx, MainMenuActivity.class);
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

    private void updateNote(final Note note_class) {

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

                DiaryCRUD diary_crud = new DiaryCRUD();

                Note note_model = new Note();
                note_model.setTitle(title);
                note_model.setNote(note);
                note_model.setId(note_class.getId());
                note_model.setDate_of_note(note_class.getDate_of_note());
                note_model.setOwner(note_class.getOwner());
                note_model.setDeleted(note_class.getDeleted());
                note_model.setSynchronized_server(false);

                diary_crud.update(note_model, mDatabase);

                Toast.makeText(view.getRootView().getContext(), "Note Updated", Toast.LENGTH_SHORT).show();

                try {
                    reloadNotesFromDatabase();
                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println("Error---"+e);
                }
                dialog.dismiss();

                //reload notes from database
                Intent intent = null;
                intent = new Intent(mCtx, MainMenuActivity.class);
                mCtx.startActivity(intent);
                //..

            }
        });

    }

    public void reloadNotesFromDatabase() throws ParseException {
        this.notesList = Generators.generate_list_of_notes(notesList);
        notifyDataSetChanged();
    }

}
