package controller;

import handler.MedicationHandler;
import handler.UserHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import model.Medication;
import model.User;
import reporting.Report;
import reporting.ReportFactory;

public class AdminLayoutController {

	// user view
	@FXML
	private TableView<User> usersTable;
	@FXML
	private TableColumn<User, String> usernameColumn;
	@FXML
	private TableColumn<User, String> passwordColumn;

	@FXML
	private Label userIdLabel;
	@FXML
	private Label usernameLabel;
	@FXML
	private Label passwordLabel;
	@FXML
	private Label typeLabel;

	// user edit
	@FXML
	private Label userIdEditLabel;
	@FXML
	private Label typeEditLabel;
	@FXML
	private TextField usernameEditTextField;
	@FXML
	private TextField passwordEditTextField;

	// user add
	@FXML
	private TextField usernameCreateTextField;
	@FXML
	private TextField passwordCreateTextField;

	// medication view
	@FXML
	private TableView<Medication> medicationsTable;
	@FXML
	private TableColumn<Medication, String> medicationNameColumn;
	@FXML
	private TableColumn<Medication, String> medicationManufacturerColumn;

	@FXML
	private Label medicationIdLabel;
	@FXML
	private Label medicationNameLabel;
	@FXML
	private Label ingredientsLabel;
	@FXML
	private Label manufacturerLabel;
	@FXML
	private Label quantityLabel;
	@FXML
	private Label priceLabel;

	// medication edit
	@FXML
	private Label medicationIdEditLabel;
	@FXML
	private TextField medicationNameEditTextField;
	@FXML
	private TextField ingredientsEditTextField;
	@FXML
	private TextField manufacturerEditTextField;
	@FXML
	private TextField quantityEditTextField;
	@FXML
	private TextField priceEditTextField;

	// medication add
	@FXML
	private TextField medicationNameCreateTextField;
	@FXML
	private TextField ingredientsCreateTextField;
	@FXML
	private TextField manufacturerCreateTextField;
	@FXML
	private TextField quantityCreateTextField;
	@FXML
	private TextField priceCreateTextField;

	// reporting
	@FXML
	private ComboBox<String> fileFormatComboBox;

	@FXML
	private void initialize() {

		// user
		usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
		passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());

		usernameCreateTextField.setText("");
		passwordCreateTextField.setText("");

		showUserInformation(null);

		usersTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showUserInformation(newValue));

		// medication
		medicationNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		medicationManufacturerColumn.setCellValueFactory(cellData -> cellData.getValue().manufacturerProperty());

		showMedicationInformation(null);

		medicationsTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showMedicationInformation(newValue));

		// reporting
		ObservableList<String> formatsData = FXCollections.observableArrayList();
		formatsData.add("PDF");
		formatsData.add("CSV");
		fileFormatComboBox.setItems(formatsData);
		fileFormatComboBox.setValue(formatsData.get(0));
	}

	// user
	private void showUserInformation(User user) {
		if (user != null) {
			userIdLabel.setText(Long.toString(user.getUserId()));
			usernameLabel.setText(user.getUsername());
			passwordLabel.setText(user.getPassword());
			typeLabel.setText(user.getType());

			userIdEditLabel.setText(Long.toString(user.getUserId()));
			usernameEditTextField.setText(user.getUsername());
			passwordEditTextField.setText(user.getPassword());
			typeEditLabel.setText(user.getType());

		} else {
			userIdLabel.setText("");
			usernameLabel.setText("");
			passwordLabel.setText("");
			typeLabel.setText("");

			userIdEditLabel.setText("");
			usernameEditTextField.setText("");
			passwordEditTextField.setText("");
			typeEditLabel.setText("");
		}
	}

	@FXML
	public void loadUsers() {
		try {
			usersTable.setItems(UserHandler.getUsersList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleEditUser() {
		try {
			if (!userIdEditLabel.getText().isEmpty() && !usernameEditTextField.getText().isEmpty()
					&& !passwordEditTextField.getText().isEmpty() && !typeEditLabel.getText().isEmpty()) {
				UserHandler.edit(new User(Long.valueOf(userIdEditLabel.getText()), usernameEditTextField.getText(),
						passwordEditTextField.getText(), typeEditLabel.getText()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		loadUsers();
	}

	@FXML
	public void handleAddUser() {
		boolean ok = false;
		try {
			if (!usernameCreateTextField.getText().isEmpty() && !passwordCreateTextField.getText().isEmpty()) {
				ok = UserHandler.add(new User(0, usernameCreateTextField.getText(), passwordCreateTextField.getText(),
						"regularUser"));
				if (ok) {
					usernameCreateTextField.setText("");
					passwordCreateTextField.setText("");
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Add user error");
					alert.setHeaderText("Bad Input");
					alert.setContentText("Username already exists.");
					alert.showAndWait();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		loadUsers();
	}

	@FXML
	public void handleDeleteUser() {
		if (!userIdLabel.getText().isEmpty() && !usernameLabel.getText().isEmpty() && !passwordLabel.getText().isEmpty()
				&& !typeLabel.getText().isEmpty()) {
			UserHandler.delete(new User(Long.valueOf(userIdLabel.getText()), usernameLabel.getText(),
					passwordLabel.getText(), typeLabel.getText()));
			loadUsers();
		}
	}

	// medication
	private void showMedicationInformation(Medication medication) {
		if (medication != null) {
			medicationIdLabel.setText(Long.toString(medication.getMedicationId()));
			medicationNameLabel.setText(medication.getName());
			ingredientsLabel.setText(medication.getIngredients());
			manufacturerLabel.setText(medication.getManufacturer());
			quantityLabel.setText(Integer.toString(medication.getQuantity()));
			priceLabel.setText(Integer.toString(medication.getPrice()));

			medicationIdEditLabel.setText(Long.toString(medication.getMedicationId()));
			medicationNameEditTextField.setText(medication.getName());
			ingredientsEditTextField.setText(medication.getIngredients());
			manufacturerEditTextField.setText(medication.getManufacturer());
			quantityEditTextField.setText(Integer.toString(medication.getQuantity()));
			priceEditTextField.setText(Integer.toString(medication.getPrice()));

		} else {
			medicationIdLabel.setText("");
			medicationNameLabel.setText("");
			ingredientsLabel.setText("");
			manufacturerLabel.setText("");
			quantityLabel.setText("");
			priceLabel.setText("");

			medicationIdEditLabel.setText("");
			medicationNameEditTextField.setText("");
			ingredientsEditTextField.setText("");
			manufacturerEditTextField.setText("");
			quantityEditTextField.setText("");
			priceEditTextField.setText("");
		}
	}

	@FXML
	public void loadMedications() {
		try {
			medicationsTable.setItems(MedicationHandler.getMedicationsList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleEditMedication() {
		try {
			if (!medicationIdEditLabel.getText().isEmpty() && !medicationNameEditTextField.getText().isEmpty()
					&& !ingredientsEditTextField.getText().isEmpty() && !manufacturerEditTextField.getText().isEmpty()
					&& !quantityEditTextField.getText().isEmpty() && !priceEditTextField.getText().isEmpty()
					&& Integer.valueOf(quantityEditTextField.getText()).intValue() > 0
					&& Integer.valueOf(priceEditTextField.getText()).intValue() > 0) {
				MedicationHandler.edit(new Medication(Long.valueOf(medicationIdEditLabel.getText()),
						medicationNameEditTextField.getText(), ingredientsEditTextField.getText(),
						manufacturerEditTextField.getText(), Integer.valueOf(quantityEditTextField.getText()),
						Integer.valueOf(priceEditTextField.getText())));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		loadMedications();
	}

	@FXML
	public void handleAddMedication() {
		boolean ok = false;
		if (Integer.valueOf(quantityCreateTextField.getText()).intValue() < 1
				|| Integer.valueOf(priceCreateTextField.getText()).intValue() < 1) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Add medication error");
			alert.setHeaderText("Bad Input");
			alert.setContentText("Quantity and price should be greater or equal to 1.");
			alert.showAndWait();
		} else {
			try {
				if (!medicationNameCreateTextField.getText().isEmpty()
						&& !ingredientsCreateTextField.getText().isEmpty()
						&& !manufacturerCreateTextField.getText().isEmpty()
						&& !quantityCreateTextField.getText().isEmpty() && !priceCreateTextField.getText().isEmpty()
						&& Integer.valueOf(quantityCreateTextField.getText()).intValue() > 0
						&& Integer.valueOf(priceCreateTextField.getText()).intValue() > 0) {
					ok = MedicationHandler.add(new Medication(0, medicationNameCreateTextField.getText(),
							ingredientsCreateTextField.getText(), manufacturerCreateTextField.getText(),
							Integer.valueOf(quantityCreateTextField.getText()),
							Integer.valueOf(priceCreateTextField.getText())));
					if (ok) {
						medicationNameCreateTextField.setText("");
						ingredientsCreateTextField.setText("");
						manufacturerCreateTextField.setText("");
						quantityCreateTextField.setText("");
						priceCreateTextField.setText("");
						loadMedications();
					} else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Add medication error");
						alert.setHeaderText("Bad Input");
						alert.setContentText("Medication name already exists.");
						alert.showAndWait();
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	public void handleDeleteMedication() {
		if (!medicationIdLabel.getText().isEmpty() && !medicationNameLabel.getText().isEmpty()
				&& !ingredientsLabel.getText().isEmpty() && !manufacturerLabel.getText().isEmpty()
				&& !quantityLabel.getText().isEmpty() && !priceLabel.getText().isEmpty()) {
			MedicationHandler.delete(new Medication(Long.valueOf(medicationIdLabel.getText()),
					medicationNameLabel.getText(), ingredientsLabel.getText(), manufacturerLabel.getText(),
					Integer.valueOf(quantityLabel.getText()), Integer.valueOf(priceLabel.getText())));

			loadMedications();
		}
	}

	// reporting
	@FXML
	public void handleGenerateButton() {
		ReportFactory reportFactory = new ReportFactory();
		Report report = reportFactory.getReport(fileFormatComboBox.getValue());
		report.generateReport();
	}
}
