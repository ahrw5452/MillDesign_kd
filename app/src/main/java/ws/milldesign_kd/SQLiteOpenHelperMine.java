package ws.milldesign_kd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteOpenHelperMine extends SQLiteOpenHelper {
    //DBのクエリをここから取得
    static DataBaseConstant dbc = new DataBaseConstant();
    // コンストラクタ
    public SQLiteOpenHelperMine(Context context) {
        super(context,dbc.constructor, null, 1);
    }
    //データベースファイル初回使用時に実行される処理
    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブル作成のクエリを発行
        db.execSQL(dbc.create);
    }
    //データベースのバージョンアップ時に実行される処理
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // テーブルの破棄と再作成
        db.execSQL(dbc.drop);
        onCreate(db);
    }
}
