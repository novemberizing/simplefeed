package net.novemberizing.simplefeed.db.site;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import net.novemberizing.simplefeed.data.Webpage;

@Entity(tableName = "site", indices = {@Index(value="url", unique = true)})
public class SimplefeedSite {
    public static class Type {
        public static final String feed = "feed";
        public static final String rss = "rss";
        public static final String webpage = "webpage";

        public static String gen(Webpage webpage) {
            if(webpage.feed != null) {
                return SimplefeedSite.Type.feed;
            }
            return SimplefeedSite.Type.webpage;
        }
    }


    public static SimplefeedSite from(Webpage webpage) {
        SimplefeedSite o = new SimplefeedSite();

        o.site = webpage.site != null ? webpage.site.url : null;
        o.feed = webpage.feed != null ? webpage.feed.url : null;
        o.url = webpage.site != null ? webpage.site.url : webpage.url;
        o.favicon = webpage.site != null && webpage.site.favicon != null ? webpage.site.favicon : webpage.favicon;
        o.type = SimplefeedSite.Type.gen(webpage);
        o.datetime = System.currentTimeMillis();

        return o;
    }
    @PrimaryKey(autoGenerate = true)
    public Integer uid;
    @ColumnInfo(name = "site")
    public String site;
    @ColumnInfo(name = "feed")
    public String feed;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "favicon")
    public String favicon;

    @ColumnInfo(name = "type")
    public String type;
    @ColumnInfo(name = "datetime")
    public Long datetime;
}
