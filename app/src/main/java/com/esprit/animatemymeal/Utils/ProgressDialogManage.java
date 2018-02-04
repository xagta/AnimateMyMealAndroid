package com.esprit.animatemymeal.Utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Yahya on 23/01/2018.
 */

public class ProgressDialogManage {
    ProgressDialog mProgressDialog ;
    public void showProgressDialog(String caption, Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.setMessage(caption);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
