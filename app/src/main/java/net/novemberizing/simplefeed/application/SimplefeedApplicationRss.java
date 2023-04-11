package net.novemberizing.simplefeed.application;

import org.jsoup.nodes.Document;

public class SimplefeedApplicationRss {
    public static String title(Document document) {
        return SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(document, "title", 0));
    }
}
