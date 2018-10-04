package grupo9.usjt.usjt.com.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import grupo9.usjt.usjt.com.dao.FavoritoDAO;
import grupo9.usjt.usjt.com.dto.BuscaDTO;
import grupo9.usjt.usjt.com.helper.utils.LoginHelper;

public class ListaFavoritosAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<BuscaDTO> list;
    private Context context;



    ListaFavoritosAdapter(ArrayList<BuscaDTO> list, Context context) {
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
            view = inflater.inflate(R.layout.activity_adapter_favoritos, null);
        }

        //Handle TextView and display string from your list
        TextView btnExcluirFav = view.findViewById(R.id.list_item_string);
        btnExcluirFav.setText(list.get(position).toString());

        //Handle buttons and add onClickListeners
        final Button btnExcluirFavorito = view.findViewById(R.id.btn_excluir_favorito);


        btnExcluirFavorito.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FavoritoDAO dao = new FavoritoDAO(context);
                dao.deleteFavoritoByIdUsuarioAndCdLinha(LoginHelper.ID_USER,list.get(position).getCdLinha());
                list.remove(list.get(position));
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
