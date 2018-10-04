package grupo9.usjt.usjt.com.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import grupo9.usjt.usjt.com.dao.FavoritoDAO;
import grupo9.usjt.usjt.com.dto.BuscaDTO;
import grupo9.usjt.usjt.com.helper.utils.LoginHelper;

;

public class ListaFavoritosActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_linhas);

        FavoritoDAO dao = new FavoritoDAO(getApplicationContext());
        ArrayList<BuscaDTO> listaFavoritos = dao.findAllFavoritosByIdUsuario(LoginHelper.ID_USER);

        //instantiate custom adapter
        ListaFavoritosAdapter adapter = new ListaFavoritosAdapter(listaFavoritos, this);

        //handle listview and assign adapter
        ListView lView = findViewById(android.R.id.list);
        lView.setAdapter(adapter);
    }

}
