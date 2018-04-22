package com.example.an.pnaandroid.library;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import com.example.an.pnaandroid.BuildConfig;

import java.util.List;

public class UtilsSystem {
    /**
     * Hide default constructor.
     */
    private UtilsSystem() {
        throw new UnsupportedOperationException();
    }

    /**
     * get version code of application
     *
     * @return version code
     */
    public static int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    /**
     * get version name of application
     *
     * @return version
     */
    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * Get information if Android version is Kitkat (4.4).
     *
     * @return true if Kitkat.
     */
    public static boolean isKitkat() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT;
    }

    /**
     * Get information if Android version is Lollipop (5.0) or higher.
     *
     * @return true if Lollipop or higher.
     */
    public static boolean isAndroid5() {
        return isAtLeastVersion(Build.VERSION_CODES.LOLLIPOP);
    }

    /**
     * Check if Android version is at least the given version.
     *
     * @param version The version
     * @return true if Android version is at least the given version
     */
    private static boolean isAtLeastVersion(final int version) {
        return Build.VERSION.SDK_INT >= version;
    }

    /**
     * check connection is available
     *
     * @return true if a connection is available
     * @author tuan.giao
     */
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null){
            NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
            return activeNetwork != null;
        }
        return false;
    }

    public static void openAppInGoogleStore(Context activity, String packageName) {
        Intent rateIntent = new Intent(Intent.ACTION_VIEW);
        rateIntent.setData(Uri.parse("market://details?id=" + packageName));
        boolean marketFound = false;
        final List<ResolveInfo> otherApps = activity.getPackageManager().queryIntentActivities(rateIntent, 0);
        for (ResolveInfo otherApp : otherApps) {
            if (otherApp.activityInfo.applicationInfo.packageName.equals("com.android.vending")) {

                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(
                        otherAppActivity.applicationInfo.packageName,
                        otherAppActivity.name
                );
                // rateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                // rateIntent.setComponent(componentName);
                activity.startActivity(rateIntent);
                marketFound = true;
                break;
            }
        }
        // if GP not present on device, open web browser
        if (!marketFound) {
            try {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName));
                activity.startActivity(webIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
