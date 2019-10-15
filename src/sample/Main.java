package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Scene escena;
    private BorderPane panel;
    private Button generarBtn;
    private Label title;
    private TextField quantityInputText;
    private VBox uiVbox;
    @Override
    public void start(Stage primaryStage) {
        panel = new BorderPane();
        title = new Label("Método congruencial aditivo");
        quantityInputText = new TextField();
        quantityInputText.setPromptText("Introduce la cantidad de números a generar");
        generarBtn = new Button("Generar");
        uiVbox = new VBox();
        uiVbox.getChildren().addAll(title,quantityInputText,generarBtn);
        panel.setCenter(uiVbox);
        escena = new Scene(panel, 300, 275);
        primaryStage.setTitle("Generador números pseudoaleatorios");
        primaryStage.setScene(escena);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
