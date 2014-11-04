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
        findViewById(R.id.Menu).setOnClickListener(this);
        findViewById(R.id.Timer).setOnClickListener(this);
        findViewById(R.id.Alarm).setOnClickListener(this);
        findViewById(R.id.StopWatch).setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mDrawerToggle.onOptionsItemSelected(item)){
            actionBar.hide();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void onClick(View v){
        if(v == findViewById(R.id.all_view)){
            actionBar.show();
        }else if(v == findViewById(R.id.Menu)){
            Intent toMenu = new Intent(this,menuMode.class);
            startActivity(toMenu);
        }else if(v == findViewById(R.id.Timer)){
            actionBar.show();
            new timerDialogFragment().show(getFragmentManager(), "timer");
        }else if(v == findViewById(R.id.Alarm)){
            actionBar.show();
            new alarmDialogFragment().show(getFragmentManager(), "alarm");
        }else if(v == findViewById(R.id.StopWatch)){
            actionBar.show();
            new stopWatchDialogFragment().show(getFragmentManager(), "stopWatch");
        }
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.watch_view, menu);
        return true;
    }
}