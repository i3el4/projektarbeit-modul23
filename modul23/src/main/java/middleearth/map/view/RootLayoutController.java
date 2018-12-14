package middleearth.map.view;
import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 * @author codemakery.ch
 */
public class RootLayoutController {

    // Reference to the main application
    private middleearth.map.MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(middleearth.map.MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creates an empty list for suggestions.
     */
    @FXML
    private void handleNew() {
        mainApp.getSuggestionData().clear();
        mainApp.setSuggestionFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an suggestion list to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadSuggestionDataFromFile(file);
        }
    }

    /**
     * Saves the file to the suggestion file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File vorschlagFile = mainApp.getSuggestionFilePath();
        if (vorschlagFile != null) {
            mainApp.saveSuggestionDataToFile(vorschlagFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     * Version 1 = speichern als xml File
     * Version 2 = speichern als serial
     */
    @FXML
    private void handleSaveAs() {
    	
    	FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveSuggestionDataToFile(file);
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("EntscheidungsknopfApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Bela Ackermann, with the help of http://code.makery.ch");

        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}