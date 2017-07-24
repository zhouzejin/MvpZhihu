package com.sunny.mvpzhihu.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.sunny.mvpzhihu.injection.qualifier.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {

    private static final String PREF_FILE_NAME = "zhihu_pref_file";

    public static final String IS_SHOW_SWIPE_BACK_HINT = "is_show_swipe_back_hint";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public void putBoolean(String key, boolean value) {
        mPref.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPref.getBoolean(key, defValue);
    }

}
