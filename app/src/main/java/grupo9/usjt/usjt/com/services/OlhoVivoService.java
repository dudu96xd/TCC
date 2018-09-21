package grupo9.usjt.usjt.com.services;

import java.util.List;

import grupo9.usjt.usjt.com.dto.BuscaDTO;
import grupo9.usjt.usjt.com.dto.ParadaDTO;
import grupo9.usjt.usjt.com.dto.PosicaoOnibusDTO;
import grupo9.usjt.usjt.com.helper.utils.TokenHelper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OlhoVivoService {
    @POST("Login/Autenticar")
    Call<String>auth(@Query("token") String token);

    @GET("Linha/Buscar/{termosBusca}")
    Call<List<BuscaDTO>>buscar(@Query("termosBusca")String linha);

    @GET("Posicao/Linha/{codigoLinha}")
    Call<PosicaoOnibusDTO>buscarOnibus(@Query("codigoLinha")String linha);

    @GET("Parada/BuscarParadasPorLinha/{codigoLinha}")
    Call<List<ParadaDTO>>buscarParadas(@Query("codigoLinha")String cdLinha);
}
