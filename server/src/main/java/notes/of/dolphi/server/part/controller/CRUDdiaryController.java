package notes.of.dolphi.server.part.controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.model.User;

import notes.of.dolphi.server.part.crud.CRUDdiary;
import notes.of.dolphi.server.part.crud.JDBC_initializing;

public class CRUDdiaryController {

	public static void read(Socket socket, Message message) throws IOException {
		
		//init database
		System.out.println("Initialize database...");
		JDBC_initializing jdbc_init = new JDBC_initializing();
		jdbc_init.create_databases_if_not_exist();
		System.out.println("Initialize database finish.");
		//...
		
		//get request from client 
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
		//...
		
		//execute process of method
		String logged_user = message.getUser().getEmail();		
		CRUDdiary crud_diary = new CRUDdiary();
		List<Note> notes = crud_diary.read(logged_user);
		//...
		
		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(notes);
		System.out.println("Finish process...");
		//...
	}

	public static void read_by_id(Socket socket, Message message) throws IOException {

		//init database
		System.out.println("Initialize database...");
		JDBC_initializing jdbc_init = new JDBC_initializing();
		jdbc_init.create_databases_if_not_exist();
		System.out.println("Initialize database finish.");
		//...
		
		//get request from client 
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
		//...
		
		//execute process of method
		String logged_user = message.getUser().getEmail();
		int id = message.getNote().getId();
		CRUDdiary crud_diary = new CRUDdiary();
		Note notes = crud_diary.read_by_id(logged_user, id);
		//...
		
		
		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(notes);
		System.out.println("Finish process...");
		//...
	}

	public static void create_note(Socket socket, Message message) throws IOException, SQLException 
	{
		//init database
		System.out.println("Initialize database...");
		JDBC_initializing jdbc_init = new JDBC_initializing();
		jdbc_init.create_databases_if_not_exist();
		System.out.println("Initialize database finish.");
		//...
		
		//get request from client 
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
		//...
		
		//execute process of method
		String logged_user = message.getUser().getEmail();
		Note note = message.getNote();
		note.setDate_of_note(note.getDate_of_note());
		CRUDdiary crud_diary = new CRUDdiary();
		String result = crud_diary.create(note, logged_user);
		//...
		
		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(result);
		System.out.println("Finish process...");
		//...
	}

	public static void update_note(Socket socket,
			Message message) throws IOException 
	{

		//init database
		System.out.println("Initialize database...");
		JDBC_initializing jdbc_init = new JDBC_initializing();
		jdbc_init.create_databases_if_not_exist();
		System.out.println("Initialize database finish.");
		//...
		
		//get request from client 
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
		//...
		
		//execute process of method
		String logged_user = message.getUser().getEmail();
		Note note = message.getNote();
		CRUDdiary crud_diary = new CRUDdiary();
		String result = crud_diary.update(note);
		//...
		
		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(result);
		System.out.println("Finish process...");
		//...
		}

	public static void delete_note(Socket socket, Message message) throws IOException {

				//init database
				System.out.println("Initialize database...");
				JDBC_initializing jdbc_init = new JDBC_initializing();
				jdbc_init.create_databases_if_not_exist();
				System.out.println("Initialize database finish.");
				//...
				
				//get request from client 
				OutputStream outputStream = socket.getOutputStream();
				ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
				//...
				
				//execute process of method
				int id = message.getNote().getId();
				CRUDdiary crud_diary = new CRUDdiary();
				String result = crud_diary.delete(id);
				//...
				
				//send request from server
				System.out.println("sending resources....");
				object_output_stream.writeObject(result);
				System.out.println("Finish process");
				//...
	}
	
	
	public static void read_all(Socket socket, Message message) throws IOException {
		
		//init database
		System.out.println("Initialize database...");
		JDBC_initializing jdbc_init = new JDBC_initializing();
		jdbc_init.create_databases_if_not_exist();
		System.out.println("Initialize database finish.");
		//...
		
		//get request from client 
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
		//...
		
		//execute process of method
		String logged_user = message.getUser().getEmail();		
		CRUDdiary crud_diary = new CRUDdiary();
		List<Note> notes = crud_diary.read_all(logged_user);
		//...
		
		//send request from server
		System.out.println("sending resources...");
		object_output_stream.writeObject(notes);
		System.out.println("Finish process...");
		//...
	}


}
