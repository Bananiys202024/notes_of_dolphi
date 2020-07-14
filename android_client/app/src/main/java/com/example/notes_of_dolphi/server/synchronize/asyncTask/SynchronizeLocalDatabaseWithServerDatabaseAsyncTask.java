package com.example.notes_of_dolphi.server.synchronize.asyncTask;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.notes_of_dolphi.crud.UsersCRUD;
import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.POWER_SERVICE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class SynchronizeLocalDatabaseWithServerDatabaseAsyncTask extends AsyncTask<List<User>, Void, List<User>> {

    @Override
    protected List<User> doInBackground(List<User>... lists) {

        List<User> local_list_of_all_users;
        List<User> server_list_of_all_users;

        local_list_of_all_users = lists[0];
        server_list_of_all_users = lists[1];

        List<User> filtered_list = new ArrayList<>();

        //filter lists, check if lists have not synchronized notes;
        if(server_list_of_all_users.size()>0) {
            for (User e : server_list_of_all_users) {
                if (!e.getSynchronized_server()) {
                    filtered_list.add(e);
                }
            }
        }

        return filtered_list;
    }

}
