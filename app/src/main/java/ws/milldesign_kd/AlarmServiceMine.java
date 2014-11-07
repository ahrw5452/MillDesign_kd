package ws.milldesign_kd;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmServiceMine extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("まいあらーむさーびすろぐ（おんくりえいとのなか）","時間だぜ！！");
        Thread thr = new Thread(null, mTask, "MyAlarmServiceThread");
        thr.start();
        Log.i("まいあらーむさーびすろぐ（おんくりえいとのなか）", "スレッド開始");
    }

    /**
     * アラームサービス
     */
    Runnable mTask = new Runnable() {
        public void run() {
            // ここでアラーム通知する前の処理など...
            Intent alarmBroadcast = new Intent();
            alarmBroadcast.setAction("MyAlarmAction");//独自のメッセージを送信します
            sendBroadcast(alarmBroadcast);
            Log.i("まいあらーむさーびすろぐ(らんのなか)", "通知画面起動メッセージを送った");
            AlarmServiceMine.this.stopSelf();//サービスを止める
            Log.i("まいあらーむさーびすろぐ(らんのなか)", "サービス停止");
        }
    };
}