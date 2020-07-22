package com.example.getrgb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mBtnClick;
    private Button mBtnTouch;
    private Button mBtnTem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnClick = findViewById(R.id.click);
        mBtnTouch = findViewById(R.id.touch);
        mBtnTem = findViewById(R.id.getT);
        mBtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClickActivity.class);
                startActivity(intent);
            }
        });
        mBtnTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TouchActivity.class);
                startActivity(intent);
            }
        });
        mBtnTem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TemActivity.class);
                startActivity(intent);
            }
        });
    }
}