package middleearth.map.view;


import java.util.Random;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import middleearth.map.model.MapEntry;


public class MapIcon {
	
	final Button entscheidungsKnopf = new Button();
	@FXML
    private TableView<MapEntry> vorschlagTable;
	
	public MapIcon() {
		}	
	
	public Button getEntscheidungsKnopf() {
		return entscheidungsKnopf;
	}
	
	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteVorschlag() {
	    int selectedIndex = vorschlagTable.getSelectionModel().getSelectedIndex();
	    vorschlagTable.getItems().remove(selectedIndex);
	}
	
	/**
	 * Called when the user clicks on the decision button.
	 */
	@FXML
	public void handleAendereKnopfFarbe() {
		
		entscheidungsKnopf.addEventHandler(	MouseEvent.MOUSE_CLICKED, 
											new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
				Random rand = new Random();
				double r = rand.nextDouble();
				double g = rand.nextDouble();
				double b = rand.nextDouble();
				
				Color randomColor = new Color(r, g, b, 0.4);
				System.out.println("The Color is: " + randomColor.toString());
				
				
				if ( event.getSource() instanceof Button ) {
					Button button = (Button) event.getSource();
					button.setBorder(new Border(new BorderStroke(Color.DARKGRAY, 
				            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
					button.setBackground(new Background (new BackgroundFill(randomColor, null, null)));
					}
				}
			});
		}
	
}