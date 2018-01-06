package musicVisualizingV2;

import javafx.application.Application;
import javafx.stage.Stage;
import musicVisualizingV2.renderer.Controller;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;

public class MusicVisualizerV2 extends Application {

    private Controller controller = new Controller();

    @Override
    public void start(Stage primaryStage) throws Exception{
        controller.render(primaryStage);
    }

    public static void main(String[] args) {

/*
        try {
            MVMidiParser.parse();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        launch(args);
    }
}
