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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmDialogFragment extends DialogFragment {
    private TimePicker alarmTimePicker;
    private NumberPicker alarmHoursNumberPicker,alarmMinutesNumberPicker;
    private Switch alarmRepeatSwitch;
    private boolean Repeat = false;
    private CheckBox sunCheck,monCheck,tueCheck,wedCheck,thuCheck,friCheck,satCheck;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.activity_alarm_mode);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //alarmTimePicker = (TimePicker)dialog.findViewById(R.id.alarmTimePicker);
        //alarmTimePicker.setIs24HourView(true);

        alarmHoursNumberPicker = (NumberPicker)dialog.findViewById(R.id.alarmHoursNumberPicker);
        alarmHoursNumberPicker.setMinValue(00);
        alarmHoursNumberPicker.setMaxValue(23);
        alarmMinutesNumberPicker = (NumberPicker)dialog.findViewById(R.id.alarmMinutesNumberPicker);
        alarmMinutesNumberPicker.setMaxValue(59);
        alarmMinutesNumberPicker.setMinValue(00);




        sunCheck = (CheckBox)dialog.findViewById(R.id.Sunday);
        monCheck = (CheckBox)dialog.findViewById(R.id.Monday);
        tueCheck = (CheckBox)dialog.findViewById(R.id.Tuesday);
        wedCheck = (CheckBox)dialog.findViewById(R.id.Wednesday);
        thuCheck = (CheckBox)dialog.findViewById(R.id.Thursday);
        friCheck = (CheckBox)dialog.findViewById(R.id.Friday);
        satCheck = (CheckBox)dialog.findViewById(R.id.Saturday);

        /*
        * 繰り返しのON/OFF
        * ONなら曜日設定
        * OFFならTimePickerに設定した時間が次に来た時に処理を走らせる
        */
        alarmRepeatSwitch = (Switch)dialog.findViewById(R.id.alarmRepeatSwitch);
        alarmRepeatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Repeat = isChecked;
                //繰り返しONにした時
                if(isChecked){
                    dayOfTheWeekChoice();
                }else{//繰り返しOFFにした時
                    notDayOfTheWeekChoice();
                }

            }
        });

        //setAlarmボタンのリスナ
        dialog.findViewById(R.id.setAlarmButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {setAlarm();}
        });

        //closeAlarmボタンのリスナ
        dialog.findViewById(R.id.closeAlarmButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {closeAlarm();}
        });
        return dialog;
    }

    //曜日選択
    private void dayOfTheWeekChoice(){
        sunCheck.setEnabled(true);
        sunCheck.setBackgroundColor(00000000);
        monCheck.setEnabled(true);
        monCheck.setBackgroundColor(00000000);
        tueCheck.setEnabled(true);
        tueCheck.setBackgroundColor(00000000);
        wedCheck.setEnabled(true);
        wedCheck.setBackgroundColor(00000000);
        thuCheck.setEnabled(true);
        thuCheck.setBackgroundColor(00000000);
        friCheck.setEnabled(true);
        friCheck.setBackgroundColor(00000000);
        satCheck.setEnabled(true);
        satCheck.setBackgroundColor(00000000);
    }
    private void notDayOfTheWeekChoice(){
        sunCheck.setEnabled(false);
        sunCheck.setBackgroundColor(77000000);
        monCheck.setEnabled(false);
        monCheck.setBackgroundColor(77000000);
        tueCheck.setEnabled(false);
        tueCheck.setBackgroundColor(77000000);
        wedCheck.setEnabled(false);
        wedCheck.setBackgroundColor(77000000);
        thuCheck.setEnabled(false);
        thuCheck.setBackgroundColor(77000000);
        friCheck.setEnabled(false);
        friCheck.setBackgroundColor(77000000);
        satCheck.setEnabled(false);
        satCheck.setBackgroundColor(77000000);
    }


    //setAlarmボタン押下
    private void setAlarm(){
        /*
        * 繰り返しがONなら、
        * 繰り返しがOFFなら、SETボタンを押した瞬間にアラームマネージャに発動する時間を渡す
        *
        * */
        if(Repeat){
            Toast.makeText(getActivity(), "繰り返しだよ曜日指定してね", Toast.LENGTH_SHORT).show();
        }else{
            //Setボタンを押した瞬間の時刻をカレンダー型の変数に格納(2つ)
            Calendar currentTime = Calendar.getInstance();
            Calendar setTime = Calendar.getInstance();
            //setTimeの方はタイマーピッカーで選択した時間に書き換わる

            //setTime.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            //setTime.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            setTime.set(Calendar.HOUR_OF_DAY, alarmHoursNumberPicker.getValue());
            setTime.set(Calendar.MINUTE, alarmMinutesNumberPicker.getValue());

            setTime.set(Calendar.SECOND, 0);
            //currentTime(現在時刻)が、setTime(ユーザーにセットされた時刻)より先に行ってたらsetTimeを1日進める
            if(currentTime.getTimeInMillis() > setTime.getTimeInMillis()){
                setTime.add(Calendar.HOUR_OF_DAY,+24);
            };
            //アラームマネージャにsetTimeを渡す
            new AlarmManagerMine(getActivity()).noRepertAddAlarm(setTime.getTimeInMillis());
            Log.i("現在の時間","→"+currentTime.getTime());
            Log.i("セットされた時間","→"+setTime.getTime());
            Toast.makeText(getActivity(), "繰り返し無しで通知セット完了!→"+setTime.getTime(), Toast.LENGTH_SHORT).show();
        }
    }

    //closeAlarmボタン押下
    private void closeAlarm(){
        super.onDismiss(getDialog());
    }
}