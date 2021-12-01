package ui;

import java.util.Optional;

import business.Address;
import business.ControllerInterface;
import business.CustomException;
import business.SystemController;
import dataaccess.Auth;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditMemberWindow extends Stage implements LibWindow {
	
	public static final EditMemberWindow INSTANCE = new EditMemberWindow();
	
	
	private boolean isInitialized = false;
	
	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	private Text messageBar = new Text();
	public void clear() {
		messageBar.setText("");
	}
	
	Alert alert = new Alert(AlertType.INFORMATION);
	
	/* This class is a singleton */
    private EditMemberWindow() {}
    
    public void init() { 
        GridPane grid = new GridPane();
        grid.setId("top-container");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        this.setTitle("Edit Member");
        
        
        //TODO :- check this out first without it.
        grid.setGridLinesVisible(false) ;

        Scene scene = new Scene(grid, 420, 375);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        setScene(scene);
        
    }



}

