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
    Button btn_rank;
    Button btn_glass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        btnmedia = findViewById(R.id.btn_media);
        btn_rank=findViewById(R.id.btn_rank1);
        btn_glass= findViewById(R.id.btn_glass1);
        btnmedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent to_media = new Intent(home_Activity.this, BtActivity.class);
                    startActivity(to_media);
//                startActivityForResult(MediaIntent,1);

            }
        });
        btn_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_media = new Intent(home_Activity.this, rankActivity.class);
                startActivity(to_media);
//                startActivityForResult(MediaIntent,1);

            }
        });
        btn_glass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_media = new Intent(home_Activity.this, SampleBtActivity.class);
                startActivity(to_media);
//                startActivityForResult(MediaIntent,1);

            }
        });
    }
}
