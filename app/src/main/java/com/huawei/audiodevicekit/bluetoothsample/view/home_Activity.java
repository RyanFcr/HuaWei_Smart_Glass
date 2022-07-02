package com.huawei.audiodevicekit.bluetoothsample.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.huawei.audiodevicekit.R;
import com.huawei.audiodevicekit.mediaplayer.view.BtActivity;

public class home_Activity extends AppCompatActivity {
    Button btnmedia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        btnmedia = findViewById(R.id.btn_media);
        btnmedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_media = new Intent(home_Activity.this, BtActivity.class);

//                startActivityForResult(MediaIntent,1);
                startActivity(to_media);
            }
        });
    }
}
