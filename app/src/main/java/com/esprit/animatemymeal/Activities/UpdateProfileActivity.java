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
import android.support.annotation.NonNull;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.esprit.animatemymeal.Entities.User;
import com.esprit.animatemymeal.R;
import com.esprit.animatemymeal.UploadImage.FireMissilesDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UpdateProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;

    private static final String TAG = "Storage#UpdateProfileActivity";
    private Uri mFileUri = null;
    private ProgressDialog mProgressDialog;
    private DatabaseReference mDatabase;
    User user ;


    @BindView(R.id.save)
    Button _save;
    @BindView(R.id.input_nameP)
    EditText _nameText;
    @BindView(R.id.input_addressP)
    EditText _addressText;
    @BindView(R.id.input_emailP)
    EditText _emailText;
    @BindView(R.id.input_mobileP)
    EditText _mobileText;
    @BindView(R.id.sign_out)
    ImageButton _logoutButton;
    @BindView(R.id.mImageView)
    ImageView imageView;
    @BindView(R.id.editImage)
    ImageButton editImage ;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showProgressDialog(getString(R.string.charging)) ;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        user = new User();


        _save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
          String  name = _nameText.getText().toString();
          String address = _addressText.getText().toString();
          String email = _emailText.getText().toString();
          String mobile = _mobileText.getText().toString();
          String photo =   user.getPhoto() ;
          User user = new User( name, address,  mobile , photo,email) ;
          Map<String, Object> childUpdates = new HashMap<>();
          childUpdates.put( mAuth.getCurrentUser().getUid(),user) ;

        mDatabase.child("users").updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"changes saved",Toast.LENGTH_LONG).show();
            }
        }) ;

    }
});

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                user = dataSnapshot.getValue(User.class);

                    new DownloadImageTask(imageView)
                           .execute(user.getPhoto());

                 _nameText.setText(user.getName());
                 _addressText.setText(user.getAddress());
                 _emailText.setText(user.getEmail());
                 _mobileText.setText(user.getMobile());

//                Log.d("aaa",user.photo) ;
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(userListener);



        _logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();

            }
        });

        editImage.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {

                FireMissilesDialogFragment fireMissilesDialogFragment = new FireMissilesDialogFragment();
                fireMissilesDialogFragment.show(getSupportFragmentManager(),"dialog");
                fireMissilesDialogFragment.setmListener(new FireMissilesDialogFragment.FireMissilesDialogListener() {
                    @Override
                    public void onDialogPositiveClick(DialogFragment dialog) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE) ;
                        if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                            startActivityForResult(intent, 1);
                        }

                    }

                    @Override
                    public void onDialogNegativeClick(DialogFragment dialog) {
                        Intent intent = new Intent(Intent.ACTION_PICK) ;
                        if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                            File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) ;
                            String pictureDirectoryPath = pictureDirectory.getPath() ;
                            Uri data = Uri.parse(pictureDirectoryPath);

                            intent.setDataAndType(data,"image/*") ;
                            startActivityForResult(intent, 2);


                        }
                    }
                });



            }
        });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }
    private void updateUI(FirebaseUser user) {
       // hideProgressDialog();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class) ;
            startActivity(intent);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri file = null;
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            bitmap = (Bitmap) data.getExtras().get("data");
            file = getImageUri(getApplicationContext(),bitmap);
            imageView.setImageBitmap(bitmap);

        }
        if (requestCode == 2 && resultCode == RESULT_OK)
        {
            file = data.getData();
            Uri imageUri = data.getData() ;
            InputStream inputStream ;
            try {
                inputStream =  getApplicationContext().getContentResolver().openInputStream(imageUri) ;
                bitmap   = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap );

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"can't open",Toast.LENGTH_LONG).show();

            }
        }

      //  uploadFromUri(file) ;
        showProgressDialog("charging");
        StorageReference riversRef = mStorageRef.child("images/"+mAuth.getCurrentUser().getUid());

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("photo",downloadUrl.toString()) ;
                        user.setPhoto(downloadUrl.toString());
                        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).updateChildren(childUpdates) ;

                        Toast.makeText(getApplicationContext(),"uploded",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(getApplicationContext(),"not uploded",Toast.LENGTH_LONG).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                hideProgressDialog();
            }
        });

    }

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


    private void showProgressDialog(String caption) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.setMessage(caption);
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
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
}
