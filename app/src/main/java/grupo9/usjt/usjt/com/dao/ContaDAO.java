package grupo9.usjt.usjt.com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import grupo9.usjt.usjt.com.dto.ContaDTO;
import grupo9.usjt.usjt.com.dto.UsuarioDTO;
import grupo9.usjt.usjt.com.helper.dao.DBHelper;

public class ContaDAO extends DBHelper {

    public static final String CONTA_COLUMN_ID = "id_conta";
    private static final String CONTA_COLUMN_NAME = "name";
    private static final String CONTA_COLUMN_EMAIL = "email";
    private static final String CONTA_COLUMN_PASSWORD = "password";

    public ContaDAO(Context context) {
        super(context);
    }


    public boolean insertConta(ContaDTO dto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTA_COLUMN_NAME, dto.getNome());
        contentValues.put(CONTA_COLUMN_EMAIL, dto.getEmail());
        contentValues.put(CONTA_COLUMN_PASSWORD, dto.getSenha());
        db.insert("conta", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select * from conta where id_conta="+id, null );
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, CONTA_TABLE_NAME);
    }

    public boolean updateConta (Integer id, String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTA_COLUMN_NAME, name);
        contentValues.put(CONTA_COLUMN_EMAIL, email);
        contentValues.put(CONTA_COLUMN_PASSWORD, password);
        db.update("conta", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteConta (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("conta",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> findAllContas() {
        ArrayList<String> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from conta", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(CONTA_COLUMN_NAME)));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }
    public boolean findConta(UsuarioDTO dto){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "SELECT 1 from conta where email=? and password=?",
                new String[]{dto.getEmail(),dto.getSenha() } );
        int qtdLinhas = res.getCount();
        res.close();
        return qtdLinhas == 1;
    }

}
