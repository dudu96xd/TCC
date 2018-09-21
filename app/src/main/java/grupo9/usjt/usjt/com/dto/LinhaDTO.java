package grupo9.usjt.usjt.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LinhaDTO {
    @JsonProperty("c")
    private String letreiroLinha;
    @JsonProperty("cl")
    private int cdLinha;
    @JsonProperty("sl")
    private int sentido;
    @JsonProperty("lt0")
    private String letreiroDestino;
    @JsonProperty("lt1")
    private String letreiroOrigem;
    @JsonProperty("qv")
    private int qtVeiculos;
    @JsonProperty("vs")
    private List<OnibusDTO> onibusDTOList;

    public String getLetreiroLinha() {
        return letreiroLinha;
    }

    public void setLetreiroLinha(String letreiroLinha) {
        this.letreiroLinha = letreiroLinha;
    }

    public int getCdLinha() {
        return cdLinha;
    }

    public void setCdLinha(int cdLinha) {
        this.cdLinha = cdLinha;
    }

    public int getSentido() {
        return sentido;
    }

    public void setSentido(int sentido) {
        this.sentido = sentido;
    }

    public String getLetreiroDestino() {
        return letreiroDestino;
    }

    public void setLetreiroDestino(String letreiroDestino) {
        this.letreiroDestino = letreiroDestino;
    }

    public String getLetreiroOrigem() {
        return letreiroOrigem;
    }

    public void setLetreiroOrigem(String letreiroOrigem) {
        this.letreiroOrigem = letreiroOrigem;
    }

    public int getQtVeiculos() {
        return qtVeiculos;
    }

    public void setQtVeiculos(int qtVeiculos) {
        this.qtVeiculos = qtVeiculos;
    }

    public List<OnibusDTO> getOnibusDTOList() {
        return onibusDTOList;
    }

    public void setOnibusDTOList(List<OnibusDTO> onibusDTOList) {
        this.onibusDTOList = onibusDTOList;
    }
}
