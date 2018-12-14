package middleearth.map;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ScrollPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import middleearth.map.model.MapEntry;
import middleearth.map.model.MapEntriesWrapper;
import middleearth.map.view.MapOverviewController;
import middleearth.map.view.RootLayoutController;
import middleearth.map.view.MapEntryEditDialogController;


public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	/**
	 * The data as an observable list of suggestions.
	 */
	private ObservableList<MapEntry> suggestionData = FXCollections.observableArrayList();
	private MapEntriesWrapper wrapper;

	private int gestureCount;
	private ObservableList<String> events = FXCollections.observableArrayList();

	private ImageView backgroundImage = new ImageView();

	/**
	 * Constructor
	 */
	public MainApp() {

		// Add some sample data
		suggestionData.add(new MapEntry(1, "Rollenspielen"));
		suggestionData.add(new MapEntry(2, "Schlafen"));

	}

	/**
	 * Returns the data as an observable list of suggestions. 
	 * @return
	 */
	public ObservableList<MapEntry> getSuggestionData() {
		return suggestionData;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Karte von Mittelerde");

		// Set the application icon.
		this.primaryStage.getIcons().add(new Image("file:resources/images/TolkienIcon.jpg"));

		initRootLayout();

		// show the scene
		showEntscheidungOverview();
	}

	/**
	 * Initializes the root layout and tries to load the last opened
	 * suggestion file.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class
					.getResource("view/RootLayout.fxml"));

			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Try to load last opened suggestion file.
		File file = getSuggestionFilePath();
		if (file != null) {
			loadSuggestionDataFromFile(file);
		}
	}


	/**
	 * Shows the decision button overview inside the root layout.
	 */
	public void showEntscheidungOverview() {
		try {
			// Load suggestion overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MapOverview.fxml"));
			AnchorPane entscheidungOverview = (AnchorPane) loader.load();

			Map<String, Object> fxmlNamespace = loader.getNamespace();
			StackPane stack = (StackPane) fxmlNamespace.get("imageStack");
			ScrollPane scroll = (ScrollPane) fxmlNamespace.get("imageScroller");

			ImageView mapImage = new ImageView();
			mapImage.setImage(new Image("file:resources/images/ihypkemxzapuu.jpg"));

			stack.getChildren().setAll(mapImage);
			
			// bind the preferred size of the scroll area to the size of the scene.
			mapImage.fitWidthProperty().bind(scroll.widthProperty());
			mapImage.fitHeightProperty().bind(scroll.widthProperty());

			// center the scroll contents.
			scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
			scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);

			// Set Map Overview into the center of root layout.
			rootLayout.setCenter(entscheidungOverview);

			// Give the controller access to the main app.
			MapOverviewController controller = loader.getController();
			controller.setImageOnZoom(mapImage);
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads suggestion data from the specified file. The current suggestion data will
	 * be replaced.
	 * 
	 * @param file
	 */
	public void loadSuggestionDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(MapEntriesWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			MapEntriesWrapper wrapper = (MapEntriesWrapper) um.unmarshal(file);


			suggestionData.clear();
			suggestionData.addAll(wrapper.getVorschlaege());

			// Save the file path to the registry.
			setSuggestionFilePath(file);

		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath());

			alert.showAndWait();
		}
	}

	public void wrapSuggestionData() {
		// Wrapping our suggestion data.
		wrapper = new MapEntriesWrapper();
		wrapper.setVorschlaege(suggestionData);
	}


	/**
	 * Saves the current suggestion data to the specified file.
	 * 
	 * @param file
	 */
	public void saveSuggestionDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(MapEntriesWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our suggestion data.
			MapEntriesWrapper wrapper = new MapEntriesWrapper();
			wrapper.setVorschlaege(suggestionData);

			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);

			// Save the file path to the registry.
			setSuggestionFilePath(file);




		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());

			alert.showAndWait();
		}
	}

	/**
	 * Opens a dialog to edit details for the specified suggestion. If the user
	 * clicks OK, the changes are saved into the provided suggestion object and true
	 * is returned.
	 * 
	 * @param entscheidung the suggestion object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showSuggestionEditDialog(MapEntry entscheidung) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SuggestionEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			// Set the application icon.
			dialogStage.getIcons().add(new Image("file:resources/images/if_Spongebob_379417.png"));
			dialogStage.setTitle("Vorschlag bearbeiten");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the suggestion into the controller.
			MapEntryEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setSuggestion(entscheidung);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns the suggestion file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 * 
	 * @return
	 */
	public File getSuggestionFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		System.out.println(filePath);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 * 
	 * @param file the file or null to remove the path
	 */
	public void setSuggestionFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());

			// Update the stage title.
			primaryStage.setTitle("EntscheidungsknopfApp - " + file.getName());
		} else {
			prefs.remove("filePath");

			// Update the stage title.
			primaryStage.setTitle("EntscheidungsknopfApp");
		}
	}

	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}