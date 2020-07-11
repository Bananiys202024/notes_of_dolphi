package notes.of.dolphi.Notebook.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.example.notes_of_dolphi.model.Note;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import notes.of.dolphi.Notebook.server.ServerDiary;
import notes.of.dolphi.Notebook.utils.DateFormat;

public class EditnoteController implements Initializable {

	@FXML private Label status;
	@FXML private JFXTextField title;
	@FXML private JFXTextArea note;
	@FXML private Label date_of_create_of_note;
	@FXML private int passed_id_from_page_all_notes;
	
	public void init_id_from_page_all_notes(int id)
	{
		this.passed_id_from_page_all_notes = id;
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		DateFormat date_format = new DateFormat();
		
		ServerDiary server = new ServerDiary();;
		
		//get object note
		Note note = null;
		try {
			note = server.read_by_id(passed_id_from_page_all_notes);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//....
		
		this.title.setText(note.getTitle());
		this.note.setText(note.getNote());
		this.date_of_create_of_note.setText(date_format.get_date_in_proper_format(note.getDate_of_note()));

	}
	
	public void save_edited_note(ActionEvent event) throws ClassNotFoundException, IOException
	{
		Note note = Note.builder()
								  .withId(this.passed_id_from_page_all_notes)
								  .withTitle(this.title.getText())
								  .withNote(this.note.getText())
								  .build();
								  
		ServerDiary server = new ServerDiary();
		server.update(note);
		
		status.setText("Saved success");
	
	}
	
	public void go_to_page_add_note(ActionEvent event) throws IOException
	{
		RouterController router = new RouterController();
		router.go_to_page_add_note(event);
	}
	
	public void logout_and_exit() throws IOException
	{
		RouterController router = new RouterController();
		router.logout_and_exit();
	}
	
	public void go_to_page_all_notes(ActionEvent event) throws IOException
	{
		RouterController router = new RouterController();
		router.go_to_page_all_notes(event);	
	}
	
}
