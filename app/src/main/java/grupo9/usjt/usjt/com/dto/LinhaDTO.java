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
    private List<OnibusDTO> linhaDTOList;
}
