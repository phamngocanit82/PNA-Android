package com.example.an.pnaandroid.library.prefs;

public interface HelperPreferences {
    String getMainLanguage();

    void setMainLanguage(String data);

    String getPrivateKey();

    int getLannguageVersion();

    int getStaticDataVersion();

    int getMinimumRequiredVersion();

    void setLanguageVersion(int version);

    void setStaticDataVersion(int version);

    void setMinimumRequiredVersion(int version);
}
