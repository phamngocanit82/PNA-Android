package com.example.an.pnaandroid.library.prefs;

import android.content.Context;
import android.support.annotation.NonNull;

public class AppHelperPreferences implements HelperPreferences{
    private final SecurePreferences mPrefs;
    private static final String SE_KEY = "xyztal";
    private static final String SE_ENCRYPTKEY = "kdsjfb238lsksdjkf";
    private static AppHelperPreferences mInstance;

    public static synchronized AppHelperPreferences newInstance(@NonNull Context context) {
        if (mInstance == null) {
            mInstance = new AppHelperPreferences(context);
        }
        return mInstance;
    }
    private AppHelperPreferences(Context context) {
        mPrefs = new SecurePreferences(context, SE_KEY, SE_ENCRYPTKEY, true);
    }

    @Override
    public String getMainLanguage() {
        return mPrefs.getString(KeyPreferences.PREF_KEY_LANGUAGE);
    }

    @Override
    public void setMainLanguage(String data) {
        mPrefs.put(KeyPreferences.PREF_KEY_LANGUAGE, data);
    }

    @Override
    public String getPrivateKey() {
        return mPrefs.getString(KeyPreferences.PREF_KEY_PKEY);
    }

    @Override
    public int getLannguageVersion() {
        return mPrefs.getInt(KeyPreferences.PREF_KEY_LANGUAGE_VERSION, 0);
    }

    @Override
    public int getStaticDataVersion() {
        return mPrefs.getInt(KeyPreferences.PREF_KEY_STATIC_VERSION, 0);
    }

    @Override
    public int getMinimumRequiredVersion() {
        return mPrefs.getInt(KeyPreferences.PREF_KEY_MIN_VERSION, 1);
    }

    @Override
    public void setLanguageVersion(int version) {
        mPrefs.put(KeyPreferences.PREF_KEY_LANGUAGE_VERSION, version);
    }

    @Override
    public void setStaticDataVersion(int version) {
        mPrefs.put(KeyPreferences.PREF_KEY_STATIC_VERSION, version);
    }

    @Override
    public void setMinimumRequiredVersion(int version) {
        mPrefs.put(KeyPreferences.PREF_KEY_MIN_VERSION, version);
    }
}
