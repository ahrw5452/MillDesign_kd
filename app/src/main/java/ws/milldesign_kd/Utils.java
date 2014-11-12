package ws.milldesign_kd;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static String stopWatchTextCurrent;
    public static boolean startStopWatchButtonEnabled,stopStopWatchButtonEnabled,resetStopWatchButtonEnabled,rapStopWatchButtonEnabled = false;
    public static List<String> rapTimeCurrent = new ArrayList<String>();

    //アラームのセット画面でセットされた時間をUTC時刻にして返す
    public static Calendar alarmSetTime(int currentHour,int currentMinute){
        //Setボタンを押した瞬間の時刻をカレンダー型の変数に格納(2つ)
        Calendar currentTime = Calendar.getInstance();
        Calendar setTime = Calendar.getInstance();
        //setTimeの方はタイマーピッカーで選択した時間に書き換わる
        setTime.set(Calendar.HOUR_OF_DAY, currentHour);
        setTime.set(Calendar.MINUTE, currentMinute);
        setTime.set(Calendar.SECOND, 0);
        //currentTime(現在時刻)が、setTime(ユーザーにセットされた時刻)より先に行ってたらsetTimeを1日進める
        if(currentTime.getTimeInMillis() > setTime.getTimeInMillis()){
            setTime.add(Calendar.HOUR_OF_DAY,+24);
        };
        return setTime;
    }

    //ストップウォッチの状態を預かる
    public static void stopWatchEnabledBank(String stopWatchText,
                                            boolean startStopWatchButton,
                                            boolean stopStopWatchButton,
                                            boolean resetStopWatchButton,
                                            boolean rapStopWatchButton,
                                            List<String> rapTime){
        stopWatchTextCurrent =stopWatchText;
        startStopWatchButtonEnabled = startStopWatchButton;
        stopStopWatchButtonEnabled = stopStopWatchButton;
        resetStopWatchButtonEnabled = resetStopWatchButton;
        rapStopWatchButtonEnabled = rapStopWatchButton;
        rapTimeCurrent = rapTime;
    }

    public final static void actionBarUpsideDown(Activity activity) {
        View root = activity.getWindow().getDecorView();//watchViewのrootを取得
        dumpViewTree(root, "");
        View firstChild = ((ViewGroup) root).getChildAt(0);//rootの0番目の子ViewをfirstChildとして取得/これがリニアレイアウトじゃないといけない？

        if (firstChild instanceof ViewGroup) {//firstChildがViewGroupで実装されていれば。
            ViewGroup viewGroup = (ViewGroup) firstChild;//なんでこれやってんの
            List<View> views = findViewsWithClassName(root,
                    "com.android.internal.widget.ActionBarContainer");//ActionBarのビューをroot以下で探し出してlistに格納してる
            List<View> viewActionBarOverlayLayout = findViewsWithClassName(root,
                    "com.android.internal.widget.ActionBarOverlayLayout");
            if (!views.isEmpty()) {
                for (View vv : views) {
                    viewGroup.removeView(vv);
                }
                for (View vv : views) {
                    viewGroup.addView(vv);
                }
            }
        } else {
            Log.e(TAG, "first child is not ViewGroup.");
        }
    }

    private static List<View> findViewsWithClassName(View v, String className) {
        List<View> views = new ArrayList<View>();
        findViewsWithClass(v, className, views);
        return views;
    }

    @SuppressWarnings("unchecked")
    private static <T extends View> void findViewsWithClass(View v, String clazz, List<T> views) {
        if (v.getClass().getName().equals(clazz)) {
            views.add((T) v);
        }
        if (v instanceof ViewGroup) {
            ViewGroup g = (ViewGroup) v;
            for (int i = 0; i < g.getChildCount(); i++) {
                findViewsWithClass(g.getChildAt(i), clazz, views);
            }
        }
    }

    /*Viewの階層構造をダンプするスニペット*/
    public static void dumpViewTree(View v, String padding){
        Log.d("だんぷ", padding + v.getClass().getName());
        if(v instanceof ViewGroup){
            ViewGroup g = (ViewGroup)v;
            for(int i = 0; i < g.getChildCount(); i++){
                dumpViewTree(g.getChildAt(i), padding+" ");
            }
        }
    }
}
