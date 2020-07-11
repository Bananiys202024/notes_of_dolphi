package notes.of.dolphi.Notebook.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.example.notes_of_dolphi.model.Cashe;
import com.example.notes_of_dolphi.model.Constants;
import com.example.notes_of_dolphi.model.Message;
import com.example.notes_of_dolphi.model.Note;
import com.example.notes_of_dolphi.model.User;

public class ServerLogin {

	public boolean check_if_user_exist(String email, String password) throws ClassNotFoundException, IOException {
		
				//open socket 
				Socket socket = new Socket(Constants.getIp_host(), Constants.getPORT());
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
				user.setPassword(password);
				
				Message message = new Message();
				message.setUser(user);
				message.setMessage("check_if_user_exist_for_process_login");
				
				List<Message> request = new ArrayList();
				request.add(message);
				//...
				
				//send request
				object_output_stream.writeObject(request);
				//...
					
				//get response
				InputStream inputStream = socket.getInputStream();
				ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);	
				Boolean result = (Boolean) object_input_stream.readObject();
				System.out.println("Finish...");
				//...

		return result;
	}

	public boolean create_user(String email, String username, String password) throws UnknownHostException, IOException, ClassNotFoundException {
		

		//open socket 
		Socket socket = new Socket(Constants.getIp_host(), Constants.getPORT());
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
		user.setPassword(password);
		user.setUsername(username);
		
		Message message = new Message();
		message.setUser(user);
		message.setMessage("create_user");
		
		List<Message> request = new ArrayList();
		request.add(message);
		//...
		
		//send request
		object_output_stream.writeObject(request);
		//...
			
		//get response
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);	
		Boolean result = (Boolean) object_input_stream.readObject();
		System.out.println("Finish...");
		//...
		
		return result;
		
	}
	
}
