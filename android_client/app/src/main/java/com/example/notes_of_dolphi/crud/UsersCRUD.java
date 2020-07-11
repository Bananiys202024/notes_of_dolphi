package com.example.notes_of_dolphi.crud;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.User;

public class UsersCRUD {

	private final String create_sql = "INSERT INTO users(email, username, password, permission) VALUES(?, ?, ?, ?)";
	private final String read_sql = "SELECT id, email, username , password, permission FROM users";
    private final String read_all_notes_sql = "SELECT id, email, username , password, permission, synchronized_server, deleted FROM users";
    private final String read_by_email = "SELECT id, email, username, password, permission, synchronized_server, deleted FROM users WHERE email = ?";

    public List<User> read_not_synchronized_users(SQLiteDatabase mDatabase)
    {
        List<User> result = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery(read_all_notes_sql, null);

        if(cursor.moveToFirst()) {
            do {
                Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;

                if(!synchronized_server) {
                    Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;

                    User user = new User();
                    user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                    user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    user.setPermission(cursor.getString(cursor.getColumnIndex("permission")));
                    user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                    user.setDeleted(deleted);
                    user.setSynchronized_server(synchronized_server);

                    result.add(user);
                }

            } while (cursor.moveToNext());

            cursor.close();

            return result;
        }

        cursor.close();

        return null;

    }

    public List<User> read(SQLiteDatabase mDatabase)
    {
        List<User> all_users_list = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery(read_all_notes_sql, null);

        if(cursor.moveToFirst()) {
            do {
                System.out.println("Form inside cursor did read all notes");

                Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;
                Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;

                if(!deleted) {
                    User user = new User();
                    user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                    user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    user.setPermission(cursor.getString(cursor.getColumnIndex("permission")));
                    user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                    user.setDeleted(deleted);
                    user.setSynchronized_server(synchronized_server);

                    all_users_list.add(user);
                }

            } while (cursor.moveToNext());

            cursor.close();

            System.out.println("Data fo all notes of user------"+ all_users_list);

            return all_users_list;
        }

        System.out.println("Empty users crud fewfewfew/,mp");
        cursor.close();

        return null;
    }


	//read
	public boolean login(User user, SQLiteDatabase mDatabase) throws SQLException {

        System.out.println("login---");
		String email = user.getEmail();
		String password = user.getPassword();

		System.out.println(email+"----"+password+"-----Did");
        Cursor cursor = mDatabase.rawQuery(read_sql, null);


        System.out.println("-----List of all notes------did");
        System.out.println(read(mDatabase));
        System.out.println("-----finish----");

        if(cursor.moveToFirst()) {
            do {

                String email_from_database = cursor.getString(cursor.getColumnIndex("email"));
                String password_from_database = cursor.getString(cursor.getColumnIndex("password"));
                System.out.println("llooooop");
                System.out.println(email_from_database);
                System.out.println(password_from_database);
                System.out.println("llooop end did");

                if (email_from_database.equals(email) && password_from_database.equals(password)) {
                    cursor.close();
                    return true;
                }

            } while (cursor.moveToNext());
        }
            cursor.close();

            return false;

	}


    public boolean create_synch(User user, SQLiteDatabase mDatabase)
    {
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("permission", user.getPermission());
        values.put("synchronized_server", true);

        boolean created_successfull = mDatabase.insert("users", null, values) > 0;

        return created_successfull;
    }

    public boolean create(User user, SQLiteDatabase mDatabase) throws SQLException
    {

        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("permission", user.getPermission());
        values.put("synchronized_server", false);

        boolean created_successfull = mDatabase.insert("users", null, values) > 0;

        return created_successfull;
    }


    public boolean create_deleted_record(SQLiteDatabase mDatabase, List<User> list_all_users)
    {
        boolean created_successfull = false;

        if(list_all_users != null)
        for(User user : list_all_users) {
            ContentValues values = new ContentValues();
            values.put("username", user.getUsername());
            values.put("password", user.getPassword());
            values.put("email", user.getEmail());
            values.put("permission", user.getPermission());
            values.put("synchronized_server", false);

            created_successfull = mDatabase.insert("users", null, values) > 0;
        }

        return created_successfull;
    }

