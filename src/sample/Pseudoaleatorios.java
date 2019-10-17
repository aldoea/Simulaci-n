package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Pseudoaleatorios {
    //  Periodo  Xi      X        Ri
    private  int periodo;
    private  String xi;
    private  int x;
    private  double ri;

    public int getPeriodo() {
        return periodo;
    }

    public String getXi() {
        return xi;
    }

    public int getX() {
        return x;
    }

    public double getRi() {
        return ri;
    }
    

    public ObservableList<Pseudoaleatorios> generar() {
        ObservableList<Pseudoaleatorios> lista = FXCollections.observableArrayList();
        Pseudoaleatorios paObj = null;
        try{
            while (true){
                paObj = new Pseudoaleatorios();
                paObj.periodo = 0;
                paObj.xi = "X0";
                paObj.x = 99;
                paObj.ri = 0.167;
                lista.add(paObj);
                break;
            }
        }catch (Exception e) {
            System.err.println("An error happens" + e.toString());
        }
        return lista;
    }
}