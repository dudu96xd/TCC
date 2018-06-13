package grupo9.usjt.usjt.com.helper.dao;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import grupo9.usjt.usjt.com.helper.crypto.EncriptaHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TCC.appBusao";
    public static final String CONTA_TABLE_NAME = "conta";
    public static EncriptaHelper encriptaHelper;

    static {
        try {
            encriptaHelper = new EncriptaHelper();
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
                        "(id_conta integer primary key autoincrement, name text,email text unique not null, password text not null)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS conta");
        onCreate(db);
    }


}
