package sample;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.math3.distribution.NormalDistribution;

public class Pruebas {
    double mediana = 0, media = 0, varianza = 0, suma = 0, aux;
    final static double confianza = .95;
    static double alfa = 1-confianza;

    public static void PruebaDeMedias(ObservableList<Pseudoaleatorios> numerosList) {
        int n = numerosList.size();
        NormalDistribution normal = new NormalDistribution();
        Stage ministage = new Stage();
        VBox panel = new VBox();
        Label limitInfLabel, mriLabel, limitSupLabel;
        Double mri = 0.0;
        Double limInf = (0.5) - (normal.inverseCumulativeProbability(1-alfa/2) * (1/Math.sqrt(12*n)));
        Double limSup = (0.5) + (normal.inverseCumulativeProbability(1-alfa/2) * (1/Math.sqrt(12*n)));
        for (int i = 0; i < n; i++) {
            mri = mri + numerosList.get(i).getRi();
        }
        mri = mri/n;
        mriLabel = new Label("Mri: " + mri);
        limitInfLabel = new Label("LIMri: " + limInf);
        limitSupLabel = new Label("LSMri: " + limSup);
        panel.getChildren().addAll(limitInfLabel, mriLabel, limitSupLabel);
        panel.setStyle("-fx-padding: 30px; -fx-alignment: center; -fx-spacing: 25px;");
        Scene escena = new Scene(panel, 400, 200);
        ministage.setTitle("Resultados de pruebas de Medias");
        ministage.setScene(escena);
        ministage.show();
    }

    public void datos(int totalDatos,double[] datos) {
        double muestra[] = new double[100];
        double ordenar[] = new double[100];
        int c1, c2, nm, cal, va;
        nm = totalDatos;
        for (c1 = 0; c1 < nm; c1++) {
            muestra[c1] = datos[c1];
            ordenar[c1] = muestra[c1];
            suma = suma + muestra[c1];
        }
        for (c1 = 0; c1 < nm; c1++) {
            for (c2 = 0; c2 < (nm - 1); c2++) {
                if (ordenar[c2] > ordenar[(c2 + 1)]) {
                    aux = ordenar[c2];
                    ordenar[c2] = ordenar[c2 + 1];
                    ordenar[c2 + 1] = aux;
                }
            }
        }
        cal = nm % 2;
        if (cal == 0) {
            va = nm / 2;
            mediana = (ordenar[va - 1] + ordenar[va]) / 2;
        }
        if (cal == 1) {
            va = nm / 2;
            mediana = ordenar[va];
        }
        media = suma / nm;
        suma = 0;
        for (c1 = 0; c1 < nm; c1++) {
            suma = suma + ((muestra[c1] - media) * (muestra[c1] - media));
        }
        varianza = suma / (nm - 1);
        System.out.println(varianza);
    }
}
