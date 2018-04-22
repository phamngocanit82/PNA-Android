package com.example.an.pnaandroid.library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class UtilsDevice {
    public static Point getScreenSize(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        return screenSize;
    }
    private static Display getDefaultDisplay(Activity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }
    public static int getScreenWidth(Activity activity) {
        Display display = getDefaultDisplay(activity);
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
    public static int getScreenHeight(Activity activity) {
        Display display = getDefaultDisplay(activity);
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }
    public static boolean isTablet(Activity activity) {
        return (activity.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    public static boolean isLandscape(Activity activity) {
        // use screen width as criterion rather than getRotation
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return width > height;
    }
    public static boolean hasCamera(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
    public static boolean hasFlashlight(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
    @SuppressLint("InlinedApi")
    public static boolean hasManualSensor(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        return UtilsSystem.isAndroid5() && pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_SENSOR);
    }
    public static boolean isAppInstalled(Activity activity, final String appPackage) {
        Intent appIntent = activity.getPackageManager().getLaunchIntentForPackage(appPackage);
        return appIntent != null;
    }
}
