package com.example.getrgb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.Math.pow;

public class TemActivity extends AppCompatActivity {
    private TextView mTv_out;
    private ImageView mIv_image;
    private Button mIv_btn;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tem);
        mTv_out = findViewById(R.id.textView);
        mIv_image = findViewById(R.id.imageView);
        mIv_btn = findViewById(R.id.btn);
        mIv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIv_image.setDrawingCacheEnabled(true);
                bitmap = Bitmap.createBitmap(mIv_image.getDrawingCache());
                mIv_image.setDrawingCacheEnabled(false);
                int width = mIv_image.getWidth();
                int height = mIv_image.getHeight();
                int minx = mIv_image.getMinimumWidth();
                int miny = mIv_image.getMinimumHeight();
                int[][] R = new int[width][height];
                int[][] G = new int[width][height];
                for (int i = minx; i < width; i++) {
                    for (int j = miny; j < height; j++) {
                        int color = bitmap.getPixel(i, j);
                        R[i][j] = Color.red(color);
                        G[i][j] = Color.green(color);
                    }
                }
                double WaveR = (6.3 * pow(10, -7));
                double WaveG = (5.2 * pow(10, -7)); //计算温度中使用的参数
                double C2 = 1.4388 * pow(10, -2);//普朗克常数
                final int Ry = 10;//为温度计算阈值
                final int ref_yu = 10;//ref_yu为参考点选取的范围
                double ref_rmax = 0;
                double ref_r = 0;
                double ref_g = 0;
                double number = 0;
                double Ir[][] = new double[width][height];
                double Ig[][] = new double[width][height];//辐射强度
                double temp[][] = new double[width][height];//温度
                for (int i = minx; i < width; i++) {
                    for (int j = miny; j < height; j++) {
                        if (ref_rmax <= R[i][j]) {
                            ref_rmax = R[i][j]; //找最亮点
                        }
                    }
                    if (ref_rmax > 245) {
                        ref_rmax = ref_rmax - 5;//避免图像过亮
                    }
                }
                for (int i = minx; i < width; i++) {
                    for (int j = miny; j < height; j++) {
                        if (R[i][j] <= ref_rmax && R[i][j] >= (ref_rmax - ref_yu)) {
                            ref_r = ref_r + R[i][j];
                            ref_g = ref_g + G[i][j];
                            number++;
                        }
                    }
                }
                ref_r = ref_r / number;
                ref_g = ref_g / number;//选平均值作为参考点进行温度计算
                double k1 = 0.8;
                double ref_ir1 = 0;
                double ref_ig1 = 0;
                ref_ir1 = getIr(ref_r);
                ref_ig1 = getIg(ref_g);
                double ref_temp;
                ref_temp = -C2 * ((1 / WaveR) - (1 / WaveG)) / Math.log(Math.abs((ref_ir1 * pow(WaveR, 5)) / (ref_ig1 * pow(WaveG, 5))));//参考点温度
                for (int j = 0; j < height; j++) {
                    for (int i = 0; i < width; i++) {
                        if (R[i][j] > Ry)//需要R值大于计算阈值，如果小于阈值将温度设为800
                        {
                            Ir[i][j] = getIr(R[i][j]);
                            temp[i][j] = (1 / (1 / ref_temp - WaveR / C2 * Math.log(Ir[i][j] / ref_ir1))) * k1;
                            if (temp[i][j] < 800) {
                                temp[i][j] = 800;
                            }
                        } else temp[i][j] = 800;
                    }
                }
                double sum = 0;
                int n = 0;
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        if (temp[i][j] > 800) {
                            sum = sum + temp[i][j];
                            n++;
                        }
                    }
                }
                double avr = sum / n;
                mTv_out.setText("平均温度" + (int) avr + "K");
            }
        });
    }

    public double getIr(double R) {
        double Ir;
        Ir = -49862300 + 1680340 * R + 18485.52794 * pow(R, 2) - 42.78872 * pow(R, 3) + 0.293 * pow(R, 4);
        if (Ir <= 0) {
            return Ir = 1;
        } else {
            return Ir;
        }
    }

    public double getIg(double G) {
        double Ig;
        Ig = -49896300 + 4535190 * G + 29827.97485 * pow(G, 2) - 175.92019 * pow(G, 3) + 0.41259 * pow(G, 4);
        if (Ig <= 0) {
            return Ig = 1;
        } else {
            return Ig;
        }
    }
}