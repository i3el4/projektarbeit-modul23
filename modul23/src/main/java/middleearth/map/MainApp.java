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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
	private ObservableList<MapEntry> mapData = FXCollections.observableArrayList();
	private MapEntriesWrapper wrapper;

	/**
	 * Constructor
	 */
	public MainApp() {

		// Add some sample data
		mapData.add(new MapEntry(1, "Rollenspielen"));
		mapData.add(new MapEntry(2, "Schlafen"));

	}

	/**
	 * Returns the data as an observable list of suggestions. 
	 * @return
	 */
	public ObservableList<MapEntry> getMapData() {
		return mapData;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Karte von Mittelerde");

		// Set the application icon.
		this.primaryStage.getIcons().add(new Image("file:resources/images/TolkienIcon.jpg"));

		initRootLayout();

		// show the scene
		showMapOverview();
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
		File file = getMapDataFilePath();
		if (file != null) {
			loadMapDataFromFile(file);
		}
	}


	/**
	 * Shows the decision button overview inside the root layout.
	 */
	public void showMapOverview() {
		try {
			// Load suggestion overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MapOverview.fxml"));
			AnchorPane mapOverview = (AnchorPane) loader.load();

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
			rootLayout.setCenter(mapOverview);

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
	public void loadMapDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(MapEntriesWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			MapEntriesWrapper wrapper = (MapEntriesWrapper) um.unmarshal(file);


			mapData.clear();
			mapData.addAll(wrapper.getMapEntries());

			// Save the file path to the registry.
			setMapDataFilePath(file);

		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath());

			alert.showAndWait();
		}
	}

	public void wrapMapData() {
		// Wrapping our suggestion data.
		wrapper = new MapEntriesWrapper();
		wrapper.setMapEntries(mapData);
	}


	/**
	 * Saves the current suggestion data to the specified file.
	 * 
	 * @param file
	 */
	public void saveMapDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(MapEntriesWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our suggestion data.
			MapEntriesWrapper wrapper = new MapEntriesWrapper();
			wrapper.setMapEntries(mapData);

			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);

			// Save the file path to the registry.
			setMapDataFilePath(file);




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
	 * @param mapEntry the suggestion object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showMapEntryEditDialog(MapEntry mapEntry) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MapEntryEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			// Set the application icon.
			dialogStage.getIcons().add(new Image("file:resources/images/TolkienIcon.jpg"));
			dialogStage.setTitle("Karten Eintrag bearbeiten");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the suggestion into the controller.
			MapEntryEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMapEntry(mapEntry);

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
	public File getMapDataFilePath() {
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
	public void setMapDataFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());

			// Update the stage title.
			primaryStage.setTitle("Mittelerde Karte - " + file.getName());
		} else {
			prefs.remove("filePath");

			// Update the stage title.
			primaryStage.setTitle("Mittelerde Karte");
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