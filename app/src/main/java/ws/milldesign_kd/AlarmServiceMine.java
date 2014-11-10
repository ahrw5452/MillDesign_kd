package ws.milldesign_kd;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/*
* サービスってクラスは、バックグラウンドで動くプログラムを作るクラス
*
* 他のActivityのライフサイクルとは別に動くクラスの意
* 要は時計アプリが動いていても、端末がスリープ状態でも
* アラーム通知が起動する
*
* サービス作成時にはonCreateが呼ばれる
* スレッドを別に作るとログの出力先が変わるので、
* onCreateのLogが消される可能際がある(要調査)
*/
public class AlarmServiceMine extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //まずここでサービスを作る
    @Override
    public void onCreate() {
        Thread thr = new Thread(null, mTask, "MyAlarmServiceThread");
        thr.start();
    }
    /**
     * アラームサービス
     */
    Runnable mTask = new Runnable() {
        public void run() {
            // ここでアラーム通知する前の処理など...
            Intent alarmBroadcastIntent = new Intent();
            //独自のインテントにアクションをセットする
            alarmBroadcastIntent.setAction("AlarmActionMine");
            //インテントをブロードキャストする
            sendBroadcast(alarmBroadcastIntent);
            //サービスはいつ止める？
            AlarmServiceMine.this.stopSelf();//サービスを止める
        }
    };
}