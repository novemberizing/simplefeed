package net.novemberizing.simplefeed.data;

import net.novemberizing.simplefeed.SimplefeedApplication;
import net.novemberizing.simplefeed.application.SimplefeedApplicationFeed;
import net.novemberizing.simplefeed.application.SimplefeedApplicationRss;
import net.novemberizing.simplefeed.application.SimplefeedApplicationXml;

import org.jsoup.nodes.Document;

public class Feed {
    public static Feed gen(String url) {
        if(url != null) {
            Feed o = new Feed();

            o.url = url;

            return o;
        }
        return null;
    }

    public static Feed gen(String url, String title, String type, String description) {
        Feed o = new Feed();

        o.url = url;
        o.title = title;
        o.type = type;
        o.description = description;

        return o;
    }

    public String url;
    public String title;
    public String type;
    public String description;

    public void load(Document document) {
        if(SimplefeedApplication.equal(SimplefeedApplicationXml.is(document), SimplefeedApplicationXml.FEED)) {
            title = SimplefeedApplicationFeed.title(document);
            type = SimplefeedApplicationXml.FEED;
            description = SimplefeedApplicationFeed.description(document);
        } else {
            title = SimplefeedApplicationRss.title(document);
            type = SimplefeedApplicationXml.RSS;
            description = null;
        }
    }
}
