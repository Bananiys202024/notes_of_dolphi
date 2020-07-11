package notes.of.dolphi;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage ) throws IOException
    {
		String fxmlFile = "fxml/Login.fxml";
	    FXMLLoader loader = new FXMLLoader();
	    Parent root = (Parent) loader.load(getClass().getClassLoader().getResource(fxmlFile));
	    primaryStage.setTitle("Notes of dolphi");
       
		Scene scene = new Scene(root,400,400);
		primaryStage.setScene(scene);
		primaryStage.show();
    }
    
	public static void main(String[] args) 
	{
		launch(args);
	}
	
}
