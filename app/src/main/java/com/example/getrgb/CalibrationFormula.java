package com.example.getrgb;

import static java.lang.Math.pow;

public class CalibrationFormula{
    public static double getIr(double R) {
        double Ir;
        Ir = -49862300 + 1680340 * R + 18485.52794 * pow(R, 2) - 42.78872 * pow(R, 3) + 0.293 * pow(R, 4);
        if (Ir <= 0) {
            return Ir = 1;
        } else {
            return Ir;
        }
    }

    public static double getIg(double G) {
        double Ig;
        Ig = -49896300 + 4535190 * G + 29827.97485 * pow(G, 2) - 175.92019 * pow(G, 3) + 0.41259 * pow(G, 4);
        if (Ig <= 0) {
            return Ig = 1;
        } else {
            return Ig;
        }
    }
}
