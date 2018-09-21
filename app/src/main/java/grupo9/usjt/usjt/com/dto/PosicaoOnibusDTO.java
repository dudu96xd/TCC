package grupo9.usjt.usjt.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PosicaoOnibusDTO {
    @JsonProperty("hr")
    private String dataReferencia;
    @JsonProperty("vs")
    private List<OnibusDTO> linhaDTO;

    public String getDataReferencia() {
        return dataReferencia;
    }

    public void setDataReferencia(String dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    public List<OnibusDTO> getLinhaDTO() {
        return linhaDTO;
    }

    public void setLinhaDTO(List<OnibusDTO> linhaDTO) {
        this.linhaDTO = linhaDTO;
    }
}
