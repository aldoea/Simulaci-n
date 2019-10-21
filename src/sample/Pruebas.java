package sample;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import sample.Anita.Independencia;

import java.util.ArrayList;

public class Pruebas {
    final static double confianza = .95;
    static double alfa = 1-confianza;

    public static void PruebaDeMedias(ObservableList<Pseudoaleatorios> numerosList) {
        int n = numerosList.size();
        boolean conclusion;
        String conclusionTxt;
        NormalDistribution normal = new NormalDistribution();
        Stage ministage = new Stage();
        VBox panel = new VBox();
        Label limitInfLabel, mriLabel, limitSupLabel, conclusionLabel;
        Double mri = 0.0;
        Double limInf = (0.5) - (normal.inverseCumulativeProbability(1-alfa/2) * (1/Math.sqrt(12*n)));
        Double limSup = (0.5) + (normal.inverseCumulativeProbability(1-alfa/2) * (1/Math.sqrt(12*n)));
        for (int i = 0; i < n; i++) {
            mri = mri + numerosList.get(i).getRi();
        }
        mri = mri/n;
        conclusion = (limInf <= mri) && (mri <= limSup);
        mriLabel = new Label("Mri: " + mri);
        limitInfLabel = new Label("LIMri: " + limInf);
        limitSupLabel = new Label("LSMri: " + limSup);
        if(conclusion) {
            conclusionTxt = "Los números cumplen con la media";
            conclusionLabel = new Label("Conclusión: " + conclusionTxt);
            conclusionLabel.setTextFill(Color.web("#29ba30"));
        }else {
            conclusionTxt = "Los números no cumplen con la media";
            conclusionLabel = new Label("Conclusión: " + conclusionTxt);
            conclusionLabel.setTextFill(Color.web("#ff0808"));
        }
        panel.getChildren().addAll(limitInfLabel, mriLabel, limitSupLabel, conclusionLabel);
        panel.setStyle("-fx-padding: 30px; -fx-alignment: center; -fx-spacing: 25px;");
        Scene escena = new Scene(panel, 400, 200);
        ministage.setTitle("Resultados de pruebas de Medias");
        ministage.setScene(escena);
        ministage.show();
    }

    public static void PruebaDeVarianza(ObservableList<Pseudoaleatorios> numeroList){

        int n = numeroList.size();
        double[] regreso = datos(n,numeroList);
        ChiSquaredDistribution chi = new ChiSquaredDistribution((n-1));
        Double limInf = (chi.inverseCumulativeProbability((alfa/2))/(12*(n-1)));
        Double limSup = (chi.inverseCumulativeProbability((confianza/2))/(12*(n-1)));

        Stage ministage = new Stage();
        VBox panel = new VBox();
        Label limitInfLabel, mriLabel, limitSupLabel,resultado;
        mriLabel = new Label("σ^2ri: " + regreso[0]);
        limitInfLabel = new Label("LIMri: " + limInf);
        limitSupLabel = new Label("LSMri: " + limSup);

        if(limInf<regreso[0] && regreso[0]<limSup){
            resultado = new Label("los numeros pseudo siguen una varianza de: "+regreso[0]);
            resultado.setTextFill(Color.web("#29ba30"));
        }else{
            resultado = new Label("No cumplen con la varianza");
            resultado.setTextFill(Color.web("#ff0808"));
        }

        panel.getChildren().addAll(limitInfLabel, mriLabel, limitSupLabel,resultado);
        panel.setStyle("-fx-padding: 30px; -fx-alignment: center; -fx-spacing: 25px;");
        Scene escena = new Scene(panel, 400, 200);
        ministage.setTitle("Resultados de pruebas de Varianza");
        ministage.setScene(escena);
        ministage.show();
    }

    public static void pruebaDeIndependencia(ObservableList<Pseudoaleatorios> numeroList) {
        new Independencia(numeroList);
    }

    public static void myPruebaDeIndependencia(ObservableList<Pseudoaleatorios> numeroLsit) {
        //Creamos una lista para guardar los ceros y unos.
        ArrayList<Integer> bits = new ArrayList<>();
        int i, corridas, dato;
        double  media, varianza, z;
        //Revisa si cada dato actual es menor al dato anterior.
        //Si es así, se guarda un 0, de lo contrario, se guarda un 1
        for (i=1; i<numeroLsit.size(); i++){
            if (numeroLsit.get(i).getRi()<=numeroLsit.get(i-1).getRi()){
                bits.add(0);
            }
            else{
                bits.add(1);
            }
        }

        //Imprimimos la cadena de ceros y unos
        for (i=0; i<bits.size(); i++){
            System.out.print(bits.get(i));
        }

        //Contamos las corridas.
        corridas = 1;
        //Comenzamos observando el primer dígito
        dato= bits.get(0);
        //Comparamos cada dígito con el observado, cuando cambia es
        //una nueva corrida
        for (i=1; i<bits.size(); i++){
            if (bits.get(i) != dato){
                corridas++;
                dato = bits.get(i);
            }
        }
        System.out.println("Corridas " + corridas);

        //Aplicamos las fórmulas para media, varianza y Z.
        media = (2*numeroLsit.size()-1)/ (double)3;
        System.out.println("Media: " +media);
        varianza = (16*numeroLsit.size()-29)/(double) 90;
        System.out.println("Varianza: " + varianza);
        z= Math.abs((corridas-media)/Math.sqrt(varianza));
        System.out.println("Z=" + z);

        //Obtenemos el valor Z de la tabla de distribución normal
        NormalDistribution normal = new NormalDistribution();
        double  zn =  normal.inverseCumulativeProbability(1-alfa/2);
        //Comparamos: si es mayor mi valor Z al de la tabla, no pasa
        if (z < zn){
            System.out.println("No se rechaza que son independientes. " );
        }
        else{
            System.out.println("No Pasa la prueba de corridas");
        }

    }

    public static double[] datos(int totalDatos,ObservableList<Pseudoaleatorios> datos) {
        double regreso[] = new double[3];
        double muestra[] = new double[totalDatos];
        double ordenar[] = new double[totalDatos];
        int c1, c2, nm, cal, va;
        double mediana = 0, media = 0, varianza = 0, aux;
        double suma=0;
        nm = totalDatos;
        for (c1 = 0; c1 < nm; c1++) {
            muestra[c1] = datos.get(c1).getRi();
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

        regreso[0]=varianza;
        regreso[1]=media;
        regreso[2]=mediana;

        return regreso;
    }
}
