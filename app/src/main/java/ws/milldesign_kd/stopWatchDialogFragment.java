package ws.milldesign_kd;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StopWatchDialogFragment extends DialogFragment {

    private long startTime,stopTime,delta,rapIntervals = 0;
    private String result,rap = "";
    private boolean stopWatchIsRunning = false;
    private Timer timer = null;
    private Handler handler = new Handler();
    private List<String> rapTime = new ArrayList<String>();
    private Utils utils = new Utils();
    private TextView stopWatchText;
    private ListView rapStopWatchList;
    private Button startStopWatchButton,stopStopWatchButton,resetStopWatchButton,rapStopWatchButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*
        * getActivity()でこのフラグメントを呼び出したActivityのインスタンスにアクセス
        * Dialogのインスタンス生成時に自分を呼び出したActivityを引数にして関連したインスタンスとして出現する
        */
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.activity_stop_watch_mode);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        stopWatchText = (TextView)dialog.findViewById(R.id.stopWatchTimerText);
        startStopWatchButton = (Button) dialog.findViewById(R.id.startStopWatchButton);
        stopStopWatchButton = (Button) dialog.findViewById(R.id.stopStopWatchButton);
        resetStopWatchButton = (Button) dialog.findViewById(R.id.resetStopWatchButton);
        rapStopWatchButton = (Button) dialog.findViewById(R.id.rapStopWatchButton);
        rapStopWatchList = (ListView) dialog.findViewById(R.id.rapStopWatchList);

        //ダイアログが過去に開かれていればその状態を引き継ぐ
        if(!stopWatchIsRunning){
            setStopWatchButtonState(true, false, false, false);
        }else{
            stopWatchText.setText(utils.stopWatchTextCurrent);
            startStopWatchButton.setEnabled(utils.startStopWatchButtonEnabled);
            stopStopWatchButton.setEnabled(utils.stopStopWatchButtonEnabled);
            resetStopWatchButton.setEnabled(utils.resetStopWatchButtonEnabled);
            rapStopWatchButton.setEnabled(utils.rapStopWatchButtonEnabled);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.activity_rowdata_text_view,utils.rapTimeCurrent);
            rapStopWatchList.setAdapter(adapter);
        }

        //startボタンのリスナ
        dialog.findViewById(R.id.startStopWatchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startStopWatch();}
        });
        //stopボタンのリスナ
        dialog.findViewById(R.id.stopStopWatchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {stopStopWatch();}
        });
        //resetボタンのリスナ
        dialog.findViewById(R.id.resetStopWatchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {resetStopWatch();}
        });
        //rapボタンのリスナ
        dialog.findViewById(R.id.rapStopWatchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {rapStopWatch();}
        });
        //closeボタンのリスナ
        dialog.findViewById(R.id.closeStopWatchButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!(timer==null)){closeStopWatch();}
                closeStopWatchInstantly();
            }
        });
        return dialog;
    }

    //startボタン押下
    private void startStopWatch() {
        if (!stopWatchIsRunning) {
            startTime = SystemClock.elapsedRealtime();
            stopWatchIsRunning = true;
        } else {
            //現在時刻からstopButton押下時間を引き、stopWatch停止間隔を得る
            delta += SystemClock.elapsedRealtime() - stopTime;
        }
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    /*デバイスの起動からの経過時間をonCreateで取得しているので
                    * runの中で、常に現在時刻からその時間を引き表示させる
                    * activityの方のタイマー部分はテキストなので、値はString型で渡す
                    * */
                    @Override
                    public void run() {
                        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
                        /*
                        * 10ミリ秒ごとに現在時刻から起動時刻を引く
                        * stopWatch停止間隔があればその時間も引く(delta)
                        */
                        result = sdf.format(new Date(SystemClock.elapsedRealtime() - startTime - delta));
                        stopWatchText.setText(result);//activityのテキストに10ミリ秒ごとにセット
                    }
                });
            }
        }, 0, 10);
        setStopWatchButtonState(false, true, false, true);
    }

    //stopボタン押下
    private void stopStopWatch() {
        stopTime = SystemClock.elapsedRealtime();
        //Timerのインスタンスを破棄
        timer.cancel();
        setStopWatchButtonState(true, false, true, false);
    }

    //resetボタン押下
    private void resetStopWatch() {
        stopWatchIsRunning = false;
        delta = 0;
        stopWatchText.setText("00:00:000");
        rapTime.clear();
        rapIntervals = 0;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_rowdata_text_view, rapTime);
        rapStopWatchList.setAdapter(adapter);
        setStopWatchButtonState(true, false, false, false);
    }

    //rapボタン押下
    private void rapStopWatch() {
        /*
        * startStopWatchのrunと同じ時刻を取得の後、
        * 前のrap時刻を引き、リストにアダプターをセットする
        */
        SimpleDateFormat sdfrap = new SimpleDateFormat("mm:ss.SSS");
        rap = sdfrap.format(new Date(SystemClock.elapsedRealtime() - startTime - delta - rapIntervals));
        rapIntervals += SystemClock.elapsedRealtime() - startTime - delta - rapIntervals;
        rapTime.add(0,rap);
        //ArrayAdapterのコンストラクタの第一引数にはActivityを渡す
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.activity_rowdata_text_view,rapTime);
        rapStopWatchList.setAdapter(adapter);
    }

    //closeボタン押下
    private void closeStopWatch() {
        /*
        * StopWatchの状態を預ける
        * 現在のタイム(runが裏で走っていれば瞬殺で書き換わりstop状態なら止めた時間で残る)
        * 各ボタンの有効無効状態
        * rapのタイム
        */
        utils.stopWatchEnabledBank(result,
                                   startStopWatchButton.isEnabled(),
                                   stopStopWatchButton.isEnabled(),
                                   resetStopWatchButton.isEnabled(),
                                   rapStopWatchButton.isEnabled(),
                                   rapTime);
        //ダイアログを隠す
        getDialog().hide();
    }

    //何もしないでcloseボタン押下
    private void closeStopWatchInstantly(){super.onDismiss(getDialog());}

    //ストップウォッチのボタンの有効無効設定
    private void setStopWatchButtonState(boolean start, boolean stop, boolean reset, boolean rap) {
        startStopWatchButton.setEnabled(start);
        stopStopWatchButton.setEnabled(stop);
        resetStopWatchButton.setEnabled(reset);
        rapStopWatchButton.setEnabled(rap);
    }
}