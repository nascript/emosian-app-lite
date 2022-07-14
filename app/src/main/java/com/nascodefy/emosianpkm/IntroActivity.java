package com.nascodefy.emosianpkm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nascodefy.emosianpkm.Model.User;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPageAdapter introViewPageAdapter;
    TabLayout tabIndicator;
    Button btnGetStarted;
    Button btnNext;
    Animation btnAnim ;
    TextView tvSkip;
    int position = 0 ;



    @Override
    protected void onStart() {
        super.onStart();




    }
        //check if user is null
//        if (firebaseUser != null){
//            Intent intent = new Intent(StartActivity.this, LandingPageSiswa.class);
//            startActivity(intent);
//            finish();
//        }
  //  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (restorePrefData()) {
            Intent regis = new Intent(getApplicationContext(), com.nascodefy.emosianpkm.RegisterActivity.class );
            startActivity(regis);
            finish();
        }





        setContentView(R.layout.activity_intro);



        //hilangin actionBar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        }
        tabIndicator = (TabLayout) findViewById(R.id.tab_indicator);

        // mengisi list screen pada viepager
        final List<ScreenItems> mList = new ArrayList<>();

        mList.add(new ScreenItems("Merasa Stress ?","Jangan Pendam Masalahmu" , R.drawable.ic_vecstress));
        mList.add(new ScreenItems("Cemas & Banyak Pikiran ?","Konsultasikan Masalahmu Langsung \n" + "Pada Ahlinya" , R.drawable.ic_vecsadness));
        mList.add(new ScreenItems("Bagi Ceritamu Disini","Lalu Temukan Solusi Masalahmu \n" + "di Sini", R.drawable.ic_result));


        // set viewpager
        screenPager = findViewById(R.id.vpager);
        introViewPageAdapter = new IntroViewPageAdapter(this, mList);
        screenPager.setAdapter(introViewPageAdapter);

        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);

              //set TabLayout
        tabIndicator.setupWithViewPager(screenPager);

        btnNext = (Button) findViewById(R.id.btn_next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if (position == mList.size()-1) {

                    // TODO : show the GETSTARTED Button and hide the indicator and the next button

                    loaddLastScreen();
                }

            }
        });

        btnGetStarted = (Button) findViewById(R.id.btn_mulai);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open main activity
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                //savePrefsData();
                finish();
            }
        });




    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;
    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();


    }

    private void loaddLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        //tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
        btnGetStarted.setAnimation(btnAnim);



    }



}