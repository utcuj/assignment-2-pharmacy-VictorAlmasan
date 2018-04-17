package controller;

import handler.MedicationHandler;
import handler.SaleHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.Medication;
import model.Sale;

public class UserLayoutController {

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

	// medication search
	@FXML
	private TextField nameSearchTextField;
	@FXML
	private TextField ingredientsSearchTextField;
	@FXML
	private TextField manufacturerSearchTextField;

	// sell
	@FXML
	private Label medicationNameSellLabel;
	@FXML
	private Label manufacturerSellLabel;
	@FXML
	private TextField quantitySellTextField;

	@FXML
	private void initialize() {

		medicationNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		medicationManufacturerColumn.setCellValueFactory(cellData -> cellData.getValue().manufacturerProperty());

		showMedicationInformation(null);

		nameSearchTextField.setText("");
		ingredientsSearchTextField.setText("");
		manufacturerSearchTextField.setText("");

		medicationsTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showMedicationInformation(newValue));
	}

	private void showMedicationInformation(Medication medication) {
		if (medication != null) {
			medicationIdLabel.setText(Long.toString(medication.getMedicationId()));
			medicationNameLabel.setText(medication.getName());
			ingredientsLabel.setText(medication.getIngredients());
			manufacturerLabel.setText(medication.getManufacturer());
			quantityLabel.setText(Integer.toString(medication.getQuantity()));
			priceLabel.setText(Integer.toString(medication.getPrice()));

			medicationNameSellLabel.setText(medication.getName());
			manufacturerSellLabel.setText(medication.getManufacturer());
			quantitySellTextField.setDisable(false);
			quantitySellTextField.setText("0");
		} else {
			medicationIdLabel.setText("");
			medicationNameLabel.setText("");
			ingredientsLabel.setText("");
			manufacturerLabel.setText("");
			quantityLabel.setText("");
			priceLabel.setText("");

			medicationNameSellLabel.setText("");
			manufacturerSellLabel.setText("");
		}
	}

	@FXML
	public void loadMedications() {
		try {
			medicationsTable.setItems(MedicationHandler.getMedicationsList(nameSearchTextField.getText(),
					ingredientsSearchTextField.getText(), manufacturerSearchTextField.getText()));
			nameSearchTextField.setText("");
			ingredientsSearchTextField.setText("");
			manufacturerSearchTextField.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleSellMedication() {

		if (quantitySellTextField.getText().isEmpty()
				|| Integer.valueOf(quantitySellTextField.getText())
						.intValue() > (Integer.valueOf(quantityLabel.getText()).intValue())
				|| Integer.valueOf(quantitySellTextField.getText()).intValue() == 0
				|| Integer.valueOf(quantitySellTextField.getText()).intValue() < 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Sell Medication error");
			alert.setHeaderText("Bad Input");
			alert.setContentText("Not enough quantity, 0 quantity or less than 0 quantity.");
			alert.showAndWait();
		} else {
			if (!medicationNameSellLabel.getText().isEmpty() && !manufacturerSellLabel.getText().isEmpty()) {
				int totalPrice = Integer.valueOf(quantitySellTextField.getText()).intValue()
						* Integer.valueOf(priceLabel.getText()).intValue();
				boolean ok = SaleHandler.add(new Sale(0, medicationNameSellLabel.getText(),
						manufacturerSellLabel.getText(), Integer.valueOf(quantitySellTextField.getText()), totalPrice));
				if (ok) {
					int stoc = Integer.valueOf(quantityLabel.getText()).intValue()
							- Integer.valueOf(quantitySellTextField.getText()).intValue();
					MedicationHandler.edit(new Medication(Long.valueOf(medicationIdLabel.getText()),
							medicationNameLabel.getText(), ingredientsLabel.getText(), manufacturerLabel.getText(),
							stoc, Integer.valueOf(priceLabel.getText())));

					medicationNameSellLabel.setText("");
					manufacturerSellLabel.setText("");
					quantitySellTextField.setDisable(true);

					loadMedications();

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText("The medication has been sold.");
					alert.setContentText("Total price: " + totalPrice);
					alert.showAndWait();
				}
			}
		}
	}
}
