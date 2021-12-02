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

public class AddMemberWindow extends Stage implements LibWindow {
	
	public static final AddMemberWindow INSTANCE = new AddMemberWindow();
	
	
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
    private AddMemberWindow() {}
    
    public void init() { 
        GridPane grid = new GridPane();
        grid.setId("top-container");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        this.setTitle("Add Member");
        
        Label memberId = new Label("Member ID:");
        grid.add(memberId, 0, 1);
        
        TextField memberIdTextField = new TextField();
        memberIdTextField.setPromptText("Member ID");
        grid.add(memberIdTextField, 1, 1);
        
        Label fName = new Label("First Name:");
        grid.add(fName, 0, 2);

        TextField fnTextField = new TextField();
        fnTextField.setPromptText("First Name");
        grid.add(fnTextField, 1, 2);
        
        Label lName = new Label("Last Name:");
        grid.add(lName, 0, 3);
        
        TextField lnTextField = new TextField();
        lnTextField.setPromptText("Last Name");
        grid.add(lnTextField, 1, 3);
        
        Label street = new Label("Street:");
        grid.add(street, 0, 4);

        TextField streetTextField = new TextField();
        streetTextField.setPromptText("Street");
        grid.add(streetTextField, 1, 4);
        
        
        Label city = new Label("City:");
        grid.add(city, 0, 5);
        
        TextField cityTextField = new TextField();
        cityTextField.setPromptText("City");
        grid.add(cityTextField, 1, 5);
        
        Label state = new Label("State:");
        grid.add(state, 0, 6);

        TextField stateTextField = new TextField();
        stateTextField.setPromptText("State");
        grid.add(stateTextField, 1, 6);
        
        Label zip = new Label("Zip:");
        grid.add(zip, 0, 7);
        
        TextField zipTextField = new TextField();
        zipTextField.setPromptText("Zip");
        grid.add(zipTextField, 1, 7);
        
        Label phone = new Label("Phone Number:");
        grid.add(phone, 0, 8);

        TextField phoneTextField = new TextField();
        phoneTextField.setPromptText("Phone Number");
        grid.add(phoneTextField, 1, 8);
        
        Button addMember = new Button("Add Member");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(addMember);
        grid.add(hbBtn, 1, 10);
        
        
        HBox messageBox = new HBox(10);
        messageBox.setAlignment(Pos.BOTTOM_RIGHT);
        messageBox.getChildren().add(messageBar);;
        grid.add(messageBox, 1, 9);
        

        addMember.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		System.out.println("Added.");
        		ControllerInterface c = new SystemController();
        		Address address = new Address(streetTextField.getText().trim(), 
        									 cityTextField.getText().trim(), 
        									 stateTextField.getText().trim(), 
        									 zipTextField.getText().trim());
        		
    			try {
					c.addMember(memberIdTextField.getText().trim(), 
							fnTextField.getText().trim(),
							lnTextField.getText().trim(),
							phoneTextField.getText().trim(),
							address);
					alert.setHeaderText(null);
					alert.setContentText("Add Member Success");
					
					Optional<ButtonType> result = alert.showAndWait();
					
					if (result.get() == ButtonType.OK) {
						Start.hideAllWindows();
						 if(!AdminWindow.INSTANCE.isInitialized()) {
		             	    	AdminWindow.INSTANCE.init();
		             	    }
		             	    AdminWindow.INSTANCE.clear();
		             	    AdminWindow.INSTANCE.show();
					}
				} catch (CustomException ex) {
					messageBar.setFill(Start.Colors.red);
        			messageBar.setText("Error! " + ex.getMessage());
        			
				}	     	   
        	}
        });
        
        Scene scene = new Scene(grid, 420, 375);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        setScene(scene);
        
    }



}
