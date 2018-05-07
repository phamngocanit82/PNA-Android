package com.example.an.pnaandroid.library;

import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsString {
    public static final String EMPTY_STRING = "";

    /**
     * get a key of string from resources
     *
     * @param resID
     * @return key
     */
    public static String getString(Context context, int resID) {
        return context.getResources().getString(resID);
    }

    /**
     * get a string with main language of app with key get from resource
     *
     * @param resID
     * @return string value
     */
    public static String getStringBaseOnLanguage(Context context, int resID) {
        String key = getString(context, resID);
        return UtilsLanguage.getUtilsLanguage().getStringFromMap(context, key);
    }

    public static String getStringFormatBaseOnLanguage(Context context, int resID, Object... format) {
        String key = getString(context, resID);
        return String.format(UtilsLanguage.getUtilsLanguage().getStringFromMap(context, key), format);
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        if (TextUtils.isEmpty(email)) return false;
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static <E extends Enum<E>> E lookup(Class<E> e, String id) {
        try {
            return Enum.valueOf(e, id);
        } catch (IllegalArgumentException ex) {
            // log error or something here
            throw new RuntimeException(
                    "Invalid value for enum " + e.getSimpleName() + ": " + id);
        }
    }

    public static boolean isPwValid(String pw) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return pw.matches(pattern);
    }

    public static boolean isPhoneNumerValid(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            return AppConstants.MINIMUM_CHARACTER_PHONE_NUMBER <= phone.length() && phone.length() <= AppConstants.MAXIMUM_CHARACTER_PHONE_NUMBER;
        }
    }

    public static InputFilter[] getMaximumCharInputFilter(int max) {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(max);
        return fArray;
    }

    public static String getRoundPrice(float price) {
        return String.format(Locale.getDefault(), "฿%.0f", price);
    }
}
