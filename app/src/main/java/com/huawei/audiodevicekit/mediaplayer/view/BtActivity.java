package com.huawei.audiodevicekit.mediaplayer.view;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huawei.audiobluetooth.api.ATCmdApi;
import com.huawei.audiobluetooth.api.Cmd;
import com.huawei.audiobluetooth.api.data.SensorData;
import com.huawei.audiobluetooth.layer.data.entity.IRspListener;
import com.huawei.audiobluetooth.layer.protocol.mbb.DeviceInfo;
import com.huawei.audiobluetooth.utils.LocaleUtils;
import com.huawei.audiobluetooth.utils.LogUtils;
import com.huawei.audiodevicekit.R;
import com.huawei.audiodevicekit.mediaplayer.contract.BtContract;
import com.huawei.audiodevicekit.mediaplayer.presenter.BtPresenter;
import com.huawei.audiodevicekit.mediaplayer.view.adapter.SingleChoiceAdapter;
import com.huawei.audiodevicekit.mvp.view.support.BaseAppCompatActivity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BtActivity
        extends BaseAppCompatActivity<BtContract.Presenter, BtContract.View>
        implements BtContract.View {
    private static final String TAG = "BtActivity";

    private String mMac;

    private List<Map<String, String>> maps;

//    private SimpleAdapter simpleAdapter;
//

    private VideoView mVideoView;
    private Button playBtn, stopBtn;
    MediaController mMediaController;

    public BtActivity() {
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public BtContract.Presenter createPresenter() {
        return new BtPresenter();
    }

    @Override
    public BtContract.View getUiImplement() {
        return this;
    }

//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_activity_main);
        LogUtils.i(TAG, "onItemSelected position = ");
        mVideoView = new VideoView(this);
        mVideoView = (VideoView) findViewById(R.id.video);
        mMediaController = new MediaController(this);
        playBtn = (Button) findViewById(R.id.playbutton);
        stopBtn = (Button) findViewById(R.id.stopbutton);
        playBtn.setOnClickListener(new mClick());
        stopBtn.setOnClickListener(new mClick());

//        mMediaController.setPrevNextListeners(mVideoView.setOnClickListener(););
    }

//状态栏的高度
    //    int statusBarHeight = getStatusBarHeight();
//    /**
//     * 获得状态栏的高度
//     *
//     * @param
//     * @return
//     */
//    public int getStatusBarHeight() {
//        int result = 0;
//        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            result = getResources().getDimensionPixelSize(resourceId);
//        }
//        return result;
//    }
    class mClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            String uri = "android.resource://" + getPackageName() + "/" + R.raw.vid_bigbuckbunny;  //本地
            //String uri2 = "https://flv2.bn.netease.com/videolib1/1811/26/OqJAZ893T/HD/OqJAZ893T-mobile.mp4";  //网络
            mVideoView.setVideoURI(Uri.parse(uri));  //本地
            //mVideoView.setVideoURI(Uri.parse(uri2));  //网络
            mMediaController.setMediaPlayer(mVideoView);
            mVideoView.setMediaController(mMediaController);

            //        int toolBar =.getHeight();
            int videoViewheight = mVideoView.getHeight();
            //获取屏幕的高度
            int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
            mMediaController.setPadding(0, 0, 0, screenHeight-videoViewheight);

            if (v == playBtn) {
                mVideoView.start();
            } else if (v == stopBtn) {
                mVideoView.stopPlayback();
            }
        }
    }

    @Override
    protected int getResId() {
        return R.layout.m_activity_main;
    }
//
    @Override
    protected void initView() {
//        tvDevice = findViewById(R.id.tv_device);
//        tvStatus = findViewById(R.id.tv_status);
////        tvDataCount = findViewById(R.id.tv_data_count);
////        listView = findViewById(R.id.listview);
//        btnSearch = findViewById(R.id.btn_search);
//        btnConnect = findViewById(R.id.btn_connect);
//        btnDisconnect = findViewById(R.id.btn_disconnect);
////        spinner = findViewById(R.id.spinner);
//        rvFoundDevice = findViewById(R.id.found_device);
//        testblock = findViewById(R.id.testblock);
////        initSpinner();
//        initRecyclerView();
//        maps = new ArrayList<>();
//        simpleAdapter = new SimpleAdapter(this, maps, android.R.layout.simple_list_item_1,
//                new String[] {"data"}, new int[] {android.R.id.text1});
////        listView.setAdapter(simpleAdapter);
//        int a=0;
    }


