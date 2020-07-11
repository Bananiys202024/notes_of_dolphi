package com.example.notes_of_dolphi.crud;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.Draft;
import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.Note;


public class DiaryCRUD {

	private final String read_sql = "SELECT id, title, note , date_of_add, owner, deleted, synchronized_server FROM notes";
	private final String create_sql = "INSERT INTO notes(title, note, date_of_add, user) VALUES(?, ?, ?, ?)";
	private final String sql = "DELETE FROM notes WHERE id = ?";
	private final String read_by_id = "SELECT id, title, note , date_of_add, owner FROM notes WHERE id= ?";
	private final String update_by_id = "UPDATE notes SET title=?, note=? WHERE id= ?";
	private final String read_by_date_of_add = "SELECT id, title, note, date_of_add, owner FROM notes WHERE date_of_add=?";

	/*


	public DiaryCRUD(@Nullable Context context) {
		super(context);
	}
*/

	public boolean create(Note note, SQLiteDatabase mDatabase)
	{


		String date_of_add = String.valueOf(new Date());

		System.out.println("Result from here:--Checking time of creation-----"+date_of_add);

        try {
			ContentValues values = new ContentValues();
			values.put("note", note.getNote());
			values.put("date_of_add", date_of_add);
			values.put("owner", note.getOwner());
			values.put("title", note.getTitle());
			values.put("deleted", false);
			values.put("synchronized_server", false);


			boolean created_successfull = mDatabase.insert("notes", null, values) > 0;
			System.out.println("result---" + created_successfull);

			return created_successfull;

		} catch(Exception e)
		{
			System.out.println("Error- create note---"+e);
		}

		return false;
	}



	public List<Note> read_not_synchronized_notes(SQLiteDatabase mDatabase)
	{

		List<Note> list = new ArrayList<Note>();

		Cursor cursor = mDatabase.rawQuery(read_sql, null);

		if(cursor.moveToFirst()) {
			do {

				Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;

				if(!synchronized_server) {

					Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;

					Note note = new Note();
					note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
					note.setId(cursor.getInt(cursor.getColumnIndex("id")));
					note.setNote(cursor.getString(cursor.getColumnIndex("note")));
					note.setDate_of_note(cursor.getString(cursor.getColumnIndex("date_of_add")));
					note.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
					note.setDeleted(deleted);
					note.setSynchronized_server(synchronized_server);

					list.add(note);
				}

			} while (cursor.moveToNext());

			cursor.close();
			return list;


		}
		cursor.close();


		return null;
	}


	public List<Note> read(SQLiteDatabase mDatabase)
	{

		String logged_user = Cashe.getLogged_user();

		List<Note> list = new ArrayList<Note>();

		Cursor cursor = mDatabase.rawQuery(read_sql, null);

		if(cursor.moveToFirst()) {
			do {

				Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;
				Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;

				if(!deleted) {
					Note note = new Note();
					note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
					note.setId(cursor.getInt(cursor.getColumnIndex("id")));
					note.setNote(cursor.getString(cursor.getColumnIndex("note")));
					note.setDate_of_note(cursor.getString(cursor.getColumnIndex("date_of_add")));
					note.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
					note.setDeleted(deleted);
					note.setSynchronized_server(synchronized_server);

					list.add(note);
				}

			} while (cursor.moveToNext());

			cursor.close();
			return list;


		}
		cursor.close();


		return null;
	}


	public List<Note> read_all(SQLiteDatabase mDatabase)
	{

		String logged_user = Cashe.getLogged_user();

		List<Note> list = new ArrayList<Note>();

		Cursor cursor = mDatabase.rawQuery(read_sql, null);

		if(cursor.moveToFirst()) {
			do {

				Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;
				Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;

					Note note = new Note();
					note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
					note.setId(cursor.getInt(cursor.getColumnIndex("id")));
					note.setNote(cursor.getString(cursor.getColumnIndex("note")));
					note.setDate_of_note(cursor.getString(cursor.getColumnIndex("date_of_add")));
					note.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
					note.setDeleted(deleted);
					note.setSynchronized_server(synchronized_server);

					list.add(note);

			} while (cursor.moveToNext());

			cursor.close();
			return list;


		}
		cursor.close();


		return null;
	}


