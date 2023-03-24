package net.novemberizing.simplefeed.application;

import net.novemberizing.simplefeed.SimplefeedApplication;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SimplefeedApplicationXml {
    public static class Type {
        public static final String feed = "feed";
        public static final String rss = "rss";

        public static String is(Document document) {
            // TODO: IMPLEMENT THIS
            return SimplefeedApplicationXml.Type.feed;
        }
    }
    public static String is(Document document) {
        Element element = SimplefeedApplication.get(document.children(), 0);

        return element.tagName();
    }


    public static String title(Document document) {
        switch(SimplefeedApplicationXml.Type.is(document)) {
            case SimplefeedApplicationXml.Type.feed:    return SimplefeedApplicationFeed.title(document);
            case SimplefeedApplicationXml.Type.rss:     return SimplefeedApplicationRss.title(document);
            default:                                    return SimplefeedApplicationFeed.title(document);
        }
    }

    public static String description(Document document) {
        switch(SimplefeedApplicationXml.Type.is(document)) {
            case SimplefeedApplicationXml.Type.feed:    return SimplefeedApplicationFeed.description(document);
            case SimplefeedApplicationXml.Type.rss:     return SimplefeedApplicationRss.description(document);
            default:                                    return SimplefeedApplicationFeed.description(document);
        }
    }

    public static String type(Document document) {
        return SimplefeedApplicationXml.Type.feed;
    }

}
