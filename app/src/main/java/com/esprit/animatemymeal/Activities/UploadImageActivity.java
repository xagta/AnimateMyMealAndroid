package com.esprit.animatemymeal.Activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.esprit.animatemymeal.Entities.User;
import com.esprit.animatemymeal.UploadImage.FireMissilesDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;


public class UploadImageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;

    private static final String TAG = "Storage#UpdateProfileActivity";
    private Uri mFileUri = null;
    private ProgressDialog mProgressDialog;
    private DatabaseReference mDatabase;
    User user ;



    ImageView imageView;

    ImageButton editImage ;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


      //  showProgressDialog(getString(R.string.charging)) ;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        user = new User();




    }

    public View.OnClickListener ImageListener ()
    {  return new View.OnClickListener()  {
        @Override
        public void onClick (View v) {

            FireMissilesDialogFragment fireMissilesDialogFragment = new FireMissilesDialogFragment();
            fireMissilesDialogFragment.show(getSupportFragmentManager(),"dialog");
            fireMissilesDialogFragment.setmListener(new FireMissilesDialogFragment.FireMissilesDialogListener() {
                @Override
                public void onDialogPositiveClick(DialogFragment dialog) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE) ;
                    if (intent.resolveActivity(getBaseContext().getPackageManager()) != null) {
                        startActivityForResult(intent, 1);
                    }

                }

                @Override
                public void onDialogNegativeClick(DialogFragment dialog) {
                    Intent intent = new Intent(Intent.ACTION_PICK) ;
                    if (intent.resolveActivity(getBaseContext().getPackageManager()) != null) {
                        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) ;
                        String pictureDirectoryPath = pictureDirectory.getPath() ;
                        Uri data = Uri.parse(pictureDirectoryPath);

                        intent.setDataAndType(data,"image/*") ;
                        startActivityForResult(intent, 2);


                    }
                }
            });



        }
    } ;}




    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            hideProgressDialog();
        }
    }


   public void showProgressDialog(String caption) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public StorageReference getmStorageRef() {
        return mStorageRef;
    }

    public void setmStorageRef(StorageReference mStorageRef) {
        this.mStorageRef = mStorageRef;
    }

    public DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public void setmDatabase(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageButton getEditImage() {
        return editImage;
    }

    public void setEditImage(ImageButton editImage) {
        this.editImage = editImage;
    }
}