	public void delete(int id, SQLiteDatabase mDatabase) {

		String logged_user = Cashe.getLogged_user();
		Note note = read_by_id(logged_user, id, mDatabase);

		ContentValues values = new ContentValues();
		values.put("note", note.getNote());
		values.put("date_of_add", note.getDate_of_note());
		values.put("owner", note.getOwner());
		values.put("title",note.getTitle());
		values.put("deleted", true);
		values.put("synchronized_server", note.getSynchronized_server());
		values.put("id", id);


		String where = "id = ?";
		String[] whereArgs = { Integer.toString(note.getId()) };
		boolean updated_successful = mDatabase.update("notes", values, where, whereArgs) > 0;


	}

	public Note read_by_id(String logged_user, int id, SQLiteDatabase mDatabase) {

		String read_by_id_local = "select * from notes where id='" + id + "'";

		Note note = new Note();
		Cursor cursor = mDatabase.rawQuery(read_by_id_local, null);

		if(cursor.moveToFirst())
		{
			Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;
			Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;

			note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			note.setId(cursor.getInt(cursor.getColumnIndex("id")));
			note.setNote(cursor.getString(cursor.getColumnIndex("note")));
			note.setDate_of_note(cursor.getString(cursor.getColumnIndex("date_of_add")));
			note.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
			note.setDeleted(deleted);
			note.setSynchronized_server(synchronized_server);

			cursor.close();
			return note;
		}


		cursor.close();
		return null;
	}


	public void put_mark_on_synchronized_notes(List<Note> local_list_all_not_synchronized_users, SQLiteDatabase mDatabase)
	{
		for(Note note : local_list_all_not_synchronized_users) {
			ContentValues values = new ContentValues();
			values.put("note", note.getNote());
			values.put("date_of_add", note.getDate_of_note());
			values.put("owner", note.getOwner());
			values.put("title", note.getTitle());
			values.put("deleted", note.getDeleted());
			values.put("synchronized_server", true);
			values.put("id", note.getId());


			String where = "id = ?";
			String[] whereArgs = {Integer.toString(note.getId())};
			mDatabase.update("notes", values, where, whereArgs);
			}
	}

	public boolean update(Note note, SQLiteDatabase mDatabase) {

		ContentValues values = new ContentValues();
		values.put("note", note.getNote());
		values.put("date_of_add", note.getDate_of_note());
		values.put("owner", note.getOwner());
		values.put("title",note.getTitle());
		values.put("deleted", note.getDeleted());
		values.put("synchronized_server", note.getSynchronized_server());
	    values.put("id", note.getId());


	    String where = "id = ?";
	    String[] whereArgs = { Integer.toString(note.getId()) };
	    boolean updated_successful = mDatabase.update("notes", values, where, whereArgs) > 0;


	    return updated_successful;
	}

	public void create_list(List<Note> list_of_not_synchronized_all_users_from_server, SQLiteDatabase mDatabase) {

	for( Note note : list_of_not_synchronized_all_users_from_server) {
		ContentValues values = new ContentValues();
		values.put("note", note.getNote());
		values.put("date_of_add", note.getDate_of_note());
		values.put("owner", note.getOwner());
		values.put("title", note.getTitle());
		values.put("deleted", note.getDeleted());
		values.put("synchronized_server", "true");

		mDatabase.insert("notes", null, values);
		}
	}

    public int count_records(SQLiteDatabase mDatabase)
	{

		int count = 0;
		String logged_user = Cashe.getLogged_user();

		List<Note> list = new ArrayList<Note>();

		Cursor cursor = mDatabase.rawQuery(read_sql, null);


		System.out.println("Checking this right here----");
		if(cursor.moveToFirst()) {
			do {
				System.out.println(cursor.getString(cursor.getColumnIndex("date_of_add")));
				count += 1;

			} while (cursor.moveToNext());

			cursor.close();

			System.out.println("Finish check");
			return count;

		}
		cursor.close();

		return 0;
	}

	public void update_deleted_records_from_server(SQLiteDatabase mDatabase, Message msg)
	{
		List<Note> note_list = msg.getList_all_notes();

		for(Note note : note_list)
		{

			ContentValues values = new ContentValues();
			values.put("note", note.getNote());
			values.put("date_of_add", note.getDate_of_note());
			values.put("owner", note.getOwner());
			values.put("title",note.getTitle());
			values.put("deleted", note.getDeleted());
			values.put("synchronized_server", note.getSynchronized_server());
			values.put("id", note.getId());


			String where = "id = ?";
			String[] whereArgs = { Integer.toString(note.getId()) };
			boolean updated_successful = mDatabase.update("notes", values, where, whereArgs) > 0;

		}

	}

