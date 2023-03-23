package net.novemberizing.simplefeed.application;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import net.novemberizing.simplefeed.db.site.SimplefeedSite;
import net.novemberizing.simplefeed.db.site.SimplefeedSiteDao;
import net.novemberizing.simplefeed.db.site.SimplefeedSiteRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {SimplefeedSite.class}, version = 1)
public abstract class SimplefeedApplicationDB extends RoomDatabase {
    public static final String NAME = "simplefeed.db";

    private static SimplefeedApplicationDB instance;
    private static final ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void gen(Context context) {
        synchronized (SimplefeedApplicationDB.class) {
            context.deleteDatabase(NAME);   // TODO: REMOVE THIS

            if(instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(), SimplefeedApplicationDB.class, NAME)
                        .build();

                SimplefeedSiteRepository repository = new SimplefeedSiteRepository();
                LiveData<List<SimplefeedSite>> data = repository.all();
                // Application application;

                // application.getLi
                // context.get
                // data.observe(this.getLif, );
            }
        }
    }

    public static SimplefeedApplicationDB get() {
        return instance;
    }

    public static void execute(Runnable runnable) {
        SimplefeedApplicationDB.pool.execute(runnable);
    }

    public abstract SimplefeedSiteDao simplefeedSiteDao();
}
