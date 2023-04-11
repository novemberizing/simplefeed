package net.novemberizing.simplefeed.application;

import net.novemberizing.simplefeed.SimplefeedApplication;

import org.jsoup.nodes.Document;

public class SimplefeedApplicationFeed {
    public static String title(Document document) {
        return SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(document, "title", 0));
    }

    public static String description(Document document) {
        return SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(document, "subtitle", 0));
    }

    public static String site(Document document, String url) {
        String o = SimplefeedApplicationJsoup.select(document, "link[rel=alternate]", "href");
        return o != null ? SimplefeedApplication.buildUrl(url, o) : null;
    }
}