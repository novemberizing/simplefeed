package net.novemberizing.simplefeed.application;

import android.net.Uri;

import net.novemberizing.simplefeed.SimplefeedApplication;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * TODO: OPEN GRAPH 가 존재를 하지 않을 경우
 */
public class SimplefeedApplicationHtml {
    public static String buildFaviconUrl(String baseUrl) {
        return SimplefeedApplicationHtml.buildUrl(baseUrl, "/favicon.ico");
    }
    public static String buildUrl(String baseUrl, String relativeUrl) {
        if(relativeUrl != null) {
            if(relativeUrl.startsWith("https://") || relativeUrl.startsWith("http://")) {
                return relativeUrl;
            }
            Uri uri = Uri.parse(baseUrl);
            Uri.Builder builder = uri.buildUpon();
            if(relativeUrl.startsWith("/")) {
                builder.path(relativeUrl);
            } else {
                builder.path(uri.getPath() + "/" + relativeUrl);
            }
            uri = builder.build();
            return uri.toString();
        }
        return SimplefeedApplicationHtml.buildUrl(baseUrl, "/favicon.ico");
    }
    public static String buildSiteUrl(String url) {
        Uri uri = Uri.parse(url);
        Uri.Builder builder = uri.buildUpon();

        builder.path("");

        uri = builder.build();
        return uri.toString();
    }

    public static String title(Document document) {
        return SimplefeedApplicationJsoup.select(document, "meta[property=og:title]", "content");
    }

    public static String image(String url, Document document) {
        return SimplefeedApplication.buildUrl(url, SimplefeedApplicationJsoup.select(document, "meta[property=og:image]", "content"));
    }

    public static String alternate(String url, Document document) {
        String href = SimplefeedApplicationJsoup.select(document, "link[rel=alternate][type=application/atom+xml]", "href");
        if(href == null) {
            href = SimplefeedApplicationJsoup.select(document, "link[rel=alternate][type=application/rss+xml]", "href");
        }

        return href != null ? SimplefeedApplication.buildUrl(url, href) : null;
    }

    public static String type(Document document) {
        return SimplefeedApplicationJsoup.select(document, "meta[property=og:type]", "content");
    }

    public static String description(Document document) {
        return SimplefeedApplicationJsoup.select(document, "meta[property=og:description]", "content");
    }

    public static String favicon(Document document, String baseUrl) {
        Element element = document.selectFirst("link[rel=icon]");
        if(element != null) {
            String href = element.attr("href");
            if(href.length() > 0) {
                return SimplefeedApplicationHtml.buildUrl(baseUrl, href);
            }
        }
        return SimplefeedApplicationHtml.buildUrl(baseUrl, "/favicon.ico");
    }


}
