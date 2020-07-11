package notes.of.dolphi.Notebook.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import notes.of.dolphi.Notebook.jdbc.crud.UsersCRUD;
import notes.of.dolphi.Notebook.server.ServerLogin;
import notes.of.dolphi.Notebook.utils.Checker;

public class RegistrationController implements Initializable {

	@FXML private JFXTextField email;
	@FXML private JFXTextField username;
	@FXML private JFXPasswordField password;
	@FXML private Label status;
	
	public void create_new_user(ActionEvent event) throws SQLException, IOException, ClassNotFoundException
	{
		Checker checker = new Checker();
		boolean is_fields_empty = checker.is_values_empty(email.getText(), username.getText(), password.getText());
		if(is_fields_empty)
		{
			status.setText("All fields must be filled");
			status.setVisible(true);
			status.setManaged(true);
		}
		else
		{
			ServerLogin server = new ServerLogin();
			boolean is_user_exist = server.check_if_user_exist(email.getText(),  password.getText());
			
			if(is_user_exist)
			{
				status.setText("That user already exist");
				status.setVisible(true);
				status.setManaged(true);
			}
			else
			{
			boolean result = server.create_user(email.getText(), username.getText(), password.getText());
			RouterController router = new RouterController();
			router.go_to_login_page(event);
			}
			
		}
			
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		status.setVisible(false);
		status.setManaged(false);
		
	}
	
}
