package pl.polsl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;
import pl.polsl.jktab.model.Tab;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Tab tab;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("tab"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("TAB app");
        stage.getIcons().add(new Image("https://i.pinimg.com/736x/ba/92/7f/ba927ff34cd961ce2c184d47e8ead9f6.jpg"));
        stage.show();
    }

//    static void setRoot(String fxml) throws IOException {
//        scene.setRoot(loadFXML(fxml));
//    }
    

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setControllerFactory( t -> { return new TabController(tab); });
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        tab = new Tab();
        launch();
    }

}