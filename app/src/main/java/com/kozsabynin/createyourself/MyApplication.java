package com.kozsabynin.createyourself;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.Locale;

/**
 * Created by Evgeni Developer on 02.04.2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        updateLanguage(this);
        super.onCreate();
    }

    public static void updateLanguage(Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        String lang = "ru";
        updateLanguage(ctx, lang);
    }

    public static void updateLanguage(Context ctx, String lang) {
        Configuration cfg = new Configuration();
        if (!TextUtils.isEmpty(lang))
            cfg.locale = new Locale(lang);
        else
            cfg.locale = Locale.getDefault();

        ctx.getResources().updateConfiguration(cfg, null);
    }
}
