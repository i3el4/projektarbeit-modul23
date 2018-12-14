package middleearth.map.view;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import middleearth.map.MainApp;
import middleearth.map.model.MapEntry;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;




public class MapOverviewController {

	//	@FXML
	//	private TableView<Suggestion> vorschlagTable;
	//	@FXML
	//	private TableColumn<Suggestion, Number> nummerColumn;
	//	@FXML
	//	private TableColumn<Suggestion, String> vorschlagColumn;
	//
	//	@FXML
	//	private Label nummerLabel;
	//	@FXML
	//	private Label entscheidungLabel;
	//
	//	@FXML
	//	private TextField vorschlagField;
	//
	//	@FXML
	//	private Button entscheidungKnopf;


	// Reference to the main application.
	private MainApp mainApp;

	private ImageView mapImage = new ImageView();
	private int gestureCount;
	private ObservableList<String> events = FXCollections.observableArrayList();


	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public MapOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
	}
	
	public void setImageOnZoom(ImageView image) {
		// make the scroll zoomeable
				image.setOnZoom(new EventHandler<ZoomEvent>() {
					
					@Override
					public void handle(ZoomEvent event) {
						image.setScaleX(image.getScaleX() * event.getZoomFactor());
						image.setScaleY(image.getScaleY() * event.getZoomFactor());
						log("Rectangle: Zoom event" +
								", inertia: " + event.isInertia() + 
								", direct: " + event.isDirect());

						event.consume();
					}
				});

				image.setOnZoomStarted(new EventHandler<ZoomEvent>() {
					
					@Override
					public void handle(ZoomEvent event) {
						inc(image);
						log("Map: Zoom event started");
						event.consume();
					}
				});

				image.setOnZoomFinished(new EventHandler<ZoomEvent>() {
					
					@Override
					public void handle(ZoomEvent event) {
						dec(image);
						log("Map: Zoom event finished");
						event.consume();
					}
				});
	}

	/**
	 * Creates a log that shows the events.
	 */
	private ListView<String> createLog(ObservableList<String> messages){

		final ListView<String> log = new ListView<String>();
		log.setPrefSize(500, 200);
		log.setItems(messages);

		return log;

	}

	/**
	 * Uses lighting to visually change the object for the duration of 
	 * the gesture.
	 * 
	 * @param shape Target of the gesture
	 */    
	private void inc(ImageView image) {

		if (gestureCount == 0) {
			image.setEffect(new Lighting());
		}
		gestureCount++;

	}

	/**
	 * Restores the object to its original state when the gesture completes.
	 * 
	 * @param shape Target of the gesture
	 */    
	private void dec(ImageView image) {

		gestureCount--;
		if (gestureCount == 0) {
			image.setEffect(null);
		}

	}   

	/**
	 * Adds a message to the log.
	 * 
	 * @param message Message to be logged
	 */
	private void log(String message) {

		// Limit log to 50 entries, delete from bottom and add to top
		if (events.size() == 50) {
			events.remove(49);
		}
		events.add(0, message);

	}


	/**
	 * Adds a new suggestion to the suggestion-list through a click on the button
	 * 
	 * @param MapEntry
	 */

	public void handleHinzufuegenKnopf() {

		//		// Wurde ein neuer Vorschlag im Textfeld für neue Vorschläge eingegeben?
		//		if(!textfeldLeer()) {
		//			// Erzeuge String mit neuem Vorschlag aus dem Textfeld
		//			String neuerVorschlag = vorschlagField.getText().toString();
		//			// Erzeuge Nummer für neuen Vorschlag aus dem Textfeld
		//			int index = mainApp.getSuggestionData().size() + 1;
		//
		//			if(istNeu(neuerVorschlag)) {
		//				// Füge Vorschlag der Liste hinzu und entferne den Vorschlag aus dem Textfeld
		//				mainApp.getSuggestionData().add(new Suggestion(index, neuerVorschlag));
		//				System.out.println("Der Vorschlag: '" + neuerVorschlag + "' wurde der Liste hinzugefügt.");
		//				vorschlagField.clear();
		//			} else {
		//				// Gebe Fehlermeldung aus und gebe false zurück
		//				Alert alert = new Alert(AlertType.ERROR);
		//				alert.setTitle("Vorschlag besteht bereits");
		//				alert.setHeaderText("Duplikation des Vorschlags");
		//				alert.setContentText("Der Vorschlag ist in der Liste bereits enthalten\n");
		//
		//				alert.showAndWait();
		//			}
		//		}
	}

	/**
	 * Kontrolliert ob Textfeld leer ist
	 * 
	 * @return  true wenn Textfeld leer ist, false wenn es gefüllt ist
	 */
	public boolean textfeldLeer() {

		//		// Wenn Textfeld leer ist:
		//		if (vorschlagField.getText() == null || vorschlagField.getText().length() == 0) {
		//			// Gebe Fehlermeldung aus und gebe true zurück
		//			Alert alert = new Alert(AlertType.ERROR);
		//			alert.setTitle("Invalid Fields");
		//			alert.setHeaderText("Achtung!");
		//			alert.setContentText("Gib erst einen Vorschlag ein!\n");
		//
		//			alert.showAndWait();
		//
		//			return true;
		//
		//		} else {
		return false;
		//		}
	}

	/**
	 * Kontrolliert ob Vorschlag bereits in der Liste besteht
	 * 
	 * @param neuerVorschlag  neuer Vorschlag aus dem Textfeld
	 * @return unikat true wenn kein Duplikat vorhanden, false wenn Duplikat vorhanden
	 */
	public boolean istNeu(String neuerVorschlag) {

		boolean	unikat = true;
		int i = 0;
		String alterVorschlag;

		// Iteriere über vorhandene Vorschläge in der Liste
		while( 	unikat && (i < mainApp.getSuggestionData().size())) {

			alterVorschlag = mainApp.getSuggestionData().get(i).getEntscheidung();
			i = i + 1;
			unikat = !alterVorschlag.toLowerCase().equals(neuerVorschlag.toLowerCase());
		}
		return unikat;
	}

	@FXML
	public void handleEnterPressed(KeyEvent event) {

		if (event.getCode() == KeyCode.ENTER) {
			handleHinzufuegenKnopf();
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected
	 */
	@FXML
	private void handleBearbeiteVorschlag() {
		//		Suggestion ausgewaehlterVorschlag = vorschlagTable.getSelectionModel().getSelectedItem();
		//		if (ausgewaehlterVorschlag != null) {
		//			boolean bearbeitenGeklickt = mainApp.showSuggestionEditDialog(ausgewaehlterVorschlag);
		//
		//		} else {
		//			// Nothing selected.
		//			Alert alert = new Alert(AlertType.WARNING);
		//			alert.initOwner(mainApp.getPrimaryStage());
		//			alert.setTitle("Keine Auswahl getroffen");
		//			alert.setHeaderText("Es wurde kein Vorschlag ausgewählt");
		//			alert.setContentText("Bitte wählen Sie erst einen Vorschlag zur Bearbeitung aus der Liste aus.");
		//
		//			alert.showAndWait();
		//		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteVorschlag() {
		//
		//		int selectedIndex = vorschlagTable.getSelectionModel().getSelectedIndex();
		//
		//		if (selectedIndex >= 0) {
		//			vorschlagTable.getItems().remove(selectedIndex);
		//		} else {
		//			// Nothing selected.
		//			Alert alert = new Alert(AlertType.WARNING);
		//			alert.initOwner(mainApp.getPrimaryStage());
		//			alert.setTitle("Keine Auswahl");
		//			alert.setHeaderText("Kein bestehender Vorschlag ist ausgewählt");
		//			alert.setContentText("Bitte wähle einen Vorschlag aus, denn du aus der Liste löschen möchtest.");
		//
		//			alert.showAndWait();
		//		}
	}


	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		//		// Add observable list data to the table
		//		vorschlagTable.setItems(mainApp.getSuggestionData());
	}
}