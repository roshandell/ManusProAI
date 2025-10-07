package com.manuspro.ai;

import android.content.Context;
import android.content.SharedPreferences;

public class StatsManager {
    
    private static final String PREFS_NAME = "ManusProAI";
    private static final String KEY_TOTAL_MESSAGES = "total_messages";
    private static final String KEY_TOTAL_IMAGES = "total_images";

    public static void incrementMessages(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int current = prefs.getInt(KEY_TOTAL_MESSAGES, 0);
        prefs.edit().putInt(KEY_TOTAL_MESSAGES, current + 1).apply();
    }

    public static void incrementImages(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int current = prefs.getInt(KEY_TOTAL_IMAGES, 0);
        prefs.edit().putInt(KEY_TOTAL_IMAGES, current + 1).apply();
    }

    public static int getTotalMessages(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_TOTAL_MESSAGES, 0);
    }

    public static int getTotalImages(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_TOTAL_IMAGES, 0);
    }
}
