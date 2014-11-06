package ws.milldesign_kd;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    public final static void actionBarUpsideDown(Activity activity) {

        View root = activity.getWindow().getDecorView();//watchViewのrootを取得
        dumpViewTree(root, "");

        View firstChild = ((ViewGroup) root).getChildAt(0);//rootの0番目の子ViewをfirstChildとして取得/これがリニアレイアウトじゃないといけない？



        if (firstChild instanceof ViewGroup) {//firstChildがViewGroupで実装されていれば。

            ViewGroup viewGroup = (ViewGroup) firstChild;//なんでこれやってんの
            Log.i("Log","いれた。ビューグループの子の数は→"+viewGroup.getChildCount());
            Log.i("Log","初期値。ビューグループレイアウトモード、子じゃなくて。→"+viewGroup.getLayoutParams());
            Log.i("Log","初期値。ビューグループの0子→"+viewGroup.getChildAt(0));
            Log.i("Log","初期値。ビューグループの1子→"+viewGroup.getChildAt(1));
            Log.i("Log","初期値。ビューグループの2子→"+viewGroup.getChildAt(2));

            List<View> views = findViewsWithClassName(root,
                    "com.android.internal.widget.ActionBarContainer");//ActionBarのビューをroot以下で探し出してlistに格納してる

            List<View> viewActionBarOverlayLayout = findViewsWithClassName(root,
                    "com.android.internal.widget.ActionBarOverlayLayout");
            Log.i("びゅーあくしょんばーおーばーれいあうとのかず",""+viewActionBarOverlayLayout.size());


            //viewGroup.removeViewAt(0);
            //android.widget.FrameLayout



            if (!views.isEmpty()) {
                for (View vv : views) {
                    viewGroup.removeView(vv);
                    Log.i("Log","消し中！！。ビューグループのvvは→"+vv);
                    Log.i("Log","消し中。ビューグループの子の数は→"+viewGroup.getChildCount());
                    Log.i("Log","消し中。ビューグループの0子→"+viewGroup.getChildAt(0));
                    Log.i("Log","消し中。ビューグループの1子→"+viewGroup.getChildAt(1));
                    Log.i("Log","消し中。ビューグループの2子→"+viewGroup.getChildAt(2));
                }
                for (View vv : views) {
                    viewGroup.addView(vv);

                    Log.i("Log","追加中！！。ビューグループのvvは→"+vv);
                    Log.i("Log","追加中。ビューグループの子の数は→"+viewGroup.getChildCount());
                    Log.i("Log","追加中。ビューグループの0子→"+viewGroup.getChildAt(0));
                    Log.i("Log","追加中。ビューグループの1子→"+viewGroup.getChildAt(1));
                    Log.i("Log","追加中。ビューグループの2子→"+viewGroup.getChildAt(2));
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
    private Utils() {
        // ignore
    }
}
