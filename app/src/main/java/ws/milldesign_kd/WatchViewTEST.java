package ws.milldesign_kd;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class WatchViewTEST extends FragmentActivity implements View.OnClickListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar actionBar;
    private StopWatchDialogFragmentTEST swdf;
    public static Utils utils;

    //初期化
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_view);
        utils = new Utils();

        //DrawerLatoutの設定
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        //ActionBarの設定(初期状態では隠しておく)
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.hide();
        //Toggleボタンの設定
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawView){}//NavigationDrawer閉じた時
            @Override
            public void onDrawerOpened(View drawView){}//NavigationDrawer開いた時
            @Override
            public void onDrawerStateChanged(int newState) {}//NavigationDrawerの状態が変わった時
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);//これをトグル作成より前にやるとNG
        findViewById(R.id.all_view).setOnClickListener(this);//どこを押してもリスナーが走る
    }
    //ActionBarに項目を作成
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("設定");

        MenuItem actionItemTimer = menu.add(Menu.NONE, 1, Menu.NONE, "Timer");
        actionItemTimer.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        actionItemTimer.setIcon(R.drawable.ic_launcher);

        MenuItem actionItemAlarm = menu.add(Menu.NONE, 2, Menu.NONE, "Alarm");
        actionItemAlarm.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        actionItemAlarm.setIcon(R.drawable.ic_launcher);

        MenuItem actionItemStopWatch = menu.add(Menu.NONE, 3, Menu.NONE, "StopWatch");
        actionItemStopWatch.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        actionItemStopWatch.setIcon(R.drawable.ic_launcher);

        return true;
    }
    //ActionBarの項目を押下した時の処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        actionBar.hide();

        if(mDrawerToggle.onOptionsItemSelected(item)){
            findViewById(R.id.Calendar).setOnClickListener(this);
            findViewById(R.id.Date).setOnClickListener(this);
            findViewById(R.id.Weather).setOnClickListener(this);
            findViewById(R.id.Temperature).setOnClickListener(this);
            findViewById(R.id.Humidity).setOnClickListener(this);
            return true;
        }else if(item.getItemId()==0){
            Toast.makeText(this,"設定ダイアログが開く",Toast.LENGTH_LONG);
            return true;
        }else if(item.getItemId()==1){
            new TimerDialogFragmentTEST().show(getFragmentManager(), "timer");
            return true;
        }else if(item.getItemId()==2){
            new AlarmDialogFragmentTEST().show(getFragmentManager(), "alarm");
            return true;
        }else if(item.getItemId()==3){
            if(swdf==null){
                swdf = (StopWatchDialogFragmentTEST)new StopWatchDialogFragmentTEST();
            }else{

            }
            swdf.show(getFragmentManager(), "stopWatch");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //NavigationDrawer内のボタンを押下したときの処理等
    @Override
    public void onClick(View v){
        if(v == findViewById(R.id.all_view)){//これだけNavigationDrawerじゃない
            actionBar.show();
        }else if(v == findViewById(R.id.Calendar)){
            Toast.makeText(this,"カレンダーON",Toast.LENGTH_LONG).show();
        }else if(v == findViewById(R.id.Date)){
            Toast.makeText(this,"日付ON",Toast.LENGTH_LONG).show();
        }else if(v == findViewById(R.id.Weather)){
            Toast.makeText(this,"天気ON",Toast.LENGTH_LONG).show();
        }else if(v == findViewById(R.id.Temperature)){
            Toast.makeText(this,"温度ON",Toast.LENGTH_LONG).show();
        }else if(v == findViewById(R.id.Humidity)){
            Toast.makeText(this,"湿度ON",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected  void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }




}