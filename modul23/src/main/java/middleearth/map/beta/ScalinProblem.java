package middleearth.map.beta;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ScalinProblem extends Application {

    private static final String IMAGE_URL = "file:resources/images/ihypkemxzapuu.jpg";

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Prepare the main part of the GUI.
        Image backgroundImage = new Image(IMAGE_URL);
        ImageView imageView = new ImageView(backgroundImage);
        Circle circle = new Circle(40, Color.ORANGE);
        circle.setStroke(Color.BLUE);
        circle.setStrokeWidth(5);
        Pane layer = new Pane(circle);
        StackPane stackPane = new StackPane(imageView, layer);
        ScrollPane scrollPane = new ScrollPane(new Group(stackPane));
        scrollPane.setPannable(true);

        // Prepare the controls on the left side.
        Slider xSlider = new Slider();
        Slider ySlider = new Slider();
        Slider radiusSlider = new Slider();
        Slider zoomSlider = new Slider();
        VBox vBox = new VBox(xSlider, ySlider, radiusSlider, zoomSlider);
        vBox.setSpacing(10);
        vBox.setPrefWidth(200);

        // Adjust the sliders.
        adjustSlider(xSlider, 0, 1000, 50);
        adjustSlider(ySlider, 0, 1000, 50);
        adjustSlider(radiusSlider, 0, 100, 15);
        adjustSlider(zoomSlider, 0, 3, 1);
        zoomSlider.setMajorTickUnit(1);
        zoomSlider.setMinorTickCount(1);
        zoomSlider.setShowTickMarks(true);
        zoomSlider.setShowTickLabels(true);

        // Do all necessary binding.
        circle.centerXProperty().bind(xSlider.valueProperty());
        circle.centerYProperty().bind(ySlider.valueProperty());
        circle.radiusProperty().bind(radiusSlider.valueProperty());
        stackPane.scaleXProperty().bind(zoomSlider.valueProperty());
        stackPane.scaleYProperty().bind(zoomSlider.valueProperty());
        /*
         * So far, I always needed to make the StackPane's size grow / shrink depending on the zoom level. If the
         * following two lines are commented out, zooming in will make the ImageView exceed the bounds of the StackPane.
         * As a result, the StackPane does not 'grow' appropriately so that you cannot pan the view to see the edges of
         * the background.
         */
//        stackPane.prefWidthProperty().bind(zoomSlider.valueProperty().multiply(backgroundImage.getWidth()));
//        stackPane.prefHeightProperty().bind(zoomSlider.valueProperty().multiply(backgroundImage.getHeight()));

        // Piece the GUI together.
        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setLeft(vBox);
        root.setPrefHeight(300);
        root.setPrefWidth(500);

        // Show the stage.
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    private void adjustSlider(Slider slider, double min, double max, double start) {
        slider.setMin(min);
        slider.setMax(max);
        slider.setValue(start);
    }

    public static void main(String[] args) {
        launch();
    }
}