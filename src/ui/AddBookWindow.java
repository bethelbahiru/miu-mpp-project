package ui;

import java.util.Optional;

import business.Address;
import business.Book;
import business.ControllerInterface;
import business.CustomException;
import business.LibraryMember;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class AddBookWindow extends Stage implements LibWindow {
	
	public static final AddBookWindow INSTANCE = new AddBookWindow();
	
	
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
    private AddBookWindow() {}
    
    public void init() { 
        GridPane grid = new GridPane();
        grid.setId("top-container");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        this.setTitle("Add Book");
        
        Label bookISBN = new Label("ISBN: ");
        grid.add(bookISBN, 0, 1);
        
        TextField bookISBNTextField = new TextField();
        bookISBNTextField.setPromptText("ISBN");
        grid.add(bookISBNTextField, 1, 1,7,1);
        
        Label title = new Label("Title: ");
        grid.add(title, 0, 2);

        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        grid.add(titleField, 1, 2,7,1);
        
        Label lName = new Label("Checkout Days: ");
        grid.add(lName, 0, 3);
       
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().add("7");
        comboBox.getItems().add("21");
        comboBox.setValue("7");
        HBox hbox = new HBox(comboBox);
        grid.add(hbox, 1, 3);
        
        Label author = new Label("Add Author: ");
        grid.add(author, 0, 4);
        
        Button addAuthor = new Button("Add Author Information");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BASELINE_LEFT);
        hbBtn.getChildren().add(addAuthor);
        grid.add(hbBtn, 1, 4);
        
        HBox messageBox = new HBox(10);
        messageBox.setAlignment(Pos.BOTTOM_RIGHT);
        messageBox.getChildren().add(messageBar);;
        grid.add(messageBox, 1, 5);
        
        Button addBook = new Button("Add Book");
        HBox bookHbBtn = new HBox(10);
        bookHbBtn.setAlignment(Pos.BASELINE_RIGHT);
        bookHbBtn.getChildren().add(addBook);
        grid.add(bookHbBtn, 1, 6,7,1);
        
        
        TableView tableView = new TableView();
    	tableView.setEditable(true);

        TableColumn<Book, String> bookISBNCol = new TableColumn<>("ISBN");
        bookISBNCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        TableColumn<Book, String> checkoutCol = new TableColumn<>("Max CheckOut");
        checkoutCol.setCellValueFactory(new PropertyValueFactory<>("maxCheckoutLength"));
        
        
        tableView.getColumns().add(bookISBNCol);
        tableView.getColumns().add(titleCol);
        tableView.getColumns().add(checkoutCol);
        
        VBox vbox = new VBox(tableView);
        vbox.setAlignment(Pos.CENTER);
        grid.add(vbox, 0, 7, 8, 2);
        

        addAuthor.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		
        	}
     
        });
        
        Scene scene = new Scene(grid, 420, 375);
        scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        setScene(scene);
        
    }
}

