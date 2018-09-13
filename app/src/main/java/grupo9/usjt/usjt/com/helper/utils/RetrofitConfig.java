package grupo9.usjt.usjt.com.helper.utils;

import java.io.IOException;

import grupo9.usjt.usjt.com.services.OlhoVivoService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request;
                if(TokenHelper.token!=null){
                request = original.newBuilder()
                        .addHeader("Cookie","apiCredentials="+TokenHelper.token+"; path=/; HttpOnly")
                        .method(original.method(), original.body())
                        .build();
                }
                else{
                    request = original.newBuilder()
                            .method(original.method(), original.body())
                            .build();
                }

                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://api.olhovivo.sptrans.com.br/v2.1/")
                .client(client)

                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public OlhoVivoService create() {
        return this.retrofit.create(OlhoVivoService.class);
    }

}
