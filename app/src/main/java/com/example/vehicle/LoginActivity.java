package com.example.vehicle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    GoogleSignInClient mSignInClient;
    FirebaseAuth firebaseAuth;
    SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        signInButton = findViewById(R.id.GoogleSignBtn);

        // Google signin options to use Gmail login
        GoogleSignInOptions signInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("529000103980-evjm12tkr7m0cqtnljdj8v0t3nq9nech.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mSignInClient = GoogleSignIn.getClient(getApplicationContext(), signInOptions);

        // Implementing onclick listener to access all available Gmail accounts
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = mSignInClient.getSignInIntent();
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> googleSignInAccountTask = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            if (googleSignInAccountTask.isSuccessful()) {
                try {
                    GoogleSignInAccount googleSignInAccount = googleSignInAccountTask.getResult(ApiException.class);

                    if (googleSignInAccount != null) {
                        AuthCredential authCredential = GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken(), null);

                        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://music-d664b-default-rtdb.asia-southeast1.firebasedatabase.app/");
                                    DatabaseReference myRef = database.getReference().child("users");

                                    // Hashmap to store the user details and setting it to Firebase
                                    HashMap<String, Object> user_details = new HashMap<>();

                                    // Accessing the user details from Gmail
                                    String id = googleSignInAccount.getId().toString();
                                    String name = googleSignInAccount.getDisplayName().toString();
                                    String mail = googleSignInAccount.getEmail().toString();
                                    String pic = googleSignInAccount.getPhotoUrl().toString();

                                    // Adding user details to HashMap
                                    user_details.put("id", id);
                                    user_details.put("name", name);
                                    user_details.put("mail", mail);
                                    user_details.put("profilepic", pic);
                                    user_details.put("role", "empty");

                                    // Updating the user details in Firebase
                                    myRef.child(id).updateChildren(user_details).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                                // Navigating to the main activity after user successfully registers
                                                Intent intent = new Intent(LoginActivity.this, RoleActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }

                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
