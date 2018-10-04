package grupo9.usjt.usjt.com.activities;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import grupo9.usjt.usjt.com.dao.FavoritoDAO;
import grupo9.usjt.usjt.com.dto.BuscaDTO;
import grupo9.usjt.usjt.com.dto.OnibusDTO;
import grupo9.usjt.usjt.com.dto.PosicaoOnibusDTO;
import grupo9.usjt.usjt.com.helper.utils.RetrofitConfig;
import grupo9.usjt.usjt.com.services.OlhoVivoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaLinhasAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<BuscaDTO> list;
    private Context context;



    ListaLinhasAdapter(ArrayList<BuscaDTO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0L;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            view = inflater.inflate(R.layout.activity_adapter, null);
        }

        //Handle TextView and display string from your list
        TextView dsListaLinha = view.findViewById(R.id.list_item_string);
        dsListaLinha.setText(list.get(position).toString());
        dsListaLinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitConfig config = new RetrofitConfig();
                OlhoVivoService service = config.create();
                Call<PosicaoOnibusDTO> call2 = service.buscarOnibus(list.get(position).getCdLinha());
                call2.enqueue(new Callback<PosicaoOnibusDTO>() {
                    @Override
                    public void onResponse(@NonNull Call<PosicaoOnibusDTO> call, @NonNull Response<PosicaoOnibusDTO> response) {
                        List<OnibusDTO> listOnibus = new ArrayList<>(Objects.requireNonNull(response.body()).getLinhaDTO());
                        Intent intent = new Intent(context, MapActivity.class);
                        intent.putExtra("listaOnibus", (Serializable) listOnibus);
                        intent.putExtra("letreiroPrinc",list.get(position).getPrimLetreiro());
                        intent.putExtra("letreiroSec",list.get(position).getSegLetreiro());
                        intent.putExtra("cdLinha",list.get(position).getCdLinha());
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(@NonNull Call<PosicaoOnibusDTO> call, @NonNull Throwable t) {
                        Log.e("Erro de integracao", "Deu ruim na integracao");
                        t.printStackTrace();
                    }
                });
            }
        });

        //Handle buttons and add onClickListeners
        final Button btnFavoritar = view.findViewById(R.id.btn_favoritar);


        btnFavoritar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Favoritar selecionado!",Toast.LENGTH_SHORT).show();

                FavoritoDAO dao = new FavoritoDAO(context);
                if(!dao.findFavorito(list.get(position))){
                    dao.insertFavorito(list.get(position));
                }
                btnFavoritar.setEnabled(false);
                btnFavoritar.setBackground(context.getResources().getDrawable(android.R.drawable.btn_star_big_on));
            }
        });

        return view;
    }
}
