package com.huawei.audiodevicekit.bluetoothsample.model;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.huawei.audiobluetooth.api.AudioBluetoothApi;
import com.huawei.audiobluetooth.api.data.SensorData;
import com.huawei.audiobluetooth.layer.bluetooth.BluetoothManager;
import com.huawei.audiobluetooth.layer.bluetooth.DiscoveryHelper;
import com.huawei.audiobluetooth.layer.bluetooth.IInitResultCallBack;
import com.huawei.audiobluetooth.layer.data.entity.INotifyListener;
import com.huawei.audiobluetooth.layer.data.entity.IRspListener;
import com.huawei.audiobluetooth.layer.protocol.mbb.DeviceInfo;
import com.huawei.audiobluetooth.utils.LogUtils;

public class SampleBtRepository implements SampleBtModel {
    private static final String TAG = "SampleBtRepository";

    private Callback mCallback;

    public SampleBtRepository(Callback callback) {
        this.mCallback = callback;
    }

    @Override
    public void initBluetooth(Context context) {
        try {
            AudioBluetoothApi.getInstance().init(context, new IInitResultCallBack() {
                @Override
                public void onResult(boolean result) {
                    LogUtils.i(TAG, "onResult result = " + result);
                }

                @Override
                public void onFinish() {
                    LogUtils.i(TAG, "onFinish");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startSearch() {
        AudioBluetoothApi.getInstance().stopSearch();
        AudioBluetoothApi.getInstance().searchDevice(new DiscoveryHelper.FoundCallback() {
            @Override
            public void onFound(DeviceInfo deviceInfo) {
                LogUtils.d(TAG, "startSearch Found device: " + deviceInfo);
                mCallback.onDeviceFound(deviceInfo);
            }

            @Override
            public void onFinish() {
                LogUtils.i(TAG, "searchDevice onFinish");
            }
        });
    }

    @Override
    public void connect(String mac) {
        AudioBluetoothApi.getInstance().connect(mac, state -> {
            LogUtils.i(TAG, "onConnectStateChanged state = " + state);
            BluetoothDevice device = BluetoothManager.getInstance().getBtDevice(mac);
            mCallback.onConnectStateChanged(device, state);
        });
    }

    @Override
    public void disConnect(String mac) {
        AudioBluetoothApi.getInstance().disConnect(mac, state -> {
            LogUtils.i(TAG, "onConnectStateChanged state = " + state);
            BluetoothDevice device = BluetoothManager.getInstance().getBtDevice(mac);
            mCallback.onConnectStateChanged(device, state);
        });
    }


    @Override
    public void registerListener(String mac, INotifyListener listener) {
        AudioBluetoothApi.getInstance().registerListener(mac, listener);
    }

    @Override
    public void unregisterListener(String mac) {
        AudioBluetoothApi.getInstance().unregisterListener(mac);
    }
}
