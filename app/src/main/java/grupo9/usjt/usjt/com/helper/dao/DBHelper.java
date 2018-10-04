package grupo9.usjt.usjt.com.helper.dao;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import grupo9.usjt.usjt.com.helper.crypto.EncriptaHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TCC.appBusao";
    protected static final String CONTA_TABLE_NAME = "conta";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table conta " +
                        "(id_conta integer primary key autoincrement, name text,email text unique not null, password text not null)"
        );
        db.execSQL("create table favorito " +
                "(id_favorito integer primary key autoincrement, cd_linha integer not null, prim_letreiro text,seg_letreiro text,sentido_linha integer,prim_letreiro_sent_princ text,prim_letreiro_sent_seg text,fk_id_conta integer not null, foreign key(fk_id_conta) references conta(id_conta))");
        //db.execSQL("create table local_pref (id_local_pref)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS conta");
        db.execSQL("DROP TABLE IF EXISTS favoritos");
        onCreate(db);
    }


}