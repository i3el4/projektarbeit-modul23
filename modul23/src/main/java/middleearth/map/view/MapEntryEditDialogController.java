package middleearth.map.view;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a suggestion.
 * 
 * @author Bela Ackermann
 */
public class MapEntryEditDialogController {

	@FXML
	private TextField vorschlagField;

	@FXML 
	private Button abbrechenButton;
	@FXML 
	private Button speichernButton;

	private Stage dialogStage;
	private middleearth.map.model.MapEntry entscheidung;
	//private boolean speichernGeklickt = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	protected void initialize() {
	}


	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}


	/**
	 * Sets the suggestion to be edited in the dialog.
	 * 
	 * @param entscheidung
	 */
	public void setSuggestion(middleearth.map.model.MapEntry entscheidung) {
		this.entscheidung = entscheidung;

		vorschlagField.setText(entscheidung.getEntscheidung());
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (vorschlagField.getText() == null || vorschlagField.getText().length() == 0) {
			errorMessage += "Bitte geben sie einen Vorschlag ein bevor sie auf Speichern klicken.\n"; 
		}

		if (errorMessage.length() == 0) {
			return true;

		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Leeres Feld");
			alert.setHeaderText("Es darf kein leerer Vorschlag gespeicher werden.");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}


	@FXML
	private void handleSave() {

		if (isInputValid()) {
			entscheidung.setEntscheidung(vorschlagField.getText());

			dialogStage.close();
		}
	}


	@FXML
	private void handleCancle() {
		dialogStage.close();
	}

	@FXML
	private void handleEnter(KeyEvent event) {

		if (event.getCode() == KeyCode.ENTER && isInputValid()) {
			entscheidung.setEntscheidung(vorschlagField.getText());
			dialogStage.close();
		}
	}
}