	public List<Note> read_deleted_records(SQLiteDatabase mDatabase)
	{
		String logged_user = Cashe.getLogged_user();

		List<Note> list = new ArrayList<Note>();

		Cursor cursor = mDatabase.rawQuery(read_sql, null);

		if(cursor.moveToFirst()) {
			do {

				Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;
				Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;

				if(deleted) {
					Note note = new Note();
					note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
					note.setId(cursor.getInt(cursor.getColumnIndex("id")));
					note.setNote(cursor.getString(cursor.getColumnIndex("note")));
					note.setDate_of_note(cursor.getString(cursor.getColumnIndex("date_of_add")));
					note.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
					note.setDeleted(deleted);
					note.setSynchronized_server(synchronized_server);

					list.add(note);
				}

			} while (cursor.moveToNext());

			cursor.close();
			return list;


		}
		cursor.close();


		return null;
	}


	public void put_mark_on_deleted_records_from_server(SQLiteDatabase mDatabase, List<Note> list_all_notes)
	{

		boolean size_of_table_notes_zero =  read_all(mDatabase) == null;

		if( list_all_notes != null && !size_of_table_notes_zero)
		for(Note not : list_all_notes)
		{

			System.out.println("Checking this right here-----");
			System.out.println(not.getDate_of_note());
			Note note = find_record_by_date_of_add(not, mDatabase);

			ContentValues values = new ContentValues();
			values.put("note", note.getNote());
			values.put("date_of_add", note.getDate_of_note());
			values.put("owner", note.getOwner());
			values.put("title",note.getTitle());
			values.put("deleted", true);
			values.put("synchronized_server", note.getSynchronized_server());
			values.put("id", note.getId());

			String where = "id = ?";
			String[] whereArgs = { Integer.toString(note.getId()) };
			boolean updated_successful = mDatabase.update("notes", values, where, whereArgs) > 0;
		}
	}

	private Note find_record_by_date_of_add(Note not, SQLiteDatabase mDatabase)
	{

		List<Note> list_notes = read_all(mDatabase);
		System.out.println("Start here---0---0--00---0-");
		for(Note note  : list_notes)
		{
			System.out.println("-g-"+note.getDate_of_note()+"-g-");
		}
		System.out.println("End here---0-0-----0----0----");

		String read_by_date_of_add = "select * from notes where date_of_add='" + not.getDate_of_note() + "';";

		System.out.println("Looking for date---"+read_by_date_of_add);

		System.out.println("From method --g-"+not.getDate_of_note()+"-g-");

		Note note = new Note();
		Cursor cursor = mDatabase.rawQuery(read_by_date_of_add, null);

		if(cursor.moveToFirst())
		{
			System.out.println("Show connection0-----");
			Boolean synchronized_server = cursor.getInt(cursor.getColumnIndex("synchronized_server")) != 0;
			Boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) != 0;

			note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			note.setId(cursor.getInt(cursor.getColumnIndex("id")));
			note.setNote(cursor.getString(cursor.getColumnIndex("note")));
			note.setDate_of_note(cursor.getString(cursor.getColumnIndex("date_of_add")));
			note.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
			note.setDeleted(deleted);
			note.setSynchronized_server(synchronized_server);

			cursor.close();
			return note;
		}


		cursor.close();
		return null;

	}

	public boolean create_synch(Note note, SQLiteDatabase mDatabase)
	{



		try {
			ContentValues values = new ContentValues();
			values.put("note", note.getNote());
			values.put("date_of_add", note.getDate_of_note());
			values.put("owner", note.getOwner());
			values.put("title", note.getTitle());
			values.put("deleted", false);
			values.put("synchronized_server", true);


			boolean created_successfull = mDatabase.insert("notes", null, values) > 0;
			System.out.println("result---" + created_successfull);

			return created_successfull;

		} catch(Exception e)
		{
			System.out.println("Error- create note---"+e);
		}

		return false;

	}


	public boolean create_deleted_record(SQLiteDatabase mDatabase, List<Note> list_all_notes)
	{

		if( list_all_notes != null)
		for(Note note : list_all_notes) {

			try {
				ContentValues values = new ContentValues();
				values.put("note", note.getNote());
				values.put("date_of_add", note.getDate_of_note());
				values.put("owner", note.getOwner());
				values.put("title", note.getTitle());
				values.put("deleted", true);
				values.put("synchronized_server", true);


				boolean created_successfull = mDatabase.insert("notes", null, values) > 0;
				System.out.println("result---" + created_successfull);

				return created_successfull;

			} catch (Exception e) {
				System.out.println("Error- create note---" + e);
			}
		}


		return false;
	}

}

