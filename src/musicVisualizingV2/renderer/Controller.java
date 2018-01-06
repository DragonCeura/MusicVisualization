package musicVisualizingV2.renderer;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller {
    /**
     * Scene/stage settings
     */
    private final int sceneWidth = 800;
    private final int sceneHeight = 800;
    private Color sceneBackground = Color.BLACK;

    /**
     * "Voices" settings initialization
     */
    private final int spacing = 3;
    private final int maxRadius = 100;
    private int centerX, centerY;

    public void render(Stage primaryStage) {

        Group root = new Group();
        primaryStage.setTitle("Music Visualization");
        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight, sceneBackground));
        primaryStage.setResizable(false);

        Group voices = new Group();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                centerX = maxRadius + (j * ((2 * maxRadius) + spacing));
                centerY = maxRadius + (i * ((2 * maxRadius) + spacing));
                // System.out.println("X: " + centerX + ", Y: " + centerY);
                Circle voice = new Circle(centerX, centerY, maxRadius, sceneBackground);
                voices.getChildren().add(voice);
            }
        }
        root.getChildren().add(voices);

        primaryStage.show();

        voiceUpdate((Circle) voices.getChildren().get(0), Color.BLUE);
    }


    private void voiceUpdate(Circle voice, Color toColor) {
        Color fromColor = (Color) voice.getFill();
        FillTransition ft = new FillTransition(Duration.millis(1000), voice, fromColor, toColor);
        ft.setCycleCount(100);
        ft.setAutoReverse(true);
        ft.play();
    }

    private void experiment(Group root) {
        /**
         * Sample testing of JavaFX by creating circles to be rendered
         */
/*
        Group circles = new Group();
        for (int i = 0; i < 30; i++) {
            Circle circle = new Circle(150, Color.web("white", 0.05));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(4);
            circles.getChildren().add(circle);
        }
        root.getChildren().add(circles);

        Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
        new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new Stop[]{
        new Stop(   0, Color.web("#f8bd55")),
        new Stop(0.14, Color.web("#c0fe56")),
        new Stop(0.28, Color.web("#5dfbc1")),
        new Stop(0.43, Color.web("#64c2f8")),
        new Stop(0.57, Color.web("#be4af7")),
        new Stop(0.71, Color.web("#ed5fc2")),
        new Stop(0.85, Color.web("#ef504c")),
        new Stop(   1, Color.web("#f2660f")),}));
        colors.widthProperty().bind(scene.widthProperty());
        colors.heightProperty().bind(scene.heightProperty());
        Group blendModeGroup =
        new Group(new Group(new Rectangle(scene.getWidth(), scene.getHeight(),
        Color.BLACK), circles), colors);
        colors.setBlendMode(BlendMode.OVERLAY);
        root.getChildren().add(blendModeGroup);
        // circles.setEffect(new BoxBlur(10, 10, 3));
*/
/*
        Timeline timeline = new Timeline();
        Duration startTime = Duration.ZERO;
        Duration endTime = new Duration(40000);
        for (Node circle: circles.getChildren()) {
            timeline.getKeyFrames().addAll(
                    new KeyFrame(startTime, // set start position at 0
                            new KeyValue(circle.translateXProperty(), random() * sceneWidth),
                            new KeyValue(circle.translateYProperty(), random() * sceneHeight)
                    ),
                    new KeyFrame(endTime, // set end position at 40s
                            new KeyValue(circle.translateXProperty(), random() * sceneWidth),
                            new KeyValue(circle.translateYProperty(), random() * sceneHeight)
                    )
            );
        }
        // play 40s of animation
        timeline.play();
*/
/*
        Animation animation = new Transition() {
            @Override
            protected void interpolate(double frac) {
                Color vcolor = Color.hsb(240, 1, 1 - frac);
                ((Circle) voices.getChildren().get(0)).setFill(vcolor);
            }
        };
*/
    }
}
