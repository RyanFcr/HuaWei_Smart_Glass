package com.huawei.audiodevicekit.mediaplayer.model;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.huawei.audiobluetooth.layer.data.entity.INotifyListener;
import com.huawei.audiobluetooth.layer.protocol.mbb.DeviceInfo;
import com.huawei.audiodevicekit.mvp.model.Model;

public interface BtModel extends Model {
    interface Callback {
        /**
         * 当发现设备时回调
         *
         * @param deviceInfo 设备信息
         */
        void onDeviceFound(DeviceInfo deviceInfo);

        /**
         * 当连接状态变化时调用
         *
         * @param device 设备
         * @param state 状态
         */
        void onConnectStateChanged(BluetoothDevice device, int state);


    }

    /**
     * 初始化蓝牙
     *
     * @param context 上下文
     */
    void initBluetooth(Context context);

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
     * 注册监听器
     *
     * @param mac 设备mac地址
     * @param listener 设备主动上报数据监听器
     */
    void registerListener(String mac, INotifyListener listener);

    /**
     * 注销监听
     *
     * @param mac 设备mac地址
     */
    void unregisterListener(String mac);
}
