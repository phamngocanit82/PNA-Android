package com.example.an.pnaandroid.library;

import android.content.Context;
import android.graphics.Typeface;

public class UtilsFont {
    public static final int MITR_LIGHT = 0;
    public static final int MITR_REGULAR = 1;
    private static final int NUM_OF_CUSTOM_FONTS = 2;

    private static boolean fontsLoaded = false;

    private static Typeface[] fonts = new Typeface[NUM_OF_CUSTOM_FONTS];

    private static String[] fontPath = {
            "fonts/Mitr-Light.ttf",
            "fonts/Mitr-Regular.ttf",
    };

    public static Typeface getTypeface(Context context, int fontIdentifier) {
        if (!fontsLoaded) {
            loadFonts(context);
        }
        return fonts[fontIdentifier];
    }

    private static void loadFonts(Context context) {
        for (int i = 0; i < NUM_OF_CUSTOM_FONTS; i++) {
            fonts[i] = Typeface.createFromAsset(context.getAssets(), fontPath[i]);
        }
        fontsLoaded = true;
    }
}
