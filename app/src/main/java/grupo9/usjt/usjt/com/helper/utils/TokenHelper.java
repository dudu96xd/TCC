package grupo9.usjt.usjt.com.helper.utils;
import java.io.IOException;

import grupo9.usjt.usjt.com.activities.R;
import grupo9.usjt.usjt.com.services.OlhoVivoService;
import retrofit2.Call;
import retrofit2.Response;

public class TokenHelper {

    public static String token = getToken();

    private static RetrofitConfig config;
    private static OlhoVivoService service;
    private static String getToken() {
        if(token == null) {
            if (config == null){
                config = new RetrofitConfig();
                service = config.create();
            }

            try { assert service != null;
                Call<String> call = service.auth("ba9a2b9775a7965dee652817a9befd6d107242a2121b5e78562f60cd14961c69");
                Response response = call.execute();
                token = response.headers().get("Set-Cookie").substring(15).split(";")[0];
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return token;
    }
}
