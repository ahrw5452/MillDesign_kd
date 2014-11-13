package ws.milldesign_kd;

import ws.milldesign_kd.Constants;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

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

    //setTimeの履歴を時間順に並び替えて返す
    public static List<Calendar> sortAlarmSetList(List<Calendar> setTimeList){

        List<Calendar> calList = new ArrayList<Calendar>();
        int count = 0;

        switch(setTimeList.size()){
            case 1:
                Log.i("Log", "要素が一つなので並び替えの必要無し,そのまま値を返します");
                return setTimeList;
            default:
                //まず引数としてもらったsetTimeListは複数の日にまたがっているので、時間と分の情報だけを抜き出して、カレンダーリストに追加(年月日と秒は適当な値に統一)
                for(Calendar setTime:setTimeList){
                    Calendar cal = Calendar.getInstance();
                    cal.set(2000,Calendar.JANUARY,1,setTime.get(setTime.HOUR_OF_DAY),setTime.get(setTime.MINUTE),0);
                    calList.add(cal);
                }
                //次に作業用カレンダーリストを並び替え
                for(Calendar cal:calList){
                    //同じものがあり、自分自身との比較じゃない時、ついかしたやつは消す
                    if(
                       calList.get(calList.size()-1).getTime().toString().equals(cal.getTime().toString())
                       &&
                       !(count==(calList.size()-1))
                       ){
                            calList.remove(calList.size()-1);
                            break;
                    }
                    //引っ張り出した要素が、今回Setした要素より大きかったら
                    if(calList.get(calList.size()-1).getTimeInMillis()<cal.getTimeInMillis()){
                        //Setした値より大きい要素のいた位置にSetした値を追加しもとのやつは消す
                        calList.add(count,calList.get(calList.size()-1));
                        calList.remove(calList.size()-1);
                        break;
                    }
                    count++;
                }
        }
        return calList;
    }

    //カレンダー型の配列を、時と分のみの文字列型の配列に変換する
    public static List<String> calendarChangeString(List<Calendar> setTimeListAfterSorting){
        List<String> alarmSetTimeListString = new ArrayList<String>();


        //とりあえず
        alarmSetTimeListString.add("test");



        return alarmSetTimeListString;
    }


    /*
    //アラームセット画面の右側の領域に保存するリストを、時間順に並び替えるメソッド
    public static List<String> sortingAlarmSetList(List<String> alarmSetTimesBeforeSorting) {
        //まず新規でフィールドインスタンスを作る(以前の参照を無くすため)
        alarmSetTimesAfterSortingInUtils = new ArrayList<String>();

        //ソート前のデータの要素数を検証し、複数あれば並び替え処理を走らせる
        switch (alarmSetTimesBeforeSorting.size()) {

            case 1:
                Log.i("Log", "要素が一つなので並び替えの必要無し,そのまま値を返します");
                return alarmSetTimesBeforeSorting;
            default://めいん--------------------------------------------------------------------------------------------------------------------------------

                //ミリ秒に直した時間を入れる数値型の配列を用意
                ArrayList<Long> millisVerSetTimes = new ArrayList<Long>();

                //まず一度、受け取った全ての時間をUTC時刻のミリ秒にする
                Log.i("既に複数ある時間データをミリ秒に直す", "for文1個目始まるよ");
                for(String setTime:alarmSetTimesBeforeSorting){
                    String[] setTimeDivide = setTime.split(":");

                    Log.i("ちょいと","今回"+setTimeDivide[0]+":::"+setTimeDivide[1]);




                    //とりあえず今の時間をとった後そこに2000年1月1日のセットした時間にカレンダーを設定
                    Calendar cal = Calendar.getInstance();
                    cal.set(2000,Calendar.JANUARY,1,Integer.parseInt(setTimeDivide[0]),Integer.parseInt(setTimeDivide[1]),0);

                    long millis = cal.getTimeInMillis()-Constants.MILLENNIUM;
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTimeInMillis(millis);

                    //配列に追加
                    millisVerSetTimes.add(millis);

                    Log.i("カレンダーの値",""+cal.getTime());
                    Log.i("今追加したミリ",""+millisVerSetTimes.get(millisVerSetTimes.size()-1));
                    Log.i("ミリから戻したカレンダーの値",""+cal2.getTime());
                }
                Log.i("既に複数ある時間データをミリ秒に直した", "for文1個目終わり");




                int count = 0;
                Log.i("ミリ秒に直したデータを並び替える", "for文2個目始まるよ");
                for(long millisVesSetTime:millisVerSetTimes){
                    Log.i("HEY", "今回セットした値→"+millisVerSetTimes.get(millisVerSetTimes.size()-1));
                    Log.i("HEY", "比較する値→"+millisVesSetTime);
                    if((millisVerSetTimes.get(millisVerSetTimes.size()-1))<millisVesSetTime){
                        Log.i("今回セットした値と比較する値を比較した結果", "比較する値のほうがセットした値よりでかい");
                        millisVerSetTimes.add(count,millisVerSetTimes.get(millisVerSetTimes.size()-1));
                        millisVerSetTimes.remove(millisVerSetTimes.size()-1);
                        break;
                    }
                    count++;
                }
                Log.i("ミリ秒に直したデータを並び替える", "for文2個目終わり");



                Log.i("ミリ秒のデータを時間に戻す", "for文3個目始まるよ");
                for(long millisVerSetTime:millisVerSetTimes){

                    Calendar cal =Calendar.getInstance();
                    cal.setTimeInMillis(millisVerSetTime);
                    String setTime =cal.HOUR_OF_DAY+":"+cal.MINUTE;
                    alarmSetTimesAfterSortingInUtils.add(setTime);
                    Log.i("戻した時間",""+cal.getTime());
                    //alarmSetTimesAfterSortingInUtils.add();
                }
                Log.i("ミリ秒のデータを時間に戻した", "for文3個目終わり");

                //めいん--------------------------------------------------------------------------------------------------------------------------------
                return alarmSetTimesAfterSortingInUtils;
        }
    }
    */

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
