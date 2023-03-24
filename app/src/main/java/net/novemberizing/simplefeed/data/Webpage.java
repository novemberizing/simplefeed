package net.novemberizing.simplefeed.data;

import android.util.Log;

import com.android.volley.Request;

import net.novemberizing.core.objects.Callback;
import net.novemberizing.simplefeed.SimplefeedApplication;
import net.novemberizing.simplefeed.application.SimplefeedApplicationFeed;
import net.novemberizing.simplefeed.application.SimplefeedApplicationHtml;
import net.novemberizing.simplefeed.application.SimplefeedApplicationJsoup;
import net.novemberizing.simplefeed.application.SimplefeedApplicationRss;
import net.novemberizing.simplefeed.application.SimplefeedApplicationVolley;
import net.novemberizing.simplefeed.application.SimplefeedApplicationXml;
import net.novemberizing.simplefeed.db.site.SimplefeedSite;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Webpage {
    private static final String Tag = "Webpage";

    public static Request<Document> gen(String url, Callback<Webpage> callback) {
        return SimplefeedApplicationVolley.jsoup(url, document -> {
            try {
                Webpage webpage = SimplefeedApplication.equal(SimplefeedApplicationJsoup.is(document), "xml") ? Webpage.generateFromXml(url, document) : Webpage.generateFromHtml(url, document);

                callback.on(webpage, null);
            } catch(Throwable e) {
                callback.on(null, e);
            }
        }, exception -> callback.on(null, exception));
    }

    public static Webpage generateFromHtml(String url, Document document) {
        Webpage o = new Webpage();

        o.url = url;

        o.title = SimplefeedApplicationHtml.title(document);
        o.image = SimplefeedApplicationHtml.image(o.url, document);

        o.type = SimplefeedApplicationHtml.type(document);
        o.description = SimplefeedApplicationHtml.description(document);
        o.favicon = SimplefeedApplicationHtml.favicon(document, o.url);

        o.feed = Feed.gen(SimplefeedApplicationHtml.alternate(o.url, document));
        // TODO
        o.site = null;

        return o;
    }

    public static Webpage generateFromXml(String url, Document document) {
        if(SimplefeedApplication.equal(SimplefeedApplicationXml.is(document), SimplefeedApplicationFeed.tag)) {
            Webpage o = new Webpage();

            o.url = url;

            o.title = SimplefeedApplicationFeed.title(document);
            o.image = null;
            o.type = SimplefeedApplicationFeed.tag;
            o.description = SimplefeedApplicationFeed.description(document);
            o.favicon = null;

            o.feed = Feed.gen(o.url, o.title, o.type, o.description);
            // TODO:
            // o.site = Site.generateFromUrl(SimplefeedApplication.buildUrl(url, ""));

            return o;

        } else if(SimplefeedApplication.equal(SimplefeedApplicationXml.is(document), SimplefeedApplicationRss.tag)) {
            Webpage o = new Webpage();

            o.url = url;

            o.title = SimplefeedApplicationHtml.title(document);
            o.image = SimplefeedApplicationHtml.image(o.url, document);
//            o.image = null;
//            o.type = SimplefeedApplicationFeed.tag;
//            o.description = SimplefeedApplicationFeed.description(document);
//            o.favicon = SimplefeedApplicationHtml.buildFaviconUrl(url);
//
//            o.feed = Feed.generateFromJsoup(url, document);
//            o.site = Site.generateFromUrl(SimplefeedApplication.buildUrl(url, ""));



        }
        Log.w(Tag, "not supported xml");
        return null;
    }













    public static Webpage xml(String url, Document document) {
        // TODO: IMPLEMENT THIS
        Log.e(Tag, "TODO: IMPLEMENT THIS");
        return null;
    }
    public static Webpage xml(String url, String xml) {
        return Webpage.xml(url, Jsoup.parse(xml, Parser.xmlParser()));
    }

    // TODO: REMOVE THIS
    public static Webpage html(String url, Document document) {
        // NOT USED
        return null;
    }

    public static Webpage html(String url, String html) {
        return Webpage.html(url, Jsoup.parse(html));
    }
    public static Webpage gen(String url, String s) {
        return s.startsWith("<?xml") ? Webpage.xml(url, s) : Webpage.html(url, s);
    }
    // TODO: REFACTOR
//    public static Request<String> gen(String url, Callback<Webpage> callback) {
//        return SimplefeedApplicationVolley.str(url, response -> {
//            try {
//                Webpage webpage = Webpage.gen(url, response);
//                if(webpage.feed != null) {
//                    webpage.feed.url = SimplefeedApplication.buildUrl(url, webpage.feed.url);
//                    SimplefeedApplicationVolley.str(webpage.feed.url, feed -> {
//                        try {
//                            webpage.feed(feed);
//                            if(webpage.site != null) {
//                                webpage.site.url = SimplefeedApplication.buildUrl(url, webpage.site.url);
//                                SimplefeedApplicationVolley.str(webpage.site.url, site -> {
//                                    try {
//                                        webpage.site(site);
//                                        callback.on(webpage, null);
//                                    } catch(Throwable e) {
//                                        callback.on(webpage, e);
//                                    }
//                                }, exception -> callback.on(webpage, exception));
//                            } else {
//                                callback.on(webpage, null);
//                            }
//                        } catch(Throwable e) {
//                            callback.on(webpage, e);
//                        }
//                    }, exception -> callback.on(webpage, exception));
//                } else {
//                    SimplefeedApplicationVolley.str(SimplefeedApplicationHtml.buildSiteUrl(webpage.url), site -> {
//                        try {
//                            webpage.site(SimplefeedApplicationHtml.buildSiteUrl(webpage.url), site);
//                            callback.on(webpage, null);
//                        } catch(Throwable e) {
//                            callback.on(webpage, e);
//                        }
//                    }, exception -> callback.on(webpage, exception));
//                }
//            } catch(Throwable e) {
//                callback.on(null, e);
//            }
//        }, exception -> callback.on(null, exception));
//    }

    public String url;
    public String title;
    public String image;
    public String type;
    public String description;
    public String favicon;

    public Site site;
    public Feed feed;

    public void generateFeed(String s) {
        if(feed != null) {
            Document document = Jsoup.parse(s, Parser.xmlParser());

            feed.title = SimplefeedApplicationXml.title(document);
            feed.description = SimplefeedApplicationXml.description(document);
            feed.type = SimplefeedApplicationXml.type(document);
        }
    }

    public void feed(String s) {
        if(feed != null) {
            Document document = Jsoup.parse(s, Parser.xmlParser());

            feed.title = SimplefeedApplicationJsoup.text(SimplefeedApplicationJsoup.select(document, "title", 0));
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
            site.image = SimplefeedApplication.buildUrl(site.url, SimplefeedApplicationJsoup.select(document, "meta[property=og:image]", "content"));
            site.type = SimplefeedApplicationJsoup.select(document, "meta[property=og:type]", "content");
            site.description = SimplefeedApplicationJsoup.select(document, "meta[property=og:description]", "content");
            site.favicon = SimplefeedApplication.buildUrl(site.url, SimplefeedApplicationJsoup.select(document, "link[rel][type=image/x-icon]", "href"));
            if(site.favicon == null) {
                site.favicon = SimplefeedApplication.buildFaviconUrl(site.url);
            }
        } else {
            Log.w(Tag, "site == null");
        }
    }

    public void site(String url, String s) {
        if(site == null) {
            site = new Site(url);
        }
        site(s);
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

    public String favicon(){
        return site != null && site.favicon != null ? site.favicon : this.favicon;
    }

    public String description() {
        return site != null && site.description != null ? site.description : this.description;
    }

    public SimplefeedSite genSimplefeedSite() {
        return SimplefeedSite.from(this);
    }
}
