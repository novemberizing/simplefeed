package net.novemberizing.simplefeed;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import net.novemberizing.simplefeed.ui.FeedListFragment;
import net.novemberizing.simplefeed.ui.FeedViewFragment;
import net.novemberizing.simplefeed.ui.RegisterFragment;
import net.novemberizing.simplefeed.ui.RegisteredSiteFragment;

public class MainActivity extends AppCompatActivity {
    private static final String Tag = "MainActivity";
    private Button menuBtn;
    private Button feedlistBtn;
    private Button registerBtn;
    private Button feedviewBtn;
    private Button registeredSiteBtn;
    private DrawerLayout drawer;

    private FeedListFragment feedListFrag;
    private FeedViewFragment feedViewFrag;
    private RegisteredSiteFragment registeredSiteFrag;
    private RegisterFragment registerFrag;
    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_activity);
        Log.e(Tag, "onCreate(...)");

        menuBtn = findViewById(R.id.main_activity_menu);
        feedlistBtn = findViewById(R.id.main_activity_feedlist_btn);
        registerBtn = findViewById(R.id.main_activity_register_btn);
        feedviewBtn = findViewById(R.id.main_activity_feedview_btn);
        registeredSiteBtn = findViewById(R.id.main_activity_registered_site_btn);
        drawer = findViewById(R.id.main_activity_drawer);

        menuBtn.setOnClickListener(view -> {
            drawer.openDrawer(GravityCompat.START);
        });

        feedlistBtn.setOnClickListener(view -> {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if(feedListFrag == null) {
                feedListFrag=new FeedListFragment();
            }
            transaction.replace(R.id.main_activity_host_fragment, feedListFrag);
            transaction.commit();
        });

        registeredSiteBtn.setOnClickListener(view -> {
            drawer.closeDrawer(GravityCompat.START);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if(registeredSiteFrag == null) {
                registeredSiteFrag=new RegisteredSiteFragment();
            }
            transaction.replace(R.id.main_activity_host_fragment, registeredSiteFrag);
            transaction.commit();
        });

        feedviewBtn.setOnClickListener(view -> {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if(feedViewFrag == null) {
                feedViewFrag=new FeedViewFragment();
            }
            transaction.replace(R.id.main_activity_host_fragment, feedViewFrag);
            transaction.commit();
        });

        registerBtn.setOnClickListener(view -> {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if(registerFrag == null) {
                registerFrag=new RegisterFragment();
            }
            transaction.replace(R.id.main_activity_host_fragment, registerFrag);
            transaction.commit();
        });
    }
}
