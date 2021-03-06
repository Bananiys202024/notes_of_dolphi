package com.example.notes_of_dolphi.crud;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.model.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DraftCRUD {

    private final String read_sql = "SELECT id, title, note , date_of_add, owner, deleted, edited_record, synchronized_server FROM draft";
    private final String create_sql = "INSERT INTO draft(title, note, date_of_add, user) VALUES(?, ?, ?, ?)";
    private final String sql = "DELETE FROM draft WHERE id = ?";
    private final String read_by_id = "SELECT id, title, note , date_of_add, owner FROM draft WHERE id= ?";
    private final String update_by_id = "UPDATE draft SET title=?, note=? WHERE id= ?";


    public boolean change_synchronized_records_to_true(Draft draft, SQLiteDatabase mDatabase) {

        ContentValues values = new ContentValues();
        values.put("note", draft.getNote());
        values.put("date_of_add", draft.getDate_of_note());
        values.put("owner", draft.getOwner());
        values.put("title",draft.getTitle());
        values.put("deleted", draft.getDeleted());
        values.put("synchronized_server", true);
        values.put("id", draft.getId());


        String where = "id = ?";
        String[] whereArgs = { Integer.toString(draft.getId()) };
        boolean updated_successful = mDatabase.update("draft", values, where, whereArgs) > 0;


        return updated_successful;
    }

    private List<Draft> read_all(SQLiteDatabase mDatabase)
    {

        String logged_user = Cashe.getLogged_user();

        List<Draft> list = new ArrayList<Draft>();

        Cursor cursor = mDatabase.rawQuery(read_sql, null);
        if(cursor.moveToFirst()) {
            do {
                    Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;
                    Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;

                    Draft draft = new Draft();
                    draft.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    draft.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    draft.setNote(cursor.getString(cursor.getColumnIndex("note")));
                    draft.setDate_of_note(cursor.getString(cursor.getColumnIndex("date_of_add")));
                    draft.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
                    draft.setDeleted(deleted);
                    draft.setSynchronized_server(synchronized_server);

                    list.add(draft);

            } while (cursor.moveToNext());

            cursor.close();

            return list;


        }
        cursor.close();


        return null;


    }


    public List<Draft> read_not_synchronized_drafts(SQLiteDatabase mDatabase)
    {
        String logged_user = Cashe.getLogged_user();

        List<Draft> list = new ArrayList<Draft>();

        Cursor cursor = mDatabase.rawQuery(read_sql, null);
        if(cursor.moveToFirst()) {
            do {
                Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;

                if(!synchronized_server) {

                    Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;

                    Draft draft = new Draft();
                    draft.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    draft.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    draft.setNote(cursor.getString(cursor.getColumnIndex("note")));
                    draft.setDate_of_note(cursor.getString(cursor.getColumnIndex("date_of_add")));
                    draft.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
                    draft.setDeleted(deleted);
                    draft.setSynchronized_server(synchronized_server);

                    list.add(draft);
                }
            } while (cursor.moveToNext());

            cursor.close();

            return list;


        }
        cursor.close();


        return null;
    }

    public List<Draft> read_drafts_for_define_user(SQLiteDatabase mDatabase)
    {
        String logged_user = Cashe.getLogged_user();

        List<Draft> list = new ArrayList<Draft>();

        Cursor cursor = mDatabase.rawQuery(read_sql, null);
        if(cursor.moveToFirst()) {
            do {

                String owner_of_draft = cursor.getString(cursor.getColumnIndex("owner"));

                if(owner_of_draft.equals(logged_user)) {


                    if(owner_of_draft.equals(logged_user)) {

                        Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;
                        Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;

                        if (!deleted) {
                            Draft draft = new Draft();
                            draft.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                            draft.setId(cursor.getInt(cursor.getColumnIndex("id")));
                            draft.setNote(cursor.getString(cursor.getColumnIndex("note")));
                            draft.setDate_of_note(cursor.getString(cursor.getColumnIndex("date_of_add")));
                            draft.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
                            draft.setDeleted(deleted);
                            draft.setSynchronized_server(synchronized_server);

                            list.add(draft);
                        }

                    }
                }

            } while (cursor.moveToNext());

            cursor.close();

            return list;


        }
        cursor.close();


        return null;
    }


    public List<Draft> read(SQLiteDatabase mDatabase)
    {
        String logged_user = Cashe.getLogged_user();

        List<Draft> list = new ArrayList<Draft>();

         Cursor cursor = mDatabase.rawQuery(read_sql, null);
         if(cursor.moveToFirst()) {
            do {


                    Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;
                    Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;

                    if (!deleted) {
                        Draft draft = new Draft();
                        draft.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                        draft.setId(cursor.getInt(cursor.getColumnIndex("id")));
                        draft.setNote(cursor.getString(cursor.getColumnIndex("note")));
                        draft.setDate_of_note(cursor.getString(cursor.getColumnIndex("date_of_add")));
                        draft.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
                        draft.setDeleted(deleted);
                        draft.setSynchronized_server(synchronized_server);

                        list.add(draft);
                    }

            } while (cursor.moveToNext());

            cursor.close();

            return list;


        }
        cursor.close();


        return null;
    }

    public boolean create(Draft draft, SQLiteDatabase mDatabase)
    {


        try {
            ContentValues values = new ContentValues();
            values.put("note", draft.getNote());
            values.put("date_of_add", String.valueOf(new Date()));
            values.put("owner", draft.getOwner());
            values.put("title", draft.getTitle());
            values.put("deleted", false);
            values.put("synchronized_server", false);


            boolean created_successfull = mDatabase.insert("draft", null, values) > 0;

            return created_successfull;

        } catch(Exception e)
        {
            System.out.println("Error---"+e);
        }

        return false;
    }

    public void delete(Draft drft, SQLiteDatabase mDatabase) {

        String logged_user = Cashe.getLogged_user();
        Draft draft = read_by_id(logged_user, drft.getId(), mDatabase);

        ContentValues values = new ContentValues();
        values.put("note", draft.getNote());
        values.put("date_of_add", draft.getDate_of_note());
        values.put("owner", draft.getOwner());
        values.put("title",draft.getTitle());
        values.put("deleted", true);
        values.put("synchronized_server", draft.getSynchronized_server());

        String where = "id = ?";
        String[] whereArgs = { Integer.toString(draft.getId()) };
        boolean updated_successful = mDatabase.update("draft", values, where, whereArgs) > 0;

    }

    public Draft read_by_id(String logged_user, int id, SQLiteDatabase mDatabase) {

        String read_by_id_local = "select * from draft where id =" + id + "";

        Draft draft = new Draft();
        Cursor cursor = mDatabase.rawQuery(read_by_id_local, null);

        if(cursor.moveToFirst())
        {

            Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;
            Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;

            draft.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            draft.setId(cursor.getInt(cursor.getColumnIndex("id")));
            draft.setNote(cursor.getString(cursor.getColumnIndex("note")));
            draft.setDate_of_note(cursor.getString(cursor.getColumnIndex("date_of_add")));
            draft.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
            draft.setDeleted(deleted);
            draft.setSynchronized_server(synchronized_server);

            cursor.close();
            return draft;
        }


        cursor.close();
        return null;
    }


    public boolean create_edited_draft(Draft draft, SQLiteDatabase mDatabase)
    {
        try {
            ContentValues values = new ContentValues();
            values.put("note", draft.getNote());
            values.put("date_of_add", draft.getDate_of_note());
            values.put("owner", draft.getOwner());
            values.put("title", draft.getTitle());
            values.put("deleted", false);
            values.put("synchronized_server", false);
            values.put("id", draft.getId());
            values.put("edited_record", true);

            boolean created_successfull = mDatabase.insert("draft", null, values) > 0;
            System.out.println("result---" + created_successfull);

            return created_successfull;

        } catch(Exception e)
        {
            System.out.println("Error- create note---"+e);
        }

        return false;

    }

    public void update(Draft draft, SQLiteDatabase mDatabase) {

//        delete(draft, mDatabase);
//        create_edited_draft(draft, mDatabase);

            ContentValues values = new ContentValues();
            values.put("note", draft.getNote());
            values.put("date_of_add", draft.getDate_of_note());
            values.put("owner", draft.getOwner());
            values.put("title", draft.getTitle());
            values.put("deleted", draft.getDeleted());
            values.put("synchronized_server", draft.getSynchronized_server());
            values.put("id", draft.getId());
            values.put("edited_record", true);

            String where = "id = ?";
            String[] whereArgs = {Integer.toString(draft.getId())};
            boolean updated_successful = mDatabase.update("draft", values, where, whereArgs) > 0;



    }

    public void create_list(List<Draft> list_of_not_synchronized_all_users_from_server, SQLiteDatabase mDatabase) {

        for( Draft draft : list_of_not_synchronized_all_users_from_server) {
            ContentValues values = new ContentValues();
            values.put("note", draft.getNote());
            values.put("date_of_add", draft.getDate_of_note());
            values.put("owner", draft.getOwner());
            values.put("title", draft.getTitle());
            values.put("deleted", draft.getDeleted());
            values.put("synchronized_server", "true");

            mDatabase.insert("notes", null, values);
        }
    }

    public int count_records(SQLiteDatabase mDatabase)
    {
        int count = 0;
        String logged_user = Cashe.getLogged_user();

        List<Draft> list = new ArrayList<Draft>();

        Cursor cursor = mDatabase.rawQuery(read_sql, null);

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

    public void change_column_synchronized_column_to_true(List<Draft> local_list_of_all_users, SQLiteDatabase mDatabase)
    {

        for(Draft draft : local_list_of_all_users) {
            ContentValues values = new ContentValues();
            values.put("note", draft.getNote());
            values.put("date_of_add", draft.getDate_of_note());
            values.put("owner", draft.getOwner());
            values.put("title", draft.getTitle());
            values.put("deleted", draft.getDeleted());
            values.put("synchronized_server", true);
            values.put("id", draft.getId());


            String where = "id = ?";
            String[] whereArgs = {Integer.toString(draft.getId())};
            boolean updated_successful = mDatabase.update("draft", values, where, whereArgs) > 0;

        }

    }


    public void put_mark_on_synchronized_drafts(Draft draft, SQLiteDatabase mDatabase)
    {
        ContentValues values = new ContentValues();
        values.put("note", draft.getNote());
        values.put("date_of_add", draft.getDate_of_note());
        values.put("owner", draft.getOwner());
        values.put("title",draft.getTitle());
        values.put("deleted", draft.getDeleted());
        values.put("synchronized_server", true);
        values.put("id", draft.getId());
        values.put("edited_record", draft.getEdited_record());

        String where = "id = ?";
        String[] whereArgs = { Integer.toString(draft.getId()) };
        boolean updated_successful = mDatabase.update("draft", values, where, whereArgs) > 0;

    }

    public List<Draft> read_deleted_records(SQLiteDatabase mDatabase)
    {

        String logged_user = Cashe.getLogged_user();

        List<Draft> list = new ArrayList<Draft>();

        Cursor cursor = mDatabase.rawQuery(read_sql, null);
        if(cursor.moveToFirst()) {
            do {

                Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;
                Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;

                if(deleted) {
                    Draft draft = new Draft();
                    draft.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    draft.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    draft.setNote(cursor.getString(cursor.getColumnIndex("note")));
                    draft.setDate_of_note(cursor.getString(cursor.getColumnIndex("date_of_add")));
                    draft.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
                    draft.setDeleted(deleted);
                    draft.setSynchronized_server(synchronized_server);

                    list.add(draft);
                }

            } while (cursor.moveToNext());

            cursor.close();

            return list;


        }
        cursor.close();


        return null;
    }

    public void put_mark_on_deleted_records_from_server(SQLiteDatabase mDatabase, List<Draft> list_all_drafts)
    {

        for(Draft model : list_all_drafts)
        {

            Draft draft = find_record_by_date_of_add(model, mDatabase);

            ContentValues values = new ContentValues();
            values.put("note", draft.getNote());
            values.put("date_of_add", draft.getDate_of_note());
            values.put("owner", draft.getOwner());
            values.put("title",draft.getTitle());
            values.put("deleted", true);
            values.put("synchronized_server", draft.getSynchronized_server());
            values.put("id", draft.getId());

            String where = "id = ?";
            String[] whereArgs = { Integer.toString(draft.getId()) };
            boolean updated_successful = mDatabase.update("notes", values, where, whereArgs) > 0;


        }

    }

    private Draft find_record_by_date_of_add(Draft model, SQLiteDatabase mDatabase)
    {
        String read_by_date_of_add = "select * from draft where date_of_add='" + model.getDate_of_note() + "';";

        Draft draft = new Draft();
        Cursor cursor = mDatabase.rawQuery(read_by_date_of_add, null);

        if(cursor.moveToFirst())
        {
            Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;
            Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;

            draft.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            draft.setId(cursor.getInt(cursor.getColumnIndex("id")));
            draft.setNote(cursor.getString(cursor.getColumnIndex("note")));
            draft.setDate_of_note(cursor.getString(cursor.getColumnIndex("date_of_add")));
            draft.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
            draft.setDeleted(deleted);
            draft.setSynchronized_server(synchronized_server);

            cursor.close();
            return draft;
        }


        cursor.close();
        return null;


    }

    public boolean create_synch(Draft draft, SQLiteDatabase mDatabase)
    {

        try {
            ContentValues values = new ContentValues();
            values.put("note", draft.getNote());
            values.put("date_of_add", draft.getDate_of_note());
            values.put("owner", draft.getOwner());
            values.put("title", draft.getTitle());
            values.put("deleted", false);
            values.put("synchronized_server", true);
            values.put("edited_record", draft.getEdited_record());


            boolean created_successfull = mDatabase.insert("draft", null, values) > 0;
            System.out.println("result---" + created_successfull);

            return created_successfull;

        } catch(Exception e)
        {
            System.out.println("Error- create note---"+e);
        }

        return false;

    }

    public boolean create_deleted_record(SQLiteDatabase mDatabase, List<Draft> list_all_drafts)
    {

        if(list_all_drafts != null)
         for(Draft draft : list_all_drafts) {
             try {
                 ContentValues values = new ContentValues();
                 values.put("note", draft.getNote());
                 values.put("date_of_add", draft.getDate_of_note());
                 values.put("owner", draft.getOwner());
                 values.put("title", draft.getTitle());
                 values.put("deleted", true);
                 values.put("synchronized_server", true);


                 boolean created_successfull = mDatabase.insert("draft", null, values) > 0;
                 System.out.println("result---" + created_successfull);

                 return created_successfull;

             } catch (Exception e) {
                 System.out.println("Error- create note---" + e);
             }
         }

        return false;


    }

    public List<Draft> read_edited_records(SQLiteDatabase mDatabase)
    {
        String logged_user = Cashe.getLogged_user();

        List<Draft> list = new ArrayList<Draft>();

        Cursor cursor = mDatabase.rawQuery(read_sql, null);
        if(cursor.moveToFirst()) {
            do {

                Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;
                Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;
                Boolean edited_record = cursor.getInt(cursor.getColumnIndex("edited_record")) != 0;


                if(edited_record) {
                    Draft draft = new Draft();
                    draft.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    draft.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    draft.setNote(cursor.getString(cursor.getColumnIndex("note")));
                    draft.setDate_of_note(cursor.getString(cursor.getColumnIndex("date_of_add")));
                    draft.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
                    draft.setDeleted(deleted);
                    draft.setSynchronized_server(synchronized_server);
                    draft.setEdited_record(edited_record);

                    list.add(draft);
                }

            } while (cursor.moveToNext());

            cursor.close();

            return list;


        }
        cursor.close();


        return null;
    }

    public void create_edited_record(SQLiteDatabase mDatabase, Draft draft)
    {

        //read by date of add
        Draft model = find_record_by_date_of_add(draft, mDatabase);

        //update record by id; from upper action;
        boolean size_of_table_notes_zero =  read_all(mDatabase) == null;

        if(!size_of_table_notes_zero) {
            ContentValues values = new ContentValues();
            values.put("note", model.getNote());
            values.put("date_of_add", model.getDate_of_note());
            values.put("owner", model.getOwner());
            values.put("title", model.getTitle());
            values.put("deleted", true);
            values.put("synchronized_server", model.getSynchronized_server());
            values.put("id", model.getId());
            values.put("edited_record", false);

            String where = "id = ?";
            String[] whereArgs = {Integer.toString(model.getId())};
            boolean updated_successful = mDatabase.update("notes", values, where, whereArgs) > 0;
        }

    }
}
