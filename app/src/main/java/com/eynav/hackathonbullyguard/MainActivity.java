package com.eynav.hackathonbullyguard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button btnRecording;
    TextView textView3;

    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private Button StartRecording, StopRecording, StartPlaying, StopPlaying;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String AudioSavaPath = null;

    private static final int SPEECH_REQUEST_CODE = 123;
    boolean isRecording = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRecording= findViewById(R.id.btnRecording);
        textView3 = findViewById(R.id.textView3);

        String text1 ="אפליקציה שתהיה שומרת הראש שלך מפני בריונות";
        String text2 = "אפליקציה שקטה שתגן עליך ותוכל לדווח לגורמים המתאימים על כל סוג של בריונות שאפשר לחוות, אנחנו מקווים שלא תזדקק לנו אבל בכל מקרה אנחנו נהיה פה בשבילך.\n";
        String text3 = "חושב לדעת שגם אם אתה לא חווה בריונות כרגע, אנשים רבים חווים בריונות ביומיום. \n";
        String text4 = "בריונות לא מוגבלת רק למקום אחד אלא גם בריונות בבית ספר, עבודה, בחברה, בזוגיות, עם חברים וכו... ולא תמיד אנחנו מודעים לכך שאנחנו חווים בריונות.\n";
        String text =text1  + "\n" + text2 + text3 + text4;
        textView3.setText(text);
        btnRecording.setOnClickListener(l ->{
            Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
            startActivity(intent);
            finish();
        });



        StartRecording = findViewById(R.id.startRecord);
        StopRecording = findViewById(R.id.stopRecord);
        StartPlaying = findViewById(R.id.startPlaying);
        StopPlaying = findViewById(R.id.stopPlaying);
        StartRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartRecording();


            }
        });

        StopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRecording = false;
                onSpeechRecognitionButtonClick();
                if (mediaRecorder != null){
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    Toast.makeText(MainActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show();

                }
            }
        });

        StartPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavaPath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this, "Start playing", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        StopPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    Toast.makeText(MainActivity.this, "Stopped playing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private boolean checkPermissions() {
        int first = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO);
        int second = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return first == PackageManager.PERMISSION_GRANTED &&
                second == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("ObsoleteSdkInt")
    private void StartRecording(){
        if (checkPermissions()) {
            isRecording = true;
            System.out.println( Environment.getExternalStorageDirectory().getAbsolutePath());
            String fileName = "recordingAudio.mp3";
            AudioSavaPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    +"/"+fileName;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
                AudioSavaPath= this.getExternalFilesDir(Environment.DIRECTORY_DCIM) + "/" + fileName;
            }
            else
            {
                AudioSavaPath= Environment.getExternalStorageDirectory().toString() + "/" + fileName;
            }
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setOutputFile(AudioSavaPath);
            speektotext();
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                Toast.makeText(MainActivity.this, "Recording started", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }else {
            Toast.makeText(MainActivity.this, "אין הרשאה", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }


    }
    @SuppressLint("ObsoleteSdkInt")
    private void checkPermission2(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }

    private void speektotext(){
        System.out.println("speektotext");
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            System.out.println("checkSelfPermission");
            checkPermission2();
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "he");
        Locale defaultLocale = Locale.getDefault();
        String languageCode = defaultLocale.getLanguage();
        System.out.println(languageCode);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
            }

            @Override
            public void onBeginningOfSpeech() {
                System.out.println("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {
            }

            @Override
            public void onBufferReceived(byte[] bytes) {
            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int i) {
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                System.out.println(data.get(0));
            }

            @Override
            public void onPartialResults(Bundle bundle) {
            }
            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
        speechRecognizer.startListening(speechRecognizerIntent);


    }
    private void startSpeechToText() {
        Toast.makeText(this, "startSpeechToText", Toast.LENGTH_SHORT).show();
    }

    private void stopSpeechToText() {
        speechRecognizer.stopListening();
        if (speechRecognizer != null) {
            speechRecognizer.stopListening();
            speechRecognizer.destroy();
            speechRecognizer = null;
            isRecording = false;
        }
    }

    private void toggleSpeechRecognition() {
        System.out.println("toggleSpeechRecognition");
        System.out.println(isRecording);
        if (isRecording) {
            System.out.println("startSpeechRecognitionProcess1");
            startSpeechRecognitionProcess();
        } else {
            stopSpeechToText();
            System.out.println("stopSpeechToText1");
        }
    }

    private void onSpeechRecognitionButtonClick() {}
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        final int REQUEST_CODE_SPEECH_INPUT = 1;
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                System.out.println(
                        Objects.requireNonNull(result).get(0));
            }
        }
    }

    private void requestAudioPermission() {
        System.out.println("requestAudioPermission");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("הרשאות");
            Toast.makeText(this, "הרשאות", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, SPEECH_REQUEST_CODE);
        } else {
            System.out.println("startSpeechToTextהרשאות");
            startSpeechToText();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SPEECH_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                System.out.println("Permission Granted");
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
            } else {
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private void startSpeechRecognitionProcess() {
        System.out.println("startSpeechRecognitionProcess");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            System.out.println("requestAudioPermission");
            requestAudioPermission();
        } else {
            System.out.println("startSpeechToText");

            startSpeechToText();
        }
    }
}