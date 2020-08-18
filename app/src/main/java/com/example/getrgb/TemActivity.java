package com.example.getrgb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;


import static com.example.getrgb.KapBitmapHalper.SaveImageView;

public class TemActivity extends AppCompatActivity {
    private TextView mTv_out;
    private ImageView mIv_image, mIv_hide;
    private Button mbtn_plot, mbtn_avr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tem);
        mTv_out = findViewById(R.id.textView);
        mIv_image = findViewById(R.id.imageView);
        mbtn_plot = findViewById(R.id.plot);
        mbtn_avr = findViewById(R.id.btn);
        mbtn_avr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double a = function.getAVG(mIv_image);
                mTv_out.setText("T=" + a);
            }
        });
        mIv_hide = findViewById(R.id.hidepicture);
        mIv_hide.setVisibility(View.GONE);
        mbtn_plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIv_hide.getDrawable() == null) {
                    int width = mIv_image.getWidth();
                    int height = mIv_image.getHeight();
                    double[][] T = function.getT(mIv_image);
                    initPython();
                    mIv_hide.setImageBitmap(getplot(width, height,T));
                    SaveImageView(mIv_hide);
                }
                Intent intent = new Intent(TemActivity.this, PlotActivity.class);
                startActivity(intent);
            }
        });
    }

    void initPython() {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }

    Bitmap getplot(int width, int height, double[][] T) {
        Python py = Python.getInstance();
        PyObject x = PyObject.fromJava(width);
        PyObject y = PyObject.fromJava(height);
        PyObject array = PyObject.fromJava(T);
        PyObject contour = py.getModule("plot").callAttr("test_contour", x, y, array);
        byte[] obj = contour.toJava(byte[].class);
        Log.e("TAG", "File Image exists");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        final Bitmap bitmap_plot = BitmapFactory.decodeByteArray(obj, 0, obj.length, options);
        if (bitmap_plot == null)
            Log.e("TAG", "Bitmap empty");
        else
            Log.e("TAG", "Bit not empty");
        return bitmap_plot;
    }

}