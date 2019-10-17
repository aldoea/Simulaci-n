package sample;

public class Pruebas {
    public void datos(int totalDatos,float[] datos) {
        float muestra[] = new float[100];
        float ordenar[] = new float[100];
        float mediana = 0, media = 0, varianza = 0, suma = 0, aux;
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
    }
}
