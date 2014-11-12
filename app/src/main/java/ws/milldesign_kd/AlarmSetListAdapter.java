package ws.milldesign_kd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class AlarmSetListAdapter extends BaseAdapter {
    private Context context;
    private List<String> alarmSetTimes = new ArrayList<String>();

    /*
    * アラームがセットされると同時に、セットされたタイムピッカーの値がリストで渡されてくる。
    * 過去にセットされたものも全てリストに入っているので。
    * 時間順に並び替えてAdapter作る
    *
    * このクラスが作られ、ListViewにSETされる時に、getViewが走る
    * このgetViewの戻り値であるViewがListViewに羅列される
    *
    * ListViewにSetされるViewの数は、getCountの戻り値の数となる
    */

    public AlarmSetListAdapter(Context context,List<String> alarmSetTimes) {
        super();
        this.context = context;
        this.alarmSetTimes = alarmSetTimes;
    }

    //リストを作る数
    @Override
    public int getCount() {
        return alarmSetTimes.size();
    }

    @Override
    public Object getItem(int position) {
        return alarmSetTimes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);

            convertView = inflater.inflate(R.layout.activity_alarm_set_list,null);

            TextView alarmSetListText = (TextView)convertView.findViewById(R.id.alarmSetListText);
            alarmSetListText.setText(alarmSetTimes.get(0));

        }else{
        }

        for(String alarmSetTime:alarmSetTimes){

            return convertView;

        }
        return null;
    }
}