package sample;

public class pseudoaleatorios {
    //  Periodo  Xi      X        Ri
    private final int periodo;
    private final String xi;
    private final int x;
    private final double ri;

    public pseudoaleatorios(int periodo, String xi, int x, double ri) {
        this.periodo = periodo;
        this.xi = xi;
        this.x = x;
        this.ri = ri;
    }

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
}