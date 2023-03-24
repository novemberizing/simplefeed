package net.novemberizing.simplefeed.application;

import org.jsoup.nodes.Document;

public class SimplefeedApplicationFeed {
    public static final String tag = "feed";

    public static Boolean is(Document document) {
        // TODO: IMPLEMENT THIS
        return true;
    }

    public static String title(Document document) {
        return SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(document, "title", 0));
    }

    public static String description(Document document) {
        return SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(document, "subtitle", 0));
    }
}