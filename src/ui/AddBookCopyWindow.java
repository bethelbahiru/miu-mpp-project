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

public class AddBookCopyWindow extends Stage implements LibWindow {

	public static final AddBookCopyWindow INSTANCE = new AddBookCopyWindow();

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
	private AddBookCopyWindow() {
	}

	public void init() {
		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		this.setTitle("Add BookCopy");

		Label bookId = new Label("Book ISBN:");
		grid.add(bookId, 0, 1);

		TextField bookIdTextField = new TextField();
		bookIdTextField.setPromptText("Book ISBN");
		grid.add(bookIdTextField, 1, 1);

		Label bookCopyNum = new Label("Copy Number:");
		grid.add(bookCopyNum, 0, 2);

		TextField copyTextField = new TextField();
		copyTextField.setPromptText("Copy Number");
		grid.add(copyTextField, 1, 2);

		Button addBookCopy = new Button("Add Book Copy");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(addBookCopy);
		grid.add(hbBtn, 2, 1);

		Button closeBookCopy = new Button("Close");
		HBox closeHbBtn = new HBox(10);
		closeHbBtn.setAlignment(Pos.BOTTOM_LEFT);
		closeHbBtn.getChildren().add(closeBookCopy);
		grid.add(closeHbBtn, 2, 2);

		TableView tableView = new TableView();
		TableViewSelectionModel<Book> selectionModel = tableView.getSelectionModel();
		tableView.setEditable(true);
		selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
		ObservableList<Book> selectedItems = selectionModel.getSelectedItems();

		TableColumn<Book, String> bookISBNCol = new TableColumn<>("ISBN");
		bookISBNCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

		TableColumn<Book, String> titleCol = new TableColumn<>("Title");
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

		TableColumn<Book, String> copiesCol = new TableColumn<>("Number of Copies");
		copiesCol.setCellValueFactory(new PropertyValueFactory<>("numOfCopies"));

		tableView.getColumns().add(bookISBNCol);
		tableView.getColumns().add(titleCol);
		tableView.getColumns().add(copiesCol);

		ControllerInterface c = new SystemController();
		createTable(tableView);

		VBox vbox = new VBox(tableView);
		vbox.setAlignment(Pos.CENTER);
		
		HBox messageBox = new HBox(10);
		messageBox.setAlignment(Pos.BOTTOM_RIGHT);
		messageBox.getChildren().add(messageBar);
		grid.add(messageBox, 1, 3);
		grid.add(vbox, 0, 7, 8, 2);

		addBookCopy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				try {
					Book b = c.getBookById(bookIdTextField.getText().trim());

					if (!copyTextField.getText().equals("") && !bookIdTextField.getText().equals("") && b != null) {
						int copyNum = Integer.parseInt(copyTextField.getText().trim());
						c.addBookCopy(bookIdTextField.getText().trim(), copyNum);
					} else {
						messageBar.setFill(Start.Colors.red);
						messageBar.setText("Error! " + "Incorrect Value of ISBN or Book Copy.");
					}
					copyTextField.clear();
					bookIdTextField.clear();
				} catch (CustomException ex) {
					messageBar.setFill(Start.Colors.red);
					messageBar.setText("Error! " + ex.getMessage());
				}
				createTable(tableView);
			}
		});

		closeBookCopy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (SystemController.currentAuth == Auth.ADMIN) {
					if (!AdminWindow.INSTANCE.isInitialized()) {
						AdminWindow.INSTANCE.init();
					}
					AdminWindow.INSTANCE.clear();
					AdminWindow.INSTANCE.show();
				} else if (SystemController.currentAuth == Auth.BOTH) {
					if (!BothWindow.INSTANCE.isInitialized()) {
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

		for (Book bk : books) {
			BookCopyLayer copyLayer = new BookCopyLayer(bk.getIsbn(), bk.getTitle(), bk.getNumCopies());
			availableBooks.add(copyLayer);	
		}
		tableView.setItems(FXCollections.observableArrayList(availableBooks));

	}

}
