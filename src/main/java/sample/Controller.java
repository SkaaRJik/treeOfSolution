package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import utils.DataFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Controller {
    @FXML
    Accordion accordion;



    public void init() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("").getAbsoluteFile());
        fileChooser.setTitle("Open Document");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv", "*.csv"));
        File file = fileChooser.showOpenDialog(accordion.getScene().getWindow());
        if (file != null) {
            try {
                DataFactory.readUsingFileReader(file, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
