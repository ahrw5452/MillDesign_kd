package ws.milldesign_kd;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import android.widget.Toast;

/*タイマーモード*/
public class TimerDialogFragment extends DialogFragment {
    private TextView timer;
    private Button startTimerButton,stopTimerButton,resetTimerButton,closeTimerButton;
    private NumberPicker hNumberPicker,mNumberPicker,sNumberPicker;
    private boolean pose = false;
    private TimerCountDown tcd;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.activity_timer_mode);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timer = (TextView)dialog.findViewById(R.id.timerView);
        startTimerButton =(Button)dialog.findViewById(R.id.startTimerButton);
        stopTimerButton =(Button)dialog.findViewById(R.id.stopTimerButton);
        resetTimerButton = (Button)dialog.findViewById(R.id.resetTimerButton);
        hNumberPicker = (NumberPicker)dialog.findViewById(R.id.hoursNumberPicker);
        mNumberPicker = (NumberPicker)dialog.findViewById(R.id.minutesNumberPicker);
        sNumberPicker = (NumberPicker)dialog.findViewById(R.id.secondsNumberPicker);
        closeTimerButton = (Button)dialog.findViewById(R.id.closeTimerButton);
        hNumberPicker.setMaxValue(99);
        mNumberPicker.setMaxValue(59);
        sNumberPicker.setMaxValue(59);
        setTimerButtonState(true, false, false,true);

        startTimerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!pose){
                    tcd = new TimerCountDown((hNumberPicker.getValue()*60*60*1000+mNumberPicker.getValue()*60*1000+sNumberPicker.getValue()*1000),1000);
                    tcd.start();//カウントダウンスタート
                }else {
                    String p = timer.getText().toString();
                    String[] poseTime = p.split(":",0);
                    tcd = new TimerCountDown((Integer.parseInt (poseTime[0])*60*60+Integer.parseInt(poseTime[1])*60+Integer.parseInt(poseTime[2]))*1000,1000);
                    tcd.start();//カウントダウンリスタート
                }
                setTimerButtonState(false,true,false,false);
            }
        });
        stopTimerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                tcd.cancel();
                pose = true;
                setTimerButtonState(true, false, true,false);
            }
        });
        resetTimerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                reset("リセット");
            }
        });
        closeTimerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(!(tcd==null)){
                    tcd.cancel();
                }
                closeTimer();
            }
        });
        return dialog;
    }
    private void reset(String s){
        Toast.makeText(getActivity(),s, Toast.LENGTH_LONG).show();
        timer.setText("00:00:00");
        pose = false;
        setTimerButtonState(true, false, false,true);
    }
    private void closeTimer(){
        super.onDismiss(getDialog());//このダイアログを終了
    }
    private void setTimerButtonState(boolean start,boolean stop,boolean reset,boolean picker){
        startTimerButton.setEnabled(start);
        stopTimerButton.setEnabled(stop);
        resetTimerButton.setEnabled(reset);
        hNumberPicker.setEnabled(picker);
        mNumberPicker.setEnabled(picker);
        sNumberPicker.setEnabled(picker);
    }

    private class TimerCountDown extends CountDownTimer {
        public TimerCountDown(long millis, long countDownInterval) {
            super(millis, countDownInterval);
        }
        @Override
        public void onFinish() {
            reset("時間ですぜ");
        }
        @Override
        public void onTick(long millis) {
            /* インターバル(countDownInterval)毎に呼ばれる*/
            if((millis/1000%60)<10){//
                if((millis/1000/60)-60*(millis/1000/60/60)<10){
                    timer.setText(Long.toString(millis/1000/60/60) + ":0" + Long.toString((millis/1000/60)-60*(millis/1000/60/60)) + ":0" + Long.toString(millis/1000%60));
                }else {
                    timer.setText(Long.toString(millis/1000/60/60) + ":" + Long.toString((millis/1000/60)-60*(millis/1000/60/60)) + ":0" + Long.toString(millis/1000%60));
                }
            }else {
                if((millis/1000/60)-60*(millis/1000/60/60)<10){
                    timer.setText(Long.toString(millis/1000/60/60) + ":0" + Long.toString((millis/1000/60)-60*(millis/1000/60/60)) + ":" + Long.toString(millis/1000%60));
                }else {
                    timer.setText(Long.toString(millis/1000/60/60) + ":" + Long.toString((millis/1000/60)-60*(millis/1000/60/60)) + ":" + Long.toString(millis/1000%60));
                }
            }
        }
    }
}