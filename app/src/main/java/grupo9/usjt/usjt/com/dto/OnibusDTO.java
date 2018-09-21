package grupo9.usjt.usjt.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OnibusDTO implements Serializable {
    private static final long serialVersionUID = 8933302320990752639L;
    @JsonProperty("p")
    private int idVeiculo;
    @JsonProperty("a")
    private boolean acessoDef;
    @JsonProperty("ta")
    private String horario;
    @JsonProperty("py")
    private double latOnibus;
    @JsonProperty("px")
    private double lngOnibus;

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public boolean isAcessoDef() {
        return acessoDef;
    }

    public void setAcessoDef(boolean acessoDef) {
        this.acessoDef = acessoDef;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public double getLatOnibus() {
        return latOnibus;
    }

    public void setLatOnibus(double latOnibus) {
        this.latOnibus = latOnibus;
    }

    public double getLngOnibus() {
        return lngOnibus;
    }

    public void setLngOnibus(double lngOnibus) {
        this.lngOnibus = lngOnibus;
    }
}
