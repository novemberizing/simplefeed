package net.novemberizing.simplefeed.ui;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import net.novemberizing.simplefeed.R;
import net.novemberizing.simplefeed.application.SimplefeedApplicationDialog;
import net.novemberizing.simplefeed.application.SimplefeedApplicationGson;
import net.novemberizing.simplefeed.data.Webpage;
import net.novemberizing.simplefeed.objects.SimplefeedTextWatcher;

import java.util.regex.Matcher;

public class RegisterFragment extends Fragment {
    private static final String Tag = "RegisterFragment";
    private TextInputEditText webpageAddEdit;
    private MaterialCardView webpageCardView;
    private ImageView webpagePreviewImage;
    private TextView webpagePreviewTitle;
    private TextView webpagePreviewUrl;
    private TextView webpagePreviewDescription;
    private ProgressBar webpagePreviewProgressBar;
    private LinearLayout webpagePreviewInvalidLayout;
    private TextView webpagePreviewInvalidText;
    // private RequestFuture<OpenGraph> fetch;
    private Request<String> fetch;
    private String url;

    private Snackbar snackbar;
    private Webpage webpage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        // Inflate the layout for this fragment
        Log.e(Tag, "RegisterFragment");
        View view = inflater.inflate(R.layout.register_fragment_layout, container, false);

        webpageAddEdit = view.findViewById(R.id.register_fragment_webpage_add_edit);
        webpageCardView = view.findViewById(R.id.register_fragment_webpage_card_view);
        webpagePreviewImage = view.findViewById(R.id.register_fragment_webpage_preview_image);
        webpagePreviewTitle = view.findViewById(R.id.register_fragment_webpage_preview_title);
        webpagePreviewUrl = view.findViewById(R.id.register_fragment_webpage_preview_url);
        webpagePreviewDescription = view.findViewById(R.id.register_fragment_webpage_preview_description);
        webpagePreviewProgressBar = view.findViewById(R.id.register_fragment_webpage_preview_progress_bar);
        webpagePreviewInvalidLayout = view.findViewById(R.id.register_fragment_webpage_preview_invalid_layout);
        webpagePreviewInvalidText = view.findViewById(R.id.register_fragment_webpage_preview_invalid_text);
        // snackbar =
        // register_fragment_webpage_preview_invalid_text

        webpageCardView.setOnClickListener(v -> {
            if(snackbar != null) {
                snackbar.dismiss();
            }
            if(webpagePreviewProgressBar.getVisibility() == View.VISIBLE) {
                snackbar = Snackbar.make(webpageCardView, "웹페이지 미리보기를 로딩하고 있습니다.", Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                if(webpagePreviewInvalidText.getVisibility() == View.VISIBLE) {
                    // this.getActivity();
                    SimplefeedApplicationDialog.showRegisterWebpageDialog(getActivity(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            registerWebpage();
                        }
                    });
                } else {
                    registerWebpage();
                    // Log.e(Tag, "add webpage");
                    // SimplefeedApplicationDialog.showRegisterWebpageDialog(getActivity());
                }
            }

            Log.e(Tag, "onClick");
        });

        webpageAddEdit.addTextChangedListener(new SimplefeedTextWatcher() {

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                Log.e(Tag, "onTextChanged => " + sequence.toString());
                url = sequence.toString();
                Matcher matcher =  Patterns.WEB_URL.matcher(sequence);
                if(matcher.matches()) {
                    Log.e(Tag, "match");
                    if (fetch != null) {
                        fetch.cancel();
                    }
                    webpagePreviewProgressBar.setVisibility(View.VISIBLE);
                    webpagePreviewInvalidLayout.setVisibility(View.VISIBLE);
                    webpagePreviewInvalidText.setVisibility(View.INVISIBLE);
                    fetch = Webpage.gen(url, (o, exception) -> {
                        Log.e(Tag, SimplefeedApplicationGson.to(o));
                        webpagePreviewProgressBar.setVisibility(View.INVISIBLE);
                        if(o != null) {
                            webpage = o;
                            webpageCardView.setVisibility(View.VISIBLE);
                            webpagePreviewTitle.setText(o.title());
                            webpagePreviewUrl.setText(o.url());
                            webpagePreviewDescription.setText(o.description());
                            Glide.with(RegisterFragment.this)
                                    .load(o.image())
                                    .into(webpagePreviewImage);
                            webpagePreviewInvalidLayout.setVisibility(View.INVISIBLE);
                        } else {
                            webpagePreviewInvalidLayout.setVisibility(View.VISIBLE);
                            webpagePreviewInvalidText.setVisibility(View.VISIBLE);
                        }
                        if(exception != null) {
                            Log.e(Tag, exception.toString());
                        }
                        fetch = null;
                    });
                } else {
                    Log.e(Tag, "not match");
                }
            }
        });

        view.setOnClickListener(v -> {
            Log.e(Tag, "Hello world");
        });

        return view;
    }

    private void registerWebpage() {
        if(webpage != null) {
            // TODO: SAVE MAIN FEED
            webpageCardView.setVisibility(View.INVISIBLE);
            webpageAddEdit.setText("");
            if(snackbar != null) {
                snackbar.dismiss();
            }
            if(getView() != null) {
                snackbar = Snackbar.make(getView(), "새로운 주소가 등록 되었습니다.", Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                Log.e(Tag, "getView() is null");
            }
        } else {
            Log.w(Tag, "webpage is null");
        }
    }
}
