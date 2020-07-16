package com.example.getrgb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ClickActivity extends AppCompatActivity {

    private ImageView mImageView_click;
    private TextView mTextView_click;
    private Bitmap bitmap_click;
    private Button mButton_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);
        mImageView_click = findViewById(R.id.imageView_click);
        mTextView_click = findViewById(R.id.textView_click);
        mButton_click = findViewById(R.id.button_click);
        mButton_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView_click.setDrawingCacheEnabled(true);
                bitmap_click = Bitmap.createBitmap(mImageView_click.getDrawingCache());
                mImageView_click.setDrawingCacheEnabled(false);
                int color = bitmap_click.getPixel(320, 320);
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                mTextView_click.setText("R=" + r + ",G=" + g + ",B=" + b);
            }
        });
    }
}