package net.novemberizing.simplefeed.db.site;

import androidx.lifecycle.LiveData;

import net.novemberizing.core.objects.Callback;
import net.novemberizing.simplefeed.application.SimplefeedApplicationDB;

import java.util.List;

public class SimplefeedSiteRepository {
    private SimplefeedSiteDao simplefeedSiteDao;

    public LiveData<List<SimplefeedSite>> all(){
        return simplefeedSiteDao.all();
    }

    public void insert(SimplefeedSite site, Callback<SimplefeedSite> callback) {
        SimplefeedApplicationDB.execute(() -> {
            try {
                simplefeedSiteDao.insert(site);
                callback.on(site, null);
            } catch(Throwable e) {
                callback.on(site, e);
            }
        });
    }

    public SimplefeedSiteRepository() {
        SimplefeedApplicationDB db = SimplefeedApplicationDB.get();

        simplefeedSiteDao = db.simplefeedSiteDao();
    }
}
