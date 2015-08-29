package org.wikispeedia.backseatdriver;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {
	 public static final String KEY_PREFS_SMS_BODY = "sms_body";
	 public static final String KEY_PREFS_FIRST_SYNC= "first_sync";
     private static final String APP_SHARED_PREFS = AppPreferences.class.getSimpleName(); //  Name of the file -.xml
     private SharedPreferences _sharedPrefs;
     private Editor _prefsEditor;

     public AppPreferences(Context context) {
         this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
         this._prefsEditor = _sharedPrefs.edit();
     }     
     public String getSmsBody() {
         return _sharedPrefs.getString(KEY_PREFS_SMS_BODY, "");
     }
     public void saveSmsBody(String text) {
         _prefsEditor.putString(KEY_PREFS_SMS_BODY, text);
         _prefsEditor.commit();
     }
     
     public Boolean getFirstSync() {
         return _sharedPrefs.getBoolean(KEY_PREFS_FIRST_SYNC, true);         
     }
     public void saveFirstSync(Boolean text) {
         _prefsEditor.putBoolean(KEY_PREFS_FIRST_SYNC, text);
         _prefsEditor.commit();
     }
}
