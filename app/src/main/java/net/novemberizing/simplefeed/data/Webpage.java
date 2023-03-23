package net.novemberizing.simplefeed.data;

import android.util.Log;

import com.android.volley.Request;

import net.novemberizing.core.objects.Callback;
import net.novemberizing.simplefeed.SimplefeedApplication;
import net.novemberizing.simplefeed.application.SimplefeedApplicationJsoup;
import net.novemberizing.simplefeed.application.SimplefeedApplicationVolley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Webpage {
    private static final String Tag = "Webpage";

    public static Webpage xml(String url, Document document) {
        // TODO: IMPLEMENT THIS
        return null;
    }
    public static Webpage xml(String url, String xml) {
        return Webpage.xml(url, Jsoup.parse(xml, Parser.xmlParser()));
    }

    public static Webpage html(String url, Document document) {
        Webpage o = new Webpage();

        o.url = url;

        o.title = SimplefeedApplicationJsoup.select(document, "meta[property=og:title]", "content");
        o.image = SimplefeedApplicationJsoup.select(document, "meta[property=og:image]", "content");
        o.type = SimplefeedApplicationJsoup.select(document, "meta[property=og:type]", "content");
        o.description = SimplefeedApplicationJsoup.select(document, "meta[property=og:description]", "content");
        o.feed = Feed.gen(document);

        // TODO: OPENGRAPH 가 존재를 하지 않을 경우

        return o;
    }

    public static Webpage html(String url, String html) {
        return Webpage.html(url, Jsoup.parse(html));
    }
    public static Webpage gen(String url, String s) {
        return s.startsWith("<?xml") ? Webpage.xml(url, s) : Webpage.html(url, s);
    }
    public static Request<String> gen(String url, Callback<Webpage> callback) {
        return SimplefeedApplicationVolley.str(url, response -> {
            try {
                Webpage webpage = Webpage.gen(url, response);
                if(webpage.feed != null) {
                    webpage.feed.url = SimplefeedApplication.buildUrl(url, webpage.feed.url);
                    SimplefeedApplicationVolley.str(webpage.feed.url, feed -> {
                        try {
                            webpage.feed(feed);
                            if(webpage.site != null) {
                                webpage.site.url = SimplefeedApplication.buildUrl(url, webpage.site.url);
                                SimplefeedApplicationVolley.str(webpage.site.url, site -> {
                                    try {
                                        webpage.site(site);
                                        callback.on(webpage, null);
                                    } catch(Throwable e) {
                                        callback.on(webpage, e);
                                    }
                                }, exception -> callback.on(webpage, exception));
                            } else {
                                callback.on(webpage, null);
                            }
                        } catch(Throwable e) {
                            callback.on(webpage, e);
                        }
                    }, exception -> callback.on(webpage, exception));
                } else {
                    callback.on(webpage, null);
                }
            } catch(Throwable e) {
                callback.on(null, e);
            }
        }, exception -> callback.on(null, exception));
    }

    public String url;
    public String title;
    public String image;
    public String type;
    public String description;

    public Site site;
    public Feed feed;

    public void feed(String s) {
        if(feed != null) {
            Document document = Jsoup.parse(s, Parser.xmlParser());

            feed.title = SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(document, "title", 0));
            feed.image = null;
            feed.description = SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(document, "subtitle", 0));

            feed.type = Feed.type(document);

            String url = SimplefeedApplicationJsoup.select(document, "link[rel=alternate][type=text/html]", "href");

            if(url != null) {
                site = new Site(url);
            }

            // TODO: ENTRY
            Elements elements = document.select("entry");
            if(elements.size() > 0) {
                feed.entries = new ArrayList<>(elements.size());
                for(Element element : elements) {
                    feed.entries.add(Feed.Entry.gen(element));
                }
            }
        } else {
            Log.w(Tag, "feed == null");
        }
    }

    public void site(String s) {
        if(site != null) {
            Document document = Jsoup.parse(s);

            site.title = SimplefeedApplicationJsoup.select(document, "meta[property=og:title]", "content");
            site.image = SimplefeedApplicationJsoup.select(document, "meta[property=og:image]", "content");
            site.type = SimplefeedApplicationJsoup.select(document, "meta[property=og:type]", "content");
            site.description = SimplefeedApplicationJsoup.select(document, "meta[property=og:description]", "content");
        } else {
            Log.w(Tag, "site == null");
        }
    }

    public String title() {
        return site != null && site.title != null ? site.title : this.title;
    }

    public String type() {
        return site != null && site.type != null ? site.type : this.type;
    }

    public String image() {
        return site != null && site.image != null ? site.image : this.image;
    }

    public String url() {
        return site != null && site.url != null ? site.url : this.url;
    }

    public String description() {
        return site != null && site.description != null ? site.description : this.description;
    }
}
