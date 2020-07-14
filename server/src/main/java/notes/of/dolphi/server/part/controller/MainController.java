package notes.of.dolphi.server.part.controller;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import com.example.notes_of_dolphi.model.Message;

import notes.of.dolphi.server.part.socket.actions.orders.ExecutorOforders;
import notes.of.dolphi.server.part.socket.actions.orders.GetterOforders;

public class MainController extends Thread {

	 private Socket socket;

	 // сокет, через который сервер общается с клиентом,
	 // кроме него - клиент и сервер никак не связаны
	 
	public MainController(Socket socket) {
		this.socket = socket;
		start();
	}
	
	public void run()
	{
		//start
		Message message = null;
		
		try {
			message = GetterOforders.get_request_from_client(socket);
			ExecutorOforders.execute_order_from_client(socket, message);
		} catch (ClassNotFoundException e) {
			System.out.println("Error---"+e);
		} catch (IOException e) {
			System.out.println("Error---"+e);
		}
		//...		
		catch (SQLException e) {
			System.out.println("Error---"+e);
		}
	}

}
