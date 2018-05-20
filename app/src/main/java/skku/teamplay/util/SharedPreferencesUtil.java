package skku.teamplay.util;

import android.content.Context;
import android.content.SharedPreferences;

import skku.teamplay.app.TeamPlayApp;

public class SharedPreferencesUtil {
    public static SharedPreferences pref;

    public static void putString(String key, String value) {
        validate();
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void putInt(String key, int value) {
        validate();;
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static String getString(String key) {
        validate();
        return pref.getString(key, null);
    }

    public static int getInt(String key) {
        validate();
        return pref.getInt(key, 0);
    }

    private static void validate() {
        if(pref == null) {
            pref = TeamPlayApp.getAppInstance().getSharedPreferences("teamplay", Context.MODE_PRIVATE);
        }
    }
}
