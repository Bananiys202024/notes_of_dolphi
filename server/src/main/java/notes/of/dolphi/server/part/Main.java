package notes.of.dolphi.server.part;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import com.example.notes_of_dolphi.model.Constants;

import notes.of.dolphi.server.part.controller.MainController;

public class Main 
{

    public static LinkedList<MainController> serverList = new LinkedList(); // список всех нитей
    
    public static void main( String[] args ) throws IOException
    {
    	ServerSocket server = new ServerSocket(Constants.getPort());
		
        try {
            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = server.accept();
                serverList.add(new MainController(socket)); // добавить новое соединенние в список
            }
        } finally {
            server.close();
        }
    }
}
