package net.novemberizing.simplefeed.db.site;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import net.novemberizing.simplefeed.data.Webpage;

@Entity(tableName = "site", indices = {@Index(value="url", unique = true)})
public class SimplefeedSite {
    public static SimplefeedSite from(Webpage webpage) {
        SimplefeedSite o = new SimplefeedSite();

        o.url = webpage.url;
        o.favicon = webpage.favicon;
        o.title = webpage.title;
        o.description = webpage.description;
        o.image = webpage.image;
        o.root = webpage.site != null ? webpage.site.url : null;
        o.feed = webpage.feed != null ? webpage.feed.url : null;
        o.datetime = System.currentTimeMillis();
        o.last = 0L;

        return o;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    public Integer uid;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "favicon")
    public String favicon;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "feed")
    public String feed;

    @ColumnInfo(name = "root")
    public String root;

    @ColumnInfo(name = "datetime")
    public Long datetime;

    @ColumnInfo(name = "last")
    public Long last;








//
//
//    @PrimaryKey(autoGenerate = true)
//    public Integer uid;
//
//    public String title;
//    public String image;
//    public String url;
//    public String description;
//    public String type;
//    public String favicon;
//    public String feed;
//    public String site;
//    public Long datetime;
//
//
//
//
//
//
//
//    @ColumnInfo(name = "site")
//    public String site;
//    @ColumnInfo(name = "feed")
//    public String feed;
//
//    @ColumnInfo(name = "url")
//    public String url;
//
//    @ColumnInfo(name = "favicon")
//    public String favicon;
//
//    @ColumnInfo(name = "type")
//    public String type;
//    @ColumnInfo(name = "datetime")
//    public Long datetime;
}
