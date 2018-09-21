package grupo9.usjt.usjt.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BuscaDTO implements Serializable{

    private static final long serialVersionUID = 3591765487136062258L;
    /**cdLinha*/
    @JsonProperty("cl")
    private String cdLinha;
    /**modoCircular*/
    @JsonProperty("lc")
    private boolean modoCircular;
    /**primLetreiro*/
    @JsonProperty("lt")
    private String primLetreiro;
    /**segLetreiro*/
    @JsonProperty("tl")
    private String segLetreiro;
    /**segLetreiro*/
    @JsonProperty("sl")
    private int sentidoLinha;
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

    public boolean getModoCircular() {
        return modoCircular;
    }

    public void setModoCircular(boolean modoCircular) {
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

    public int getSentidoLinha() {
        return sentidoLinha;
    }

    public void setSentidoLinha(int sentidoLinha) {
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

    @Override
    public String toString() {
        return  "Nº do Onibus = "+ primLetreiro + "-" + segLetreiro +"\n" +
                (sentidoLinha==1?   "-> " + letreiroSentidoPrinc +"\n"+ "<- " + letreiroSentidoSec:
                                    "-> " + letreiroSentidoSec + "\n" + "<- "+ letreiroSentidoPrinc) ;
    }
}
