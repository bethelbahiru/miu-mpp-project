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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

public class EditMemberWindow extends Stage implements LibWindow{
	
	
	//TODO :- Validate Editable fields.
	
	public static final EditMemberWindow INSTANCE = new EditMemberWindow();
	
	
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
    private EditMemberWindow() {}
    
    public void init() { 
    	this.setTitle("Edit Member");
  
    	
    	TableView tableView = new TableView();
    	tableView.setEditable(true);

        TableColumn<LibraryMember, String> memberIdCol = new TableColumn<>("Member Id");
        memberIdCol.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        
        TableColumn<LibraryMember, String> fNameCol = new TableColumn<>("First Name");
        fNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        fNameCol.setCellFactory(TextFieldTableCell.<LibraryMember>forTableColumn());
        fNameCol.setOnEditCommit(
            new EventHandler<CellEditEvent<LibraryMember, String>>() {
                @Override
                public void handle(CellEditEvent<LibraryMember, String> t) {
                    ((LibraryMember) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setFirstName(t.getNewValue());
                    
                    
                    LibraryMember lb = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    
                    c.updateMember(lb);
                    List<LibraryMember> lbs = c.getAllMembers();
                    System.out.println(lbs.toString());
                    
                }
            }
        );
        
        TableColumn<LibraryMember, String> lNameCol = new TableColumn<>("Last Name");
        lNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lNameCol.setCellFactory(TextFieldTableCell.<LibraryMember>forTableColumn());
        lNameCol.setOnEditCommit(
            new EventHandler<CellEditEvent<LibraryMember, String>>() {
                @Override
                public void handle(CellEditEvent<LibraryMember, String> t) {
                    ((LibraryMember) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setLastName(t.getNewValue());
                    
                    
                    LibraryMember lb = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    
                    c.updateMember(lb);
                    List<LibraryMember> lbs = c.getAllMembers();
                    System.out.println(lbs.toString());
                    
                }
            }
        );


        TableColumn<LibraryMember, String> phoneNumCol = new TableColumn<>("Phone Number");
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        phoneNumCol.setCellFactory(TextFieldTableCell.<LibraryMember>forTableColumn());
        phoneNumCol.setOnEditCommit(
            new EventHandler<CellEditEvent<LibraryMember, String>>() {
                @Override
                public void handle(CellEditEvent<LibraryMember, String> t) {
                    ((LibraryMember) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setTelephone(t.getNewValue());
                    
                    
                    LibraryMember lb = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    System.out.println("after edit" + lb);
                    c.updateMember(lb);
                    List<LibraryMember> lbs = c.getAllMembers();
                    System.out.println(lbs.toString());
                    
                }
            }
        );
       
        TableColumn<LibraryMember, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        tableView.getColumns().addAll(memberIdCol, fNameCol, lNameCol, phoneNumCol, addressCol);
//        tableView.getColumns().add(fNameCol);
//        tableView.getColumns().add(lNameCol);
//        tableView.getColumns().add(phoneNumCol);
//        tableView.getColumns().add(addressCol);
        
  
        List<LibraryMember> lbs = c.getAllMembers();
        
        
//        System.out.println(lbs.toString());
        tableView.getItems().addAll(lbs);
        
        TableViewSelectionModel<LibraryMember> selectionModel = tableView.getSelectionModel();
        
        ObservableList<LibraryMember> selectedItems = selectionModel.getSelectedItems();
//        selectedItems.get(0);
        
        
        final Button delButton = new Button("Delete Member");
        final Button closeEditButton = new Button("Close Edit");
        delButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

            	ControllerInterface c = new SystemController();
            	c.removeMember(selectedItems.get(0));
                
     
            	
            	tableView.getColumns().clear();
            	tableView.getColumns().addAll(memberIdCol, fNameCol, lNameCol, phoneNumCol, addressCol);
            	
            	List<LibraryMember> lbs = c.getAllMembers();
            	System.out.println(lbs.toString());
                tableView.getItems().clear();
                tableView.getItems().addAll(lbs);
                
                 
//                selectionModel.clearSelection();
//                tableView.refresh();
                
               
            }
        });
        
        closeEditButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	Start.hideAllWindows();
         	    if(!AdminWindow.INSTANCE.isInitialized()) {
         	    	AdminWindow.INSTANCE.init();
         	    }
         	    AdminWindow.INSTANCE.clear();
         	    AdminWindow.INSTANCE.show();
				
            }
        });
        
      
        
        HBox hDel = new HBox(10);
        hDel.setAlignment(Pos.BOTTOM_RIGHT);
        hDel.getChildren().add(delButton);
        
        HBox closeEdit = new HBox(10);
        closeEdit.setAlignment(Pos.BOTTOM_RIGHT);
        closeEdit.getChildren().add(closeEditButton);
     
        VBox vbox = new VBox();
        vbox.getChildren().addAll(tableView, hDel, closeEdit);

        Scene scene = new Scene(vbox,420, 375);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());

        setScene(scene); 
        
    }



}

