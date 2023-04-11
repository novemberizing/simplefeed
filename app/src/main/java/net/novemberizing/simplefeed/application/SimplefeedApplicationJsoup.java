package net.novemberizing.simplefeed.application;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SimplefeedApplicationJsoup {
    public static final String XML = "xml";
    public static String is(Document document) {
        Document.OutputSettings settings = document.outputSettings();
        Document.OutputSettings.Syntax syntax = settings.syntax();

        return syntax.name();
    }

    public static String select(Document document, String query, String key) {
        Element element = document.selectFirst(query);

        return element != null ? element.attr(key) : null;
    }

    public static Element select(Document document, String query, int index) {
        if(index == 0) {
            return document.selectFirst(query);
        }
        Elements elements = document.select(query);

        return elements.get(index);
    }

    public static String text(Element element) {
        return element != null ? element.text() : null;
    }
}
