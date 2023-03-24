package net.novemberizing.simplefeed.data;

import net.novemberizing.simplefeed.application.SimplefeedApplicationJsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Feed {

    public static class Entry {
        public static Entry gen(Element element) {
            Entry o = new Entry();

            o.title = SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(element, "title", 0));
            o.url = SimplefeedApplicationJsoup.select(element, "link", "href");
            o.publish = SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(element, "published", 0));
            o.update = SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(element, "updated", 0));
            o.id = SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(element, "id", 0));
            o.content = SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(element, "content", 0));
            o.author = SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(element, "author name", 0));
            o.categories = null;
            Elements elements = element.select("category");
            if(elements.size() > 0) {
                o.categories = new ArrayList<>(elements.size());
                for(Element e : elements) {
                    o.categories.add(e.attr("term"));
                }
            }
            o.summary = SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(element, "summary", 0));

            return o;
        }
        public String url;
        public String title;
        public String publish;
        public String update;
        public String id;
        public String content;

        public String author;
        public ArrayList<String> categories;
        public String summary;
    }
    public static final String ATOM = "atom";
    public static final String RSS = "rss";

    // gen(o.url, o.title, o.type, o.description);
    public static Feed gen(String url) {
        Feed o = new Feed();

        o.url = url;

        return o;
    }
    public static Feed gen(String url, String title, String type, String description) {
        Feed o = new Feed();

        o.url = url;
        o.title = title;
        o.type = type;
        o.description = description;

        return o;
    }


    public static String type(Document document) {
        Element element = document.selectFirst("feed");
        if(element != null) {
            return Feed.ATOM;
        }
        return null;
    }
    public static Feed gen(Document document) {
        String url = SimplefeedApplicationJsoup.select(document, List.of("link[rel=alternate][type=application/atom+xml]", "link[rel=alternate][type=application/rss+xml]"), "href");
        if(url != null) {
            Feed o = new Feed();

            o.url = url;

            return o;
        }
        return null;
    }

    public String url;
    public String title;
    public String type;
    public String description;
    public ArrayList<Entry> entries;
}
