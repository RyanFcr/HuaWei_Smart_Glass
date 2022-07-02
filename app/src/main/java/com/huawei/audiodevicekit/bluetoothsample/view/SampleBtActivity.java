package com.huawei.audiodevicekit.bluetoothsample.view;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huawei.audiobluetooth.api.ATCmdApi;
import com.huawei.audiobluetooth.api.Cmd;
import com.huawei.audiobluetooth.api.data.SensorData;
import com.huawei.audiobluetooth.layer.data.entity.IRspListener;
import com.huawei.audiobluetooth.layer.protocol.mbb.DeviceInfo;
import com.huawei.audiobluetooth.utils.BluetoothUtils;
import com.huawei.audiobluetooth.utils.DateUtils;
import com.huawei.audiobluetooth.utils.LocaleUtils;
import com.huawei.audiobluetooth.utils.LogUtils;
import com.huawei.audiodevicekit.NotConnected;
import com.huawei.audiodevicekit.R;
import com.huawei.audiodevicekit.bluetoothsample.model.RecognizeListener;
import com.huawei.audiodevicekit.bluetoothsample.model.VoiceRecognition;
import com.huawei.audiodevicekit.bluetoothsample.contract.SampleBtContract;
import com.huawei.audiodevicekit.bluetoothsample.presenter.SampleBtPresenter;
import com.huawei.audiodevicekit.bluetoothsample.view.adapter.SingleChoiceAdapter;
import com.huawei.audiodevicekit.mediaplayer.view.BtActivity;
import com.huawei.audiodevicekit.mvp.view.support.BaseAppCompatActivity;
import com.iflytek.cloud.SpeechError;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.Inflater;

public class SampleBtActivity
        extends BaseAppCompatActivity<SampleBtContract.Presenter, SampleBtContract.View>
        implements SampleBtContract.View
//        ,RecognizeListener
{
    private static final String TAG = "SampleBtActivity";

    private TextView tvDevice;

    private TextView tvStatus;

//    private ListView listView;

    private Button btnSearch;

    private Button btnConnect;

    private Button btnDisconnect;


    private Button btnRecognition;


    private RecyclerView rvFoundDevice;

    private SingleChoiceAdapter mAdapter;

    private Cmd mATCmd = Cmd.VERSION;

    private String mMac;

    private List<Map<String, String>> maps;

    private SimpleAdapter simpleAdapter;

    private TextView tvDataCount;

    private TextView test_block;

    private Button switch_media;

    private Button btnrank;

    public Intent trans;

    public Intent checktrans;



    public SampleBtActivity() {
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public SampleBtContract.Presenter createPresenter() {
        return new SampleBtPresenter();
    }

    @Override
    public SampleBtContract.View getUiImplement() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        VoiceRecognition.instance().init(this);
//        VoiceRecognition.instance().setRecognizeListener(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tvDevice = findViewById(R.id.tv_device);
        //tvStatus = findViewById(R.id.tv_status);
//        tvDataCount = findViewById(R.id.tv_data_count);
//        listView = findViewById(R.id.listview);
        btnSearch = findViewById(R.id.btn_search);
        btnConnect = findViewById(R.id.btn_connect);
        btnDisconnect = findViewById(R.id.btn_disconnect);
//        spinner = findViewById(R.id.spinner);
        btnRecognition = findViewById(R.id.btn_recognition);
        rvFoundDevice = findViewById(R.id.found_device);
//        test_block = findViewById(R.id.testblock);
        switch_media = findViewById(R.id.switch_media);
        btnrank = findViewById(R.id.btnrank);
//        test_block.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                btnSearch.setText("be");
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                btnConnect.setText("on");
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                btnDisconnect.setText("af");
//
//            }
//        }
//        );
        initSpinner();
        initRecyclerView();
        maps = new ArrayList<>();
        simpleAdapter = new SimpleAdapter(this, maps, android.R.layout.simple_list_item_1,
                new String[] {"data"}, new int[] {android.R.id.text1});
        //listView.setAdapter(simpleAdapter);
        int a=0;
    }

    private void initSpinner() {
        List<Map<String, String>> data = new ArrayList<>();
        for (Cmd cmd : Cmd.values()) {
            if (cmd.isEnable()) {
                HashMap<String, String> map = new HashMap<>();
                Boolean isChinese = LocaleUtils.isChinese(this);
                String name = isChinese ? cmd.getNameCN() : cmd.getName();
                map.put("title", cmd.getType() + "-" + name);
                data.add(map);
            }
        }
    }

    private void initRecyclerView() {
        SingleChoiceAdapter.SaveOptionListener mOptionListener = new SingleChoiceAdapter.SaveOptionListener() {
            @Override
            public void saveOption(String optionText, int pos) {
                LogUtils.i(TAG, "saveOption optionText = " + optionText + ",pos = " + pos);
                mMac = optionText.substring(1, 18);
                boolean connected = getPresenter().isConnected(mMac);
                if (connected) {
                    getPresenter().disConnect(mMac);
                } else {
                    getPresenter().connect(mMac);
                }
            }

            @Override
            public void longClickOption(String optionText, int pos) {
                LogUtils.i(TAG, "longClickOption optionText = " + optionText + ",pos = " + pos);
            }
        };
        mAdapter = new SingleChoiceAdapter(this, new ArrayList<>());
        mAdapter.setSaveOptionListener(mOptionListener);
        rvFoundDevice.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvFoundDevice.setAdapter(mAdapter);
    }



    @Override
    protected void initData() {
        getPresenter().initBluetooth(this);
    }

    @Override
    protected void setOnclick() {
        super.setOnclick();
        btnConnect.setOnClickListener(v -> getPresenter().connect(mMac));
        btnDisconnect.setOnClickListener(v -> getPresenter().disConnect(mMac));
        btnSearch.setOnClickListener(v -> getPresenter().checkLocationPermission(this));
        switch_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trans = new Intent(SampleBtActivity.this, BtActivity.class);

//                startActivityForResult(MediaIntent,1);
                startActivity(trans);
            }
        });
        btnrank.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SampleBtActivity.this, rankActivity.class);
                startActivity(intent);

            }
        });

