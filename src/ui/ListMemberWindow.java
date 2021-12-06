package ui;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import business.Address;
import business.ControllerInterface;
import business.CustomException;
import business.LibraryMember;
import business.SystemController;
import dataaccess.Auth;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ListMemberWindow extends Stage implements LibWindow{
	
	
	//TODO :- Validate Editable fields.
	
	public static final ListMemberWindow INSTANCE = new ListMemberWindow();
	
	
	private boolean isInitialized = false;
	private ControllerInterface c = new SystemController();
	
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
    private ListMemberWindow() {}
	
    public void init() { 
    	this.setTitle("Edit Member");
  
    	TableView tableView = new TableView();
    	tableView.setEditable(true);

        TableColumn<LibraryMember, String> memberIdCol = new TableColumn<>("Member Id");
        memberIdCol.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        
        TableColumn<LibraryMember, String> fNameCol = new TableColumn<>("First Name");
        fNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
     
        
        TableColumn<LibraryMember, String> lNameCol = new TableColumn<>("Last Name");
        lNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    

        TableColumn<LibraryMember, String> phoneNumCol = new TableColumn<>("Phone Number");
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
       
        TableColumn<LibraryMember, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        tableView.getColumns().addAll(memberIdCol, fNameCol, lNameCol, phoneNumCol, addressCol);
  
        List<LibraryMember> lbs = c.getAllMembers();
        
        tableView.getItems().addAll(lbs);
        
        TableViewSelectionModel<LibraryMember> selectionModel = tableView.getSelectionModel();
        
        ObservableList<LibraryMember> selectedItems = selectionModel.getSelectedItems();
        
        final Button delButton = new Button("Delete Member");
        delButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

            	ControllerInterface c = new SystemController();
            	
            	if (selectedItems.size() > 0){
            		c.removeMember(selectedItems.get(0));
                    
                	tableView.getColumns().clear();
                	tableView.getColumns().addAll(memberIdCol, fNameCol, lNameCol, phoneNumCol, addressCol);
                	
                	List<LibraryMember> lbs = c.getAllMembers();
                	System.out.println(lbs.toString());
                    tableView.getItems().clear();
                    tableView.getItems().addAll(lbs);
            	}
            }
        });
        
        final Button closeEditButton = new Button("Back");
        closeEditButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	Start.hideAllWindows();
            	if (SystemController.currentAuth == Auth.ADMIN) {
					 if(!AdminWindow.INSTANCE.isInitialized()) {
	             	    	AdminWindow.INSTANCE.init();
	             	    }
	             	    AdminWindow.INSTANCE.clear();
	             	    AdminWindow.INSTANCE.show();
				} else if (SystemController.currentAuth == Auth.BOTH){
					if(!BothWindow.INSTANCE.isInitialized()) {
            	    	BothWindow.INSTANCE.init();
            	    }
            	    BothWindow.INSTANCE.clear();
            	    BothWindow.INSTANCE.show();	
				}
				
            }
        });
        
        final Button editButton = new Button("Edit Member");
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	
            	System.out.println("EditPressed");
         	    TableViewSelectionModel<LibraryMember> selectionModel = tableView.getSelectionModel();
                
                ObservableList<LibraryMember> selectedItems = selectionModel.getSelectedItems();
                if (selectedItems.size() > 0) {
                	LibraryMember lm = selectedItems.get(0);
                    Address a = lm.getAddress();
                	
                    // Create the custom dialog.
                    Dialog<ButtonType> dialog = new Dialog();
                    messageBar.setText("");
                    DialogPane dialogPane = dialog.getDialogPane();
                    dialogPane.getStylesheets().add(
                    		   getClass().getResource("myDialog.css").toExternalForm());
                    		dialogPane.getStyleClass().add("myDialog");
                    dialog.setTitle("Edit Member");
                    dialog.setHeaderText("Edit info for member " + lm.getMemberId());
                    
                 // Create the fields.
                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.setPadding(new Insets(20, 200, 10, 10));
                   
                    TextField fnTextField = new TextField();
                    fnTextField.setPromptText("First Name");
                    fnTextField.setText(lm.getFirstName());
                    grid.add(fnTextField, 1, 1);

                    TextField lnTextField = new TextField();
                    lnTextField.setPromptText("Last Name");
                    lnTextField.setText(lm.getLastName());
                    grid.add(lnTextField, 1, 2);
                    
                    TextField streetTextField = new TextField();
                    streetTextField.setPromptText("Street");
                    streetTextField.setText(a.getStreet());
                    grid.add(streetTextField, 1, 3);
                    
                    TextField cityTextField = new TextField();
                    cityTextField.setPromptText("City");
                    cityTextField.setText(a.getCity());
                    grid.add(cityTextField, 1, 4);
                    
                    TextField stateTextField = new TextField();
                    stateTextField.setPromptText("State");
                    stateTextField.setText(a.getState());
                    grid.add(stateTextField, 1, 5);
                    
                    TextField zipTextField = new TextField();
                    zipTextField.setPromptText("Zip");
                    zipTextField.setText(a.getZip());
                    grid.add(zipTextField, 1, 6);
                    
                    TextField phoneTextField = new TextField();
                    phoneTextField.setPromptText("Phone Number");
                    phoneTextField.setText(lm.getTelephone());
                    grid.add(phoneTextField, 1, 7);
                    
                    
                    grid.add(new Label("First Name:"), 0, 1);
                    grid.add(new Label("Last Name:"), 0, 2);
                    grid.add(new Label("Street:"), 0, 3);
                    grid.add(new Label("City:"), 0, 4);
                    grid.add(new Label("State:"), 0, 5);
                    grid.add(new Label("Zip:"), 0, 6);
                    grid.add(new Label("Phone Number:"), 0, 7);
                    
                    HBox messageBox = new HBox(10);
                    messageBox.setAlignment(Pos.BOTTOM_LEFT);
                    messageBox.getChildren().add(messageBar);
                    
                    grid.add(messageBox, 1, 8);
                    
                    dialog.getDialogPane().setContent(grid);
                    
                    final Button updateButton = new Button("Update");
                    updateButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                        	
                        	ControllerInterface c = new SystemController();
                        	Address address = new Address(streetTextField.getText().trim(), 
              						 cityTextField.getText().trim(), 
              						 stateTextField.getText().trim(), 
              						 zipTextField.getText().trim());
                        	LibraryMember lmember = new LibraryMember(
                           			lm.getMemberId(), 
               						fnTextField.getText().trim(),
               						lnTextField.getText().trim(),
               						phoneTextField.getText().trim(),
               						address);
                    		
                        	System.out.println(lmember);
                        	
                			try {
            					c.updateMember(lmember);
            					List<LibraryMember> lbs = c.getAllMembers();
            	            	System.out.println(lbs.toString());
            	                tableView.getItems().clear();
            	                tableView.getItems().addAll(lbs);

                            	dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
                            	dialog.close();
            				} catch (CustomException ex) {
            					messageBar.setFill(Start.Colors.red);
                    			messageBar.setText("Error! " + ex.getMessage());
            				}
                        }
                    });
                    
                    final Button cancelButton = new Button("Cancel");
                    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                        	dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                        	dialog.close();
            				
                        }
                    });
                    
                    HBox buttonBox = new HBox(10);
                    buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
                    buttonBox.getChildren().addAll(cancelButton, updateButton);
                    buttonBox.setPadding(new Insets(25, 25, 5, 15));
                    grid.add(buttonBox, 1, 9);
                    
                    dialog.showAndWait();

                }

            }
        });
                  
        VBox vbox = new VBox();
        vbox.getChildren().addAll(tableView, editButton, delButton, closeEditButton);

        Scene scene = new Scene(vbox,620, 575);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());

        setScene(scene); 
        
    }



}

