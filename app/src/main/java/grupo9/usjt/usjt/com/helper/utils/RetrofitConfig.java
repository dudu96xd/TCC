package grupo9.usjt.usjt.com.helper.utils;

import grupo9.usjt.usjt.com.services.OlhoVivoService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://api.olhovivo.sptrans.com.br/v2.1/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public OlhoVivoService getTokenService() {
        return this.retrofit.create(OlhoVivoService.class);
    }

}
