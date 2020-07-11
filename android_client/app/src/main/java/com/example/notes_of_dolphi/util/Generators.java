package com.example.notes_of_dolphi.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.model.Note;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Generators {

    public static List<Note> generate_list_of_notes(List<Note> notesList) throws ParseException {

        List<Note> generated_list = new ArrayList<Note>();

        if(!(notesList == null)) {
            for (Note n : notesList) {

                if(!n.getDeleted()) {
                    String format_date = FormatDate.get_in_proper_format(n.getDate_of_note());
                    Note note = new Note();
                    note.setSynchronized_server(n.getSynchronized_server());
                    note.setDeleted(n.getDeleted());
                    note.setOwner(n.getOwner());
                    note.setDate_of_note(format_date);
                    note.setNote(n.getNote());
                    note.setTitle(n.getTitle());
                    note.setId(n.getId());

                    generated_list.add(note);
                }


            }
        }//if
/*
        Collections.reverse(generated_list);
*/

        return generated_list;
    }

    public static List<Draft> generate_list_of_draft(List<Draft> list) throws ParseException {


        List<Draft> generated_list = new ArrayList<Draft>();

        if(!(list == null)) {
            for (Draft n : list) {

                if(!n.getDeleted()) {
                    String format_date = FormatDate.get_in_proper_format(n.getDate_of_note());
                    Draft draft = new Draft();
                    draft.setSynchronized_server(n.getSynchronized_server());
                    draft.setDeleted(n.getDeleted());
                    draft.setOwner(n.getOwner());
                    draft.setDate_of_note(format_date);
                    draft.setNote(n.getNote());
                    draft.setTitle(n.getTitle());
                    draft.setId(n.getId());

                    generated_list.add(draft);
                }

             }
        }//if
/*
        Collections.reverse(generated_list);
*/

        return generated_list;

    }
}


