package com.example.getrgb;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

import static com.example.getrgb.CalibrationFormula.getIg;
import static com.example.getrgb.CalibrationFormula.getIr;
import static java.lang.Math.pow;

public class function {

    public static double[][] getT(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
        imageView.setDrawingCacheEnabled(false);
        int width = imageView.getWidth();
        int height = imageView.getHeight();
        int[][] R = new int[width][height];
        int[][] G = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
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
        double[][] Ir = new double[width][height];
        double[][] temp = new double[width][height];//温度
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (ref_rmax <= R[i][j]) {
                    ref_rmax = R[i][j]; //找最亮点
                }
            }
            if (ref_rmax > 245) {
                ref_rmax = ref_rmax - 5;//避免图像过亮
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (R[i][j] <= ref_rmax && R[i][j] >= (ref_rmax - ref_yu)) {
                    ref_r = ref_r + R[i][j];
                    ref_g = ref_g + G[i][j];
                    number++;
                }
            }
        }
        ref_r = ref_r / number;
        ref_g = ref_g / number;//选平均值作为参考点进行温度计算
        double k1 = 1;
        double ref_ir1 = getIr(ref_r);
        double ref_ig1 = CalibrationFormula.getIg(ref_g);
        double ref_temp;
        ref_temp = (-C2 * ((1 / WaveR) - (1 / WaveG))) / Math.log(Math.abs((ref_ir1 * pow(WaveR, 5)) / (ref_ig1 * pow(WaveG, 5))));//参考点温度
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
        return temp;
    }

    public static double getAVG(ImageView imageView) {
        double[][] temp=getT(imageView);
        int width = imageView.getWidth();
        int height = imageView.getHeight();
        double sum = 0;
        int n = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (temp[i][j] > 800) {
                    sum = sum + temp[i][j];
                    n = n + 1;
                }
            }
        }
        double avr = sum / n;
        return avr;
    }

    public static double getpointT(Bitmap bitmap, int x, int y) {
        int color = bitmap.getPixel(x, y);
        int r = Color.red(color);
        int g = Color.green(color);
        double WaveR = (6.3 * pow(10, -7));
        double WaveG = (5.2 * pow(10, -7)); //计算温度中使用的参数
        double C2 = 1.4388 * pow(10, -2);//普朗克常数
        double ir = getIr(r);
        double ig = getIg(g);
        double temp = -C2 * ((1 / WaveR) - (1 / WaveG)) / Math.log(Math.abs((ir * pow(WaveR, 5)) / (ig * pow(WaveG, 5))));
        if (r < 50) {
            return 800;
        } else {
            return temp;
        }
    }
}
