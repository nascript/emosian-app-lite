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
import com.nascodefy.emosianpkm.FragmentsSiswa.BerandaFragment;
import com.nascodefy.emosianpkm.FragmentsSiswa.ChatFragment;
import com.nascodefy.emosianpkm.FragmentsSiswa.PenggunaFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class LandingPageSiswa extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;


    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_siswa);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");
//
//        profile_image = findViewById(R.id.profile_image);
//        username = findViewById(R.id.username);

//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_landing_page_siswa);

        //hilangin actionBar
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
//            Window window = getWindow();
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }

        /*load bottom navbar*/
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListenerSiswa);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new BerandaFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListenerSiswa =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;


                    switch (menuItem.getItemId()){
                        case R.id.navHome:
                            selectedFragment = new BerandaFragment();
                            break;
                        case R.id.navArtikel:
                            selectedFragment = new ArticleFragment();
                            break;
                        case R.id.navChat:
                            selectedFragment = new ChatFragment();
                            break;
                        case R.id.navUser:
                            selectedFragment = new PenggunaFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
                    return true;
                }
            };
}