package ws.milldesign_kd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmNotificationReceiverMine extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("レシーバログ", "action: " + intent.getAction());
        Intent notification = new Intent(context,
                AlarmNotificationActivity.class);
        //ここがないと画面を起動できません
        notification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(notification);
    }

}