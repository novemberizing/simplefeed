package net.novemberizing.simplefeed.data;

public class Site {
    public static Site generateFromUrl(String url) {
        Site o = new Site();

        o.url = url;

        return o;
    }

    public String url;
    public String title;
    public String image;
    public String type;
    public String description;

    public String favicon;
    public Site(){

    }

    public Site(String url) {
        this.url = url;
    }
}
