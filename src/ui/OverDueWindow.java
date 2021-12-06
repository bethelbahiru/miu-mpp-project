package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import business.*;
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

public class OverDueWindow extends Stage implements LibWindow {

	public static final OverDueWindow INSTANCE = new OverDueWindow();

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
	private OverDueWindow() {
	}

	public void init() {
		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		this.setTitle("OverDue Information");

		Label bookId = new Label("Book ISBN:");
		grid.add(bookId, 0, 1);

		TextField bookIdTextField = new TextField();
		bookIdTextField.setPromptText("Book ISBN");
		grid.add(bookIdTextField, 1, 1);

		Button searchBtn = new Button("Search");
		Button closeBtn = new Button("Close");

		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().addAll(searchBtn, closeBtn);
		grid.add(hbBtn, 2, 1);

		TableView tableView = new TableView();

		TableColumn<Book, String> bookISBNCol = new TableColumn<>("ISBN");
		bookISBNCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

		TableColumn<Book, String> titleCol = new TableColumn<>("Title");
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

		TableColumn<Book, String> memberCol = new TableColumn<>("Member Id");
		memberCol.setCellValueFactory(new PropertyValueFactory<>("memberId"));

		TableColumn<Book, String> copiesCol = new TableColumn<>("Copy Number");
		copiesCol.setCellValueFactory(new PropertyValueFactory<>("copyNumber"));

		TableColumn<Book, String> dueDateCol = new TableColumn<>("Due Date");
		dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

		TableColumn<Book, String> overDueCol = new TableColumn<>("OverDue");
		overDueCol.setCellValueFactory(new PropertyValueFactory<>("isOverDue"));

		TableColumn<Book, String> isAvailableCol = new TableColumn<>("Available");
		isAvailableCol.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));

		ControllerInterface c = new SystemController();

		tableView.getColumns().add(bookISBNCol);
		tableView.getColumns().add(titleCol);
		tableView.getColumns().add(memberCol);
		tableView.getColumns().add(copiesCol);
		tableView.getColumns().add(dueDateCol);
		tableView.getColumns().add(overDueCol);
		tableView.getColumns().add(isAvailableCol);

		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Book b = c.getBookById(bookIdTextField.getText().trim());

				if (b != null && !bookIdTextField.getText().equals("")) {
					List<OverDueLayer> availableBooks = c.getOverDue(bookIdTextField.getText().trim());
					tableView.setItems(FXCollections.observableArrayList(availableBooks));
				} else {
					messageBar.setFill(Start.Colors.red);
					messageBar.setText("Error! " + "Incorrect Value of Book ISBN.");
				}

			}
		});

		closeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (SystemController.currentAuth == Auth.LIBRARIAN) {
					if (!LibrarianWindow.INSTANCE.isInitialized()) {
						LibrarianWindow.INSTANCE.init();
					}
					LibrarianWindow.INSTANCE.clear();
					LibrarianWindow.INSTANCE.show();
				} else if (SystemController.currentAuth == Auth.BOTH) {
					if (!BothWindow.INSTANCE.isInitialized()) {
						BothWindow.INSTANCE.init();
					}
					BothWindow.INSTANCE.clear();
					BothWindow.INSTANCE.show();
				}

			}
		});
		
		HBox messageBox = new HBox(10);
		messageBox.setAlignment(Pos.BOTTOM_RIGHT);
		messageBox.getChildren().add(messageBar);
		grid.add(messageBox, 1, 3);

		VBox vbox = new VBox(tableView);
		vbox.setAlignment(Pos.CENTER);
		grid.add(vbox, 0, 7, 16, 2);

		Scene scene = new Scene(grid, 620, 575);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		setScene(scene);

	}

}
