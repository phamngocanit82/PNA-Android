package com.example.an.pnaandroid.library;

import android.util.Base64;

import com.bumptech.glide.load.model.LazyHeaderFactory;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.an.pnaandroid.BuildConfig;

public final class ImageLoaderAuthenticator implements LazyHeaderFactory {

    private static ImageLoaderAuthenticator mInstance;
    private static LazyHeaders lazyHeader;
    private final String un;
    private final String pw;

    public static ImageLoaderAuthenticator newInstance() {
        if (lazyHeader == null) {
            mInstance = new ImageLoaderAuthenticator(BuildConfig.DEFAULT_USR,
                    BuildConfig.DEFAULT_PW);
            lazyHeader = new LazyHeaders.Builder() // can be cached in a field and reused
                    .addHeader("Authorization", mInstance)
                    .build();
        }
        return mInstance;
    }

    public static LazyHeaders getLazyHeader() {
        if (lazyHeader == null) {
            newInstance();
        }
        return lazyHeader;
    }

    public ImageLoaderAuthenticator(String un, String pw) {
        this.un = un;
        this.pw = pw;
    }

    @Override
    public String buildHeader() {
        return "Basic " + android.util.Base64.encodeToString((un + ":" + pw).getBytes(),
                Base64.NO_WRAP);
    }
}
