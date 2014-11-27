package ws.milldesign_kd;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;

/**
 * Created by sudo on 2014/11/27.
 */
public class DataBaseOperation {
    //データ検索
    String searchAlarm(SQLiteDatabase db){
        // Cursorを確実にcloseするために、try{}～finally{}にする
        Cursor cursor = null;
        try{
            /*
            * Cursor query(
            * String  table,           →alarm_test_table(省略不可)
            * String[]  columns,       →null(全カラム取得)
            * String  selection,       →(検索条件)
            * String[]  selectionArgs, →(検索条件に埋め込むパラメータ)
            * String  groupBy,         →
            * String  having,          →
            * String  orderBy,         →
            * String  limit　          →
            * )
            * */
            cursor = db.query( "alarm_table",
                    null,
                    null,
                    null,
                    null,
                    null,
                    "id DESC"
            );
            // cursoに検索結果入ったので読み込んで返す
            return readCursor( cursor );
        }
        finally{if( cursor != null ){cursor.close();}}
    }
    //検索結果の読み込み
    String readCursor( Cursor cursor ){
        String result = "";
        // まず、Cursorからnameカラムとageカラムを
        // 取り出すためのインデクス値を確認しておく
        int indexHour = cursor.getColumnIndex( "hour" );
        int indexMinutes  = cursor.getColumnIndex( "minutes"  );
        int indexRepert = cursor.getColumnIndex( "repert" );
        int indexWeekday = cursor.getColumnIndex( "weekday" );
        int indexOnoff = cursor.getColumnIndex( "onoff" );
        // ↓のようにすると、検索結果の件数分だけ繰り返される
        while(cursor.moveToNext()){
            // 検索結果をCursorから取り出す
            String hour = cursor.getString(indexHour);
            String minutes = cursor.getString(indexMinutes);
            int repert = cursor.getInt(indexRepert);
            String weekday = cursor.getString(indexWeekday);
            int onoff = cursor.getInt(indexOnoff);
            result += hour + "時" +minutes + "分     " +repert + "繰り返し     " +weekday+ "曜日     " +onoff+ "セット状態\n";
        }
        return result;
    }

    //テーブル上データの全削除
    long delAlarm( SQLiteDatabase db ){
        return db.delete( "alarm_table", null, null );
    }
}
