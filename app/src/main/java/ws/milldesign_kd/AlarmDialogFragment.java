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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmDialogFragment extends DialogFragment {

    private boolean Repeat = false;
    private Calendar setTime;
    private List<Calendar> setTimeList = new ArrayList<Calendar>();
    private List<String> alarmSetTimeListString = new ArrayList<String>();

    private TimePicker alarmTimePicker;
    private Switch alarmRepeatSwitch;
    private CheckBox sunCheck,monCheck,tueCheck,wedCheck,thuCheck,friCheck,satCheck;
    private ListView alarmSetList;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.activity_alarm_mode);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Viewの各ウィジェットを取得
        alarmTimePicker = (TimePicker)dialog.findViewById(R.id.alarmTimePicker);
        alarmRepeatSwitch = (Switch)dialog.findViewById(R.id.alarmRepeatSwitch);
        sunCheck = (CheckBox)dialog.findViewById(R.id.sunday);
        monCheck = (CheckBox)dialog.findViewById(R.id.monday);
        tueCheck = (CheckBox)dialog.findViewById(R.id.tuesday);
        wedCheck = (CheckBox)dialog.findViewById(R.id.wednesday);
        thuCheck = (CheckBox)dialog.findViewById(R.id.thursday);
        friCheck = (CheckBox)dialog.findViewById(R.id.friday);
        satCheck = (CheckBox)dialog.findViewById(R.id.saturday);
        alarmSetList = (ListView)dialog.findViewById(R.id.alarmSetList);

        //繰り返しのON/OFFスイッチリスナー
        alarmRepeatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxSetEnabled(isChecked);
                if(isChecked){dayOfTheWeekChoice();}
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

    //setAlarmボタン押下
    private void setAlarm(){
        //まずはタイムピッカーでセットされた値をsetTimeにする
        setTime = Utils.alarmSetTime(alarmTimePicker.getCurrentHour(),alarmTimePicker.getCurrentMinute());
        if(Repeat){//繰り返しがONなら、
            Toast.makeText(getActivity(), "繰り返しだよ曜日指定してね", Toast.LENGTH_SHORT).show();
        }else{//繰り返しがOFFならsetTimeをアラームマネージャに渡す
            new AlarmManagerMine(getActivity()).noRepertAddAlarm(setTime.getTimeInMillis());
        };
        //setTimeをListに格納し並び替えて文字列にしてListViewのアダプタにセットする
        setTimeList.add(setTime);
        setTimeList = Utils.sortAlarmSetList(setTimeList);
        alarmSetTimeListString = Utils.calendarChangeString(setTimeList);
        AlarmSetListAdapter asla = new AlarmSetListAdapter(getActivity(),alarmSetTimeListString);
        alarmSetList.setAdapter(asla);
    }

    //closeAlarmボタン押下
    private void closeAlarm(){super.onDismiss(getDialog());}

    //繰り返しスイッチON
    private void dayOfTheWeekChoice(){


    }

    //繰り返しスイッチの状態
    private void checkBoxSetEnabled(boolean Enabled){
        Repeat = Enabled;
        sunCheck.setEnabled(Enabled);
        monCheck.setEnabled(Enabled);
        tueCheck.setEnabled(Enabled);
        wedCheck.setEnabled(Enabled);
        thuCheck.setEnabled(Enabled);
        friCheck.setEnabled(Enabled);
        satCheck.setEnabled(Enabled);
    }
}