package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

/**
 * This is a Class that I create Loading Dialog to inform to user to wait for current location
 */
class LoadingDialog {
    Activity activity;
    AlertDialog dialog;
    LoadingDialog(Activity myActivity){
        activity = myActivity;

    }
    /**
     * This is a method that when we call this the activity LoadingDialog start in order to appear it on UI
     */
    void startLoadingDialog(){
        /**
         * Here i set specific style for LoadingDialog
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.CustomDialog);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog,null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }
    void dismissDialog(){
        dialog.dismiss();
    }
}
