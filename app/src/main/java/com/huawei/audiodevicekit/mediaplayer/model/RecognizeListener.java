package com.huawei.audiodevicekit.mediaplayer.model;

import com.iflytek.cloud.SpeechError;

public interface RecognizeListener {
    void onNewResult(String result);

    void onTotalResult(String result,boolean isLast);

    void onError(SpeechError speechError);
}
