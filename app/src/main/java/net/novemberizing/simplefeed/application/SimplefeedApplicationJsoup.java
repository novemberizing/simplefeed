package net.novemberizing.simplefeed.application;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;

import net.novemberizing.core.ListUtil;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class SimplefeedApplicationJsoup {
    private static final String Tag = "SimplefeedApplicationJsoup";
    public static String htmlUrl(Document document, String key) {
        Element element = document.selectFirst("link[rel=alternate][type=text/html]");

        return element != null ? element.attr(key) : null;
    }
    public static String feedUrl(Document document, String key) {
        Element element = document.selectFirst("link[rel=alternate][type=application/atom+xml]");
        if(element == null) {
            element = document.selectFirst("link[rel=alternate][type=application/rss+xml]");
        }
        return element != null ? element.attr(key) : null;
    }

    public static Elements select(Document document, String query) {
        return document.select(query);
    }

    public static String select(Element element, String query, String key) {
        Element e = element.selectFirst(query);
        if(e != null) {
            return e.attr(key);
        }
        return null;
    }

    public static Element select(Element element, String query, int index) {
        if(index == 0) {
            return element.selectFirst(query);
        }
        return ListUtil.get(element.select(query), index);
    }

    public static Element select(Document document, String query, int index) {
        if(index == 0) {
            return document.selectFirst(query);
        }
        return ListUtil.get(document.select(query), index);
    }


    public static String select(Document document, List<String> queries, String key) {
        for(String query : queries) {
            String o = SimplefeedApplicationJsoup.select(document, query, key);
            if(o != null) {
                return o;
            }
        }
        return null;
    }

    public static String select(Document document, String query, String key) {
        Element element = document.selectFirst(query);
        if(element != null) {
            return element.attr(key);
        }
        return null;
    }

    public static String text(Element element) {
        return element != null ? element.text() : null;
    }

    public static String html(Element element) {
        return element != null ? StringEscapeUtils.unescapeXml(element.html()) : null;
    }
}
