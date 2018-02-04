package com.esprit.animatemymeal.UploadImage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class FireMissilesDialogFragment extends DialogFragment {
   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
       builder.setMessage("choose ")
               .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Send the positive button event back to the host activity
                       mListener.onDialogPositiveClick(FireMissilesDialogFragment.this);
                   }
               })
               .setNegativeButton("Galerie" , new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Send the negative button event back to the host activity
                       mListener.onDialogNegativeClick(FireMissilesDialogFragment.this);
                   }
               });
       return builder.create();
   }
    public interface FireMissilesDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    FireMissilesDialogListener mListener;
   public void setmListener(FireMissilesDialogListener mListener)
   {
         this.mListener =   mListener ;
   }



}
