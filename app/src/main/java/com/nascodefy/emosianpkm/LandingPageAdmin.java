package com.nascodefy.emosianpkm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nascodefy.emosianpkm.FragmentsAdmin.ArticleFragment;
import com.nascodefy.emosianpkm.FragmentsAdmin.BerandaFragmentAdmin;
import com.nascodefy.emosianpkm.FragmentsAdmin.ChatFragmentAdmin;
import com.nascodefy.emosianpkm.FragmentsAdmin.PenggunaFragmentAdmin;

import de.hdodenhof.circleimageview.CircleImageView;

public class LandingPageAdmin extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;


    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_admin);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new BerandaFragmentAdmin()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.navHome:
                            selectedFragment = new BerandaFragmentAdmin();
                            break;
                        case R.id.navArtikel:
                            selectedFragment = new ArticleFragment();
                            break;
                        case R.id.navChat:
                            selectedFragment = new ChatFragmentAdmin();
                            break;
                        case R.id.navUser:
                            selectedFragment = new PenggunaFragmentAdmin();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
                    return true;
                }
            };
}