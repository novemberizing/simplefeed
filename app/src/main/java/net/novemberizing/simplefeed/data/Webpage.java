package net.novemberizing.simplefeed.data;

import com.android.volley.Request;

import net.novemberizing.core.objects.Callback;
import net.novemberizing.simplefeed.SimplefeedApplication;
import net.novemberizing.simplefeed.application.SimplefeedApplicationFeed;
import net.novemberizing.simplefeed.application.SimplefeedApplicationHtml;
import net.novemberizing.simplefeed.application.SimplefeedApplicationJsoup;
import net.novemberizing.simplefeed.application.SimplefeedApplicationRss;
import net.novemberizing.simplefeed.application.SimplefeedApplicationVolley;
import net.novemberizing.simplefeed.application.SimplefeedApplicationXml;

import org.jsoup.nodes.Document;

public class Webpage {
    public static Request<Document> gen(String url, Callback<Webpage> callback) {
        return SimplefeedApplicationVolley.jsoup(url, document -> {
            if(SimplefeedApplication.equal(SimplefeedApplicationJsoup.is(document), SimplefeedApplicationJsoup.XML)) {
                Webpage webpage = generateFromXml(url, document);
                SimplefeedApplicationVolley.jsoup(webpage.site.url, html -> {
                    try {
                        webpage.site.load(html);
                        callback.on(webpage, null);
                    } catch(Exception e) {
                        callback.on(webpage, e);
                    }
                }, exception -> callback.on(webpage, exception));
            } else {
                Webpage webpage = generateFromHtml(url, document);
                if(webpage.feed != null && webpage.feed.url != null) {
                    SimplefeedApplicationVolley.jsoup(webpage.feed.url, xml -> {
                        try {
                            webpage.feed.load(xml);
                            SimplefeedApplicationVolley.jsoup(webpage.site.url, html -> {
                                try {
                                    webpage.site.load(html);
                                    callback.on(webpage, null);
                                } catch(Exception e) {
                                    callback.on(webpage, e);
                                }
                            }, exception -> callback.on(webpage, exception));
                        } catch(Exception e) {
                            callback.on(webpage, e);
                        }
                    }, exception -> callback.on(webpage, exception));
                } else if(webpage.site != null && webpage.site.url != null) {
                    SimplefeedApplicationVolley.jsoup(webpage.site.url, html -> {
                        try {
                            webpage.site.load(html);
                            callback.on(webpage, null);
                        } catch(Exception e) {
                            callback.on(webpage, e);
                        }
                    }, exception -> callback.on(webpage, exception));
                } else {
                    callback.on(webpage, null);
                }
            }
        }, exception -> callback.on(null, exception));
    }
    public static Webpage generateFromHtml(String url, Document document) {
        Webpage o = new Webpage();

        o.url = url;

        o.title = SimplefeedApplicationHtml.title(document);
        o.type = SimplefeedApplicationHtml.type(document);
        o.image = SimplefeedApplicationHtml.image(document, url);
        o.description = SimplefeedApplicationHtml.description(document);
        o.favicon = SimplefeedApplicationHtml.favicon(document, url);

        o.feed = Feed.gen(SimplefeedApplicationHtml.alternate(document, url));
        o.site = Site.gen(SimplefeedApplication.buildHostUrl(url));

        return o;
    }
    public static Webpage generateFromXml(String url, Document document) {
        return SimplefeedApplication.equal(SimplefeedApplicationXml.is(document), SimplefeedApplicationXml.FEED) ? generateFromFeed(url, document) : generateFromRss(url, document);
    }
    public static Webpage generateFromRss(String url, Document document) {
        Webpage o = new Webpage();

        o.url = url;

        o.title = SimplefeedApplicationRss.title(document);
        o.type = SimplefeedApplicationXml.RSS;
        o.image = null;
        o.description = null;
        o.favicon = SimplefeedApplication.buildUrl(url, "/favicon.ico");

        o.feed = Feed.gen(o.url, o.title, o.type, o.description);
        o.site = Site.gen(SimplefeedApplication.buildHostUrl(url));

        return o;
    }
    public static Webpage generateFromFeed(String url, Document document) {
        Webpage o = new Webpage();

        o.url = url;

        o.title = SimplefeedApplicationFeed.title(document);
        o.type = SimplefeedApplicationXml.FEED;
        o.image = null;
        o.description = SimplefeedApplicationFeed.description(document);
        o.favicon = SimplefeedApplication.buildUrl(url, "/favicon.ico");

        o.feed = Feed.gen(o.url, o.title, o.type, o.description);
        o.site = Site.gen(SimplefeedApplicationFeed.site(document, url));
        if(o.site == null) {
            o.site = Site.gen(SimplefeedApplication.buildHostUrl(url));
        }

        return o;
    }

    public String url;
    public String title;
    public String type;
    public String image;
    public String description;
    public String favicon;
    public Feed feed;
    public Site site;
}
