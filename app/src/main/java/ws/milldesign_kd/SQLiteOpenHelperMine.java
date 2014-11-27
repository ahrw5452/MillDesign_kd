package ws.milldesign_kd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteOpenHelperMine extends SQLiteOpenHelper {

    static DataBaseConstant dbc = new DataBaseConstant();
    // コンストラクタ(任意のデータベースファイル名と、バージョンを指定する)
    public SQLiteOpenHelperMine(Context context) {super(context,dbc.dbName, null, 1);}
    //DB生成
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
         * このデータベースを初めて使用する時に実行される処理
         * テーブルの作成や初期データの投入を行う
         * execSQL()は、SQLをそのまま実行する
         * まずはアラームのテーブルを作成
         */
        db.beginTransaction();
        try{
            db.execSQL(dbc.createAlarmTable);
        } finally {
            db.endTransaction();
            Log.i("Log","DB作成成功");
        }
    }
    //データベースのバージョンアップ時に実行される処理
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //アプリケーションの更新などによって、データベースのバージョンが上がった場合に実行される処理
    }
}
