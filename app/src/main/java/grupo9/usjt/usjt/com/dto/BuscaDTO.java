package grupo9.usjt.usjt.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BuscaDTO {
    /**cdLinha*/
    @JsonProperty("cl")
    private String cdLinha;
    /**modoCircular*/
    @JsonProperty("lc")
    private String modoCircular;
    /**primLetreiro*/
    @JsonProperty("lt")
    private String primLetreiro;
    /**segLetreiro*/
    @JsonProperty("tl")
    private String segLetreiro;
    /**segLetreiro*/
    @JsonProperty("sl")
    private String sentidoLinha;
    /**letreiroSentidoPrinc*/
    @JsonProperty("tp")
    private String letreiroSentidoPrinc;
    /**letreiroSentidoSec*/
    @JsonProperty("ts")
    private String letreiroSentidoSec;

    public String getCdLinha() {
        return cdLinha;
    }

    public void setCdLinha(String cdLinha) {
        this.cdLinha = cdLinha;
    }

    public String getModoCircular() {
        return modoCircular;
    }

    public void setModoCircular(String modoCircular) {
        this.modoCircular = modoCircular;
    }

    public String getPrimLetreiro() {
        return primLetreiro;
    }

    public void setPrimLetreiro(String primLetreiro) {
        this.primLetreiro = primLetreiro;
    }

    public String getSegLetreiro() {
        return segLetreiro;
    }

    public void setSegLetreiro(String segLetreiro) {
        this.segLetreiro = segLetreiro;
    }

    public String getSentidoLinha() {
        return sentidoLinha;
    }

    public void setSentidoLinha(String sentidoLinha) {
        this.sentidoLinha = sentidoLinha;
    }

    public String getLetreiroSentidoPrinc() {
        return letreiroSentidoPrinc;
    }

    public void setLetreiroSentidoPrinc(String letreiroSentidoPrinc) {
        this.letreiroSentidoPrinc = letreiroSentidoPrinc;
    }

    public String getLetreiroSentidoSec() {
        return letreiroSentidoSec;
    }

    public void setLetreiroSentidoSec(String letreiroSentidoSec) {
        this.letreiroSentidoSec = letreiroSentidoSec;
    }
}
