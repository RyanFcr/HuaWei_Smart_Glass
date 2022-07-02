package com.huawei.audiodevicekit.bluetoothsample.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.huawei.audiodevicekit.mediaplayer.view.BtActivity;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.speech.util.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * 音频读写转换
 */
public class VoiceRecognition extends Activity implements View.OnClickListener, RecognizerListener, InitListener {

    private static final String TAG = "RecognizeSpeechManager";

    private RecognizeListener recognizeListener;

    // 语音听写对象
    private SpeechRecognizer iat;

    private StringBuffer charBufffer = new StringBuffer();

    private WeakReference<Context> bindContext;

    private static VoiceRecognition instance;

    private VoiceRecognition() {
    }

    public static VoiceRecognition instance() {
        if (instance == null) {
            instance = new VoiceRecognition();
        }
        return instance;
    }

    public void setRecognizeListener(RecognizeListener recognizeListener) {
        this.recognizeListener = recognizeListener;
    }

    public void init(Context context) {
        if (bindContext == null) {
            bindContext = new WeakReference<Context>(context);
        }
        if (iat == null) {
            iat = SpeechRecognizer.createRecognizer(bindContext.get(), this);
        }
    }

    @Override
    public void onInit(int code) {
        if (code != ErrorCode.SUCCESS) {
            Log.d(TAG, "init error code " + code);
        }
    }

    /**
     * 开始监听
     * ErrorCode.SUCCESS 监听成功状态码
     */
    public int startRecognize() {
        setParam();
        return iat.startListening(this);
    }

    /**
     * 取消听写
     */
    public void cancelRecognize() {
        iat.cancel();
    }

    /**
     * 停止听写
     */
    public void stopRecognize() {
        iat.stopListening();
    }

    public void release() {
        iat.cancel();
        iat.destroy();
        iat = null;
        bindContext.clear();
        bindContext = null;
        charBufffer.delete(0, charBufffer.length());
    }

    @Override
    public void onVolumeChanged(int i, byte[] bytes) {

    }

    @Override
    public void onBeginOfSpeech() {
        Log.d(TAG, "onBeginOfSpeech");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d(TAG, "onEndOfSpeech isListening " + iat.isListening());
    }

    @Override
    public void onResult(RecognizerResult results, boolean b) {
        if (recognizeListener != null) {
            recognizeListener.onNewResult(printResult(results));
            //recognizeListener.onTotalResult(charBufffer.toString(), iat.isListening());
        }
    }


    @Override
    public void onError(SpeechError speechError) {
        if (recognizeListener != null) {
            recognizeListener.onError(speechError);
        }
    }

    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {
        Log.d(TAG, "onEvent type " + i);
    }


    private String printResult(RecognizerResult results) {
//        Intent sendtext = new Intent(this,BtActivity.class);
        String text = JsonParser.parseIatResult(results.getResultString());
//        sendtext.putExtra("text_send",text);
//        startActivity(sendtext);
        Log.d(TAG, "printResult " + text + " isListening " + iat.isListening());
        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(text)) {
            charBufffer.append(text);
        }
        return text;
    }

    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        iat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        iat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置返回结果格式
        iat.setParameter(SpeechConstant.RESULT_TYPE, "json");


        iat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        iat.setParameter(SpeechConstant.ACCENT, "mandarin");

        //此处用于设置dialog中不显示错误码信息
        //iat.setParameter("view_tips_plain","false");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        iat.setParameter(SpeechConstant.VAD_BOS, "1000000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入，自动停止录音
        iat.setParameter(SpeechConstant.VAD_EOS, "1000000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        iat.setParameter(SpeechConstant.ASR_PTT, "0");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
       /* iat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        iat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");*/
    }

    @Override
    public void onClick(View view) {
        if (null == iat) {
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            //this.showTip("创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化");
            return;
        }
        startRecognize();
    }
}