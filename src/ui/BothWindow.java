package ui;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BothWindow extends Stage implements LibWindow {
	
	public static final BothWindow INSTANCE = new BothWindow();
	
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
	
	/* This class is a singleton */
    private BothWindow() {}
    
    public void init() { 
        GridPane grid = new GridPane();
        grid.setId("top-container");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        this.setTitle("Administration and Librarian");

        Text scenetitle = new Text("Admin and Lib");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, 0, 2, 1);

        Button adminBtn1 = new Button("Add Book");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BASELINE_CENTER);
        hbBtn.getChildren().add(adminBtn1);
        grid.add(hbBtn, 1, 4);
        
        Button adminBtn2 = new Button("Add Member");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BASELINE_CENTER);
        hbBtn2.getChildren().add(adminBtn2);
        grid.add(hbBtn2, 1, 4);
        
        Button adminBtn3 = new Button("Edit Member");
        HBox hbBtn3 = new HBox(10);
        hbBtn3.setAlignment(Pos.BASELINE_CENTER);
        hbBtn3.getChildren().add(adminBtn3);
        grid.add(hbBtn3, 1, 4);

        Scene scene = new Scene(grid, 420, 375);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        setScene(scene);
        
    }



}

