package com.eynav.hackathonbullyguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RegisterContinuedActivity extends AppCompatActivity {
    Button idBtnManagRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_continued);
        idBtnManagRegister = findViewById(R.id.idBtnManagRegister2);

        idBtnManagRegister.setOnClickListener(l ->{
            Intent intent = new Intent(RegisterContinuedActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}