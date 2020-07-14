package notes.of.dolphi.Notebook.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.model.User;

public class ServerDiary {

	public List<Note> read() throws IOException, ClassNotFoundException {
				
		//open socket 
		Socket socket = new Socket(Constants.getIpHost(), Constants.getPort());
		System.out.println("Connected");
		//...
		
		//send request
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
		//...
		
		//execute method
		String logged_user = Cashe.getLogged_user();
		
		User user = new User();
		user.setEmail(logged_user);
		
		Message message = new Message();
		message.setUser(user);
		message.setMessage("read_notes_online");
		
		List<Message> request = new ArrayList();
		request.add(message);
		//...
		
		//send request
		object_output_stream.writeObject(request);
		//...
			
		//get response
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);	
		List<Note> notes = (List<Note>) object_input_stream.readObject();
		System.out.println("Finish...");
		//...

		return notes;
	}

	public String delete(int id) throws IOException, ClassNotFoundException {

				//open socket 
				Socket socket = new Socket(Constants.getIpHost(), Constants.getPort());
				System.out.println("Connected");
				//...
				
				//send request
				OutputStream outputStream = socket.getOutputStream();
				ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
				//...
				
				//execute method
				String logged_user = Cashe.getLogged_user();
				
				Note note = new Note();
				note.setId(id);
				
				User user = new User();
				user.setEmail(logged_user);
				
				Message message = new Message();
				message.setUser(user);
				message.setMessage("delete");
				message.setNote(note);
				
				List<Message> request = new ArrayList();
				request.add(message);
				//...
				
				//send request
				object_output_stream.writeObject(request);
				//...
					
				//get response
				InputStream inputStream = socket.getInputStream();
				ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);	
				String result = (String) object_input_stream.readObject();
				System.out.println("Finish...");
				//...
		
		return result;
	}

	public String create(Note model) throws IOException, ClassNotFoundException {
	
		//open socket 
		Socket socket = new Socket(Constants.getIpHost(), Constants.getPort());
		System.out.println("Connected");
		//...
		
		//send request
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
		//...
		
		//execute method
		String logged_user = Cashe.getLogged_user();
		
		User user = new User();
		user.setEmail(logged_user);
		
		Message message = new Message();
		message.setUser(user);
		message.setMessage("create");
		message.setNote(model);
		
		List<Message> request = new ArrayList();
		request.add(message);
		//...
		
		//send request
		object_output_stream.writeObject(request);
		//...
			
		//get response
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);	
		String result = (String) object_input_stream.readObject();
		System.out.println("Finish...");
		//...
	
		return result;
	}

	public Note read_by_id(int id) throws IOException, ClassNotFoundException {
		
		//open socket 
		Socket socket = new Socket(Constants.getIpHost(), Constants.getPort());
		System.out.println("Connected");
		//...
		
		//send request
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
		//...
		
		//execute method
		String logged_user = Cashe.getLogged_user();
		
		Note note = new Note();
		note.setId(id);
		
		User user = new User();
		user.setEmail(logged_user);
		
		Message message = new Message();
		message.setUser(user);
		message.setMessage("read_by_id");
		message.setNote(note);
		
		List<Message> request = new ArrayList();
		request.add(message);
		//...
		
		//send request
		object_output_stream.writeObject(request);
		//...
			
		//get response
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);	
		Note notes = (Note) object_input_stream.readObject();
		System.out.println("Finish...");
		//...
	
		return notes;
	}

	public String update(Note note) throws IOException, ClassNotFoundException {
		
		
		//open socket 
		Socket socket = new Socket(Constants.getIpHost(), Constants.getPort());
		System.out.println("Connected");
		//...
		
		//send request
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream object_output_stream = new ObjectOutputStream(outputStream);
		//...
		
		//execute method
		String logged_user = Cashe.getLogged_user();
		;
		
		User user = new User();
		user.setEmail(logged_user);
			
		Message message = new Message();
		message.setUser(user);
		message.setMessage("update");
		message.setNote(note);
		
		List<Message> request = new ArrayList();
		request.add(message);
		//...
		
		//send request
		object_output_stream.writeObject(request);
		//...
			
		//get response
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);	
		String result = (String) object_input_stream.readObject();
		System.out.println("Finish...");
		//...
	
		return result;	
	}
}
