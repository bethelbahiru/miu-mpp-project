package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.Address;
import business.Author;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddAuthorWindow extends Stage implements LibWindow {

	public static final AddAuthorWindow INSTANCE = new AddAuthorWindow();
	public static List<Author> authors = new ArrayList<Author>();
	Alert alert = new Alert(AlertType.INFORMATION);

	private boolean isInitialized = false;
	private Text messageBar = new Text();

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	public void clear() {
		messageBar.setText("");
	}

	/* This class is a singleton */
	private AddAuthorWindow() {
	}

	public void init() {
		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		this.setTitle("Add Author");

//        Label firstName = new Label("First Name");
//        grid.add(firstName, 0, 1);
//        
//        TextField firstNameTextField = new TextField();
//        firstNameTextField.setPromptText("First Name");
//        grid.add(firstNameTextField, 1, 1);

		Label fName = new Label("First Name:");
		grid.add(fName, 0, 1);

		TextField fnTextField = new TextField();
		fnTextField.setPromptText("First Name");
		grid.add(fnTextField, 1, 1);

		Label lName = new Label("Last Name:");
		grid.add(lName, 0, 2);

		TextField lnTextField = new TextField();
		lnTextField.setPromptText("Last Name");
		grid.add(lnTextField, 1, 2);

		Label street = new Label("Street:");
		grid.add(street, 0, 3);

		TextField streetTextField = new TextField();
		streetTextField.setPromptText("Street");
		grid.add(streetTextField, 1, 3);

		Label city = new Label("City:");
		grid.add(city, 0, 4);

		TextField cityTextField = new TextField();
		cityTextField.setPromptText("City");
		grid.add(cityTextField, 1, 4);

		Label state = new Label("State:");
		grid.add(state, 0, 5);

		TextField stateTextField = new TextField();
		stateTextField.setPromptText("State");
		grid.add(stateTextField, 1, 5);

		Label zip = new Label("Zip:");
		grid.add(zip, 0, 6);

		TextField zipTextField = new TextField();
		zipTextField.setPromptText("Zip");
		grid.add(zipTextField, 1, 6);

		Label phone = new Label("Phone Number:");
		grid.add(phone, 0, 7);

		TextField phoneTextField = new TextField();
		phoneTextField.setPromptText("Phone Number");
		grid.add(phoneTextField, 1, 7);

		Label bio = new Label("Author Bio:");
		grid.add(bio, 0, 8);

		TextArea bioField = new TextArea();
		bioField.setPromptText("Author Bio");
		grid.add(bioField, 1, 8);

		Button addAuthor = new Button("Add Author");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(addAuthor);
		grid.add(hbBtn, 1, 10);

		HBox messageBox = new HBox(10);
		messageBox.setAlignment(Pos.BOTTOM_RIGHT);
		messageBox.getChildren().add(messageBar);
		grid.add(messageBox, 1, 9);

		addAuthor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Address address = new Address(streetTextField.getText().trim(), cityTextField.getText().trim(),
						stateTextField.getText().trim(), zipTextField.getText().trim());
				Author author = new Author(fnTextField.getText().trim(), lnTextField.getText().trim(),
						phoneTextField.getText().trim(), address, bioField.getText().trim());

				if (streetTextField.getText().equals("") || 
						cityTextField.getText().equals("") ||
						stateTextField.getText().equals("") ||
						zipTextField.getText().equals("") || 
						fnTextField.getText().equals("") ||
						lnTextField.getText().equals("") ||
						phoneTextField.getText().equals("") ||
						bioField.getText().equals("")) {
					System.out.println("if loop reached");
					messageBar.setFill(Start.Colors.red);
        			messageBar.setText("Error! One or More Fields are empty.");
        			return;
				} else {
					authors.add(author);
					System.out.println(author.toString());
					alert.setHeaderText(null);
					alert.setContentText("Add Author Success");

					Optional<ButtonType> result = alert.showAndWait();

					if (result.get() == ButtonType.OK) {
						AddAuthorWindow.INSTANCE.hide();
						AddBookWindow.INSTANCE.show();
					}
				}	
			}
		});

		Scene scene = new Scene(grid, 620, 575);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		setScene(scene);

	}

}
