package net.novemberizing.simplefeed.application;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

public class SimplefeedApplicationVolley {
    private static final String Tag = "SimplefeedApplicationVolley";
    private static SimplefeedApplicationVolley instance = null;

    public static void gen(Context context) {
        synchronized (SimplefeedApplicationVolley.class) {
            if(instance == null) {
                instance = new SimplefeedApplicationVolley(context);
            }
        }
    }

    public static Request<String> str(String url, Response.Listener<String> success, Response.ErrorListener fail) {
        return instance.queue.add(new Request<String>(Request.Method.GET, url, fail) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String s = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                    return Response.success(s, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException | JsonSyntaxException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(String response) {
                success.onResponse(response);
            }
        });
    }

    public static <T> Request<T> json(String url, Class<T> c, Response.Listener<T> success, Response.ErrorListener fail) {
        return instance.queue.add(new Request<T>(Request.Method.GET, url, fail) {
            @Override
            protected Response<T> parseNetworkResponse(NetworkResponse response) {
                try {
                    Gson gson = SimplefeedApplicationGson.get();
                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                    return Response.success(gson.fromJson(json, c), HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException | JsonSyntaxException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(T response) {
                success.onResponse(response);
            }
        });
    }

    private final RequestQueue queue;

    public SimplefeedApplicationVolley(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }
}
