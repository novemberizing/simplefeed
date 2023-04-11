package net.novemberizing.simplefeed.application;

import net.novemberizing.simplefeed.SimplefeedApplication;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SimplefeedApplicationXml {
    public static final String FEED = "feed";
    public static final String RSS = "rss";

    public static String is(Document document) {
        Element element = SimplefeedApplication.get(document.children(), 0);

        return element != null ?  element.tagName() :  null;
    }
}
