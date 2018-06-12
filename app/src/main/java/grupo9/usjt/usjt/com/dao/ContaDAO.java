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
    public static final String CONTA_COLUMN_NAME = "name";
    public static final String CONTA_COLUMN_EMAIL = "email";
    public static final String CONTA_COLUMN_PASSWORD = "password";

    public ContaDAO(Context context) {
        super(context);
    }


    public boolean insertConta(ContaDTO dto) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTA_COLUMN_NAME, dto.getNome());
        contentValues.put(CONTA_COLUMN_EMAIL, dto.getEmail());
        contentValues.put(CONTA_COLUMN_PASSWORD, encripta.encrypt(dto.getSenha()));
        db.insert("conta", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from conta where id_conta="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTA_TABLE_NAME);
        return numRows;
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
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from conta", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTA_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public boolean findConta(UsuarioDTO dto) throws Exception {
        String senhaEncriptada = encripta.encrypt(dto.getSenha());
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "SELECT 1 from conta where email=? and password=?",
                new String[]{dto.getEmail(),senhaEncriptada } );
        int qtdLinhas = res.getCount();
        if(qtdLinhas == 1){
            return true;
        }
        return false;
    }

}
