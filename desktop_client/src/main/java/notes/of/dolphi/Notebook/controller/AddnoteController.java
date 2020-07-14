package notes.of.dolphi.Notebook.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import com.example.notes_of_dolphi.model.Note;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import notes.of.dolphi.Notebook.server.ServerDiary;
import notes.of.dolphi.Notebook.utils.DateFormat;


public class AddnoteController implements Initializable {

	@FXML private Label status;
	@FXML private JFXTextField title;
	@FXML private JFXTextArea note;
	@FXML private Label dateToday;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DateFormat date_format = new DateFormat();
		dateToday.setText(date_format.get_date_in_proper_format(""+new Date()));
	}
	
	public void go_to_page_all_notes(ActionEvent event) throws IOException
	{
		RouterController router = new RouterController();
		router.go_to_page_all_notes(event);	
	}
	
	public void logout_and_exit() throws IOException
	{
		RouterController router = new RouterController();
		router.logout_and_exit();
	}
	
	public void create_new_note(ActionEvent event) throws SQLException
	{
		Note model = new Note();
		model.setDate_of_note(new Date().toString());
		model.setNote(note.getText());
		model.setTitle(title.getText());
		
		ServerDiary server = new ServerDiary();
		String result_of_process_create_note = "";
		try
		{
		result_of_process_create_note = (String) server.create(model);
		
		if(result_of_process_create_note.equals("success"))
		{
		status.setText("Note saved");
		}
		else
		{
		status.setText("Failure to save note");	
		}
		
		}
		catch(Exception e)
		{
			System.out.println(e);
			status.setText("Some troubles, we can't save note, please contact us: BestJavaDeveloper24@gmail.com");
		}
		
		}

	
}
