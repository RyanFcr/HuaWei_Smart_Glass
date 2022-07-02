package com.huawei.audiodevicekit.bluetoothsample.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.huawei.audiodevicekit.NotConnected;
import com.huawei.audiodevicekit.R;
import com.huawei.audiodevicekit.bluetoothsample.view.SampleBtActivity;

public class rankActivity extends AppCompatActivity {
    private Button btn_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rank);
        btn_video = (Button) findViewById(R.id.btn_video);
        btn_video.setOnClickListener(new ButtonListener());

    }
    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(rankActivity.this, SampleBtActivity.class);
            startActivity(intent);
        }
    }
}