    public void create_from_synchronization(User user, SQLiteDatabase mDatabase)
    {
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("permission", user.getPermission());
        values.put("synchronized_server", true);
        values.put("deleted", user.getDeleted());

        mDatabase.insert("users", null, values);

    }


    public int counte_records(SQLiteDatabase mDatabase) {

        int count = 0;
        List<User> all_users_list = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery(read_all_notes_sql, null);

        if(cursor.moveToFirst()) {
            do {
               count+=1;
            } while (cursor.moveToNext());

            cursor.close();
            return count;
        }
        cursor.close();

        return 0;

    }


    public void put_mark_on_synchronized_users(List<User> local_list_all_not_synchronized_users, SQLiteDatabase mDatabase)
    {
        for(User user : local_list_all_not_synchronized_users)
        {
            ContentValues values = new ContentValues();
            values.put("username", user.getUsername());
            values.put("password", user.getPassword());
            values.put("email", user.getEmail());
            values.put("permission", user.getPermission());
            values.put("deleted", user.getDeleted());
            values.put("synchronized_server", true);
            values.put("id", user.getId());


            String where = "id = ?";
            String[] whereArgs = {Integer.toString(user.getId())};
            mDatabase.update("users", values, where, whereArgs);

        }
    }

    public void update_deleted_records_from_server(SQLiteDatabase mDatabase, Message msg)
    {
        List<User> list = msg.getList_all_users();

        for(User us : list)
        {
            User user = read_by_email(mDatabase, us.getEmail());

            ContentValues values = new ContentValues();
            values.put("username", user.getUsername());
            values.put("password", user.getPassword());
            values.put("email", user.getEmail());
            values.put("permission", user.getPermission());
            values.put("deleted", true);
            values.put("synchronized_server", user.getSynchronized_server());
            values.put("id", user.getId());


            String where = "id = ?";
            String[] whereArgs = {Integer.toString(user.getId())};
            mDatabase.update("users", values, where, whereArgs);

        }
    }

    public User read_by_email(SQLiteDatabase mDatabase, String email)
    {
        Cursor cursor = mDatabase.rawQuery(read_by_email, null);

        if(cursor.moveToFirst()) {
            do {
                Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;
                Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;

                User user = new User();
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setPermission(cursor.getString(cursor.getColumnIndex("permission")));
                user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                user.setDeleted(deleted);
                user.setSynchronized_server(synchronized_server);

                return user;
            } while (cursor.moveToNext());
        }


        cursor.close();
        return null;
    }

    public List<User> read_deleted_records(SQLiteDatabase mDatabase)
    {

        List<User> all_users_list = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery(read_all_notes_sql, null);

        if(cursor.moveToFirst()) {
            do {

                Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;
                Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;

                if(deleted) {
                    User user = new User();
                    user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                    user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    user.setPermission(cursor.getString(cursor.getColumnIndex("permission")));
                    user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                    user.setDeleted(deleted);
                    user.setSynchronized_server(synchronized_server);

                    all_users_list.add(user);
                }

            } while (cursor.moveToNext());

            cursor.close();

            return all_users_list;
        }

        cursor.close();

        return null;

    }

    public void put_mark_on_deleted_records_from_server(SQLiteDatabase mDatabase, List<User> list_all_users)
    {

        for(User us : list_all_users)
        {
            User user = read_by_email(mDatabase, us.getEmail());

            ContentValues values = new ContentValues();
            values.put("username", user.getUsername());
            values.put("password", user.getPassword());
            values.put("email", user.getEmail());
            values.put("permission", user.getPermission());
            values.put("deleted", true);
            values.put("synchronized_server", user.getSynchronized_server());
            values.put("id", user.getId());

            String where = "id = ?";
            String[] whereArgs = {Integer.toString(user.getId())};
            mDatabase.update("users", values, where, whereArgs);

        }

    }


}
