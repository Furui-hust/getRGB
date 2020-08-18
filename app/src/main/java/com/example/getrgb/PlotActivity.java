package com.example.getrgb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import static com.example.getrgb.KapBitmapHalper.GetImageBitMap;

public class PlotActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        ImageView imageView_plot = findViewById(R.id.image_plot);
        imageView_plot.setImageBitmap(GetImageBitMap());
    }
}