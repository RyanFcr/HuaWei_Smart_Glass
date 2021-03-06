package com.huawei.audiodevicekit.mediaplayer.view;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.SynthesisCallback;
import android.speech.tts.SynthesisRequest;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.Toolbar;

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
import com.huawei.audiodevicekit.bluetoothsample.model.RecognizeListener;
import com.huawei.audiodevicekit.mediaplayer.contract.BtContract;
import com.huawei.audiodevicekit.mediaplayer.presenter.BtPresenter;
import com.huawei.audiodevicekit.mediaplayer.view.adapter.SingleChoiceAdapter;
import com.huawei.audiodevicekit.mvp.view.support.BaseAppCompatActivity;
import com.huawei.audiodevicekit.bluetoothsample.view.SampleBtActivity;
import com.huawei.audiodevicekit.mediaplayer.model.VoiceRecognition;
import com.iflytek.cloud.InitListener;
//import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.speech.util.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class BtActivity
        extends BaseAppCompatActivity<BtContract.Presenter, BtContract.View>
        implements BtContract.View,com.huawei.audiodevicekit.bluetoothsample.model.RecognizeListener {
    private static final String TAG = "BtActivity";

    private String mMac;


    private List<Map<String, String>> maps;

    private RecognizeListener recognizeListener;
//    private SimpleAdapter simpleAdapter;
//
    private StringBuffer charBufffer = new StringBuffer();

    private VideoView mVideoView;
    private Button playBtn, stopBtn;
    private TextView testbar;
    private int pos = 354000;
    private int mov1 = 324000;
    private int mov2 = 354000;
    private int mov3 = 384000;
    MediaController mMediaController;

    public BtActivity() {
    }
//    public TextToSpeech tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//        @Override
//        public void onInit(int i) {
//
//        }
//    });
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
//        com.huawei.audiodevicekit.bluetoothsample.model.VoiceRecognition.instance().init(this);
//        com.huawei.audiodevicekit.bluetoothsample.model.VoiceRecognition.instance().setRecognizeListener(this);
        setContentView(R.layout.m_activity_main);
        LogUtils.i(TAG, "onItemSelected position = ");
        mVideoView = new VideoView(this);
        mVideoView = (VideoView) findViewById(R.id.video);
        mMediaController = new MediaController(this);
        playBtn = (Button) findViewById(R.id.playbutton);
        stopBtn = (Button) findViewById(R.id.stopbutton);
        playBtn.setOnClickListener(new mClick());
        Intent data_get;
        stopBtn.setOnClickListener(
//                new mClick()
                VoiceRecognition.instance()
        );
//        LayoutInflater inflater = (LayoutInflater) this
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View test_view = inflater.inflate(R.layout.activity_main,null);
//        testbar = test_view.findViewById(R.id.testblock);
//        VoiceRecognition.instance();
//        if (testbar==null){
//            int a=0;
//            playBtn.setText("zhaobudao");
//        }else{
//            playBtn.setText("zhaodaole");
//        }
        VoiceRecognition.instance().init(this);
        VoiceRecognition.instance().setRecognizeListener(this);
        playBtn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                playBtn.setText("before");
//                mVideoView.pause();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                playBtn.setText("on");
//                mVideoView.pause();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.equals("2")){
                    int end  =mVideoView.getDuration();
                    int cur = mVideoView.getCurrentPosition();
                    int targe = (cur+5000>end)
                            ?end:(cur+5000);
                    mVideoView.seekTo(targe);
                    stopBtn.setText("2");
                }
