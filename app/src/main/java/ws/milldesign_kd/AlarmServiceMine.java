package ws.milldesign_kd;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/*
* サービスってクラスは、バックグラウンドで動くプログラムを作るクラス
* 他のActivityのライフサイクルとは別に動くクラスの意
* サービス作成時にはonCreateが呼ばれる
* スレッドを別に作るとログの出力先が変わるので、
* onCreateのLogが消される可能際がある(要調査)
*
*
* */
public class AlarmServiceMine extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //まずここでサービスを作る
    @Override
    public void onCreate() {
        try {
            Thread.sleep(3000,0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("まいあらーむさーびすろぐ（おんくりえいとのなか）", "時間だぜ！！");

        Thread thr = new Thread(null, mTask, "MyAlarmServiceThread");
        thr.start();
    }
    /**
     * アラームサービス
     */
    Runnable mTask = new Runnable() {
        public void run() {
            // ここでアラーム通知する前の処理など...
            Log.i("まいあらーむさーびすろぐ(らんのなか)", "①");
            Intent alarmBroadcast = new Intent();

            //独自のメッセージを送信します
            Log.i("まいあらーむさーびすろぐ(らんのなか)", "通知画面起動メッセージを送る");
            alarmBroadcast.setAction("AlarmActionMine");
            sendBroadcast(alarmBroadcast);

            //サービスはいつ止める？
            Log.i("まいあらーむさーびすろぐ(らんのなか)", "サービス停止");
            AlarmServiceMine.this.stopSelf();//サービスを止める
        }
    };
}