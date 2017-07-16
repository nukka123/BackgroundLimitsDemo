package com.example.demo.utils;

public class ThreadUtils {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // Timber.e(e);
        }
    }
}
