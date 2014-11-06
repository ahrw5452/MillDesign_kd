package ws.milldesign_kd;


import android.app.ActionBar;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/*アラーム設定ダイアログ*/
public class alarmDialogFragment extends DialogFragment {

    private Button setAlarmButton,closeAlarmButton;
    private TimePicker alarmTimePicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.activity_alarm_mode);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alarmTimePicker = (TimePicker)dialog.findViewById(R.id.alarmTimePicker);
        alarmTimePicker.setIs24HourView(true);//24時間表示の方がいいかな

        //setAlarmボタンのリスナ
        dialog.findViewById(R.id.setAlarmButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MyAlarmManager mam = new MyAlarmManager(getActivity());
                mam.addAlarm(alarmTimePicker.getCurrentHour(),alarmTimePicker.getCurrentMinute());
                Toast.makeText(getActivity(), "通知セット完了!", Toast.LENGTH_SHORT).show();
            }
        });
        //closeAlarmボタンのリスナ
        dialog.findViewById(R.id.closeAlarmButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAlarm();
            }
        });
        return dialog;
    }

    //アラーム設定ダイアログを閉じる
    private void closeAlarm(){
        super.onDismiss(getDialog());
    }
}