package com.example.demo.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigPreferences {

    static final String NAME = "config_prefs";
    static final String KEY_REBOOT_DEMO = "reboot_demo";
    final Context mContext;
    final SharedPreferences mPrefs;

    ConfigPreferences(Context context) {
        mContext = context.getApplicationContext();
        mPrefs = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static ConfigPreferences from(Context context) {
        return new ConfigPreferences(context);
    }

    public boolean getRebootDemoEnable() {
        return mPrefs.getBoolean(KEY_REBOOT_DEMO, false);
    }

    public void setRebootDemoEnable(boolean enable) {
        mPrefs.edit().putBoolean(KEY_REBOOT_DEMO, enable).apply();
    }
}
