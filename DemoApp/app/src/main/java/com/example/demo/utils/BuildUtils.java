package com.example.demo.utils;

import android.os.Build;

public class BuildUtils {
    public static boolean isAtLeastO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }
}
