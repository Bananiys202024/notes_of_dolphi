package com.example.notes_of_dolphi.controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.notes_of_dolphi.R;
import com.example.notes_of_dolphi.adapter.DraftAdapter;
import com.example.notes_of_dolphi.adapter.NoteAdapter;
import com.example.notes_of_dolphi.crud.DiaryCRUD;
import com.example.notes_of_dolphi.crud.DraftCRUD;
import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.util.Generators;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class DraftTabFragment extends Fragment {

    ListView listViewNotesForDraftTab;
    DraftAdapter draft_adapter;
    SQLiteDatabase mDatabase;
    FloatingActionButton add_icon_for_draft_tab;
    List<Draft> list_view_notes_for_draft_tab = new ArrayList();

    public DraftTabFragment(SQLiteDatabase mDatabase)
    {
        this.mDatabase = mDatabase;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_draft, container,false);

        root.findViewById(R.id.add_icon_for_draft_tab).setOnClickListener((View.OnClickListener) new showPageAddDraft());
        listViewNotesForDraftTab = root.findViewById(R.id.listViewNotesForDraftTab);


        DraftCRUD draft_crud = new DraftCRUD();
        List<Draft> list = null;
        list = draft_crud.read(mDatabase);
/*

        Sorting sorting = new Sorting();
        list = sorting.sort_list_by_date(list);
*/
        System.out.println("fewfewfewfewf123-----"+list);
        if(list != null) {
            Collections.reverse(list);
            try {
                this.list_view_notes_for_draft_tab = Generators.generate_list_of_draft(list);
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("Check---"+e);
            }

            //creating the adapter object
            this.draft_adapter = new DraftAdapter(root.getContext(), R.layout.example_of_layout_for_note_of_diary , list_view_notes_for_draft_tab, mDatabase);

            System.out.println("adapter1----"+this.draft_adapter);
            //adding the adapter to listview
            listViewNotesForDraftTab.setAdapter(draft_adapter);
        }

        return root;
    }
}
