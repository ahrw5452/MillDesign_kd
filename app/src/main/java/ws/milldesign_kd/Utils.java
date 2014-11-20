package ws.milldesign_kd;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.List;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    public static String stopWatchTextCurrent;
    public static boolean startStopWatchButtonEnabled,stopStopWatchButtonEnabled,resetStopWatchButtonEnabled,rapStopWatchButtonEnabled = false;
    public static List<String> rapTimeCurrent = new ArrayList<String>();
    //アラームセット画面でセットされた時間と繰り返し曜日をListにして返す
    public static List<Calendar> repeatAlarmSetTime(int currentHour,int currentMinute,BitSet dayOfTheWeekChecked){
        List<Calendar> setTimeRepeatList = new ArrayList<Calendar>();

        if(dayOfTheWeekChecked.get(0)){
            Calendar setTime = Calendar.getInstance();
            //このメソッドが走った曜日
            switch (setTime.get(Calendar.DAY_OF_WEEK)){
                case 1://日曜に日曜日をセットした場合は+-0日
                    break;
                case 2://月曜に日曜日をセットした場合は-1日
                    setTime.add(Calendar.DAY_OF_MONTH, -1);
                    break;
                case 3://火曜に日曜日をセットした場合は-2日
                    setTime.add(Calendar.DAY_OF_MONTH, -2);
                    break;
                case 4://水曜に日曜日をセットした場合は-3日
                    setTime.add(Calendar.DAY_OF_MONTH, -3);
                    break;
                case 5://木曜に日曜日をセットした場合は-4日
                    setTime.add(Calendar.DAY_OF_MONTH, -4);
                    break;
                case 6://金曜に日曜日をセットした場合は-5日
                    setTime.add(Calendar.DAY_OF_MONTH, -5);
                    break;
                case 7://土曜に日曜日をセットした場合は-6日
                    setTime.add(Calendar.DAY_OF_MONTH, -6);
                    break;
            }
            setTime.set(Calendar.HOUR_OF_DAY, currentHour);
            setTime.set(Calendar.MINUTE, currentMinute);
            setTime.set(Calendar.SECOND, 0);
            setTimeRepeatList.add(setTime);
        }

        if(dayOfTheWeekChecked.get(1)){
            Calendar setTime = Calendar.getInstance();
            //このメソッドが走った曜日
            switch (setTime.get(Calendar.DAY_OF_WEEK)){
                case 1://日曜に月曜日をセットした場合は+1日
                    setTime.add(Calendar.DAY_OF_MONTH, +1);
                    break;
                case 2://月曜に月曜日をセットした場合は+-0日
                    break;
                case 3://火曜に月曜日をセットした場合は-1日
                    setTime.add(Calendar.DAY_OF_MONTH, -1);
                    break;
                case 4://水曜に月曜日をセットした場合は-2日
                    setTime.add(Calendar.DAY_OF_MONTH, -2);
                    break;
                case 5://木曜に月曜日をセットした場合は-3日
                    setTime.add(Calendar.DAY_OF_MONTH, -3);
                    break;
                case 6://金曜に月曜日をセットした場合は-4日
                    setTime.add(Calendar.DAY_OF_MONTH, -4);
                    break;
                case 7://土曜に月曜日をセットした場合は-5日
                    setTime.add(Calendar.DAY_OF_MONTH, -5);
                    break;
            }
            setTime.set(Calendar.HOUR_OF_DAY, currentHour);
            setTime.set(Calendar.MINUTE, currentMinute);
            setTime.set(Calendar.SECOND, 0);
            setTimeRepeatList.add(setTime);
        }

        if(dayOfTheWeekChecked.get(2)){
            Calendar setTime = Calendar.getInstance();
            //このメソッドが走った曜日
            switch (setTime.get(Calendar.DAY_OF_WEEK)){
                case 1://日曜に火曜日をセットした場合は+2日
                    setTime.add(Calendar.DAY_OF_MONTH, +2);
                    break;
                case 2://月曜に火曜日をセットした場合は+1日
                    setTime.add(Calendar.DAY_OF_MONTH, +1);
                    break;
                case 3://火曜に火曜日をセットした場合は+-0日
                    break;
                case 4://水曜に火曜日をセットした場合は-1日
                    setTime.add(Calendar.DAY_OF_MONTH, -1);
                    break;
                case 5://木曜に火曜日をセットした場合は-2日
                    setTime.add(Calendar.DAY_OF_MONTH, -2);
                    break;
                case 6://金曜に火曜日をセットした場合は-3日
                    setTime.add(Calendar.DAY_OF_MONTH, -3);
                    break;
                case 7://土曜に火曜日をセットした場合は-4日
                    setTime.add(Calendar.DAY_OF_MONTH, -4);
                    break;
            }
            setTime.set(Calendar.HOUR_OF_DAY, currentHour);
            setTime.set(Calendar.MINUTE, currentMinute);
            setTime.set(Calendar.SECOND, 0);
            setTimeRepeatList.add(setTime);
        }

        if(dayOfTheWeekChecked.get(3)){
            Calendar setTime = Calendar.getInstance();
            //このメソッドが走った曜日
            switch (setTime.get(Calendar.DAY_OF_WEEK)){
                case 1://日曜に水曜日をセットした場合は+3日
                    setTime.add(Calendar.DAY_OF_MONTH, +3);
                    break;
                case 2://月曜に水曜日をセットした場合は+2日
                    setTime.add(Calendar.DAY_OF_MONTH, +2);
                    break;
                case 3://火曜に水曜日をセットした場合は+1日
                    setTime.add(Calendar.DAY_OF_MONTH, +1);
                    break;
                case 4://水曜に水曜日をセットした場合は+-0日
                    break;
                case 5://木曜に水曜日をセットした場合は-1日
                    setTime.add(Calendar.DAY_OF_MONTH, -1);
                    break;
                case 6://金曜に水曜日をセットした場合は-2日
                    setTime.add(Calendar.DAY_OF_MONTH, -2);
                    break;
                case 7://土曜に水曜日をセットした場合は-3日
                    setTime.add(Calendar.DAY_OF_MONTH, -3);
                    break;
            }
            setTime.set(Calendar.HOUR_OF_DAY, currentHour);
            setTime.set(Calendar.MINUTE, currentMinute);
            setTime.set(Calendar.SECOND, 0);
            setTimeRepeatList.add(setTime);
        }

        if(dayOfTheWeekChecked.get(4)){
            Calendar setTime = Calendar.getInstance();
            //このメソッドが走った曜日
            switch (setTime.get(Calendar.DAY_OF_WEEK)){
                case 1://日曜に木曜日をセットした場合は+4日
                    setTime.add(Calendar.DAY_OF_MONTH, +4);
                    break;
                case 2://月曜に木曜日をセットした場合は+3日
                    setTime.add(Calendar.DAY_OF_MONTH, +3);
                    break;
                case 3://火曜に木曜日をセットした場合は+2日
                    setTime.add(Calendar.DAY_OF_MONTH, +2);
                    break;
                case 4://水曜に木曜日をセットした場合は+1日
                    setTime.add(Calendar.DAY_OF_MONTH, +1);
                    break;
                case 5://木曜に木曜日をセットした場合は+-0日
                    break;
                case 6://金曜に木曜日をセットした場合は-1日
                    setTime.add(Calendar.DAY_OF_MONTH, -1);
                    break;
                case 7://土曜に木曜日をセットした場合は-2日
                    setTime.add(Calendar.DAY_OF_MONTH, -2);
                    break;
            }
            setTime.set(Calendar.HOUR_OF_DAY, currentHour);
            setTime.set(Calendar.MINUTE, currentMinute);
            setTime.set(Calendar.SECOND, 0);
            setTimeRepeatList.add(setTime);
        }

        if(dayOfTheWeekChecked.get(5)){
            Calendar setTime = Calendar.getInstance();
            //このメソッドが走った曜日
            switch (setTime.get(Calendar.DAY_OF_WEEK)){
                case 1://日曜に金曜日をセットした場合は+5日
                    setTime.add(Calendar.DAY_OF_MONTH, +5);
                    break;
                case 2://月曜に金曜日をセットした場合は+4日
                    setTime.add(Calendar.DAY_OF_MONTH, +4);
                    break;
                case 3://火曜に金曜日をセットした場合は+3日
                    setTime.add(Calendar.DAY_OF_MONTH, +3);
                    break;
                case 4://水曜に金曜日をセットした場合は+2日
                    setTime.add(Calendar.DAY_OF_MONTH, +2);
                    break;
                case 5://木曜に金曜日をセットした場合は+1日
                    setTime.add(Calendar.DAY_OF_MONTH, +1);
                    break;
                case 6://金曜に金曜日をセットした場合は+-0日
                    break;
                case 7://土曜に金曜日をセットした場合は-1日
                    setTime.add(Calendar.DAY_OF_MONTH, -1);
                    break;
            }
            setTime.set(Calendar.HOUR_OF_DAY, currentHour);
            setTime.set(Calendar.MINUTE, currentMinute);
            setTime.set(Calendar.SECOND, 0);
            setTimeRepeatList.add(setTime);
        }

        if(dayOfTheWeekChecked.get(6)){
            Calendar setTime = Calendar.getInstance();
            //このメソッドが走った曜日
            switch (setTime.get(Calendar.DAY_OF_WEEK)){
                case 1://日曜に土曜日をセットした場合は+6日
                    setTime.add(Calendar.DAY_OF_MONTH, +6);
                    break;
                case 2://月曜に土曜日をセットした場合は+5日
                    setTime.add(Calendar.DAY_OF_MONTH, +5);
                    break;
                case 3://火曜に土曜日をセットした場合は+4日
                    setTime.add(Calendar.DAY_OF_MONTH, +4);
                    break;
                case 4://水曜に土曜日をセットした場合は+3日
                    setTime.add(Calendar.DAY_OF_MONTH, +3);
                    break;
                case 5://木曜に土曜日をセットした場合は+2日
                    setTime.add(Calendar.DAY_OF_MONTH, +2);
                    break;
                case 6://金曜に土曜日をセットした場合は+1日
                    setTime.add(Calendar.DAY_OF_MONTH, +1);
                    break;
                case 7://土曜に土曜日をセットした場合は+-0日
                    break;
            }
            setTime.set(Calendar.HOUR_OF_DAY, currentHour);
            setTime.set(Calendar.MINUTE, currentMinute);
            setTime.set(Calendar.SECOND, 0);
            setTimeRepeatList.add(setTime);
        }
        return setTimeRepeatList;
    }
    //アラームセット画面でセットされた時間をUTC時刻にして返す(繰り返し無しの場合)
    public static Calendar noRepeatAlarmSetTime(int currentHour,int currentMinute){
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
                    //同じものがあり、自分自身との比較じゃない時、追加したやつは消す
                    if(
                       calList.get(calList.size()-1).getTime().toString().equals(cal.getTime().toString())
                       &&
                       !(count==(calList.size()-1))
                       ){
                            calList.remove(calList.size()-1);
                            break;
                    }
                    //引っ張り出した要素が、今回Setした要素より大きかったらSetした値より大きい要素のいた位置にSetした値を追加しもとのやつは消す
                    if(calList.get(calList.size()-1).getTimeInMillis()<cal.getTimeInMillis()){
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
    public static List<String> calendarChangeString(List<Calendar> setTimeList){
        List<String> alarmSetTimeListString = new ArrayList<String>();

       for(Calendar cal:setTimeList){
           String s = ""+cal.getTime().getHours()+":"+cal.getTime().getMinutes();
           alarmSetTimeListString.add(s);
       }
        return alarmSetTimeListString;
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
    //Viewの階層構造をダンプするスニペット
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
