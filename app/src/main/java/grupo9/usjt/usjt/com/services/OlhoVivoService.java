package grupo9.usjt.usjt.com.services;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OlhoVivoService {
    @POST("Login/Autenticar")
    Call<String>auth(@Query("token") String token);

}
