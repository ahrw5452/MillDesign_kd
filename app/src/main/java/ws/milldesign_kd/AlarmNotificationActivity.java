package ws.milldesign_kd;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

public class AlarmNotificationActivity extends Activity {
    private WakeLock wakelock;
    private KeyguardLock keylock;

    MediaPlayer mp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_mode);
        Log.v("通知ログ", "create");
        // スリープ状態から復帰する
        wakelock = ((PowerManager) getSystemService(Context.POWER_SERVICE))
                .newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
                        | PowerManager.ACQUIRE_CAUSES_WAKEUP
                        | PowerManager.ON_AFTER_RELEASE, "disableLock");
        wakelock.acquire();

        // スクリーンロックを解除する
        KeyguardManager keyguard = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        keylock = keyguard.newKeyguardLock("disableLock");
        keylock.disableKeyguard();
    }

    @Override
    public void onStart() {
        super.onStart();
        /*if (mp == null)
            mp = MediaPlayer.create(this, R.raw.alarm);
        mp.start();*/
        Log.i("ろぐ","あらーむ");

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAndRelaese();
    }
    private void stopAndRelaese() {
        if (mp != null) {
            mp.stop();
            mp.release();
        }
    }
}