//        btnRecognition.setOnClickListener(VoiceRecognition.instance());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getPresenter().processLocationPermissionsResult(requestCode, grantResults);
    }

    @Override
    public void onDeviceFound(DeviceInfo info) {
        if (mAdapter == null) {
            return;
        }
        runOnUiThread(() -> mAdapter
                .pushData(String.format("[%s] %s", info.getDeviceBtMac(), "HUAWEI Eyewear")));
    }

    @Override
    public void onStartSearch() {
        if (mAdapter != null) {
            runOnUiThread(() -> mAdapter.clearData());
        }
    }


    @Override
    public void onDeviceChanged(BluetoothDevice device) {
        if (tvDevice != null) {
            runOnUiThread(() -> tvDevice
                    .setText(String.format("[%s] %s", device.getAddress(), "HUAWEI Eyewear")));
            Class<ATCmdApi> atCmdApiClass = ATCmdApi.class;
            /*
            try{
//                test_block.setText("first");
                atCmdApiClass.getMethod("sensorUploadOpen", String.class, IRspListener.class).
                        invoke(ATCmdApi.getInstance(), mMac, new IRspListener<Object>() {
                            @Override
                            public void onSuccess(Object o) {
//                                test_block.setText("success");
                            }

                            @Override
                            public void onFailed(int i) {
//                                test_block.setText("failed");

                            }
                        });
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

             */
        }
    }

    @Override
    public void onConnectStateChanged(String stateInfo) {

        if (tvStatus != null) {
            runOnUiThread(() -> tvStatus.setText(stateInfo));
        }


    }



    @Override
    public void onSensorDataChanged(SensorData sensorData) {
        runOnUiThread(() -> {
            Map<String, String> map = new HashMap<>();
            map.put("data", sensorData.toString());
            maps.add(0, map);
            simpleAdapter.notifyDataSetChanged();
            LayoutInflater ltep = (LayoutInflater)
                    SampleBtActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View vtep = ltep.inflate(R.layout.m_activity_main,null);
            Button tep = vtep.findViewById(R.id.playbutton);
            Integer inttep = sensorData.getKnockDetect();
            tep.setText(inttep.toString());
//            if(sensorData.getSensorType()%16>=8){
//                testblock.setText("test vic");
//            }else{
//                long[] var2 = sensorData.getCapSensorData();
//                if (var_com!=var2){
//                    var_com = var2;
//                    testblock.setText("gotit");
//                }
//            }
//            if(trans!=null){
//                int datasend = sensorData.getSensorType();
//                trans.putExtra("ctrl1",datasend);
//                if (checktrans!=trans){
//                    checktrans = trans;
//                    startActivity(trans);
//                }
//            }
        });
    }



    @Override
    public void onError(String errorMsg) {
        runOnUiThread(
                () -> Toast.makeText(SampleBtActivity.this, errorMsg, Toast.LENGTH_LONG).show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().deInit();
        VoiceRecognition.instance().release();
    }
//    @Override
    public void onNewResult(String result) {
//            if(trans!=null){
//                trans.putExtra("ctrl1",result);
                if (result!=null && !(result.equals(""))){
                    test_block.setText(result);
                }
//                if (checktrans!=trans){
//                    checktrans = trans;
//                    startActivity(trans);
//                }
//            }
//        tvSendCmdResult.append(result);
    }

//    @Override
//    public void onTotalResult(String result,boolean isLast) {
////        tvSendCmdResult.append(result);
//    }

//    @Override
//    public void onError(SpeechError speechError) {
//        Toast.makeText(this,"出错了 $speechError",Toast.LENGTH_SHORT).show();
//    }
}
