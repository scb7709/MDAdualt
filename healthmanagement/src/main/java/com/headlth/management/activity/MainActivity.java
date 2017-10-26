package com.headlth.management.activity;

import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.headlth.management.R;
import com.headlth.management.acs.BaseActivity;
import com.headlth.management.fragment.AnalizeFragment;
import com.headlth.management.fragment.MaidongCircleFragment;
import com.headlth.management.fragment.MaidongFragment;
import com.headlth.management.fragment.MyFragment;
import com.headlth.management.utils.ShareUitls;
import com.headlth.management.utils.UpadteApp;
import com.headlth.management.watchdatasqlite.MySQLiteDataDao;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by abc on 2016/9/23.
 */
@ContentView(R.layout.activity_mainn)
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @ViewInject(R.id.activity_main_tabs)
    private RadioGroup activity_main_tabs;
    @ViewInject(R.id.activity_main_maindong)
    private RadioButton activity_main_maindong;
    @ViewInject(R.id.main_messages)
    private RelativeLayout main_messages;
    @ViewInject(R.id.main_listCount)
    public TextView main_listCount;
    @ViewInject(R.id.main_share)
    private RelativeLayout main_share;
    @ViewInject(R.id.activity_main_title_left)
    public TextView activity_main_title_left;
    @ViewInject(R.id.activity_main_title_center)
    public TextView activity_main_title_center;
    @ViewInject(R.id.activity_main_title_right)
    public TextView activity_main_title_right;


    public static Activity Activity;
    private FragmentManager fragmentManager;

    private String MAC;
    boolean IsfLoginToMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mayRequestLocation();
        x.view().inject(this);
        Activity = this;
        UpadteApp.updateAPP(Activity, true);
        initialize();

    }

    private void initialize() {


        IsfLoginToMain = ShareUitls.getString(MainActivity.this, "IsfLoginToMain", "").equals("true");
        ShareUitls.putString(MainActivity.this, "isConnectActivity", "");
        fragmentManager = getSupportFragmentManager();
        activity_main_tabs.check(activity_main_maindong.getId());
        activity_main_tabs.setOnCheckedChangeListener(this);
        changeFragment(new MaidongFragment(), "MaidongFragment");

        activity_main_title_center.setText("迈动");
        ShareUitls.putString(this, "MainActivity", "YES");//用来记录当前界面是否存在
        registServiceToActivityReceiver();

        //
    }

    private void changeFragment(Fragment fragment, String tag) {
        //1.获取事务对象
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //2.切换内容的显示
        transaction.replace(R.id.activity_main_frame, fragment, tag);
//		//3.进站
//		transaction.addToBackStack(null);
        //4.提交事务
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.activity_main_maindong:
                activity_main_title_center.setText("迈动");
                activity_main_title_left.setText("");
                activity_main_title_right.setText("");
                main_share.setVisibility(View.GONE);
                main_messages.setVisibility(View.VISIBLE);
                changeFragment(new MaidongFragment(), "MaidongFragment");
                break;
            case R.id.activity_main_analyze:
                main_share.setVisibility(View.GONE);

                activity_main_title_left.setText("");
                activity_main_title_center.setText("有效运动");
                activity_main_title_right.setText("卡路里");
                main_messages.setVisibility(View.GONE);
                changeFragment(new AnalizeFragment(), "AnalizeFragment");
                break;
            case R.id.activity_main_maidongcircle:
                main_share.setVisibility(View.VISIBLE);
                activity_main_title_center.setText("迈动圈");
                activity_main_title_left.setText("");
                activity_main_title_right.setText("");
                main_messages.setVisibility(View.GONE);
                changeFragment(new MaidongCircleFragment(), "MaidongCircleFragment");
                break;
            case R.id.activity_main_my:
                main_share.setVisibility(View.GONE);
                activity_main_title_center.setText("我");
                activity_main_title_left.setText("");
                activity_main_title_right.setText("");
                main_messages.setVisibility(View.GONE);
                changeFragment(new MyFragment(), "MyFragment");
                break;
        }
    }

    @Event(value = {R.id.main_messages})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.main_messages:
                startActivity(new Intent(MainActivity.this, MessageActivity.class));
                main_listCount.setVisibility(View.GONE);
                ShareUitls.putString(MainActivity.this, "main_listCount", "0");
        }
    }

    private void registServiceToActivityReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("main_listCount");
        registerReceiver(main_listCountReceiver, filter);
    }

    //收到通知后台推送的新消息 然后首页铃铛+  1   的 广播
    private BroadcastReceiver main_listCountReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int main_list_Count = Integer.parseInt(ShareUitls.getString(context, "main_listCount", "0"));
            Log.i("main_list_Count", "" + main_list_Count);
            ShareUitls.putString(context, "main_listCount", (main_list_Count + 1) + "");
            main_listCount.setVisibility(View.VISIBLE);
            main_listCount.setText((main_list_Count + 1) + "");
        }
    };


    long temptime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)//主要是对这个函数的复写
    {
        // TODO Auto-generated method stub

        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getAction() == KeyEvent.ACTION_DOWN)) {
            if (System.currentTimeMillis() - temptime > 2000) // 2s内再次选择back键有效
            {
                Toast.makeText(this, "请再按一次返回退出", Toast.LENGTH_SHORT).show();
                temptime = System.currentTimeMillis();
            } else {
                finish();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(UpadteApp.bottomMenuUpdateAPPDialog!=null){
            UpadteApp.bottomMenuUpdateAPPDialog=null;
        }
        ShareUitls.putString(this, "MainActivity", "");//用来记录当前界面是否存在
        unregisterReceiver(main_listCountReceiver);//解除广播 收到通知后台推送的新小新 然后首页铃铛+  1   的 广播

    }


}
