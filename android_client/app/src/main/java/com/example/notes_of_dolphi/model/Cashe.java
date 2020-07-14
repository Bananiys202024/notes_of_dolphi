package com.example.notes_of_dolphi.model;

import android.database.sqlite.SQLiteDatabase;

public class Cashe {

 	private static String logged_user;
 	private static boolean connection_with_server_for_this_session;

	public static String getLogged_user() {
		return logged_user;
	}

	public static void setLogged_user(String logged_user) {
		Cashe.logged_user = logged_user;
	}

	public static boolean isConnection_with_server_for_this_session() {
		return connection_with_server_for_this_session;
	}

	public static void setConnection_with_server_for_this_session(boolean connection_with_server_for_this_session) {
		Cashe.connection_with_server_for_this_session = connection_with_server_for_this_session;
	}

}
