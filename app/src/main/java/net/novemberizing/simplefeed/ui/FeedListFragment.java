package net.novemberizing.simplefeed.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import net.novemberizing.simplefeed.R;

public class FeedListFragment extends Fragment {
    private static final String Tag = "RegisterFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        // Inflate the layout for this fragment
        Log.e(Tag, "RegisterFragment");
        return inflater.inflate(R.layout.feed_list_fragment_layout, container, false);
    }
}