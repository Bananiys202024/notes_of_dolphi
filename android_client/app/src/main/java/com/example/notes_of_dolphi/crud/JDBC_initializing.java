package com.example.notes_of_dolphi.crud;


import android.database.sqlite.SQLiteDatabase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_initializing {

	public void create_tables_if_not_exist(SQLiteDatabase mDatabase) {

		//drop_tables(mDatabase);

		create_table_draft_if_note_exist(mDatabase);
		create_table_notes_if_note_exist(mDatabase);
		create_table_users_if_note_exist(mDatabase);
	}

	private void drop_tables(SQLiteDatabase mDatabase) {

		mDatabase.execSQL("DROP TABLE IF EXISTS notes");
		mDatabase.execSQL("DROP TABLE IF EXISTS draft");
		mDatabase.execSQL("DROP TABLE IF EXISTS users");

	}

	private void create_table_notes_if_note_exist(SQLiteDatabase mDatabase) {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS notes (\n" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                        "    title text NOT NULL,\n" +
                        "    note text NOT NULL,\n" +
                        "    date_of_add text NOT NULL,\n" +
                        "    owner text NOT NULL, \n" +
						"    synchronized_server boolean  DEFAULT 0 NOT NULL, \n" +
						"    deleted boolean DEFAULT 0 NOT NULL, \n" +
						"    synchronized_deleted boolean DEFAULT 0 NOT NULL \n" +
                        ");"
        );
	}

	private void create_table_users_if_note_exist(SQLiteDatabase mDatabase) {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS users (\n" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                        "    username text NOT NULL,\n" +
                        "    password text NOT NULL,\n" +
                        "    email text NOT NULL,\n" +
                        "    permission text NOT NULL,\n" +
						"    synchronized_server boolean DEFAULT 0 NOT NULL, \n" +
						"    deleted boolean DEFAULT 0 NOT NULL, \n" +
						"    synchronized_deleted boolean DEFAULT 0 NOT NULL \n" +
						");"
        );
	}

	private void create_table_draft_if_note_exist(SQLiteDatabase mDatabase) {
		mDatabase.execSQL(
				"CREATE TABLE IF NOT EXISTS draft (\n" +
						"    id INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
						"    title text NOT NULL,\n" +
						"    note text NOT NULL,\n" +
						"    date_of_add text NOT NULL,\n" +
						"    owner text NOT NULL, \n" +
						"    synchronized_server boolean  DEFAULT 0 NOT NULL, \n" +
						"    deleted boolean DEFAULT 0 NOT NULL, \n" +
						"    synchronized_deleted boolean DEFAULT 0 NOT NULL \n" +
						");"
		);
	}

}
