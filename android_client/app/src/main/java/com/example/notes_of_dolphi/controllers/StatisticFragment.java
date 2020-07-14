package com.example.notes_of_dolphi.controllers;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes_of_dolphi.R;
import com.example.notes_of_dolphi.adapter.DraftAdapter;
import com.example.notes_of_dolphi.crud.DiaryCRUD;
import com.example.notes_of_dolphi.crud.DraftCRUD;
import com.example.notes_of_dolphi.crud.UsersCRUD;
import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.util.Generators;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

public class StatisticFragment  extends Fragment {

    TextView local_user;
    TextView local_draft;
    TextView local_note;
    TextView remote_user;
    TextView remote_draft;
    TextView remote_note;
    SQLiteDatabase mDatabase;


    public StatisticFragment(SQLiteDatabase mDatabase) {

        this.mDatabase = mDatabase;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_statics, container,false);

        this.local_draft = root.findViewById(R.id.local_draft);
        this.local_user = root.findViewById(R.id.local_user);
        this.local_note = root.findViewById(R.id.local_note);

        this.remote_draft = root.findViewById(R.id.remote_draft);
        this.remote_user = root.findViewById(R.id.remote_user);
        this.remote_note = root.findViewById(R.id.remote_note);

        UsersCRUD user_crud = new UsersCRUD();
        DraftCRUD draft_crud = new DraftCRUD();
        DiaryCRUD diary_crud = new DiaryCRUD();
        com.example.notes_of_dolphi.server.crud.DiaryCRUD server_note_crud = new com.example.notes_of_dolphi.server.crud.DiaryCRUD();
        com.example.notes_of_dolphi.server.crud.UsersCRUD server_user_crud = new com.example.notes_of_dolphi.server.crud.UsersCRUD();
        com.example.notes_of_dolphi.server.crud.DraftCRUD server_draft_crud = new com.example.notes_of_dolphi.server.crud.DraftCRUD();

        int number_local_note = diary_crud.count_records(this.mDatabase);

        int number_local_draft = draft_crud.count_records(this.mDatabase);

        int number_local_user = user_crud.counte_records(this.mDatabase);

        int number_remote_note = 0;
        int number_remote_user = 0;
        int number_remote_draft = 0;

        try {
            number_remote_user = server_user_crud.count_records();
            number_remote_draft = server_draft_crud.count_records();
            number_remote_note = server_note_crud.count_records();
        }
        catch(Exception e )
        {
            System.out.println("--Error---"+e);
        }

        this.local_user.setText("Total records in local table user "+ number_local_user);
        this.local_draft.setText("Total records in local table draft "+ number_local_draft);
        this.local_note.setText("Total records in local table note " + number_local_note);
        this.remote_user.setText("Total records in remote table user "+ number_remote_user);
        this.remote_draft.setText("Total records in remote table draft "+ number_remote_draft);
        this.remote_note.setText("Total records in remote table note " + number_remote_note);

        return root;
    }

}
