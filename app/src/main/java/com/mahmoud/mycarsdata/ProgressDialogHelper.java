package com.mahmoud.mycarsdata;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;


public class ProgressDialogHelper {

    private static ProgressDialog mProgressDialog;


    public static void showSimpleProgressDialog(Context context, boolean isCancelable) {
        try {

            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.show();
                // mProgressDialog = ProgressDialog.show(context, title, msg);

                mProgressDialog.setContentView(R.layout.progress_dialog_layout);
                mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                mProgressDialog.setCancelable(isCancelable);
                ProgressBar bar = (ProgressBar) mProgressDialog.findViewById(R.id.progress_bar);
                Drawable progressDrawable = bar.getIndeterminateDrawable().mutate();
                progressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
                bar.setProgressDrawable(progressDrawable);
                //  textView = (TextView) mProgressDialog.findViewById(R.id.texts);
                // textView.setText(msg);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
