package com.esprit.animatemymeal.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.animatemymeal.Entities.User;
import com.esprit.animatemymeal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends UploadImageActivity {
    private static final String TAG = "SignupActivity";
    Uri file = null;
    Uri downloadUrl = null ;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private final String photo = "https://firebasestorage.googleapis.com/v0/b/animate-my-meal.appspot.com/o/images%2Fsignuppicture.png?alt=media&token=c89abf39-1c97-4572-b4d1-b5785bde03b4" ;
    @BindView(R.id.input_name)
    EditText _nameText;
    @BindView(R.id.input_address)
    EditText _addressText;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_mobile)
    EditText _mobileText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;

    @BindView(R.id.mImageView)
    ImageView imageView;
    @BindView(R.id.editImage)
    ImageButton editImage ;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        editImage.setOnClickListener(ImageListener());
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setImageView(imageView);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {   bitmap = (Bitmap) data.getExtras().get("data");
            file = getImageUri(getApplicationContext(),bitmap);
            imageView.setImageBitmap(bitmap);

        }
        if (requestCode == 2 && resultCode == RESULT_OK)
        {  file = data.getData();
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
       // showProgressDialog("charging");


    }



    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String  name = _nameText.getText().toString();
        final String address = _addressText.getText().toString();
        final String email = _emailText.getText().toString();
        final String mobile = _mobileText.getText().toString();
        final String password = _passwordText.getText().toString();
              String reEnterPassword = _reEnterPasswordText.getText().toString();



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.hide();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            if (file!= null) {

                                StorageReference riversRef = getmStorageRef().child("images/" + mAuth.getCurrentUser().getUid());

                                riversRef.putFile(file)

                                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    User users ;
                                                    if ( downloadUrl == null)
                                                    {  users = new User(name, address, mobile,photo, email);}
                                                    else
                                                    {  users = new User(name, address, mobile, downloadUrl.toString(), email);}


                                                    mDatabase.child("users").child(user.getUid()).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            hideProgressDialog();

                                                            if (task.isSuccessful()) {
                                                                updateUI(mAuth.getCurrentUser());
                                                                Toast.makeText(getApplicationContext(), "account created ", Toast.LENGTH_LONG).show();
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "account creation failed ", Toast.LENGTH_LONG).show();
                                                            }

                                                        }
                                                    });


                                                } else {
                                                    Toast.makeText(getApplicationContext(), "couldn't upload image  ", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        })
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                                // Get a URL to the uploaded content
                                                downloadUrl = taskSnapshot.getDownloadUrl();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                // Handle unsuccessful uploads
                                                // ...
                                                Toast.makeText(getApplicationContext(), "not uploded", Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }else
                            { FirebaseUser user = mAuth.getCurrentUser();

                                User users = new User(name, address, mobile, photo, email);
                                mDatabase.child("users").child(user.getUid()).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        hideProgressDialog();

                                        if (task.isSuccessful()) {
                                            updateUI(mAuth.getCurrentUser());
                                            Toast.makeText(getApplicationContext(), "account created ", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "account creation failed ", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });}
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });




    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getApplicationContext(), "sign up failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=8) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 ) {
            _passwordText.setError("password has to be more than 4 characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

         return valid;

    }


    private void updateUI(FirebaseUser user) {
        // hideProgressDialog();
        if (user != null) {
            Intent intent = new Intent(getApplicationContext(),UpdateProfileActivity.class) ;
            startActivity(intent);

        }
    }
}