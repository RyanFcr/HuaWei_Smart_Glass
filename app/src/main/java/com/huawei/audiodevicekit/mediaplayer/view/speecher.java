package com.huawei.audiodevicekit.mediaplayer.view;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

public class speecher extends Activity implements TextToSpeech.OnInitListener {
    private TextToSpeech mTts;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mTts = new TextToSpeech(this, this);
    }
    @Override
    public void onInit(int i) {
int jjj=0;
    }

    public void speak(String s, int queueFlush, Object o, Object o1) {
        mTts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i==TextToSpeech.SUCCESS){
                   int a= 0;
                }
            }
        });
        mTts.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
    }
}
