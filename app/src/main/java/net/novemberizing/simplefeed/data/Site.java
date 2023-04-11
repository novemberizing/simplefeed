package net.novemberizing.simplefeed.data;

import net.novemberizing.simplefeed.application.SimplefeedApplicationHtml;

import org.jsoup.nodes.Document;

public class Site {
    public static Site gen(String url) {
        if(url != null) {
            Site o = new Site();

            o.url = url;

            return o;
        }
        return null;
    }

    public String url;
    public String title;
    public String type;
    public String image;
    public String description;
    public String favicon;

    public void load(Document document) {
        title = SimplefeedApplicationHtml.title(document);
        type = SimplefeedApplicationHtml.type(document);
        image = SimplefeedApplicationHtml.image(document, url);
        description = SimplefeedApplicationHtml.description(document);
        favicon = SimplefeedApplicationHtml.favicon(document, url);
    }
}
