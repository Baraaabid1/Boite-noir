package com.example.first_half_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView name;
    ImageView bg , Box ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        name.animate().translationY(-1400).setDuration(1000).setStartDelay(4000);
        bg = findViewById(R.id.bg);
        bg.animate().translationY(-1400).setDuration(1000).setStartDelay(4000);
        Box = findViewById(R.id.box);
        name.animate().translationY(-1400).setDuration(1000).setStartDelay(4000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
            }
        }, 3000);}
}