package net.novemberizing.simplefeed.application;

import net.novemberizing.simplefeed.SimplefeedApplication;

import org.jsoup.nodes.Document;

public class SimplefeedApplicationHtml {
    public static String title(Document document) {
        return SimplefeedApplicationJsoup.select(document,"meta[property=og:title]", "content");
    }

    public static String type(Document document) {
        return SimplefeedApplicationJsoup.select(document,"meta[property=og:type]", "content");
    }

    public static String image(Document document, String url) {
        return SimplefeedApplication.buildUrl(url, SimplefeedApplicationJsoup.select(document,"meta[property=og:image]", "content"));
    }

    public static String description(Document document) {
        return SimplefeedApplicationJsoup.select(document,"meta[property=og:description]", "content");
    }
    public static String favicon(Document document, String url) {
        String favicon = SimplefeedApplicationJsoup.select(document,"link[rel=icon]", "href");

        return SimplefeedApplication.buildUrl(url, favicon != null ? favicon : "/favicon.ico");
    }
    public static String alternate(Document document, String url) {
        String o = SimplefeedApplication.buildUrl(url, SimplefeedApplicationJsoup.select(document,"link[rel=alternate][type=application/atom+xml]", "href"));
        if(o == null){
            o = SimplefeedApplication.buildUrl(url, SimplefeedApplicationJsoup.select(document,"link[rel=alternate][type=application/rss+xml]", "href"));
        }
        return o;
    }
}
