package com.example.an.pnaandroid.library;

import android.os.Environment;

public class AppConstants {
    private static String externalStoragePatch = Environment
            .getExternalStorageDirectory().getPath();

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;
    public static final int API_STATUS_CODE_SUCCESS = 200;
    public static final int API_STATUS_CODE_BAD_REQUEST = 400;
    public static final int API_STATUS_CODE_NOT_AUTHORIZED = 403;

    public static final int MAXIMUM_CHARACTER_PHONE_NUMBER = 13;
    public static final int MINIMUM_CHARACTER_PHONE_NUMBER = 8;

    private static final String EXTERNAL_FOLDER_SAVE_LANGUAGE_PACKAGE_NAME = externalStoragePatch + "/download";
    public static final String EXTERNAL_FOLDER_SAVE_LANGUAGE_PACKAGE = EXTERNAL_FOLDER_SAVE_LANGUAGE_PACKAGE_NAME + "/";

    public static final String LOGIN_PLATFORM_FACEBOOK = "Facebook";
    public static final String LOGIN_PLATFORM_INSTA = "Instagram";
}
