package com.huawei.audiodevicekit.mediaplayer.contract;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.huawei.audiobluetooth.api.data.SensorData;
import com.huawei.audiobluetooth.layer.protocol.mbb.DeviceInfo;
import com.huawei.audiodevicekit.mvp.impl.ABaseUi;
import com.huawei.audiodevicekit.mvp.presenter.IPresenter;

public interface BtContract {
    interface View extends ABaseUi {
        /**
         * 当发现设备时回调
         *
         * @param deviceInfo 设备信息
         */
        void onDeviceFound(DeviceInfo deviceInfo);

        /**
         * 当开始扫描设备时调用
         */
        void onStartSearch();

        /**
         * 当设备状态变化时调用
         *
         * @param device 蓝牙设备
         */
        void onDeviceChanged(BluetoothDevice device);

        /**
         * 当连接状态变化时调用
         *
         * @param stateInfo 状态信息
         */
        void onConnectStateChanged(String stateInfo);

        /**
         * 当收到数据主动上报时调用
         *
         * @param sensorData 传感器数据
         */
        void onSensorDataChanged(SensorData sensorData);



        /**
         * 当出现错误时调用
         *
         * @param errorMsg 错误消息
         */
        void onError(String errorMsg);
    }

    interface Presenter extends IPresenter {
        /**
         * 初始化蓝牙
         *
         * @param context 上下文
         */
        void initBluetooth(Context context);

        /**
         * 检查位置权限
         *
         * @param activity Activity
         */
        void checkLocationPermission(Activity activity);

        /**
         * 处理位置权限结果
         *
         * @param requestCode 请求码
         * @param grantResults 授予结果
         */
        void processLocationPermissionsResult(int requestCode, int[] grantResults);

        /**
         * 反初始化，包括注册监听等
         */
        void deInit();

        /**
         * 开始扫描设备
         */
        void startSearch();

        /**
         * 连接设备
         *
         * @param mac 需要连接的设备mac地址
         */
        void connect(String mac);

        /**
         * 断连设备
         *
         * @param mac 需要断开连接的设备mac地址
         */
        void disConnect(String mac);

        /**
         * 是否已经连接
         *
         * @param mac 设备mac地址
         *
         * @return true:已连接
         */
        boolean isConnected(String mac);


        /**
         * 注册监听器
         *
         * @param mac 设备mac地址
         */
        void registerListener(String mac);

        /**
         * 注销监听器
         *
         * @param mac 设备mac地址
         */
        void unregisterListener(String mac);
    }
}
