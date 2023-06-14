package com.eynav.hackathonbullyguard;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class ActivityRegister extends AppCompatActivity {
    EditText hallName, email, password;
    Button btnRegister;
    TextView clicktxtSignupManag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText etDateEvent = findViewById(R.id.etDateEvent);

        hallName = findViewById(R.id.idHallName);
        email = findViewById(R.id.idManagMail);
        password= findViewById(R.id.idManagPassword);
        btnRegister = findViewById(R.id.idBtnManagRegister);
        clicktxtSignupManag = findViewById(R.id.idClicktxtSignupManag);

        etDateEvent.setOnClickListener( k ->{
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                String dateEvent = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                etDateEvent.setText(dateEvent);
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                String psswrd = password.getText().toString().trim();

                Intent intent = new Intent(ActivityRegister.this, RegisterContinuedActivity.class);
                startActivity(intent);
                finish();
                if(TextUtils.isEmpty(psswrd)){
                }
                else{
                }
            }
        });
    }
}