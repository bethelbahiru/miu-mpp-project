package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.Address;
import business.Book;
import business.BookCopyLayer;
import business.ControllerInterface;
import business.CustomException;
import business.LibraryMember;
import business.SystemController;
import dataaccess.Auth;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CheckOutWindow extends Stage implements LibWindow {
	
	public static final CheckOutWindow INSTANCE = new CheckOutWindow();
	
	
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
    private CheckOutWindow() {}
    
    public void init() { 
        GridPane grid = new GridPane();
        grid.setId("top-container");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        this.setTitle("Checkout");
        
        Label memberId = new Label("Member ID:");
        grid.add(memberId, 0, 1);
        
        TextField memberIdTextField = new TextField();
        memberIdTextField.setPromptText("Member ID");
        grid.add(memberIdTextField, 1, 1);
       
        
        Button checkout = new Button("Checkout");
        Button close = new Button("Close");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(checkout, close);
        grid.add(hbBtn, 2, 1);
        
        TableView tableView = new TableView();
        TableViewSelectionModel<BookCopyLayer> selectionModel = tableView.getSelectionModel();
    	tableView.setEditable(true);
    	selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
    	ObservableList<BookCopyLayer> selectedItems = selectionModel.getSelectedItems();
    	

        TableColumn<Book, String> bookISBNCol = new TableColumn<>("ISBN");
        bookISBNCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        TableColumn<Book, String> copiesCol = new TableColumn<>("Available Copies");
        copiesCol.setCellValueFactory(new PropertyValueFactory<>("numOfCopies"));
        
 
        tableView.getColumns().add(bookISBNCol);
        tableView.getColumns().add(titleCol);
        tableView.getColumns().add(copiesCol);
        
        ControllerInterface c = new SystemController();
        createTable(tableView);
        
        HBox messageBox = new HBox(10);
		messageBox.setAlignment(Pos.BOTTOM_RIGHT);
		messageBox.getChildren().add(messageBar);
		grid.add(messageBox, 1, 2);
        
        VBox vbox = new VBox(tableView);
        vbox.setAlignment(Pos.CENTER);
        grid.add(vbox, 0, 7, 8, 2);
        

        checkout.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		
        		List<String> bookISBNS = new ArrayList<String>();
        		selectedItems.forEach(item -> bookISBNS.add(item.getIsbn()));
        		LibraryMember lb = c.getMemberById(memberIdTextField.getText().trim());	
        		
        		if (!memberIdTextField.getText().equals("") && bookISBNS.size() != 0 && lb != null) {
        			c.checkoutBook(bookISBNS , memberIdTextField.getText().trim());
        		} else {
    				messageBar.setFill(Start.Colors.red);
    				messageBar.setText("Error! " + "Incorrect Value of Member ID or Book Not Selected.");
    			}

        		createTable(tableView);
        		     	   
        	}
        });
        
        close.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (SystemController.currentAuth == Auth.LIBRARIAN) {
					if(!LibrarianWindow.INSTANCE.isInitialized()) {
						LibrarianWindow.INSTANCE.init();
					}
					LibrarianWindow.INSTANCE.clear();
					LibrarianWindow.INSTANCE.show();	
				} else if (SystemController.currentAuth == Auth.BOTH){
					if(!BothWindow.INSTANCE.isInitialized()) {
             	    	BothWindow.INSTANCE.init();
             	    }
             	    BothWindow.INSTANCE.clear();
             	    BothWindow.INSTANCE.show();	
				}
				   
			}
		});

        
        Scene scene = new Scene(grid, 620, 575);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        setScene(scene);
        
    }
	private void createTable(TableView tableView) {
		// TODO Auto-generated method stub
		ControllerInterface c = new SystemController();

        List<Book> books = c.getAllBooks();  
        List<BookCopyLayer> availableBooks = new ArrayList<BookCopyLayer>();
        
        for(Book bk: books) { 
        	
        	if(bk.isAvailable2()) {
        		availableBooks.add(new BookCopyLayer(bk.getIsbn(), bk.getTitle(), bk.isAvailableCount()));
        	}
   	
        }
        tableView.setItems(FXCollections.observableArrayList(availableBooks));
		
	}



}

