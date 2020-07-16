package com.example.getrgb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TouchActivity extends AppCompatActivity {

    private ImageView mImageView_touch;
    private TextView mTextView_touch;
    private Bitmap bitmap_touch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        mImageView_touch = findViewById(R.id.imageView_touch);
        mTextView_touch = findViewById(R.id.textView_touch);
        mImageView_touch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mImageView_touch.setDrawingCacheEnabled(true);
                    bitmap_touch = Bitmap.createBitmap(mImageView_touch.getDrawingCache());
                    mImageView_touch.setDrawingCacheEnabled(false);
                    int color = bitmap_touch.getPixel(x, y);
                    int r = Color.red(color);
                    int g = Color.green(color);
                    int b = Color.blue(color);
                    //mTextView_touch.setText("R=" + r + ",G=" + g + ",B=" + b);
                    mTextView_touch.setText("x=" + x + ",y=" + y + ";R=" + r + ",G=" + g + ",B=" + b);
                }
                return true;
            }
        });
    }
}