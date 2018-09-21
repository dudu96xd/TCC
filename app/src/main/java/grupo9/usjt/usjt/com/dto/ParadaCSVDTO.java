package grupo9.usjt.usjt.com.dto;

import java.io.Serializable;

public class ParadaCSVDTO implements Serializable {

    private static final long serialVersionUID = 6624674986233101283L;

    private int idTrip;
    private double py;
    private double px;
    private int seqParada;
    private double distPercorrida;

    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public double getPy() {
        return py;
    }

    public void setPy(double py) {
        this.py = py;
    }

    public double getPx() {
        return px;
    }

    public void setPx(double px) {
        this.px = px;
    }

    public int getSeqParada() {
        return seqParada;
    }

    public void setSeqParada(int seqParada) {
        this.seqParada = seqParada;
    }

    public double getDistPercorrida() {
        return distPercorrida;
    }

    public void setDistPercorrida(double distPercorrida) {
        this.distPercorrida = distPercorrida;
    }
}
