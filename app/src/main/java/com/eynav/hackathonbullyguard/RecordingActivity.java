package com.eynav.hackathonbullyguard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

public class RecordingActivity extends AppCompatActivity {
    RecyclerView rvList;
    List<Recording> recordings = new ArrayList<>();
    Button btnChatGPT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        rvList = findViewById(R.id.rvList);
        btnChatGPT = findViewById(R.id.btnChatGPT);
        rvList.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        RecordingAdapter recordingAdapter = new RecordingAdapter(this, recordings);
        Recording recording1 = new Recording("10/7/20", "10:00",2,1);
        Recording recording2 = new Recording("11/7/20", "11:00",1,2);

        recordings.add(recording1);
        recordings.add(recording2);

        rvList.setAdapter(recordingAdapter);


        btnChatGPT.setOnClickListener(l ->{
            Intent intent = new Intent(this, ChatGPTActivity.class);
            startActivity(intent);
        });
    }


}