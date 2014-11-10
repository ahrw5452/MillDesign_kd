package ws.milldesign_kd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
* サービスがバックエンドで処理を行い、
* 完了したタイミングでアクティビティやダイアログを表示したい場合
* サービスとアクティビティのやり取りを行いたいときに使うのがこのレシーバクラス
* ※ブロードキャストとはネットワーク内で不特定多数の相手に向かってデータを送信すること。
*   アンドロイドにはインテントをブロードキャストする仕組みがある。
* 　それを受け取るのがこのレシーバクラス
* サービスからインテントに付随させて送信して、レシーバーで受け取る
*
*
* */
public class AlarmReceiverMine extends BroadcastReceiver {
    //ブロードキャストをレシーブする
    @Override
    public void onReceive(Context c, Intent intent) {
        Log.v("レシーバ！！", "action: " + intent.getAction());
        //notificationインテントをインスタンス生成する
        Intent postIntent = new Intent(c,AlarmPostActivity.class);
        //notificationインテントインスタンスにフラグをセットする
        postIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //notificationインテントをインスタンスで指定したアクティビティを起動
        c.startActivity(postIntent);
    }
}