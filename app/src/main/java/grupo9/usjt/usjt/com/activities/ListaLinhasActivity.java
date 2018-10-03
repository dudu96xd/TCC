package grupo9.usjt.usjt.com.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import grupo9.usjt.usjt.com.dto.BuscaDTO;
import grupo9.usjt.usjt.com.dto.OnibusDTO;
import grupo9.usjt.usjt.com.dto.PosicaoOnibusDTO;
import grupo9.usjt.usjt.com.helper.utils.RetrofitConfig;
import grupo9.usjt.usjt.com.services.OlhoVivoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaLinhasActivity extends ListActivity {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<BuscaDTO> listItems=new ArrayList<>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<BuscaDTO> adapter;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_list_linhas);
        adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);
        RetrofitConfig config = new RetrofitConfig();
        OlhoVivoService service = config.create();
        Call<List<BuscaDTO>> call = service.buscar(getIntent().getStringExtra("input"));
        Response<List<BuscaDTO>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null){
            listItems.addAll(Objects.requireNonNull(response.body()));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        RetrofitConfig config = new RetrofitConfig();
        OlhoVivoService service = config.create();
        Call<PosicaoOnibusDTO> call2 = service.buscarOnibus(listItems.get(position).getCdLinha());
        call2.enqueue(new Callback<PosicaoOnibusDTO>() {
            @Override
            public void onResponse(@NonNull Call<PosicaoOnibusDTO> call, @NonNull Response<PosicaoOnibusDTO> response) {
                List<OnibusDTO> listOnibus = new ArrayList<>(Objects.requireNonNull(response.body()).getLinhaDTO());
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("listaOnibus", (Serializable) listOnibus);
                intent.putExtra("letreiroPrinc",listItems.get(position).getPrimLetreiro());
                intent.putExtra("letreiroSec",listItems.get(position).getSegLetreiro());
                intent.putExtra("cdLinha",listItems.get(position).getCdLinha());
                startActivityForResult(intent, 5);

            }

            @Override
            public void onFailure(@NonNull Call<PosicaoOnibusDTO> call, @NonNull Throwable t) {
                Log.e("Erro de integracao", "Deu ruim na integracao");
                t.printStackTrace();
            }
        });
    }

}
