package net.novemberizing.simplefeed.objects;

import android.text.Editable;
import android.text.TextWatcher;

import net.novemberizing.core.objects.onTextChanged;

public class SimplefeedTextWatcher implements TextWatcher {
    private onTextChanged callback;
    public SimplefeedTextWatcher(onTextChanged callback) {
        this.callback = callback;
    }
    @Override
    public void beforeTextChanged(CharSequence sequence, int start, int before, int count) {
    }

    @Override
    public void onTextChanged(CharSequence sequence, int start, int before, int count) {
        this.callback.on(sequence, start, before, count);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
