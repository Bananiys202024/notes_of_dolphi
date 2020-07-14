package com.example.notes_of_dolphi.server.crud;


import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.model.User;
import com.example.notes_of_dolphi.server.crud.asyncTask.diary.Create;
import com.example.notes_of_dolphi.server.crud.asyncTask.diary.Delete;
import com.example.notes_of_dolphi.server.crud.asyncTask.diary.Read;
import com.example.notes_of_dolphi.server.crud.asyncTask.diary.Update;
import com.example.notes_of_dolphi.server.crud.asyncTask.users.ReadAll;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class DiaryCRUD {

	public List<Note> read() throws IOException, ClassNotFoundException, ExecutionException, InterruptedException {
		Read asynch_task_read = new Read();
		return asynch_task_read.execute().get();
	}

	public List<Note> read_all() throws IOException, ClassNotFoundException, ExecutionException, InterruptedException {
		ReadAll asynch_task_read_all = new ReadAll();
		return asynch_task_read_all.execute().get();
	}

	public Boolean delete(final int id) throws IOException, ClassNotFoundException, ExecutionException, InterruptedException {
		Delete asynch_task_delete = new Delete();
		Integer[] array_integer = new Integer[]{id};
		return asynch_task_delete.execute(array_integer).get();
	}

	public String create(Note model) throws IOException, ClassNotFoundException, ExecutionException, InterruptedException {
		Create asynch_task_create = new Create();
		return asynch_task_create.execute(model).get();
	}

	public Boolean update(Note note) throws IOException, ClassNotFoundException, ExecutionException, InterruptedException {
		Update asynch_task_update = new Update();
		return asynch_task_update.execute(note).get();
	}

	public int count_records() throws ClassNotFoundException, ExecutionException, InterruptedException, IOException {
		List<Note> list = read_all();

		if(list != null)
			return list.size();

		return 0;
	}
}
