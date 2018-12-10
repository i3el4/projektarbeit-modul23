package middleearth.map;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/** Constructs a scene with a pannable Map background. */
public class PannableView extends Application {

	private int gestureCount;
	private ObservableList<String> events = FXCollections.observableArrayList();

	private ImageView backgroundImage = new ImageView();

	@Override public void init() {

		backgroundImage.setImage(new Image("file:resources/images/ihypkemxzapuu.jpg"));


	}

	@Override
	public void start(Stage stage) {
		
		// construct the scene contents over a stacked background.
		StackPane layout = new StackPane();
		layout.getChildren().setAll(
				backgroundImage,
				createTestButton()
				);

		// wrap the scene contents in a pannable scroll pane.
		ScrollPane scroll = createScrollPane(layout);

		ListView<String> log = createLog(events);

		stage.setTitle("Karte von Mittelerde");
		
		// show the scene.
		Scene scene = new Scene(scroll, 1100, 900);
		stage.setScene(scene);
		stage.getIcons().add(new Image("file:resources/images/TolkienIcon.jpg"));
		stage.show();

		// bind the preferred size of the scroll area to the size of the scene.
		scroll.prefWidthProperty().bind(scene.widthProperty());
		scroll.prefHeightProperty().bind(scene.widthProperty());

		// center the scroll contents.
		scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
		scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);

		// make the scroll zoomeable
		backgroundImage.setOnZoom(new EventHandler<ZoomEvent>() {
			
			@Override
			public void handle(ZoomEvent event) {
				backgroundImage.setScaleX(backgroundImage.getScaleX() * event.getZoomFactor());
				backgroundImage.setScaleY(backgroundImage.getScaleY() * event.getZoomFactor());
				log("Rectangle: Zoom event" +
						", inertia: " + event.isInertia() + 
						", direct: " + event.isDirect());

				event.consume();
			}
		});

		backgroundImage.setOnZoomStarted(new EventHandler<ZoomEvent>() {
			
			@Override
			public void handle(ZoomEvent event) {
				inc(backgroundImage);
				log("Map: Zoom event started");
				event.consume();
			}
		});

		backgroundImage.setOnZoomFinished(new EventHandler<ZoomEvent>() {
			
			@Override
			public void handle(ZoomEvent event) {
				dec(backgroundImage);
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
	 *  @return a control to place on the scene. 
	 */
	private Button createTestButton() {
	
		final Button testButton = new Button("Besiege den bösen Hexenkönig");
		testButton.setStyle("-fx-base: firebrick;");
		testButton.setTranslateX(65);
		testButton.setTranslateY(-130);
		testButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent t) {
				testButton.setStyle("-fx-base: forestgreen;");
				testButton.setText("Und Zack! der Hexenkönig ist tot");
			}
		});
		return testButton;
	
	}

	/**
	 * @return a ScrollPane which scrolls the layout. 
	 */
	private ScrollPane createScrollPane(Pane layout) {
		
		ScrollPane scroll = new ScrollPane();
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setPannable(true);
		scroll.setPrefSize(800, 600);
		scroll.setContent(layout);
		return scroll;
	
	}

	public static void main(String[] args) {
		
		launch(args); 
	
	}  
}