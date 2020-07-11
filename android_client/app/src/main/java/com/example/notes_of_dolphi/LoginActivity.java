package com.example.notes_of_dolphi;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.notes_of_dolphi.crud.JDBC_initializing;
import com.example.notes_of_dolphi.crud.UsersCRUD;
import com.example.notes_of_dolphi.listeners.LoginListener;
import com.example.notes_of_dolphi.listeners.go_to_next_activity_page_listeners.showPageRegistrationListener;
import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.User;
import com.example.notes_of_dolphi.server.synchronize.Synchronise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    EditText email_login;
    EditText password_login;
    Button log_in;
    Button registration;
    SQLiteDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mDatabase = openOrCreateDatabase(Constants.getDatabaseName(), MODE_PRIVATE, null);

        JDBC_initializing jdbc_initializing = new JDBC_initializing();
        jdbc_initializing.create_tables_if_not_exist(mDatabase);


        /*
       //for offline testing
        User user = new User();
        user.setEmail("Banan");
        user.setSynchronized_server(false);
        user.setUsername("Banan");
        user.setPassword("Banan");
        user.setDeleted(false);
        user.setPermission("PEASANT");

        UsersCRUD users_crud = new UsersCRUD();
        try {
            users_crud.create(user, mDatabase);
        } catch (SQLException e) {
            e.printStackTrace();
        }
       //...

        */

        //opening the database
        email_login = (EditText) findViewById(R.id.email_login);
        password_login = (EditText) findViewById(R.id.password_login);
        log_in = (Button) findViewById(R.id.log_in);
        registration = (Button) findViewById(R.id.registration);

        log_in.setOnClickListener((View.OnClickListener) new LoginListener(email_login, password_login, mDatabase));
        registration.setOnClickListener((View.OnClickListener) new showPageRegistrationListener());

        try {
            Synchronise synchronize = new Synchronise();

            boolean connection_with_server = synchronize.check_connection_with_server();

            System.out.println("Result---"+ connection_with_server);
            if(connection_with_server)
            {
                synchronize.synchronise(mDatabase, false, false, true);
                synchronize.synchronize_deleted_records(mDatabase);

            }
            else
            {
                Toast.makeText(this, "Not possible to make synchronization because no connection with server", Toast.LENGTH_SHORT);
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("---Error---"+e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("---Error---"+e);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("---Error---"+e);
        }
        catch(Exception e)
        {
            System.out.println("Error------"+e);
        }


    }
}