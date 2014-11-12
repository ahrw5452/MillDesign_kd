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

    public static List<String> alarmSetTimesAfterSortingInUtils;

    public static String stopWatchTextCurrent;
    public static boolean startStopWatchButtonEnabled,stopStopWatchButtonEnabled,resetStopWatchButtonEnabled,rapStopWatchButtonEnabled = false;
    public static List<String> rapTimeCurrent = new ArrayList<String>();

    //アラームセット画面でセットされた時間をUTC時刻にして返す
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

    //アラームセット画面の右側の領域に保存するリストを、時間順に並び替えるメソッド
    public static List<String> sortingAlarmSetList(List<String> alarmSetTimesBeforeSorting) {
        //まず新規でフィールドインスタンスを作る(以前の参照を無くすため)
        alarmSetTimesAfterSortingInUtils = new ArrayList<String>();
        //とりあえず並び替え前のリストを入れる
        alarmSetTimesAfterSortingInUtils = alarmSetTimesBeforeSorting;

        //ソート前のデータの要素数を検証し、複数あれば並び替え処理を走らせる
        switch (alarmSetTimesBeforeSorting.size()) {
            case 0:
                Log.i("Log", "要素が無い・・・？");
                return alarmSetTimesAfterSortingInUtils;
            case 1:
                Log.i("Log", "要素が一つなので並び替えの必要無し,そのまま値を返します");
                return alarmSetTimesAfterSortingInUtils;
            default:
                //まず一度、全ての時間をUTC時刻のミリ秒にする
                for(String setTime:alarmSetTimesAfterSortingInUtils){
                    String[] setTimeDivide = setTime.split(":");
                    //とりあえず今の時間
                    Calendar cal = Calendar.getInstance();
                    //2000年1月1日のセットした時間にカレンダーを設定
                    cal.set(2000,Calendar.JANUARY,1,Integer.parseInt(setTimeDivide[0]),Integer.parseInt(setTimeDivide[1]));
                    long Millis = cal.getTimeInMillis();


                }



                /*
                //追加した要素を取り出して、同じものがあれば別にあれば0番目の要素を消す、無ければ適した位置に移動
                String[] addAry = alarmSetTimesBeforeSorting.get(alarmSetTimesAfterSortingInUtils.size()-1).split(":");
                int count = 0;
                Log.i("Log", "for文始まるよ");
                for (String alarmSetTime : alarmSetTimesBeforeSorting) {
                    if (!(count>=alarmSetTimesAfterSortingInUtils.size()-1)) {//0番目以外のコレクションを取り出した際にそれを0番目と比較する
                        Log.i("Log", "ここは複数回セットしてれば確実に通るよ。カウントの数字→"+count);
                        //比較対象を時間と分に分ける
                        String[] now = alarmSetTimesBeforeSorting.get(count).split(":");
                        Log.i("Log", "比較対象の時"+now[0]);
                        Log.i("Log", "今回セットした時間の時"+addAry[0]);

                        //比較対象の時間と0番目の時間を比較し、0番目のほうが小さければ素通り、同じならば分の比較
                        if (Integer.parseInt(now[0]) == Integer.parseInt(addAry[0])) {
                            Log.i("Log", "今回セットされた時間の方が同じなので分を比較します");
                            if (Integer.parseInt(now[1]) < Integer.parseInt(addAry[1])) {
                                Log.i("Log", "今回セットされた時間の方が分がでかいです");
                                //とりあえず今回セットしたのを消す
                                alarmSetTimesAfterSortingInUtils.remove(alarmSetTimesAfterSortingInUtils.size()-1);
                                //で、追加
                                alarmSetTimesAfterSortingInUtils.add(count,addAry[0]+":"+addAry[1]);
                                break;
                            }else{
                                Log.i("Log", "今回セットされた時間の方が分がちいさいのでここに");
                                //とりあえず今回セットしたのを消す
                                alarmSetTimesAfterSortingInUtils.remove(alarmSetTimesAfterSortingInUtils.size()-1);
                                //で、追加
                                alarmSetTimesAfterSortingInUtils.add(count,addAry[0]+":"+addAry[1]);
                                break;
                            }
                        }

                        //比較対象の時間と0番目の時間を比較し、0番目のほうが小さければ素通り、大きければ分の比較
                        if (Integer.parseInt(now[0]) <= Integer.parseInt(addAry[0])) {
                            Log.i("Log", "今回セットされた時間の方が時がでかいので分を比較します");
                            if (Integer.parseInt(now[1]) < Integer.parseInt(addAry[1])) {
                                Log.i("Log", "今回セットされた時間の方が分がでかいです");
                                //とりあえず今回セットしたのを消す
                                alarmSetTimesAfterSortingInUtils.remove(alarmSetTimesAfterSortingInUtils.size()-1);
                                //で、追加
                                alarmSetTimesAfterSortingInUtils.add(count,addAry[0]+":"+addAry[1]);
                                break;
                            }else{
                                Log.i("Log", "今回セットされた時間の方が分がちいさいのでここに");
                                //とりあえず今回セットしたのを消す
                                alarmSetTimesAfterSortingInUtils.remove(alarmSetTimesAfterSortingInUtils.size()-1);
                                //で、追加
                                alarmSetTimesAfterSortingInUtils.add(count,addAry[0]+":"+addAry[1]);
                                break;
                            }
                        }
                    }
                    count++;
                }
                Log.i("Log", "for文おわり");

                for (String alarmSetTimeAfter : alarmSetTimesAfterSortingInUtils) {
                    Log.i("Log", "このメソッドから返す値たち→"+alarmSetTimeAfter.toString());
                }
                */
                return alarmSetTimesAfterSortingInUtils;
        }
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
