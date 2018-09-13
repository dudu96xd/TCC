package grupo9.usjt.usjt.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PosicaoOnibusDTO {
    @JsonProperty("hr")
    private String dataReferencia;
    @JsonProperty("l")
    private List<LinhaDTO> Onibus;

}
