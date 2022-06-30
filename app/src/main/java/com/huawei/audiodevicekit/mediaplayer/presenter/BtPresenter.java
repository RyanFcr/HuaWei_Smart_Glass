package com.huawei.audiodevicekit.mediaplayer.presenter;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.huawei.audiobluetooth.api.AudioBluetoothApi;
import com.huawei.audiobluetooth.api.data.SensorData;
import com.huawei.audiobluetooth.api.data.SensorDataHelper;
import com.huawei.audiobluetooth.constant.ConnectState;
import com.huawei.audiobluetooth.layer.protocol.mbb.DeviceInfo;
import com.huawei.audiobluetooth.utils.BluetoothUtils;
import com.huawei.audiobluetooth.utils.LogUtils;
import com.huawei.audiodevicekit.R;
import com.huawei.audiodevicekit.mediaplayer.contract.BtContract;
import com.huawei.audiodevicekit.mediaplayer.model.BtModel;
import com.huawei.audiodevicekit.mediaplayer.model.BtRepository;
import com.huawei.audiodevicekit.mvp.impl.ABaseModelPresenter;

public class BtPresenter extends ABaseModelPresenter<BtContract.View, BtModel>
        implements BtContract.Presenter, BtModel.Callback {
    private static final String TAG = "SampleBtPresenter";

    /**
     * 位置权限
     */
    private String[] locationPermission = {"android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION"};

    private int LOCATION_PERMISSION_REQUEST_CODE = 188;

    @Override
    public BtModel createModel() {
        return new BtRepository(this);
    }

    @Override
    public void initBluetooth(Context context) {
        LogUtils.i(TAG, "initBluetooth");
        if (!isUiDestroy()) {
            getModel().initBluetooth(context);
        }
    }

    @Override
    public void checkLocationPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(activity, locationPermission, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LogUtils.i(TAG, "Already got LOCATION Permission");
            startSearch();
        }
    }

    @Override
    public void processLocationPermissionsResult(int requestCode, int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startSearch();
            } else {
                if (!isUiDestroy()) {
                    getUi().onError("缺少位置权限，扫描设备失败！");
                }
            }
        }
    }

    @Override
    public void deInit() {
        LogUtils.i(TAG, "deInit");
        AudioBluetoothApi.getInstance().deInit();
    }

    @Override
    public void startSearch() {
        LogUtils.i(TAG, "startSearch");
        if (!isUiDestroy()) {
            getUi().onStartSearch();
            getModel().startSearch();
        }
    }

    @Override
    public void connect(String mac) {
        LogUtils.i(TAG, "connect");
        if (!isUiDestroy()) {
            if (!BluetoothUtils.checkMac(mac)) {
                LogUtils.e(TAG, "Invalid MAC address.connect fail ! ");
                getUi().onError("Invalid MAC address.connect fail ! ");
                return;
            }
            getModel().connect(mac);
        }
    }

    @Override
    public boolean isConnected(String mac) {
        boolean connected = AudioBluetoothApi.getInstance().isConnected(mac);
        LogUtils.i(TAG, "isConnected connected = " + connected);
        return connected;
    }

    @Override
    public void disConnect(String mac) {
        LogUtils.i(TAG, "disConnect");
        if (!isUiDestroy()) {
            if (!BluetoothUtils.checkMac(mac)) {
                LogUtils.e(TAG, "Invalid MAC address.disConnect fail ! ");
                getUi().onError("Invalid MAC address.disConnect fail ! ");
                return;
            }
            getModel().disConnect(mac);
        }
    }



    @Override
    public void registerListener(String mac) {
        if (!isUiDestroy()) {
            getModel().registerListener(mac, result -> {
                LogUtils.i(TAG, "result = " + result);
                byte[] appData = result.getAppData();
                if (!isUiDestroy()) {
                    SensorData sensorData = SensorDataHelper.genSensorData(appData);
                    getUi().onSensorDataChanged(sensorData);
                }
            });
        }
    }

    @Override
    public void unregisterListener(String mac) {
        if (!isUiDestroy()) {
            getModel().unregisterListener(mac);
        }
    }

    @Override
    public void onDeviceFound(DeviceInfo deviceInfo) {
        if (!isUiDestroy()) {
            getUi().onDeviceFound(deviceInfo);
        }
    }

    @Override
    public void onConnectStateChanged(BluetoothDevice device, int state) {
        String stateInfo;
        switch (state) {
            case ConnectState.STATE_UNINITIALIZED:
                stateInfo = getUi().getContext().getResources().getString(R.string.not_initialized);
                break;
            case ConnectState.STATE_CONNECTING:
                stateInfo = getUi().getContext().getResources().getString(R.string.connecting);
                break;
            case ConnectState.STATE_CONNECTED:
                stateInfo = getUi().getContext().getResources().getString(R.string.connected);
                break;
            case ConnectState.STATE_DATA_READY:
                stateInfo = getUi().getContext().getResources().getString(R.string.data_channel_ready);
                registerListener(device.getAddress());

                break;
            case ConnectState.STATE_DISCONNECTED:
                stateInfo = getUi().getContext().getResources().getString(R.string.disconnected);
                unregisterListener(device.getAddress());
                break;
            default:
                stateInfo = getUi().getContext().getResources().getString(R.string.unknown) + state;
                break;
        }
        LogUtils.i(TAG,
                "onConnectStateChanged  state = " + state + "," + stateInfo + "," + ConnectState
                        .toString(state));
        if (!isUiDestroy()) {
            getUi().onConnectStateChanged(stateInfo);
            getUi().onDeviceChanged(device);
        }
    }


}
