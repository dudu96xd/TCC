package grupo9.usjt.usjt.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OnibusDTO {
    @JsonProperty("p")
    private int idVeiculo;
    @JsonProperty("a")
    private boolean acessoDef;
    @JsonProperty("ta")
    private String horario;
    @JsonProperty("py")
    private int latOnibus;
    @JsonProperty("px")
    private int lngOnibus;

}
