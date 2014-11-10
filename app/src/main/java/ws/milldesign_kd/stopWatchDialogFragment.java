package ws.milldesign_kd;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
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

/*ストップウォッチモード*/
public class stopWatchDialogFragment extends DialogFragment {

    private long startTime;//デバイスが起動してからの経過ミリ秒
    private long stopTime;//デバイスが停止してからの経過ミリ秒
    private long delta = 0;//一度ストップウォッチを起動してから止めていた時間
    private long rapIntervals = 0;
    private boolean stopWatchIsRunning = false;//ストップウォッチが既に走っているかどうか
    private Timer timer = null;//一定時間に何かをするというのを司るクラス
    private Handler handler = new Handler();//ハンドラー
    private String result = "";
    private String rap = "";
    private List<String> rapTime = new ArrayList<String>();
    private TextView stopWatchText;//ビューのタイマー部分のテキスト
    private ListView rapStopWatchList;
    private Button startStopWatchButton,stopStopWatchButton,resetStopWatchButton,rapStopWatchButton,closeStopWatchButton;
    Utils utils = new Utils();

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

       /*
       * 各ボタンが押された処理の為stopWatchのビューの中のアイテムを取り出しておく
       * dialogのfindViewByIdを使うこと
       * */
        stopWatchText = (TextView)dialog.findViewById(R.id.stopWatchTimerText);
        startStopWatchButton = (Button) dialog.findViewById(R.id.startStopWatchButton);
        stopStopWatchButton = (Button) dialog.findViewById(R.id.stopStopWatchButton);
        resetStopWatchButton = (Button) dialog.findViewById(R.id.resetStopWatchButton);
        rapStopWatchButton = (Button) dialog.findViewById(R.id.rapStopWatchButton);
        rapStopWatchList = (ListView) dialog.findViewById(R.id.rapStopWatchList);
        closeStopWatchButton = (Button)dialog.findViewById(R.id.closeStopWatchButton);

        //startButton以外を無効に
        if(!stopWatchIsRunning){
            setStopWatchButtonState(true, false, false, false);
        }else{
            startStopWatchButton.setEnabled(utils.startStopWatchButtonEnabled);
            stopStopWatchButton.setEnabled(utils.stopStopWatchButtonEnabled);
            resetStopWatchButton.setEnabled(utils.resetStopWatchButtonEnabled);
            rapStopWatchButton.setEnabled(utils.rapStopWatchButtoEnabled);
        }


        //startボタンのリスナ
        dialog.findViewById(R.id.startStopWatchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopWatch();
            }
        });
        //stopボタンのリスナ
        dialog.findViewById(R.id.stopStopWatchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStopWatch();
            }
        });
        //resetボタンのリスナ
        dialog.findViewById(R.id.resetStopWatchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetStopWatch();
            }
        });
        //rapボタンのリスナ
        dialog.findViewById(R.id.rapStopWatchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rapStopWatch();
            }
        });
        //closeボタンのリスナ
        dialog.findViewById(R.id.closeStopWatchButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!(timer==null)){
                    closeStopWatch();
                }
                closeStopWatchInstantly();
            }
        });
        return dialog;
    }
    //startボタン押下
    private void startStopWatch() {
        if (!stopWatchIsRunning) {
            startTime = SystemClock.elapsedRealtime();//起動時刻
            stopWatchIsRunning = true;
        } else {
            delta += SystemClock.elapsedRealtime() - stopTime;//現在時刻からstopButton押下時間を引き、stopWatch停止間隔を得る
        }
        timer = new Timer(true);//引数をtrueでデーモンスレッドに
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
        setStopWatchButtonState(false, true, false, true);//stopとrapのみ有効に
    }
    //stopボタン押下
    private void stopStopWatch() {
        stopTime = SystemClock.elapsedRealtime();
        timer.cancel();//Timerのインスタンスを破棄
        setStopWatchButtonState(true, false, true, false);//startとresetのみ有効に
    }
    //resetボタン押下
    private void resetStopWatch() {
        stopWatchIsRunning = false;//ストップウォッチが走った形跡を無くす
        delta = 0;//当然停止していた時間も0に
        stopWatchText.setText("00:00:000");
        rapTime.clear();
        rapIntervals = 0;//ラップタイムの間隔も0に
        /*ArrayAdapterのコンストラクタの第一引数にはActivityを渡す*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_rowdata_text_view, rapTime);
        rapStopWatchList.setAdapter(adapter);//ラップのリストを消す
        setStopWatchButtonState(true, false, false, false);//startのみ有効に
    }
    //rapボタン押下
    private void rapStopWatch() {
        /*
        * startStopWatchのrunと同じ時刻を取得の後、
        * 前のrap時刻を引き、リストにアダプターをセットする
        * */
        SimpleDateFormat sdfrap = new SimpleDateFormat("mm:ss.SSS");
        rap = sdfrap.format(new Date(SystemClock.elapsedRealtime() - startTime - delta - rapIntervals));
        rapIntervals += SystemClock.elapsedRealtime() - startTime - delta - rapIntervals;
        rapTime.add(rap);
        /*ArrayAdapterのコンストラクタの第一引数にはActivityを渡す*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.activity_rowdata_text_view,rapTime);
        rapStopWatchList.setAdapter(adapter);
    }
    //closeボタン押下
    private void closeStopWatch() {
        utils.stopWatchButtonEnabledBank(startStopWatchButton.isEnabled(),stopStopWatchButton.isEnabled(),resetStopWatchButton.isEnabled(),rapStopWatchButton.isEnabled());
        getDialog().hide();
    }
    /*何もしないでcloseボタン押下*/
    private void closeStopWatchInstantly(){
        super.onDismiss(getDialog());//このダイアログを終了
    }
    /*ストップウォッチのボタンの有効無効設定*/
    private void setStopWatchButtonState(boolean start, boolean stop, boolean reset, boolean rap) {
        startStopWatchButton.setEnabled(start);
        stopStopWatchButton.setEnabled(stop);
        resetStopWatchButton.setEnabled(reset);
        rapStopWatchButton.setEnabled(rap);
    }
}