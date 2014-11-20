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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.List;

public class AlarmDialogFragment extends DialogFragment {

    private boolean Repeat = false;

    private Calendar setTime;
    private List<Calendar> setTimeRepeatList = new ArrayList<Calendar>();
    //右カラム用
    private List<Calendar> setTimeList = new ArrayList<Calendar>();
    private List<String> setTimeListString = new ArrayList<String>();

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
        //setTimeを適正な値にし、繰り返しONならsetTimeと曜日のチェック状態を,繰り返しがOFFならsetTimeをアラームマネージャに渡す
        if(Repeat){
            /*
            * 繰り返し曜日を指定した、setTimeリストを受け取る
            * このリストはsetAlarmを押した日が属する1週間の中で、選ばれた曜日の選ばれた時間のカレンダー情報を持つ
            */
            setTimeRepeatList = Utils.repeatAlarmSetTime(alarmTimePicker.getCurrentHour(),alarmTimePicker.getCurrentMinute(),dayOfTheWeekCheckBoxIsCecked());
            for(Calendar cal:setTimeRepeatList){Log.i("setTimeRepeatListに","含まれる時間は→"+cal.getTime());}
            new AlarmManagerMine(getActivity()).repertAddAlarm(setTimeRepeatList);
        }else{
            setTime = Utils.noRepeatAlarmSetTime(alarmTimePicker.getCurrentHour(),alarmTimePicker.getCurrentMinute());
            new AlarmManagerMine(getActivity()).noRepertAddAlarm(setTime);
        };
        //setTimeをListに格納し並び替えて文字列にしてListViewのアダプタにセットする
        setTimeList.add(setTime);
        setTimeList = Utils.sortAlarmSetList(setTimeList);
        setTimeListString = Utils.calendarChangeString(setTimeList);
        AlarmSetListAdapter asla = new AlarmSetListAdapter(getActivity(),setTimeListString);
        alarmSetList.setAdapter(asla);





    }
    //closeAlarmボタン押下
    private void closeAlarm(){
        new AlarmManagerMine(getActivity()).alarmCancel(setTime);
        //super.onDismiss(getDialog());
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
    //booleanは真か偽かの二値。boolean[]やList<Boolean>はメモリーの使用効率が悪い,そこでBitSetを使う
    private BitSet dayOfTheWeekCheckBoxIsCecked(){
        BitSet Checked = new BitSet(32);
        Checked.set(0,sunCheck.isChecked());
        Checked.set(1,monCheck.isChecked());
        Checked.set(2,tueCheck.isChecked());
        Checked.set(3,wedCheck.isChecked());
        Checked.set(4,thuCheck.isChecked());
        Checked.set(5,friCheck.isChecked());
        Checked.set(6,satCheck.isChecked());
        return Checked;
    }
}