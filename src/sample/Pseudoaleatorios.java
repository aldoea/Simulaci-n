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


    public ObservableList<Pseudoaleatorios> generar(int cantidad, int semilla) {
        ObservableList<Pseudoaleatorios> lista = FXCollections.observableArrayList();
        Pseudoaleatorios paObj = null;
        try{
            int g = 0;
            int bandera2 = cantidad;
            while(cantidad>0) {
                cantidad = cantidad / 2;
                g += 1;
            }
            g = g + 2;
            periodo = 0;
            int bandera = 0;
            int mod = (int) Math.pow(2,g);
            int n = (int) Math.pow(2,g-2);
            int a = 3;

            while(bandera != semilla && periodo<=bandera2 && periodo!=n ){
                paObj = new Pseudoaleatorios();
                if (periodo == 0) bandera = semilla;
                paObj.periodo = periodo;
                paObj.xi = "X"+periodo;
                semilla = (a * semilla) % mod;
                paObj.x = semilla;
                ri = semilla / (mod - 1.0);
                paObj.ri=ri;
                periodo += 1;
                lista.add(paObj);
            }
        }catch (Exception e) {
            System.err.println("An error happens " + e.toString());
            throw e;
        }
        return lista;
    }
}