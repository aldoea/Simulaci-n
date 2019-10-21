package sample.Vistas;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.MainForm;
import sample.Pruebas;
import sample.Pseudoaleatorios;

import java.text.DecimalFormat;

public class Histograma {
    private final int cantidad;
    private final int semilla;
    private BorderPane histoParentContainer;
    private Stage histStage;
    private Scene mainScene;
    private Stage regresarStage;
    private ObservableList<Pseudoaleatorios> numerosList;
    private DecimalFormat formato1 = new DecimalFormat("#.0000");


    public Histograma(Stage stage, int cantidad, int semilla) {
        this.cantidad = cantidad;
        this.semilla = semilla;
        histStage = stage;
        regresarStage = stage;
        CrearUI();
    }

    public void CrearUI() {
        histoParentContainer = new BorderPane();
        histoParentContainer.setTop(crearHeader());
        histoParentContainer.setCenter(crearBody());
        histoParentContainer.setBottom(crearFooter());
        mainScene = new Scene(histoParentContainer);
        mainScene.getStylesheets().add(getClass().getResource("../CSS/bs3.css").toExternalForm());
        histStage.setScene(mainScene);
        histStage.setMaximized(true);
        histStage.show();
    }

    private HBox crearHeader() {
        HBox headerHbox = new HBox();
        headerHbox.setId("header-content");
        HBox regresarBox, tituloBox;
        // Boton regresar
        Button regresarBtn = new Button();
        regresarBtn.getStyleClass().add("btn-regresar");
        regresarBtn.setOnAction(event -> Regresar());
        regresarBox = new HBox();
        regresarBox.getChildren().addAll(regresarBtn);
        regresarBox.setId("back-button-box");
        // Titulo
        Label tituloMain = new Label("Datos generados e histograma");
        tituloMain.setId("main-header-label");
        tituloBox = new HBox();
        tituloBox.getChildren().add(tituloMain);
        tituloBox.setId("title-box");
        headerHbox.getChildren().addAll(regresarBox, tituloBox);
        return headerHbox;
    }

    private HBox crearBody() {
        HBox bodyHbox = new HBox();
        bodyHbox.setId("data-container");
        //bodyHbox.setStyle("-fx-max-width: 100%; -fx-pref-width: 100%;; -fx-pref-height: 100%; -fx-max-height: 100%");
        HBox tableBox = new HBox();
        HBox histBox = new HBox();
        tableBox.getChildren().add(crearTabla());
        tableBox.setId("table-data-box");
        histBox.getChildren().add(crearHistograma());
        histBox.setId("histo-box");
        bodyHbox.getChildren().addAll(tableBox, histBox);
        return bodyHbox;
    }

    private HBox crearHistograma() {
        HBox histogramaContainer = new HBox();
        histogramaContainer.setId("semana-chart-container");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis,yAxis);
        barChart.setCategoryGap(0);
        barChart.setBarGap(0);
        barChart.setTitle("Histograma");

        xAxis.setLabel("Range");
        yAxis.setLabel("Population");

        XYChart.Series series = new XYChart.Series();
        series.setName("Cantidad");

        int n = numerosList.size();
        ObservableList banderaList = numerosList;
        int m = (int) Math.sqrt(n);
        double intervalo = 1.0/m;
        double liminf = 0.0;
        double limsup = Double.parseDouble(formato1.format(intervalo));
        int [] contadores = new int[m];
        Range[] rangeGroups = new Range[m];

        for (int i = 0; i < m; i++) {
            //System.out.println(liminf + " " + limsup);
            rangeGroups[i] = new Range(liminf, limsup);
            liminf = Double.parseDouble(formato1.format(limsup));
            limsup = Double.parseDouble(formato1.format(limsup+intervalo));
        }
        // Iterate numbers
        for (int i = 0; i < n; i++) {
            boolean checksum = false;
            double ri = numerosList.get(i).getRi();
            // Explore ranges
            for (int j = 0; j < m; j++) {
                if(rangeGroups[j].contains(ri)) {
                    contadores[j]++;
                    checksum = true;
                    break;
                }
            }
            if(!checksum) {
                System.out.println("Ri: "+ ri + " i: " + i);
                System.exit(0);
            }
        }

        for(int i = 0; i < m; i++) {
            series.getData().add(new XYChart.Data(rangeGroups[i].toRangeString(), contadores[i]));
        }

        barChart.getData().addAll(series);
        histogramaContainer.getChildren().add(barChart);

        return histogramaContainer;
    }

    public class Range
    {
        private double low;
        private double high;

        public Range(double low, double high){
            this.low = low;
            this.high = high;
        }

        public boolean contains(double number){
            return (number >= low && number < high);
        }
        public void setHigh(double high){
            this.high = high;
        }

        public String toRangeString() {
            String rangeString = formato1.format(low) + "-" + formato1.format(high);
            return rangeString;
        }
    }

    private TableView crearTabla() {
        TableView numeros = new TableView();
        numeros.setStyle("-fx-pref-width: 500px");
        numerosList = new Pseudoaleatorios().generar(cantidad, semilla);
        numeros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Integer, Pseudoaleatorios> periodoCol = new TableColumn<>("Periodo");
        periodoCol.setCellValueFactory(new PropertyValueFactory<>("periodo"));

        TableColumn<String, Pseudoaleatorios> xiCol = new TableColumn<>("Xi");
        xiCol.setCellValueFactory(new PropertyValueFactory<>("xi"));

        TableColumn<Integer, Pseudoaleatorios> xCol = new TableColumn<>("X");
        xCol.setCellValueFactory(new PropertyValueFactory<>("x"));

        TableColumn<Double, Pseudoaleatorios> riCol = new TableColumn<>("Ri");
        riCol.setCellValueFactory(new PropertyValueFactory<>("ri"));


        numeros.getColumns().addAll(periodoCol, xiCol, xCol, riCol);
        numeros.setItems(numerosList);
        return numeros;
    }

    private HBox crearFooter() {
        HBox footerHBox = new HBox();
        footerHBox.setStyle("-fx-alignment: center; -fx-spacing: 20px; -fx-padding: 10px;");
        Button pruebaMedia, pruebaVarianza, pruebaUniformidad, pruebaIndependencia;
        pruebaMedia = new Button("Prueba de Medias");
        pruebaMedia.getStyleClass().add("info");
        pruebaVarianza = new Button("Prueba de Varianza");
        pruebaVarianza.setOnAction(event -> Pruebas.PruebaDeVarianza(numerosList));
        pruebaUniformidad = new Button("Prueba de Uniformidad");
        pruebaIndependencia = new Button("Prueba de independencia");

        pruebaMedia.setOnAction(event -> Pruebas.PruebaDeMedias(numerosList));

        pruebaMedia.getStyleClass().add("info");
        pruebaVarianza.getStyleClass().add("info");
        pruebaUniformidad.getStyleClass().add("info");
        pruebaIndependencia.getStyleClass().add("info");

        footerHBox.getChildren().addAll(pruebaMedia, pruebaVarianza, pruebaUniformidad, pruebaIndependencia);
        return footerHBox;
    }

    private void Regresar() {
        new MainForm(regresarStage);
    }
}


