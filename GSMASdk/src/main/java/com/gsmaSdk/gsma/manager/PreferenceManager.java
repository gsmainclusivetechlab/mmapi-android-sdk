package com.gsmaSdk.gsma.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *  store the application data
 *
 */

public class PreferenceManager {

    private SharedPreferences preferences ;



    /**
     *
     * @param context Context of the application
     */

    public void init(Context context) {
        if(preferences==null) preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    /*
    * @param token save the token to shared preferences
    *
    */
    public void saveToken(String token){
        SharedPreferences.Editor editor =  preferences.edit();
        editor.putString(PreferenceConstants.TOKEN,token);
        editor.apply();
    }

    /**
     * @return retrieve the access token
     *
     */

    public String retrieveToken(){
       return preferences.getString(PreferenceConstants.TOKEN,"");
    }
    /**
     * @return  PreferenceManager singleton instance of the class
     *
     */
    public static PreferenceManager getInstance(){
        return SingleInstanceAdmin.instance;
    }

    /**
     * creating singleton instance of the class
     *
     */
    private static class SingleInstanceAdmin{
        static final PreferenceManager instance = new PreferenceManager();
    }

}
