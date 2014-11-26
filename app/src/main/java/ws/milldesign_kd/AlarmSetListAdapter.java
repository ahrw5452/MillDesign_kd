package ws.milldesign_kd;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class AlarmSetListAdapter extends BaseAdapter {
    private Context context;
    private List<String> setTimeListString = new ArrayList<String>();

    /*
    * アラームがセットされると同時に、セットされたタイムピッカーの値が過去のものも含め整列したリストで渡されてくる。
    *
    * このクラスが作られ、ListViewにSETされる時に、getViewが走る
    * このgetViewの戻り値であるViewがListViewに羅列される
    *
    * ListViewにSetされるViewの数は、getCountの戻り値の数となる
    */
    public AlarmSetListAdapter(Context context,List<String> setTimeListString) {
        super();
        this.context = context;
        this.setTimeListString = setTimeListString;
    }
    //リストを作る数
    @Override
    public int getCount() {
        return setTimeListString.size();
    }

    @Override
    public Object getItem(int position) {
        return setTimeListString.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //引数のconvertViewは前回使用したViewオブジェクト。前回が無い場合はnull
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.activity_alarm_set_list,null);
        }
        //スクロールするたびにgetViewは走っている。描画されたpositionの数値の位置にいるリストアイテムを取得しセットする
        ((TextView)convertView.findViewById(R.id.alarmSetListText)).setText(setTimeListString.get(position));

        final ToggleButton alarmOnOffToggleButton = (ToggleButton)convertView.findViewById(R.id.alarmOnOffToggleButton);
        alarmOnOffToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("Log","clickListenar!!ポジションは→"+position+"状態は"+isChecked);
            }
        });
        return convertView;
    }
}