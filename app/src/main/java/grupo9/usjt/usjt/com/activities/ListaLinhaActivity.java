package grupo9.usjt.usjt.com.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import grupo9.usjt.usjt.com.dto.BuscaDTO;
import grupo9.usjt.usjt.com.helper.utils.RetrofitConfig;
import grupo9.usjt.usjt.com.services.OlhoVivoService;
import retrofit2.Call;
import retrofit2.Response;

public class ListaLinhaActivity extends Activity {
    ArrayList<BuscaDTO> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_linhas);

        RetrofitConfig config = new RetrofitConfig();
        OlhoVivoService service = config.create();
        Call<List<BuscaDTO>> call = service.buscar(getIntent().getStringExtra("input"));
        Response<List<BuscaDTO>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null){
            list.addAll(Objects.requireNonNull(response.body()));
        }

        //instantiate custom adapter
        ListaLinhasAdapter adapter = new ListaLinhasAdapter(list, this);

        //handle listview and assign adapter
        ListView lView = findViewById(android.R.id.list);
        lView.setAdapter(adapter);
    }
}
