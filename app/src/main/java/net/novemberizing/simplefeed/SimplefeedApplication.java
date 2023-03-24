package net.novemberizing.simplefeed;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import net.novemberizing.simplefeed.application.SimplefeedApplicationDB;
import net.novemberizing.simplefeed.application.SimplefeedApplicationGson;
import net.novemberizing.simplefeed.application.SimplefeedApplicationVolley;

import java.util.List;

public class SimplefeedApplication extends Application {
    public static <T> T get(List<T> collection, int index) {
        return collection.get(index);
    }
    public static Boolean equal(String x, String y) {
        if(x != null) {
            return x.equals(y);
        }
        return y == null;
    }
    public static void ui(Activity activity, Runnable runnable) {
        if(activity != null) {
            activity.runOnUiThread(runnable);
        }
    }

    public static Snackbar snackbar(Snackbar snackbar, View view, String value) {
        if(snackbar != null) {
            snackbar.dismiss();
        }
        if(view != null) {
            snackbar = Snackbar.make(view, value, Snackbar.LENGTH_SHORT);
            snackbar.show();
            return snackbar;
        }
        return null;
    }

    public static String buildFaviconUrl(String url) {
        Uri uri = Uri.parse(url);
        Uri.Builder builder = uri.buildUpon();
        builder.path("favicon.ico");
        uri = builder.build();
        return uri.toString();
    }

    public static String buildUrl(String url, String value) {
        if(value != null) {
            if(value.startsWith("http://") || value.startsWith("https://")) {
                return value;
            }
            Uri uri = Uri.parse(url);
            Uri.Builder builder = uri.buildUpon();
            if(value.startsWith("/")) {
                builder.path(value);
            } else {
                builder.appendPath(value);
            }
            uri = builder.build();
            return uri.toString();
        }
        return null;
    }
    private static final String Tag = "SimplefeedApplication";
    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(Tag, "onCreate()");

        SimplefeedApplicationGson.gen();
        SimplefeedApplicationVolley.gen(this);
        SimplefeedApplicationDB.gen(this);
    }

    @Override
    public void onTerminate(){
        super.onTerminate();

        Log.e(Tag, "onTerminate()");
    }
}
