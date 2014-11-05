package ws.milldesign_kd;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class watchView extends FragmentActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_view);
        /*DrawerLatoutの設定*/
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        /*ActionBarの設定*/
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayOptions(0,ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.hide();
        /*Toggleボタンの設定*/
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ){
            @Override
            public void onDrawerClosed(View view){
            }
            @Override
            public void onDrawerOpened(View drawView){
            }
            @Override
            public void onDrawerStateChanged(int newState) {
                Log.i("NavigationDrawerLog", "NavigationDrawerの状態→" + newState+"(表示済み、閉じ済み：0  ドラッグ中:1  ドラッグを放した後のアニメーション中：2)");
            }
        };
        /*NavigationDrawer内の各ボタンのClickListener設定*/
        findViewById(R.id.all_view).setOnClickListener(this);
        findViewById(R.id.Calendar).setOnClickListener(this);
        findViewById(R.id.Date).setOnClickListener(this);
        findViewById(R.id.Weather).setOnClickListener(this);
        findViewById(R.id.Temperature).setOnClickListener(this);
        findViewById(R.id.Humidity).setOnClickListener(this);
        findViewById(R.id.Close).setOnClickListener(this);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("設定");

        MenuItem actionItemTimer = menu.add(Menu.NONE, 1, Menu.NONE, "Timer");
        actionItemTimer.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        actionItemTimer.setIcon(R.drawable.ic_launcher);

        MenuItem actionItemAlarm = menu.add(Menu.NONE, 2, Menu.NONE, "Timer");
        actionItemAlarm.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        actionItemAlarm.setIcon(R.drawable.ic_launcher);

        MenuItem actionItemStopWatch = menu.add(Menu.NONE, 3, Menu.NONE, "Timer");
        actionItemStopWatch.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        actionItemStopWatch.setIcon(R.drawable.ic_launcher);

        return true;
    }
    /*ActionBarのメニューを押下した時の処理*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        actionBar.hide();
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }else if(item.getItemId()==0){
            Toast.makeText(this,"設定ダイアログが開く",Toast.LENGTH_LONG);
            return true;
        }else if(item.getItemId()==1){
            new timerDialogFragment().show(getFragmentManager(), "timer");
            return true;
        }else if(item.getItemId()==2){
            new alarmDialogFragment().show(getFragmentManager(), "alarm");
            return true;
        }else if(item.getItemId()==3){
            new stopWatchDialogFragment().show(getFragmentManager(), "stopWatch");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*NavigationDrawer内のボタンを押下したときの処理*/
    @Override
     public void onClick(View v){
        if(v == findViewById(R.id.all_view)){
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
        }else if(v == findViewById(R.id.Close)){
            mDrawerLayout.closeDrawers();
        }
    }
}