package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Vistas.Histograma;

public class MainForm {

    private Scene escena;
    private BorderPane panel;
    private Button generarBtn;
    private Label title, semillaLbl, cantidadLbl;
    private TextField quantityInputText, semillaInputText;
    private VBox uiVbox;

    public MainForm(Stage primaryStage) {
        panel = new BorderPane();
        title = new Label("Método congruencial aditivo");
        title.setId("main-header-label");
        semillaLbl = new Label("Semilla Xi:");
        semillaLbl.getStyleClass().add("my-form-label");
        cantidadLbl = new Label("Cantidad deseada:");
        cantidadLbl.getStyleClass().add("my-form-label");
        semillaInputText = new TextField();
        quantityInputText = new TextField();
        semillaInputText.setStyle("-fx-max-width: 400px");
        quantityInputText.setStyle("-fx-max-width: 400px");
        semillaInputText.setPromptText("Introduce el valor de la semilla (debe ser impar) 'Xi'");
        quantityInputText.setPromptText("Introduce la cantidad de numeros pseudo-aleatorios deseados");
        generarBtn = new Button("Generar");
        generarBtn.getStyleClass().add("danger");
        generarBtn.setOnAction(event -> ValidarDatos(primaryStage));
        uiVbox = new VBox();
        uiVbox.getChildren().addAll(title, semillaLbl, semillaInputText, cantidadLbl, quantityInputText, generarBtn);
        uiVbox.setSpacing(10);
        uiVbox.setStyle("-fx-padding: 10px");
        panel.setCenter(uiVbox);
        escena = new Scene(panel, 450, 250);
        escena.getStylesheets().add(getClass().getResource("CSS/bs3.css").toExternalForm());
        primaryStage.setTitle("Generador números pseudoaleatorios");
        primaryStage.setScene(escena);
        primaryStage.show();
    }

    private void ValidarDatos(Stage stage) {
        String cantidadTxt = quantityInputText.getText();
        String semillaTxt = semillaInputText.getText();
        try {
            int semilla = Integer.parseInt(semillaTxt);
            int cantidad = Integer.parseInt(cantidadTxt);
            if(semilla % 2 != 0) {
                new Histograma(stage, cantidad, semilla);
            }
        }catch (Exception e) {
            System.out.println("Error" + e);
        }
    }


}
