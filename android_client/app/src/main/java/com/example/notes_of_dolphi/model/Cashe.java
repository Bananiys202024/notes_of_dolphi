package com.example.notes_of_dolphi.model;

import android.database.sqlite.SQLiteDatabase;

public class Cashe {

 	private static String logged_user;


	public static String getLogged_user() {
		return logged_user;
	}

	public static void setLogged_user(String logged_user) {
		Cashe.logged_user = logged_user;
	}

}
