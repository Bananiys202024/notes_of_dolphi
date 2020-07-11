package notes.of.dolphi.Notebook.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class RouterController {

	final String main_path = "fxml/";

	public void go_to_page_all_notes(ActionEvent event) throws IOException
	{
		// get screensize of monitor
	    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	    //...
	    FXMLLoader loader = new FXMLLoader();
	    Parent root = (Parent) loader.load(getClass().getClassLoader().getResource(main_path+"Allnotes.fxml"));

		Scene scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight());

		scene.getStylesheets().add("css/css_for_block_of_note.css");
		
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setFullScreen(true);
		window.setMaximized(true);
		window.setScene(scene);
		window.show();
		
	}



	public void go_to_page_add_note(ActionEvent event) throws IOException {
		
		// get screensize of monitor
	    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	    //...
	    FXMLLoader loader = new FXMLLoader();
	    Parent root = (Parent) loader.load(getClass().getClassLoader().getResource(main_path+"Addnote.fxml"));

		Scene scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
	
		//this line gets the Stage information
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setMaximized(true);
		window.setScene(scene);
		window.show();
		
	}


	public void logout_and_exit() {
		System.exit(0);	
	}


	public void go_to_page_registration(ActionEvent event) throws IOException, URISyntaxException {
		
		// get screensize of monitor
	    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	    //...
	  
	    FXMLLoader loader = new FXMLLoader();
	    Parent root = (Parent) loader.load(getClass().getClassLoader().getResource(main_path+"Registration.fxml"));

		Scene scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
	
		//this line gets the Stage information
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setMaximized(true);
		window.setScene(scene);
		window.show();

	}


	public void go_to_login_page(ActionEvent event) throws IOException {
		
		// get screensize of monitor
	    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	    //...
	    
	    FXMLLoader loader = new FXMLLoader();
	    Parent root = (Parent) loader.load(getClass().getClassLoader().getResource(main_path+"Login.fxml"));

		Scene scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
	
		//this line gets the Stage information
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setMaximized(true);
		window.setScene(scene);
		window.show();

	}
	
public void go_to_edit_page(ActionEvent event , int id) throws IOException {
		
		// get screensize of monitor
	    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	    //...

	    EditnoteController edit_controller = new EditnoteController();
	    edit_controller.init_id_from_page_all_notes(id);

	    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(main_path+"Editnote.fxml"));
	    
	    loader.setController(edit_controller);
	    //pass data between two fxml
//		EditnoteController controller_for_page_edit_note = loader.<EditnoteController>getController();
//		controller_for_page_edit_note.init_id_from_page_all_notes(Integer.parseInt(id));
	    //...
		Parent root = (Parent) loader.load();
		
		Scene scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight());

		//this line gets the Stage information
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setMaximized(true);
		window.setScene(scene);

		window.show();

	}
	
}
