import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MyController implements Initializable {
	
	@FXML
	private BorderPane root;
	
	@FXML
    private TextField c1;

    @FXML
    private TextField t2;
    
    @FXML
    private Button send;
    
    @FXML
    private Button serverChoice;

    @FXML
    private Button clientChoice;
    
    private static String textEntered = "";
    
    
    Server serverConnection;
    private static Client clientConnection;
    static ListView<String> listItems = new ListView<String>();
    static ListView<String> listItems2 = new ListView<String>();

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    	
    }
    
    
    public void serverMethod(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/server.fxml"));
        Parent root2 = loader.load();
        
        listItems = (ListView<String>) loader.getNamespace().get("listItems"); 
        
        serverConnection = new Server(data -> {
            Platform.runLater(() -> {
                String message = data.toString();
                System.out.println(message);
                listItems.getItems().add(message); 
                listItems.refresh(); 
            });
        });

        root.setCenter(root2);
    }
    
    public void clientMethod(ActionEvent e) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/client.fxml"));
    	Parent root3 = loader.load();
    	listItems2 = (ListView<String>) loader.getNamespace().get("listItems");
    	
    	clientConnection = new Client(data->{
			Platform.runLater(()->{
				listItems2.getItems().add(data.toString());
				listItems.refresh();
				
			});
		});
			
    	clientConnection.start();
			   	
    	root.getScene().setRoot(root3);
    }
    
    public void sendMethod(ActionEvent e) throws IOException {
    	
    	textEntered = c1.getText();
		
		listItems2.getItems().add(textEntered); 
		clientConnection.send(textEntered);	
		
		listItems2.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	String selectedItem = listItems2.getSelectionModel().getSelectedItem();
		        System.out.println(selectedItem + " was clicked");
		    }
		});
		
    }
    
}