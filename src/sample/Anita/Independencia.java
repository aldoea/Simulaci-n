package sample.Anita;

import javafx.collections.ObservableList;
import sample.Pseudoaleatorios;

public class Independencia {
    double n2;
    int n;
    double[] Nsp = new double[n];
    String corr;
    String vn;
    DistNorm a = new DistNorm();

    int CO=1;
    public Independencia(ObservableList<Pseudoaleatorios> numberList) {
        n = numberList.size();
        n2 = n;
        indepen();
    }


    void indepen()
    {
        double mco,dco,dco2,zo,z0;
        mco = ((2*n2)-1)/3;
        mco = redondearDecimales(mco,2);
        dco=((16*n2)-29)/90;
        dco=redondearDecimales(dco,2);
        dco2=Math.sqrt(dco);
        dco2=redondearDecimales(dco2,2);
        zo=(CO-mco)/dco2;
        z0=Math.abs(zo);
        z0=redondearDecimales(z0,2);

        if(z0<=Double.valueOf(a.Vz))
        {
            System.out.println("Los datos son independientes");
        }
        else
        {
            System.out.println("Los datos no son independientes");
        }
    }

    public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
}