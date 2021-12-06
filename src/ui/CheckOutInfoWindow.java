package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.Address;
import business.Book;
import business.BookCopy;
import business.BookCopyLayer;
import business.CheckoutRecord;
import business.ControllerInterface;
import business.CustomException;
import business.LibraryMember;
import business.SystemController;
import dataaccess.Auth;
import javafx.beans.property.SimpleStringProperty;
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

public class CheckOutInfoWindow extends Stage implements LibWindow {

	public static final CheckOutInfoWindow INSTANCE = new CheckOutInfoWindow();

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
	private CheckOutInfoWindow() {
	}

	public void init() {
		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		this.setTitle("Checkout Record");

		Label memberId = new Label("Member ID:");
		grid.add(memberId, 0, 1);

		TextField memberIdTextField = new TextField();
		memberIdTextField.setPromptText("Member ID");
		grid.add(memberIdTextField, 1, 1);

		Button searchBtn = new Button("Search");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(searchBtn);
		grid.add(hbBtn, 2, 1);
		
		Button printBtn = new Button("Print To Console");
		HBox printHbBtn = new HBox(10);
		printHbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		printHbBtn.getChildren().add(printBtn);
		grid.add(printHbBtn, 2, 2);

		Button closeBookCopy = new Button("Close");
		HBox closeHbBtn = new HBox(10);
		closeHbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		closeHbBtn.getChildren().add(closeBookCopy);
		grid.add(closeHbBtn, 2, 3);

		TableView tableView = new TableView();

		TableColumn<CheckoutRecord, String> bookISBNCol = new TableColumn<>("ISBN");
		bookISBNCol.setCellValueFactory(
				isbn -> new SimpleStringProperty(isbn.getValue().getBookCopy().getBook().getIsbn()));

		TableColumn<CheckoutRecord, String> titleISBNCol = new TableColumn<>("Title");
		titleISBNCol.setCellValueFactory(
				title -> new SimpleStringProperty(title.getValue().getBookCopy().getBook().getTitle()));

		TableColumn<CheckoutRecord, String> checkOutISBNCol = new TableColumn<>("Checkout Date");
		checkOutISBNCol.setCellValueFactory(
				checkout -> new SimpleStringProperty(checkout.getValue().getCheckedOutDate().toString()));

		TableColumn<CheckoutRecord, String> dueDateBNCol = new TableColumn<>("Due Date");
		dueDateBNCol.setCellValueFactory(due -> new SimpleStringProperty(due.getValue().getDueDate().toString()));

		TableColumn<CheckoutRecord, String> bookCopyCol = new TableColumn<>("Copy Number");
		bookCopyCol.setCellValueFactory(
				copy -> new SimpleStringProperty(String.valueOf(copy.getValue().getBookCopy().getCopyNum())));

		tableView.getColumns().addAll(bookISBNCol, titleISBNCol, checkOutISBNCol, dueDateBNCol, bookCopyCol);

		List<CheckoutRecord> checkoutBooks = new ArrayList<CheckoutRecord>();
		tableView.setItems(FXCollections.observableArrayList(checkoutBooks));
		
		HBox messageBox = new HBox(10);
		messageBox.setAlignment(Pos.BOTTOM_RIGHT);
		messageBox.getChildren().add(messageBar);
		grid.add(messageBox, 1, 4);

		VBox vbox = new VBox(tableView);
		vbox.setAlignment(Pos.CENTER);
		grid.add(vbox, 0, 7, 10, 2);
		
		
		printBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// System.out.println("Hello");
				ControllerInterface c = new SystemController();
				LibraryMember lb = c.getMemberById(memberIdTextField.getText().trim());
				if (!memberIdTextField.getText().equals("") && lb != null) {
					for (int i = 0; i < 10; i++) {
						System.out.println("\n");
					}
					System.out.println("*******************Checkout Information**********************");
					lb.checkOutFormated();
				} else {
					messageBar.setFill(Start.Colors.red);
					messageBar.setText("Error! " + "Incorrect Value of Member ID.");
				}

			}
		});

		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// System.out.println("Hello");
				ControllerInterface c = new SystemController();
				LibraryMember lb = c.getMemberById(memberIdTextField.getText().trim());
				if (!memberIdTextField.getText().equals("") && lb != null) {
					createTable(lb, tableView);
				} else {
					messageBar.setFill(Start.Colors.red);
					messageBar.setText("Error! " + "Incorrect Value of Member ID.");
				}

			}
		});

		closeBookCopy.setOnAction(new EventHandler<ActionEvent>() {
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

		Scene scene = new Scene(grid, 620, 575);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		setScene(scene);

	}

	private void createTable(LibraryMember lb, TableView tableView) {
		// TODO Auto-generated method stub
		tableView.setItems(FXCollections.observableArrayList(lb.getCheckouts()));

	}

}
