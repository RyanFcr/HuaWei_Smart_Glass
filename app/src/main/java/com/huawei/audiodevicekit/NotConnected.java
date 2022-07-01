package com.huawei.audiodevicekit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.huawei.audiodevicekit.bluetoothsample.view.SampleBtActivity;

public class NotConnected extends AppCompatActivity {
    private Button btn_addNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_not_connected);
        btn_addNew = (Button) findViewById(R.id.btn_addnew);
        btn_addNew.setOnClickListener(new ButtonListener());
    }

    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(NotConnected.this, SampleBtActivity.class);
            startActivity(intent);
        }
    }
}