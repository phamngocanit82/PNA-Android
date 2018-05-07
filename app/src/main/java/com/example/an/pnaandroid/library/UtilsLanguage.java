package com.example.an.pnaandroid.library;

import android.content.Context;
import android.text.TextUtils;

import com.example.an.pnaandroid.library.prefs.AppHelperPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UtilsLanguage {
    private static UtilsLanguage utilsLanguage;
    private static Map<String, String> languageMap;
    private static Map<String, String> defaultLanguageMap;

    /**
     * @return a new instance of {@link UtilsLanguage}, adding the parameters into a bundle and
     * setting them as arguments.
     */
    static synchronized void newInstance(Context context) {
        if (utilsLanguage == null) {
            utilsLanguage = new UtilsLanguage();
            loadLanguageData(context);
        }
    }


    public static synchronized UtilsLanguage getUtilsLanguage() {
        return utilsLanguage;
    }


    private static Map<String, String> jsonToMap(String t) throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        JSONObject jObject = new JSONObject(t);
        Iterator<?> keys = jObject.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String value = jObject.getString(key);
            map.put(key, value);
        }
        return map;
    }

    private static synchronized String loadJSONFromAsset(Context context) {
        String json = null;
        InputStream is = null;
        try {
            is = context.getAssets().open("lang-tl-android.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            while (is.read(buffer) > 0) {
                // do nothing
            }
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            UtilsLog.l(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    UtilsLog.l(ex);
                }
            }
        }
        return json;

    }

    private static synchronized void loadLanguageData(Context context) {
        try {
            String tempData = AppHelperPreferences.newInstance(context).getMainLanguage();
            if (TextUtils.isEmpty(tempData)) {
                languageMap = jsonToMap(loadJSONFromAsset(context));
            } else {
                languageMap = jsonToMap(tempData);
            }
            //enable this when server language is enough
            defaultLanguageMap = jsonToMap(loadJSONFromAsset(context));
        } catch (JSONException e) {
            UtilsLog.l(e);
        }
    }

    public static synchronized void loadLanguageData(String data) {
        try {
            if (data != null && data.trim().length() > 0) {
                languageMap = jsonToMap(data);
            }
        } catch (JSONException e) {
            UtilsLog.l(e);
        }
    }

    public String getStringFromMap(Context context, String key) {
        String tempKey = key;
        if (tempKey == null) {
            tempKey = "";
        }
        if (languageMap == null) {
            loadLanguageData(context);
        }

        if (languageMap != null) {
            String result = languageMap.get(tempKey);
            if (result != null) {
                //todo change to regex for better performance
                result = result.replace("\\n", "\n");
                return result;
            } else {
                return defaultLanguageMap.get(tempKey);
            }
        }
        return tempKey;
    }
}
