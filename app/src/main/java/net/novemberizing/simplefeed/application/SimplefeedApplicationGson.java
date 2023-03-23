package net.novemberizing.simplefeed.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SimplefeedApplicationGson {
    private static Gson gson = null;

    public static void gen() {
        synchronized (SimplefeedApplicationGson.class) {
            GsonBuilder builder = new GsonBuilder();

            builder.disableHtmlEscaping();

            builder.setPrettyPrinting();
            // builder.registerTypeAdapter(Date.class, new OrientalismApplicationGsonDateAdapter());

            gson = builder.create();
        }
    }

    public static Gson get() {
        return gson;
    }

    public static <T> String to(T o) {
        return gson.toJson(o);
    }
}
