package net.novemberizing.simplefeed.db.site;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SimplefeedSiteDao {
    // TODO: REMOVE IF NOT USED
    @Query("SELECT * FROM site ORDER BY datetime DESC LIMIT 1")
    LiveData<SimplefeedSite> recent();

    @Query("SELECT * FROM site")
    LiveData<List<SimplefeedSite>> all();

    @Insert
    void insert(SimplefeedSite site);
}
