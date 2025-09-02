package xenon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The Main class serves as the entry point for the chatbot application.
 */
public class Main extends Application {

    private Xenon xenon = new Xenon("./data.txt");

    public static void main(String[] args) {
        Xenon chatbot = new Xenon("./data.txt");
    }

    @Override
    public void start(Stage stage) {
        try {
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setXenon(xenon);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
