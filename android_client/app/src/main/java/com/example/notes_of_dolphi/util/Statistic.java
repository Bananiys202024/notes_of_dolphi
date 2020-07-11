package com.example.notes_of_dolphi.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class Statistic {
    public static void show_conclusion_about_records_in_databases(SQLiteDatabase mDatabase, Context context)
    {


        int local_draft = 0;
        int local_note = 0;
        int local_user = 0;

        int server_draft = 0;
        int server_user = 0;
        int server_note = 0;

        Toast.makeText(context,"amount of records in local table draft" + local_draft, Toast.LENGTH_SHORT);
        Toast.makeText(context,"amount of records in local table draft" + server_draft, Toast.LENGTH_SHORT);
        Toast.makeText(context,"amount of records in local table user" + local_user, Toast.LENGTH_SHORT);
        Toast.makeText(context,"amount of records in local table user" + server_user, Toast.LENGTH_SHORT);
        Toast.makeText(context,"amount of records in local table notes" + local_note, Toast.LENGTH_SHORT);
        Toast.makeText(context,"amount of records in local table notes" + server_note, Toast.LENGTH_SHORT);

    }
}