//
//    private void initRecyclerView() {
//        SingleChoiceAdapter.SaveOptionListener mOptionListener = new SingleChoiceAdapter.SaveOptionListener() {
//            @Override
//            public void saveOption(String optionText, int pos) {
//                LogUtils.i(TAG, "saveOption optionText = " + optionText + ",pos = " + pos);
//                mMac = optionText.substring(1, 18);
//                boolean connected = getPresenter().isConnected(mMac);
//                if (connected) {
//                    getPresenter().disConnect(mMac);
//                } else {
//                    getPresenter().connect(mMac);
//                }
//            }
//
//            @Override
//            public void longClickOption(String optionText, int pos) {
//                LogUtils.i(TAG, "longClickOption optionText = " + optionText + ",pos = " + pos);
//            }
//        };
//        mAdapter = new SingleChoiceAdapter(this, new ArrayList<>());
//        mAdapter.setSaveOptionListener(mOptionListener);
//        rvFoundDevice.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        rvFoundDevice.setAdapter(mAdapter);
//    }

    @Override
    protected void initData() {
        getPresenter().initBluetooth(this);
    }

    //
    @Override
    protected void setOnclick() {
        super.setOnclick();
//        btnConnect.setOnClickListener(v -> getPresenter().connect(mMac));
//        btnDisconnect.setOnClickListener(v -> getPresenter().disConnect(mMac));
//        btnSearch.setOnClickListener(v -> getPresenter().checkLocationPermission(this));

    }
//
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getPresenter().processLocationPermissionsResult(requestCode, grantResults);
    }

    //
    @Override
    public void onDeviceFound(DeviceInfo info) {
//        if (mAdapter == null) {
//            return;
//        }
//        runOnUiThread(() -> mAdapter
//                .pushData(String.format("[%s] %s", info.getDeviceBtMac(), "HUAWEI Eyewear")));
    }

    @Override
    public void onStartSearch() {
//        if (mAdapter != null) {
//            runOnUiThread(() -> mAdapter.clearData());
//        }
    }

    @Override
    public void onDeviceChanged(BluetoothDevice device) {
//        if (tvDevice != null) {
//            runOnUiThread(() -> tvDevice
//                    .setText(String.format("[%s] %s", device.getAddress(), "HUAWEI Eyewear")));
//            Class<ATCmdApi> atCmdApiClass = ATCmdApi.class;
//            try {
//                testblock.setText("first");
//                atCmdApiClass.getMethod("sensorUploadOpen", String.class, IRspListener.class).
//                        invoke(ATCmdApi.getInstance(), mMac, new IRspListener<Object>() {
//                            @Override
//                            public void onSuccess(Object o) {
//                                testblock.setText("success");
//                            }
//
//                            @Override
//                            public void onFailed(int i) {
//                                testblock.setText("failed");
//
//                            }
//                        });
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void onConnectStateChanged(String stateInfo) {
//        if (tvStatus != null) {
//            runOnUiThread(() -> tvStatus.setText(stateInfo));
//        }
    }

    @Override
    public void onSensorDataChanged(SensorData sensorData) {
//        runOnUiThread(() -> {
//            Map<String, String> map = new HashMap<>();
//            map.put("data", sensorData.toString());
//            maps.add(0, map);
//            simpleAdapter.notifyDataSetChanged();
//            if (sensorData.getCapSensorDataLen() != 0) {
//                testblock.setText("test vic");
//            } else {
//                testblock.setText("test vic too");
//            }
//        });
    }


    @Override
    public void onError(String errorMsg) {
        runOnUiThread(
                () -> Toast.makeText(BtActivity.this, errorMsg, Toast.LENGTH_LONG).show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().deInit();
    }
}