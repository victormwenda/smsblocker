package com.marvik.apps.smsblocker.preferences.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.marvik.apps.smsblocker.preferences.userprefs.UserPreferences;

/**
 * Created by victor on 11/7/2015.
 */
public class PrefsManager implements UserPreferences{

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PrefsManager(@NonNull Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Context getContext() {
        return context;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        if (editor == null) {
            editor = getSharedPreferences().edit();
        }
        return editor;
    }

    private <T> void commit(String preference, T typeOf) {


        if (typeOf instanceof Boolean) {
            getEditor().putBoolean(preference, (Boolean) typeOf);
        }
        if (typeOf instanceof Float) {
            getEditor().putFloat(preference, (Float) typeOf);
        }
        if (typeOf instanceof Integer) {
            getEditor().putInt(preference, (Integer) typeOf);
        }
        if (typeOf instanceof Long) {
            getEditor().putLong(preference, (Long) typeOf);
        }
        if (typeOf instanceof String) {
            getEditor().putString(preference, (String) typeOf);
        }
        getEditor().commit();
    }

    private <T> T read(String preference, Class<T> componentType, T defaultValue) {


        if (componentType == Boolean.class) {
            return (T) Boolean.valueOf(getSharedPreferences().getBoolean(preference, (Boolean) defaultValue));

        }
        if (componentType == Float.class) {
            return (T) Float.valueOf(getSharedPreferences().getFloat(preference, (Float) defaultValue));
        }
        if (componentType == Integer.class) {
            return (T) Integer.valueOf(getSharedPreferences().getInt(preference, (Integer) defaultValue));
        }
        if (componentType == Long.class) {
            return (T) Long.valueOf(getSharedPreferences().getLong(preference, (Long) defaultValue));
        }
        if (componentType == String.class) {
            return (T) String.valueOf(getSharedPreferences().getString(preference, (String) defaultValue));
        }

        return null;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }
}
