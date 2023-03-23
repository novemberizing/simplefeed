package net.novemberizing.simplefeed.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import net.novemberizing.simplefeed.R;

public class RegisteredSiteFragment extends Fragment {
    private static final String Tag = "RegisterFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        // Inflate the layout for this fragment
        Log.e(Tag, "RegisteredSiteFragment");
        return inflater.inflate(R.layout.registered_site_fragment_layout, container, false);
    }
}
