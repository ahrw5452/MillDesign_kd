package ws.milldesign_kd;

//DBに流すクエリの定数とスキーマのメモ
public class DataBaseConstant {

    String constructor = "sqlite_kd.db";
    String create = "create table kd_table ( _id integer primary key autoincrement,data integer not null );";
    String drop = "drop table kd_table;";


}
