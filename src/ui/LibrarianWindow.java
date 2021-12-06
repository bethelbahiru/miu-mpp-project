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

public class LibrarianWindow extends Stage implements LibWindow {
	
	public static final LibrarianWindow INSTANCE = new LibrarianWindow();
	
	
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
    private LibrarianWindow() {}
    
    public void init() { 
        GridPane grid = new GridPane();
        grid.setId("top-container");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        this.setTitle("Librarian");
        
        Text scenetitle = new Text("Librarian Dashboard");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, 0, 2, 1);

        Button librarianBtn = new Button("Checkout Book");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BASELINE_CENTER);
        hbBtn.getChildren().add(librarianBtn);
        grid.add(hbBtn, 1, 1);
        
        Button librarianBtn2 = new Button("Checkout Information");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BASELINE_CENTER);
        hbBtn2.getChildren().add(librarianBtn2);
        grid.add(hbBtn2, 1, 2);
        
        Button librarianBtn3 = new Button("Over Due");
        HBox hbBtn3 = new HBox(10);
        hbBtn3.setAlignment(Pos.BASELINE_CENTER);
        hbBtn3.getChildren().add(librarianBtn3);
        grid.add(hbBtn3, 1, 3);
        
        Button backBtn = new Button("Back To Login");
        HBox hbBtnBack = new HBox(10);
        hbBtnBack.setAlignment(Pos.BASELINE_CENTER);
        hbBtnBack.getChildren().add(backBtn);
        grid.add(hbBtnBack , 1, 4);
        
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
        
        librarianBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		Start.hideAllWindows();
        		if(!CheckOutWindow.INSTANCE.isInitialized()) {
        			CheckOutWindow.INSTANCE.init();
         	    }
        		CheckOutWindow.INSTANCE.clear();
        		CheckOutWindow.INSTANCE.show();	
        	}
        });
        
        librarianBtn2.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		Start.hideAllWindows();
        		if(!CheckOutInfoWindow.INSTANCE.isInitialized()) {
        			CheckOutInfoWindow.INSTANCE.init();
         	    }
        		CheckOutInfoWindow.INSTANCE.clear();
        		CheckOutInfoWindow.INSTANCE.show();	
        	}
        });
        
        librarianBtn3.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		Start.hideAllWindows();
        		if(!OverDueWindow.INSTANCE.isInitialized()) {
        			OverDueWindow.INSTANCE.init();
         	    }
        		OverDueWindow.INSTANCE.clear();
        		OverDueWindow.INSTANCE.show();	
        	}
        });
        Scene scene = new Scene(grid, 620, 575);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        setScene(scene);
        
    }



}
