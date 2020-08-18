package com.example.getrgb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TouchActivity extends AppCompatActivity {

    private ImageView mImageView_touch;
    private TextView mTextView_touch;
    private Bitmap bitmap_touch;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        mImageView_touch = findViewById(R.id.imageView_touch);
        mTextView_touch = findViewById(R.id.textView_touch);
        mImageView_touch.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mImageView_touch.setDrawingCacheEnabled(true);
                    bitmap_touch = Bitmap.createBitmap(mImageView_touch.getDrawingCache());
                    mImageView_touch.setDrawingCacheEnabled(false);
                    double T = function.getpointT( bitmap_touch, x, y);
                    mTextView_touch.setText("T=" + (int) T + "K");
                }
                return true;
            }
        });
    }
}