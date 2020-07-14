package com.example.notes_of_dolphi.controllers;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.notes_of_dolphi.R;
import com.example.notes_of_dolphi.adapter.DraftAdapter;
import com.example.notes_of_dolphi.adapter.NoteAdapter;
import com.example.notes_of_dolphi.crud.DiaryCRUD;
import com.example.notes_of_dolphi.crud.DraftCRUD;
import com.example.notes_of_dolphi.listeners.go_to_next_activity_page_listeners.showPageAddNoteListener;
import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.server.synchronize.Synchronise;
import com.example.notes_of_dolphi.util.Generators;
import com.example.notes_of_dolphi.util.Sorting;
import com.example.notes_of_dolphi.util.Statistic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    List<Note> notesList = new ArrayList();
    FloatingActionButton add_icon;
    EditText note;
    EditText title;
    NoteAdapter note_adapter;
    ListView listViewNotes;
    Context context;

    SQLiteDatabase mDatabase_1;
    SQLiteDatabase mDatabase_2;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        Toolbar toolbar_bar = findViewById(R.id.toolbar_bar);
        setSupportActionBar(toolbar_bar);

        findViewById(R.id.add_icon).setOnClickListener((View.OnClickListener) new showPageAddNoteListener());
        listViewNotes = (ListView) findViewById(R.id.listViewNotes);

        Synchronise synchronize = new Synchronise();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, null,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        context = this;

        Intent in = getIntent();
        Bundle content = in.getExtras();

        this.mDatabase_1 = openOrCreateDatabase(Constants.getDatabaseName(), MODE_PRIVATE, null);
        this.mDatabase_2 = openOrCreateDatabase(Constants.getDatabaseName(), MODE_PRIVATE, null);

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new MainMenuFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_message);
        }

        if(content != null) {
            String current_page = content.getString("current_page");

            if (current_page.equals("draft_tab")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DraftTabFragment(mDatabase_2)).commit();
                NavigationView navigation_view_draft = (NavigationView) findViewById(R.id.nav_view);
                navigation_view_draft.getMenu().getItem(1).setChecked(true);
                content.remove("current_page");
            }
        }

        try {

            boolean connection_with_server = synchronize.check_connection_with_server();

            if(connection_with_server)
            {
                synchronize.synchronise(mDatabase_1, true, true, false);
//                synchronize.synchronize_deleted_records(mDatabase_1);

            }

        } catch (ExecutionException e) {
            System.out.println("Error---"+e);
        } catch (InterruptedException e) {
            System.out.println("Error---"+e);
        } catch (SQLException e) {
            System.out.println("Error---"+e);
        } catch (IOException e) {
            System.out.println("Error---"+e);
        }

        //this pard of code to async
        try {
            showNoteForNotesTab();
        } catch (ParseException e) {
            System.out.println("Error---"+e);
        }
        //...



        //We can delete this block
        DiaryCRUD diary_crud = new DiaryCRUD();
        List<Note> list = diary_crud.read_all(mDatabase_2);

        if(list != null)
        for(Note note : list)
        {
            System.out.println(note.getDate_of_note()+"-----dateOfAdd");
            System.out.println(note.getSynchronized_server()+"-----synch");
            System.out.println(note.getDeleted()+"-----deleted");
        }

        //...

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showNoteForNotesTab() throws ParseException {

        DiaryCRUD diary_crud = new DiaryCRUD();
        List<Note> list = null;
        list = diary_crud.read(mDatabase_1);
/*

        Sorting sorting = new Sorting();
        list = sorting.sort_list_by_date(list);
*/
        if(list != null) {
            Collections.reverse(list);

            this.notesList = Generators.generate_list_of_notes(list);

            //creating the adapter object
            note_adapter = new NoteAdapter(this, R.layout.example_of_layout_for_note_of_diary, notesList, mDatabase_2);

            //adding the adapter to listview
            listViewNotes.setAdapter(note_adapter);
        }


    }

    //side menu
    public void onBackPressed()
    {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Synchronise synchronize = new Synchronise();
        boolean connection_with_server = false;

        switch (menuItem.getItemId())
        {
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new MainMenuFragment()).commit();
                break;

            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DraftTabFragment(this.mDatabase_2)).commit();
                break;

           /* case R.id.nav_share:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.nav_synch:

                try {
                    connection_with_server = synchronize.check_connection_with_server();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(connection_with_server)
                {
                    synchronization();
                }
                else
                {
                    Toast.makeText(this, "Not possible make synchronization without connection with server", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.nav_statistic:

                try {
                    connection_with_server = synchronize.check_connection_with_server();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(connection_with_server)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new StatisticFragment(this.mDatabase_2)).commit();

                }
                else
                {
                    Toast.makeText(this, "Not possible get page statistic without connection with server", Toast.LENGTH_SHORT).show();
                }

                break;

        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void synchronization() {

        try {
            Synchronise synchronize = new Synchronise();
            synchronize.synchronise_table_notes(mDatabase_1);
            synchronize.synchronise_table_draft(mDatabase_1);

            Toast.makeText(this,"database synchronized", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e )
        {
            System.out.println("---Error--"+e);
            Toast.makeText(this,"Not possible to synchronize database", Toast.LENGTH_SHORT).show();
        }




    }
}
