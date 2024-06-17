package com.example.vehicle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.vehicle.Fragments.HomeFragment;
import com.example.vehicle.Fragments.UserProfileFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        frameLayout = (FrameLayout) findViewById(R.id.FragmentContainer);
        //Assigining Bottomnavigaiton Menu
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationView);
        Menu menuNav = bottomNavigationView.getMenu();
        //Setting the default fragment as HomeFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new HomeFragment()).commit();
        //Calling the bottomNavigationMethod when we click on any menu item
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);



    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if the user is already signed in with Firebase Authentication
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null) {
            // If the user is not signed in, redirect to LoginActivity
            Intent intent = new Intent(MainActivity.this, StartingActivity.class);
            startActivity(intent);
        } else {
            // Get the Google sign-in account
            GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            if (googleSignInAccount != null) {
                String id = googleSignInAccount.getId();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(id).child("role");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String data = snapshot.getValue().toString();

                        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();

                        // Navigates to admin Activity
                        if (data.equals("admin")) {
                            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        // Navigates to user Role activity if the role in the database is not equal to "Admin" or "user"
                        else if (!data.equals("user")) {
                            Intent intent = new Intent(getApplicationContext(), RoleActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Do nothing
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error if necessary
                    }
                });
            } else {
                // Handle the case where Google Sign-In account is null
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                    //Assigining Fragment as Null
                    Fragment fragment = null;
                    int itemId = item.getItemId();

                    if (itemId == R.id.homeMenu) {
                        fragment = new HomeFragment();
                    } else if (itemId == R.id.profileMenu) {
                        fragment = new UserProfileFragment();
                    }

                    //Sets the selected Fragment into the Framelayout
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, fragment).commit();
                    return true;
                }
            };
}