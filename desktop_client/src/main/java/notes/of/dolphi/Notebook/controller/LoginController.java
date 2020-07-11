package notes.of.dolphi.Notebook.controller;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.notes_of_dolphi.model.Cashe;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;
import notes.of.dolphi.Notebook.jdbc.crud.UsersCRUD;
import notes.of.dolphi.Notebook.jdbc.initializing.JDBC_initializing;
import notes.of.dolphi.Notebook.server.ServerLogin;
import notes.of.dolphi.Notebook.utils.Checker;

public class LoginController  implements Initializable
{
	
	@FXML private JFXPasswordField password;
	@FXML private JFXTextField email;
	@FXML private Label status;
	
	private String fehler = "Ungültiges Passwort oder Benutzername / E-Mail\n";
	
	public void login(ActionEvent event) throws IOException, SQLException, ClassNotFoundException
	{
		Checker checker = new Checker();
		
		boolean is_fields_empty = checker.is_values_empty(email.getText(), "efew", password.getText());
		if(is_fields_empty)
		{
			status.setText("All fields must be filled");
			status.setVisible(true);
			status.setManaged(true);
		}
		else
		{
			//1
			ServerLogin server = new ServerLogin();
			boolean is_user_exist = server.check_if_user_exist(email.getText(),  password.getText());
     		//...
			
			if(is_user_exist)
			{
				Cashe.setLogged_user(email.getText());
				RouterController router = new RouterController();
				success_login(event);
			}
			else
			{
				status.setText("Wrong password or user doesn't exist in database");
				status.setVisible(true);
				status.setManaged(true);
			}
		}
	}

	public void initialize(URL arg0, ResourceBundle arg1)
	{
		JDBC_initializing jdbc_init = new JDBC_initializing();
		jdbc_init.create_databases_if_not_exist();
		    
		status.setVisible(false);
		status.setManaged(false);
	}
	
	public void success_login(ActionEvent event) throws IOException
	{
		RouterController router = new RouterController();
		router.go_to_page_all_notes(event);
	}
	
	public void go_to_registration(ActionEvent event) throws IOException, URISyntaxException
	{
		RouterController router = new RouterController();
		try {
			router.go_to_page_registration(event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
