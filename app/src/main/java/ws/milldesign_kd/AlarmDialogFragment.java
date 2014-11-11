package ws.milldesign_kd;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmDialogFragment extends DialogFragment {
    private TimePicker alarmTimePicker;
    private Switch alarmRepeatSwitch;
    private int setHour,setMinute = 0;
    private boolean Repeat = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.activity_alarm_mode);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alarmTimePicker = (TimePicker)dialog.findViewById(R.id.alarmTimePicker);
        alarmTimePicker.setIs24HourView(true);

        /*
        * 繰り返しのON/OFF
        * ONなら曜日設定
        * OFFならTimePickerに設定した時間が次に来た時に処理を走らせる
        */
        alarmRepeatSwitch = (Switch)dialog.findViewById(R.id.alarmRepeatSwitch);
        alarmRepeatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getActivity(), "繰り返しだよ曜日指定へ", Toast.LENGTH_SHORT).show();
                }
                Repeat = isChecked;
            }
        });

        //setAlarmボタンのリスナ
        dialog.findViewById(R.id.setAlarmButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(Repeat){
                    Toast.makeText(getActivity(), "繰り返しだよ曜日指定してね", Toast.LENGTH_SHORT).show();
                }else{
                    /*繰り返しが無ければ、SETボタンを押した瞬間に
                    * アラームマネージャに発動する時間を渡す
                    */
                    setHour  = alarmTimePicker.getCurrentHour();
                    setMinute = alarmTimePicker.getCurrentMinute();
                    //Setボタンを押した瞬間の時刻をカレンダー型の変数に格納(2つ)
                    Calendar currentTime = Calendar.getInstance();
                    Calendar setTime = Calendar.getInstance();
                    //片方はタイマーピッカーで選択した時間に書き換わる
                    setTime.set(Calendar.HOUR_OF_DAY, setHour);
                    setTime.set(Calendar.MINUTE, setMinute);
                    setTime.set(Calendar.SECOND, 0);
                    //現在時刻が、セットされた時間より先に行ってたらセットされた時間を一日先に
                    if(currentTime.getTimeInMillis() > setTime.getTimeInMillis()){
                        setTime.add(Calendar.HOUR_OF_DAY,+24);
                    };
                    //アラームマネージャにセットされた値を渡す
                    new AlarmManagerMine(getActivity()).noRepertAddAlarm(setTime.getTimeInMillis());
                    Log.i("現在の時間","→"+currentTime.getTime());
                    Log.i("セットされた時間","→"+setTime.getTime());
                    Toast.makeText(getActivity(), "繰り返し無しで通知セット完了!→"+setTime.getTime(), Toast.LENGTH_SHORT).show();
                }
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