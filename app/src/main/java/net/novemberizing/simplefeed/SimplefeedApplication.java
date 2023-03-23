package net.novemberizing.simplefeed;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import net.novemberizing.simplefeed.application.SimplefeedApplicationDB;
import net.novemberizing.simplefeed.application.SimplefeedApplicationGson;
import net.novemberizing.simplefeed.application.SimplefeedApplicationVolley;

public class SimplefeedApplication extends Application {
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
