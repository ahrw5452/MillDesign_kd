package ws.milldesign_kd;

import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAlarmManager {
    Context context;
    AlarmManager alarmManager;
    private PendingIntent mAlarmSender;

    public MyAlarmManager(Context context){
        this.context = context;
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }

    public void addAlarm(int hour,int minute){
        // アラーム時間設定
        Calendar startTime = Calendar.getInstance();//カレンダー型の変数が現在時刻を持っている状態

        startTime.set(Calendar.HOUR_OF_DAY, hour);//ここから
        startTime.set(Calendar.MINUTE, minute);
        startTime.set(Calendar.SECOND, 0);//ここまででタイマーピッカーで選択した時間に書き換わる

        long alarmStartTime = startTime.getTimeInMillis();//タイマーピッカーの時間をミリ秒に(これがよくわからん)
        /*
        *↑タイマーピッカーで選択した時刻を。
        *1970年1月1日0時0分0秒からの経過時間にする。
        * 単位はミリ秒
        * */

        alarmManager.set(
                        AlarmManager.RTC_WAKEUP,//第一引数
                        alarmStartTime, //第二引数
                        PendingIntent.getService(context, -1, new Intent(context, MyAlarmService.class), PendingIntent.FLAG_UPDATE_CURRENT)//第三引数
                        );
        /*
        * アラームマネージャのset機能を解説。
        * まずこのsetは、任意の時間で実行する処理を登録
        * 第一引数　第二引数の指定のタイプを選択。RTCを指定するとUTC時刻での指定となる。_WAKEUPはオプションで、指定した時間が来たらアプリがスリープ状態でも電源をONしてくれる
        * ※UTC時刻とは全世界で時刻を記録する際に使われる公式な時刻。日本との時差は約マイナス9時間(日本は世界の公式時刻より9時間早く生活している、日本が午後15時なら公式時刻は朝6時)
        * 第二引数　処理を実行する任意の時間。今回はUTC時刻での指定。
        * 第三引数　PendingIntent operationとやら。
        *           Intentを指定したタイミングで発行するのがPendingIntent。
        *           では、PendingIntentのgetServiceを見ていく。
        *           まず第一引数。これは
        *
        *
        *
        * */

        Log.i("MyAlarmManagerログ",alarmStartTime+"ms");
        Log.i("MyAlarmManagerログ",startTime.getTime()+"");
        Log.i("MyAlarmManagerログ","アラームセット完了");
    }
}