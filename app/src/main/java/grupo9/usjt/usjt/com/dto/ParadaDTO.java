package grupo9.usjt.usjt.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ParadaDTO implements Serializable {
    @JsonProperty("cp")
    private int cdParada;
    @JsonProperty("np")
    private String nmParada;
    @JsonProperty("ed")
    private String endereco;
    @JsonProperty("py")
    private double py;
    @JsonProperty("px")
    private double px;

    public int getCdParada() {
        return cdParada;
    }

    public void setCdParada(int cdParada) {
        this.cdParada = cdParada;
    }

    public String getNmParada() {
        return nmParada;
    }

    public void setNmParada(String nmParada) {
        this.nmParada = nmParada;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
}
