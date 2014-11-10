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
import android.view.View;
import android.widget.Toast;

public class AlarmPostActivity extends Activity implements View.OnClickListener{
    private WakeLock wakelock;
    private KeyguardLock keylock;
    MediaPlayer mp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_post);

        //getActionBar().hide();

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

        findViewById(R.id.closeAlarmPostButton).setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        this.finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(this, "アラームスタート！", Toast.LENGTH_SHORT).show();
        /*if (mp == null)
            mp = MediaPlayer.create(this, R.raw.alarm);
        mp.start();*/
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