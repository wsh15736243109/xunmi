package com.pc.mimi.ui.main;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;

import com.dhh.websocket.Config;
import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketSubscriber2;
import com.pc.mimi.ui.home.HomeFragment;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.util.GPSUtils;
import com.pc.mimi.webSocket.WsManager;
import com.pc.mimi.websocketim.MessageBean;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.MainViewPagerAdapter;
import com.pc.mimi.config.CommonTags;
import com.xlyuns.xunmi.databinding.ActivityMainBinding;
import com.pc.mimi.ui.connect.ConnectFragment;
import com.pc.mimi.ui.message.MessageFragment;
import com.pc.mimi.ui.metting.MettingFragment;
import com.pc.mimi.ui.mine.MineFragment;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    public static final String TOKEN_REFRESH = "token_refresh";


    private long mExitTime = 0L;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void onResume() {
        super.onResume();
        beginLocation();
    }

    @Override
    public void initData() {
        KLog.d("----------getUser_id--------------" + AppUtils.getUser().getUser_id() + "");
        NavigationController navigationController = binding.tab.custom()
                .addItem(newItem(R.drawable.ic_home_normal, R.drawable.ic_home_selected, "首页"))
                .addItem(newItem(R.drawable.ic_metting_normal, R.drawable.ic_metting_selected, "约会"))
                .addItem(newItem(R.drawable.ic_connect_normal, R.drawable.ic_connect_select, "电台"))
                .addItem(newItem(R.drawable.ic_message_normal, R.drawable.ic_message_select, "消息"))
                .addItem(newItem(R.drawable.ic_mine_normal, R.drawable.ic_mine_selected, "我的"))
                .build();
//        connectWebSocket();
        WsManager.getInstance().init();
//        initAppStatusListener();

        binding.viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(), navigationController.getItemCount(), new ArrayList<Fragment>() {{
            add(new HomeFragment());
            add(new MettingFragment());
            add(new ConnectFragment());
            add(new MessageFragment());
            add(new MineFragment());
        }}));
        binding.viewPager.setOffscreenPageLimit(5);
        //自动适配ViewPager页面切换
        navigationController.setupWithViewPager(binding.viewPager);

        //设置消息数
        Messenger.getDefault().register(this, MainActivity.TOKEN_REFRESH, String.class, new BindingConsumer<String>() {
            private int msgNumber = 10;

            @Override
            public void call(String s) {
                if (s.equals("addMsg")) {
                    msgNumber++;
                } else {
                    msgNumber--;
                }
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                navigationController.setMessageNumber(3, msgNumber);
            }
        });


        //设置显示小圆点
//        navigationController.setHasMessage(1, true);
    }

    private void beginLocation() {
        RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            GPSUtils.getInstance(MainActivity.this).getLngAndLat(new GPSUtils.OnLocationResultListener() {
                                @Override
                                public void onLocationResult(Location location) {
                                    KLog.d("经度: " + location.getLongitude() + " 纬度: " + location.getLatitude() + "\n");
                                    AppUtils.saveLongitude(location.getLongitude()+"");
                                    AppUtils.saveLatitude( location.getLatitude()+"");

                                }

                                @Override
                                public void OnLocationChange(Location location) {
                                    KLog.d("经度: " + location.getLongitude() + " 纬度: " + location.getLatitude() + "\n");
                                }
                            });
                        } else {
                            ToastUtils.showShort("定位权限被拒绝");
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        GPSUtils.getInstance(MainActivity.this).removeListener();
        super.onDestroy();
    }

    private void connectWebSocket() {
        Config config = new Config.Builder()
                .setShowLog(true)           //show  log
//                .setClient(yourClient)   //if you want to set your okhttpClient
//                .setShowLog(true, "your logTag")
//                .setReconnectInterval(2, TimeUnit.SECONDS)  //set reconnect interval
//                .setSSLSocketFactory(yourSSlSocketFactory, yourX509TrustManager) // wss support
                .build();
        RxWebSocket.setConfig(config);
        KLog.d("WS_URL-->" + CommonTags.WS_URL);
        RxWebSocket.get(CommonTags.WS_URL)
                .subscribe(new WebSocketSubscriber2<MessageBean>() {
                    @Override
                    protected void onMessage(MessageBean o) {
                        KLog.d(this.getClass().getSimpleName(), o.toString());
                    }

                    @Override
                    protected void onReconnect() {
                        Log.d("MainActivity", "重连");
                    }
                });

    }

    private void initAppStatusListener() {
//        ForegroundCallbacks.init(this).addListener(new ForegroundCallbacks.Listener() {
//            @Override
//            public void onBecameForeground() {
//                Logger.t("WsManager").d("应用回到前台调用重连方法");
//                WsManager.getInstance().reconnect();
//            }
//
//            @Override
//            public void onBecameBackground() {
//
//            }
//        });
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(getResources().getColor(R.color.text_222222));
        normalItemView.setTextCheckedColor(getResources().getColor(R.color.text_F5922F));
        return normalItemView;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.showShort("再按一次回到桌面");
                mExitTime = System.currentTimeMillis();
                return false;
            } else {
                //转入后台
//                moveTaskToBack(false);
                //退出
//            finish();
                AppManager.getAppManager().finishAllActivity();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
