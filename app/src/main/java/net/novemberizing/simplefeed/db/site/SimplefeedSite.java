package net.novemberizing.simplefeed.db.site;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "site", indices = {@Index(value="url", unique = true)})
public class SimplefeedSite {
    @PrimaryKey(autoGenerate = true)
    public Integer uid;

    @ColumnInfo(name = "site")
    public String site;

    @ColumnInfo(name = "feed")
    public String feed;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "type")
    public String type;
    @ColumnInfo(name = "datetime")
    public Long datetime;
}
