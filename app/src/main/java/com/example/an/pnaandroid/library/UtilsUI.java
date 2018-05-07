package com.example.an.pnaandroid.library;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;

public class UtilsUI {
    public static void showToast(Context context, int aligment, String message,
                                 int time) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }

    public static void showDebugToast(Context context, String message) {
        showToast(context, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
                message, 1);
    }

    public static void showToast(Context context, String message) {
        showToast(context, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
                message, 1);
    }

    public static void showToastBottom(Context context, String message) {
        showToast(context, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, message,
                0);
    }

    public static void showToast(Context context, String message, int time) {
        showToast(context, Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,
                message, time);
    }


    public static void loadImageWithGlide(Context context, String url, ImageView imageView, @DrawableRes int imageResourceHolder) {
        if (context != null && !TextUtils.isEmpty(url)) {
            GlideUrl newUrl = new GlideUrl(url, ImageLoaderAuthenticator.getLazyHeader());
            Glide.with(context).load(newUrl).placeholder(imageResourceHolder)
                    .error(imageResourceHolder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate().into(imageView);
        } else {
            imageView.setImageResource(imageResourceHolder);
        }
    }

    public static void loadImageWithGlide(Context context, String url, ImageView imageView, @DrawableRes int imageResourceHolder, @DrawableRes int imageResourceErrorHolder) {
        if (context != null && !TextUtils.isEmpty(url)) {
            GlideUrl newUrl = new GlideUrl(url, ImageLoaderAuthenticator.getLazyHeader());
            Glide.with(context).load(newUrl).placeholder(imageResourceHolder)
                    .error(imageResourceErrorHolder)
                    .dontAnimate()
                    .into(imageView);
        } else {
            imageView.setImageResource(imageResourceHolder);
        }
    }

    public static void loadImageWithGlide(Context context, String url, ImageView imageView, @DrawableRes int imageResourceHolder,
                                          DiskCacheStrategy diskCacheStrategy) {
        if (context != null && !TextUtils.isEmpty(url)) {
            GlideUrl newUrl = new GlideUrl(url, ImageLoaderAuthenticator.getLazyHeader());
            Glide.with(context).load(newUrl).placeholder(imageResourceHolder)
                    .error(imageResourceHolder)
                    .dontAnimate()
                    .diskCacheStrategy(diskCacheStrategy)
                    .into(imageView);
        } else {
            imageView.setImageResource(imageResourceHolder);
        }
    }

    /**
     * underline a textview with font
     *
     * @param textView textview
     * @param content  content of textview
     * @param font     font
     */
    public static void underLineTextViewWithFont(TextView textView, String content, int font) {
        if (!TextUtils.isEmpty(content)){
            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            textView.setText(spannableString);
            Typeface typeFace = UtilsFont.getTypeface(textView.getContext(), font);
            textView.setTypeface(typeFace);
        }

    }

    public static Spanned fromHtml(String source) {
        if (TextUtils.isEmpty(source)) {
            source = "";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }
}
