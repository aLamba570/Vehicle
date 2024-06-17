package com.example.vehicle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.vehicle.Fragments.AddMechanicFragment;
import com.example.vehicle.Fragments.ShowMechanicFragment;
import com.example.vehicle.Fragments.UserProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        frameLayout = (FrameLayout) findViewById(R.id.FragmentContainerAdmin);
        //Assigining BottomNavigaiton Menu
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationViewAdmin);
        Menu menuNav = bottomNavigationView.getMenu();
        //Setting the default fragment as HomeFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainerAdmin, new AddMechanicFragment()).commit();
        //Calling the bottoNavigationMethod when we click on any menu items
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                    //Assigining Fragment as Null
                    Fragment fragment = null;

                    int id = item.getItemId();

                    if (id==R.id.addMechanicMenu){
                        fragment = new AddMechanicFragment();
                    }
                    else if (id==R.id.allMechanicsMenu){
                        fragment = new ShowMechanicFragment();
                    }
                    else if(id == R.id.profileMenu){
                        fragment = new UserProfileFragment();
                    }

                    //Sets the selected Fragment into the Framelayout
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainerAdmin, fragment).commit();
                    return true;
                }
            };
}