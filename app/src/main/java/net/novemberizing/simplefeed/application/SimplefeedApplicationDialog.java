package net.novemberizing.simplefeed.application;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import net.novemberizing.core.objects.Callback;
import net.novemberizing.simplefeed.R;
import net.novemberizing.simplefeed.data.Webpage;

public class SimplefeedApplicationDialog {
    private static final String Tag = "SimplefeedApplicationDialog";
    public static AlertDialog createRegisterWebpageDialog(Activity activity, DialogInterface.OnClickListener listener) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity, R.style.Theme_Orientalism_Dialog);

        builder.setTitle("바로 직전에 성공한 주소를 등록 하겠습니까?");
        builder.setPositiveButton("추가", listener);

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.e(Tag, "onNegativeButton click");
            }
        });

        return builder.create();
    }
    public static void showRegisterWebpageDialog(Activity activity, DialogInterface.OnClickListener listener) {
        AlertDialog dialog = createRegisterWebpageDialog(activity, listener);

        dialog.show();
    }
}
