
package com.example.mynotes;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {

    private static final String PREF_NAME = "MyNotesPrefs";
    private static final String KEY_TEMA = "dark_mode";

    private SharedPreferences preferences;

    public PrefsManager(Context context) {
        preferences = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE
        );
    }

    public void simpanTema(boolean darkMode) {
        preferences.edit()
                .putBoolean(KEY_TEMA, darkMode)
                .apply();
    }

    public boolean ambilTema() {
        return preferences.getBoolean(KEY_TEMA, false);
    }
}
