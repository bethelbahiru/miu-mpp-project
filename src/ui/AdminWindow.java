package ui;

import business.ControllerInterface;
import business.CustomException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminWindow extends Stage implements LibWindow {
	
	public static final AdminWindow INSTANCE = new AdminWindow();
	
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
    private AdminWindow() {}
    
    public void init() { 
        GridPane grid = new GridPane();
        grid.setId("top-container");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        this.setTitle("Administration");

        Text scenetitle = new Text("Admin Dashboard");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, 0, 2, 1);

        Button adminBtn1 = new Button("Add Book");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BASELINE_CENTER);
        hbBtn.getChildren().add(adminBtn1);
        grid.add(hbBtn, 1, 1);
        
        Button adminBtn2 = new Button("Add Member");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BASELINE_CENTER);
        hbBtn2.getChildren().add(adminBtn2);
        grid.add(hbBtn2, 1, 2);
        
        Button adminBtn3 = new Button("Edit Member");
        HBox hbBtn3 = new HBox(10);
        hbBtn3.setAlignment(Pos.BASELINE_CENTER);
        hbBtn3.getChildren().add(adminBtn3);
        grid.add(hbBtn3, 1, 3);
        
        Button adminBtn4 = new Button("Add Book Copy");
        HBox hbBtn4 = new HBox(10);
        hbBtn4.setAlignment(Pos.BASELINE_CENTER);
        hbBtn4.getChildren().add(adminBtn4);
        grid.add(hbBtn4, 1, 4);
        
        Button backBtn = new Button("Back To Login");
        HBox hbBtnBack = new HBox(10);
        hbBtnBack.setAlignment(Pos.BASELINE_CENTER);
        hbBtnBack.getChildren().add(backBtn);
        grid.add(hbBtnBack , 1, 5);
        
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		Start.hideAllWindows();
        		if(!LoginWindow.INSTANCE.isInitialized()) {
        			LoginWindow.INSTANCE.init();
         	    }
        		LoginWindow.INSTANCE.clear();
        		LoginWindow.INSTANCE.show();	
        	}
        });
        
        // Add Book Implementation
        adminBtn1.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		Start.hideAllWindows();
        		if(!AddBookWindow.INSTANCE.isInitialized()) {
        			AddBookWindow.INSTANCE.init();
         	    }
        		AddBookWindow.INSTANCE.clear();
        		AddBookWindow.INSTANCE.show();
        	}
        });
        
        // Add Member Button Implementation
        adminBtn2.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		Start.hideAllWindows();
        		if(!AddMemberWindow.INSTANCE.isInitialized()) {
        			AddMemberWindow.INSTANCE.init();
         	    }
        		AddMemberWindow.INSTANCE.clear();
        		AddMemberWindow.INSTANCE.show();
        	}
        });
        
        // Edit Member Implementation
        adminBtn3.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		Start.hideAllWindows();
        		if(!ListMemberWindow.INSTANCE.isInitialized()) {
        			ListMemberWindow.INSTANCE.init();
         	    }
        		ListMemberWindow.INSTANCE.clear();
        		ListMemberWindow.INSTANCE.show();
        	}
        });
        
        
        adminBtn4.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		Start.hideAllWindows();
        		if(!AddBookCopyWindow.INSTANCE.isInitialized()) {
        			AddBookCopyWindow.INSTANCE.init();
         	    }
        		AddBookCopyWindow.INSTANCE.clear();
        		AddBookCopyWindow.INSTANCE.show();
        	}
        });
        

        Scene scene = new Scene(grid, 620, 575);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        setScene(scene);
        
    }



}
