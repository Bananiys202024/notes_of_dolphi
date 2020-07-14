package notes.of.dolphi.server.part.socket.actions.orders;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import com.example.notes_of_dolphi.model.Message;



public class GetterOforders {
	public static Message get_request_from_client(Socket socket) throws IOException, ClassNotFoundException {
		
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream object_input_stream = new ObjectInputStream(inputStream);
		
		List<Message> message = (List<Message>) object_input_stream.readObject();
		
		return message.get(0);
	}	
}