//                playBtn.setText("after");
//                if(editable.equals("??????")){
//                    mVideoView.pause();
//                }else if (editable.equals("??????")){
//                    mVideoView.resume();
//                }else{
////                    mVideoView.pause();
//                }
            }
        });


    }
    @Override
    public void onNewResult(String result) {
        if (result!=null && !(result.equals(""))){
            if (result.equals(" stop")){
                mVideoView.pause();
                stopBtn.setText(result);
            }else if (result.equals(" continue")){
                mVideoView.start();
                stopBtn.setText(result);
            }else if (result.equals(" jump")||result.equals(" Jump")){
                mVideoView.seekTo(30000);
                stopBtn.setText(result);
            }else if (result.equals(" resume")){
                mVideoView.seekTo(0);
                stopBtn.setText(result);
            }else if (result.equals(" mark")){
                pos = mVideoView.getCurrentPosition();
                stopBtn.setText(result);
            }else if (result.equals(" arrive")){
                mVideoView.seekTo(pos);
                stopBtn.setText(result);
            }else if (result.equals(" last")||result.equals(" Last")){
                int tep = mVideoView.getCurrentPosition();
                if (tep<=mov2){
                    mVideoView.seekTo(0);
                }else if (tep>mov2&&tep<=mov3){
                    mVideoView.seekTo(mov1);
                }else {
                    mVideoView.seekTo(mov2);
                }
                stopBtn.setText(result);
            }else if (result.equals(" next")||result.equals(" Next")){
                int tep = mVideoView.getCurrentPosition();
                if (tep<mov1){
                    mVideoView.seekTo(mov1);
                }else if (tep>=mov1&&tep<=mov2){
                    mVideoView.seekTo(mov2);
                }else {
                    mVideoView.seekTo(mov3);
                }
//                speecher tts = new speecher();
//                tts.speak("neck tilt and look to shoulder(left)",TextToSpeech.QUEUE_FLUSH, null,null);

                stopBtn.setText(result);
            }else if (result.equals(" again")){
                int tep = mVideoView.getCurrentPosition();
                if (tep<mov1){
                    mVideoView.seekTo(0);
                }else if (tep>=mov1&&tep<mov2){
                    mVideoView.seekTo(mov1);
                }else if (tep>=mov2&&tep<mov3){
                    mVideoView.seekTo(mov2);
                }else{
                    mVideoView.seekTo(mov3) ;
                }
                stopBtn.setText(result);
            }else if (result.equals(" over")){
                mVideoView.seekTo(mVideoView.getDuration());
                stopBtn.setText(result);
            }else if (result.equals(" forward")){
                int end  =mVideoView.getDuration();
                int cur = mVideoView.getCurrentPosition();
                int targe = (cur+10000>end)
                        ?end:(cur+10000);
                mVideoView.seekTo(targe);
                stopBtn.setText(result);
            }else if (result.equals(" back")){
                mVideoView.seekTo((mVideoView.getCurrentPosition()-10000<0)?0:(mVideoView.getCurrentPosition()-10000));
                stopBtn.setText(result);
            }
        }
    }
    @Override
    public void onTotalResult(String result,boolean isLast) {
//        tvSendCmdResult.append(result);
    }


//??????????????????
    //    int statusBarHeight = getStatusBarHeight();
//    /**
//     * ????????????????????????
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

    /**
     * ?????????????????????
     */
    public static double getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        //??????status_bar_height?????????ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //????????????ID????????????????????????
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    class mClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            String uri = "android.resource://" + getPackageName() + "/" + R.raw.sample;  //??????
            //String uri2 = "https://flv2.bn.netease.com/videolib1/1811/26/OqJAZ893T/HD/OqJAZ893T-mobile.mp4";  //??????
            mVideoView.setVideoURI(Uri.parse(uri));  //??????
            //mVideoView.setVideoURI(Uri.parse(uri2));  //??????
            mMediaController.setMediaPlayer(mVideoView);
            mVideoView.setMediaController(mMediaController);

            //        int toolBar =.getHeight();
            int videoViewheight = mVideoView.getHeight();
            //?????????????????????
            int screenHeight = getWindowManager().getDefaultDisplay().getHeight();


            double statusBarHeight = getStatusBarHeight(getContext());
            mMediaController.setPadding(0, 0, 0, (int) (screenHeight-videoViewheight-statusBarHeight));
//            DisplayMetrics dm = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(dm);
//            //????????????
//            Rect outRect1 = new Rect();
//            getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
//            int statusBar = dm.heightPixels - outRect1.height();
//

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


//    @Override
//    public void onVolumeChanged(int i, byte[] bytes) {
//
//    }
//
//    @Override
//    public void onBeginOfSpeech() {
//
//    }
//
//    @Override
//    public void onEndOfSpeech() {
//
//    }

    public void setRecognizeListener(RecognizeListener recognizeListener) {
        this.recognizeListener = recognizeListener;
    }

//    @Override
//    public void onResult(RecognizerResult recognizerResult, boolean b) {
//        if (recognizeListener != null) {
//            String text = JsonParser.parseIatResult(recognizerResult.getResultString());
//            String sn = null;
//            // ??????json????????????sn??????
//            try {
//                JSONObject resultJson = new JSONObject(recognizerResult.getResultString());
//                sn = resultJson.optString("sn");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            if (!TextUtils.isEmpty(text)) {
//                charBufffer.append(text);
//            }
//            if(text.equals("??????")){
//                Log.d(TAG, "????????????");
//
//            }
//
//            //recognizeListener.onTotalResult(charBufffer.toString(), iat.isListening());
//        }
//    }

    @Override
    public void onError(SpeechError speechError) {
        Toast.makeText(this,"????????? $speechError",Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onEvent(int i, int i1, int i2, Bundle bundle) {
//
//    }

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