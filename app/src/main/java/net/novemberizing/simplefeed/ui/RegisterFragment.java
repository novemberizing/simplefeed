package net.novemberizing.simplefeed.ui;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import net.novemberizing.simplefeed.R;
import net.novemberizing.simplefeed.SimplefeedApplication;
import net.novemberizing.simplefeed.application.SimplefeedApplicationDialog;
import net.novemberizing.simplefeed.data.Webpage;
import net.novemberizing.simplefeed.db.site.SimplefeedSite;
import net.novemberizing.simplefeed.db.site.SimplefeedSiteRepository;
import net.novemberizing.simplefeed.objects.SimplefeedTextWatcher;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class RegisterFragment extends Fragment {
    private static final String Tag = "RegisterFragment";
    private TextInputEditText webpageAddEdit;
    private MaterialCardView webpageCardView;
    private ImageView webpagePreviewFavicon;
    private ImageView webpagePreviewImage;
    private TextView webpagePreviewTitle;
    private TextView webpagePreviewUrl;
    private TextView webpagePreviewDescription;
    private ProgressBar webpagePreviewProgressBar;
    private LinearLayout webpagePreviewInvalidLayout;
    private TextView webpagePreviewInvalidText;
    private ImageView webpagePreviewIconType;
    private ImageView webpagePreviewIconFeed;
    private Snackbar snackbar;
    private Webpage webpage;
    private SimplefeedSiteRepository simplefeedSiteRep;
    private Request<Document> fetch;

    private ChipGroup chipGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.register_fragment_layout, container, false);

        webpageAddEdit = view.findViewById(R.id.register_fragment_webpage_add_edit);
        webpageCardView = view.findViewById(R.id.register_fragment_webpage_card_view);
        webpagePreviewFavicon = view.findViewById(R.id.register_fragment_webpage_preview_favicon);
        webpagePreviewImage = view.findViewById(R.id.register_fragment_webpage_preview_image);
        webpagePreviewTitle = view.findViewById(R.id.register_fragment_webpage_preview_title);
        webpagePreviewUrl = view.findViewById(R.id.register_fragment_webpage_preview_url);
        webpagePreviewDescription = view.findViewById(R.id.register_fragment_webpage_preview_description);
        webpagePreviewProgressBar = view.findViewById(R.id.register_fragment_webpage_preview_progress_bar);
        webpagePreviewInvalidLayout = view.findViewById(R.id.register_fragment_webpage_preview_invalid_layout);
        webpagePreviewInvalidText = view.findViewById(R.id.register_fragment_webpage_preview_invalid_text);
        webpagePreviewIconType = view.findViewById(R.id.register_fragment_webpage_preview_title_icon_type);
        webpagePreviewIconFeed = view.findViewById(R.id.register_fragment_webpage_preview_title_icon_feed);
        chipGroup = view.findViewById(R.id.register_fragment_already_registered_chip_group);

        ArrayList<String> chips = new ArrayList<>();

        chips.add("TECH");
        chips.add("DESIGN");
        chips.add("MARKETING");
        chips.add("BUSINESS");
        chips.add("SCIENCE");
        chips.add("POLITICS");
        chips.add("SECURITY");
        chips.add("WEBTOON");
        chips.add("ECONOMIC");
        chips.add("MOVIE");
        chips.add("DEVELOP");
        chips.add("CAR");
        chips.add("TRAVEL");
        chips.add("GAME");
        chips.add("PHOTOGRAPHY");
        chips.add("FASHION");
        chips.add("MUSIC");
        chips.add("SPORT");
        chips.add("HEALTH");

        for(String name : chips) {
            Chip chip = (Chip) inflater.inflate(R.layout.already_registered_chip, chipGroup, false);
            chip.setText(name);
            chip.setOnClickListener(v -> {
                Log.e(Tag, "onClick => " + name);
            });
            chipGroup.addView(chip);
        }

        simplefeedSiteRep = new SimplefeedSiteRepository();

        webpageAddEdit.addTextChangedListener(new SimplefeedTextWatcher(((sequence, start, before, count) -> webpagePreviewFetch(sequence.toString()))));
        webpageCardView.setOnClickListener(v -> registerUrl());

        return view;
    }

    private void registerUrl() {
        Log.e(Tag, "register url");
        if(webpagePreviewProgressBar.getVisibility() == View.INVISIBLE) {
            if(webpagePreviewInvalidText.getVisibility() == View.INVISIBLE) {
                simplefeedSiteRep.insert(SimplefeedSite.from(webpage), this::insertSimplefeedSite);
            } else {
                SimplefeedApplicationDialog.showRegisterWebpageDialog(getActivity(), (dialog, i) -> {
                    simplefeedSiteRep.insert(SimplefeedSite.from(webpage), this::insertSimplefeedSite);
                });
            }
        }
    }

    public void insertSimplefeedSite(SimplefeedSite site, Throwable exception) {
        if(exception != null) {
            snackbar = SimplefeedApplication.snackbar(snackbar, getView(), "이미 등록된 주소입니다.");
        } else {
            webpageCardView.setVisibility(View.INVISIBLE);
            snackbar = SimplefeedApplication.snackbar(snackbar, getView(), "새로운 주소가 등록되었습니다.");
        }
    }

    public void webpagePreviewFetch(String url) {
        if(url.matches(Patterns.WEB_URL.pattern())) {
            if(fetch != null) {
                fetch.cancel();
            }
            webpagePreviewProgressBar.setVisibility(View.VISIBLE);
            webpagePreviewInvalidLayout.setVisibility(View.VISIBLE);
            webpagePreviewInvalidText.setVisibility(View.INVISIBLE);
            fetch = Webpage.gen(url, (o, exception) -> {
                if(exception != null) {
                    Log.e(Tag, exception.toString());
                }
                if(o != null) {
                    webpage = o;
                    webpageCardView.setVisibility(View.VISIBLE);
                    Glide.with(RegisterFragment.this)
                            .load(webpage.image)
                            .into(webpagePreviewImage);
                    Glide.with(RegisterFragment.this)
                            .load(webpage.favicon)
                            .into(webpagePreviewFavicon);
                    webpagePreviewTitle.setText(webpage.title);
                    webpagePreviewUrl.setText(webpage.url);
                    webpagePreviewDescription.setText(webpage.description);
                    webpagePreviewProgressBar.setVisibility(View.INVISIBLE);
                    webpagePreviewInvalidText.setVisibility(View.INVISIBLE);
                    webpagePreviewInvalidLayout.setVisibility(View.INVISIBLE);

                    if(webpage.feed != null && webpage.feed.url != null) {
                        webpagePreviewIconFeed.setVisibility(View.VISIBLE);
                    } else {
                        webpagePreviewIconFeed.setVisibility(View.INVISIBLE);
                    }
                    if(webpage.site != null && webpage.site.url != null) {
                        webpagePreviewIconType.setVisibility(View.VISIBLE);
                    } else {
                        webpagePreviewIconType.setVisibility(View.INVISIBLE);
                    }
                } else {
                    webpagePreviewInvalidText.setVisibility(View.VISIBLE);
                    webpagePreviewProgressBar.setVisibility(View.INVISIBLE);
                }
                fetch = null;
            });
        }
    }
}
