package grupo9.usjt.usjt.com.helper.dao;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import grupo9.usjt.usjt.com.dto.ContaDTO;
import grupo9.usjt.usjt.com.helper.crypto.Encripta;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TCC.app";
    public static final String CONTA_TABLE_NAME = "conta";
    public static final String CONTA_COLUMN_ID = "id_conta";
    public static final String CONTA_COLUMN_NAME = "name";
    public static final String CONTA_COLUMN_EMAIL = "email";
    public static final String CONTA_COLUMN_PASSWORD = "password";
    public static  Encripta encripta;

    static {
        try {
            encripta = new Encripta();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table conta " +
                        "(id_conta integer primary key autoincrement, name text,email text not null, password text not null)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
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
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTA_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public boolean findConta(ContaDTO dto) throws Exception {
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
