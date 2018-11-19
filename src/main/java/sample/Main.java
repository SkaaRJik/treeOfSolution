package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DataFactory;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/sample.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));

        primaryStage.show();
        ((Controller) loader.getController()).init();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
