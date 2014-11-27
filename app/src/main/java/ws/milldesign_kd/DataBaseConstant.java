package ws.milldesign_kd;

//DBに流すsqlの定数とスキーマのメモ
public class DataBaseConstant {
    //DB本体の名前:KD
    String dbName = "KdDataBase.db";
    /*
    * 登録するtable:Alarm
    *
    *
    *
    */
    String createAlarmTable =  "create table alarm_table ("
            + "id  integer primary key autoincrement not null, "
            + "hour text not null, "
            + "minutes text not null, "
            + "repert integer not null, "
            + "weekday text, "
            + "onoff  integer not null)";
}
