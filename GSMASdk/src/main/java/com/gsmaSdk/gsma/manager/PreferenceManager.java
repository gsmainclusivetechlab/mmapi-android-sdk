package com.gsmaSdk.gsma.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.security.GeneralSecurityException;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

/**
 * store the application data
 */

public class PreferenceManager {

    private SharedPreferences preferences;


    /**
     * @param context Context of the application
     */

    @SuppressLint("ObsoleteSdkInt")
    public void init(Context context) {
        if (preferences == null) {
            preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
           try {
                MasterKey masterKey = new MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();
                preferences = EncryptedSharedPreferences.create(
                        context,
                        "secret_shared_prefs",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );
            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * @param token save the token to shared preferences
     *
     */
    public void saveToken(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PreferenceConstants.TOKEN, token);
        editor.apply();
    }

    /**
     * @return retrieve the access token
     */

    public String retrieveToken() {
        return preferences.getString(PreferenceConstants.TOKEN, "");
    }

    /**
     * @return PreferenceManager singleton instance of the class
     */
    public static PreferenceManager getInstance() {
        return SingleInstanceAdmin.instance;
    }

    /**
     * creating singleton instance of the class
     */
    private static class SingleInstanceAdmin {
        static final PreferenceManager instance = new PreferenceManager();
    }

}
