package grupo9.usjt.usjt.com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import grupo9.usjt.usjt.com.dto.BuscaDTO;
import grupo9.usjt.usjt.com.helper.dao.DBHelper;
import grupo9.usjt.usjt.com.helper.utils.LoginHelper;

public class FavoritoDAO extends DBHelper {

    private Context context;
    
    public FavoritoDAO(Context context) {
        super(context);
        this.context = context;
    }

    public void insertFavorito(BuscaDTO dto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cd_linha", dto.getCdLinha());
        contentValues.put("prim_letreiro", dto.getPrimLetreiro());
        contentValues.put("seg_letreiro", dto.getSegLetreiro());
        contentValues.put("sentido_linha",dto.getSentidoLinha());
        contentValues.put("prim_letreiro_sent_princ",dto.getLetreiroSentidoPrinc());
        contentValues.put("prim_letreiro_sent_seg",dto.getLetreiroSentidoSec());
        contentValues.put("fk_id_conta", LoginHelper.ID_USER);
        try {
            db.insertOrThrow("favorito", null, contentValues);
        }
        catch(Exception exception){
            Toast.makeText(context, "Favorito já cadastrado!", Toast.LENGTH_SHORT).show();
            exception.printStackTrace();
        }
    }

    public boolean findFavorito(BuscaDTO dto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cd_linha", dto.getCdLinha());
        Cursor res = db.rawQuery( "SELECT 1 from favorito where cd_linha=?", new String[]{dto.getCdLinha() } );
        int qtdLinhas = res.getCount();
        res.close();
        return qtdLinhas > 0;
    }

    public ArrayList<BuscaDTO> findAllFavoritosByIdUsuario(String idUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT cd_linha,prim_letreiro,seg_letreiro,sentido_linha,prim_letreiro_sent_princ,prim_letreiro_sent_seg from favorito where fk_id_conta=?", new String[]{idUsuario}  );
        ArrayList<BuscaDTO> listaSaida = new ArrayList<>();
        if(res.moveToFirst()){
            do{
                BuscaDTO dto = new BuscaDTO();
                dto.setCdLinha(res.getString(res.getColumnIndex("cd_linha")));
                dto.setPrimLetreiro(res.getString(res.getColumnIndex("prim_letreiro")));
                dto.setSegLetreiro(res.getString(res.getColumnIndex("seg_letreiro")));
                dto.setSentidoLinha(res.getInt(res.getColumnIndex("sentido_linha")));
                dto.setLetreiroSentidoPrinc(res.getString(res.getColumnIndex("prim_letreiro_sent_princ")));
                dto.setLetreiroSentidoSec(res.getString(res.getColumnIndex("prim_letreiro_sent_seg")));
                listaSaida.add(dto);
            }
            while(res.moveToNext());
        }
        res.close();
        return listaSaida;
    }

    public void deleteFavoritoByIdUsuarioAndCdLinha(String idUsuario, String cdLinha) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("favorito","fk_id_conta = ? and cd_linha = ?", new String[]{idUsuario, cdLinha});
    }
}