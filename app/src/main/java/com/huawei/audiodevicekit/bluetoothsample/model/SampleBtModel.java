package com.huawei.audiodevicekit.bluetoothsample.model;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.huawei.audiobluetooth.layer.data.entity.INotifyListener;
import com.huawei.audiobluetooth.layer.protocol.mbb.DeviceInfo;
import com.huawei.audiodevicekit.mvp.model.Model;

public interface SampleBtModel extends Model {
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

        /**
         * 当发送AT指令返回结果时调用
         *
         * @param isSuccess 是否发送成功，true:发送成功
         * @param result 发送成功后返回的结果
         */
        void onSendCmdResult(boolean isSuccess, Object result);
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
     * 发送AT指令
     *
     * @param mac 设备mac地址
     * @param cmdType 指令类型
     */
    void sendCmd(String mac, int cmdType);

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
