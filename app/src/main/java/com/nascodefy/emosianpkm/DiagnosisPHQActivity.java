package com.nascodefy.emosianpkm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DiagnosisPHQActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private String userName;
    AdapterPHQ adapterPHQ;
    Button btnNext;
    Button btnGetStarted;
    int position = 0;
    Animation btnAnim;

    FirebaseAuth auth;
    DatabaseReference reference;

    String dateToday;
    Calendar calender;
    SimpleDateFormat simpleDateFormat;

    public int scoreChoice = 0;
    RadioGroup question1, question2, question3,question4,question5,question6,question7,question8,question9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_phq);

        Bundle extras = getIntent().getExtras();
        calender = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("EEEEEE, MM.dd.yyyy" , Locale.getDefault());

        userName = extras.getString("username");



        question1 =  (RadioGroup) findViewById(R.id.pertanyaan_1);

        question2 =  (RadioGroup) findViewById(R.id.pertanyaan_2);
        question3 =  (RadioGroup) findViewById(R.id.pertanyaan_3);
        question4 =  (RadioGroup) findViewById(R.id.pertanyaan_4);
        question5 =  (RadioGroup) findViewById(R.id.pertanyaan_5);
        question6 =  (RadioGroup) findViewById(R.id.pertanyaan_6);
        question7 =  (RadioGroup) findViewById(R.id.pertanyaan_7);
        question8 =  (RadioGroup) findViewById(R.id.pertanyaan_8);
        question9 =  (RadioGroup) findViewById(R.id.pertanyaan_9);



        final List<ScreenItemPHQ> mList = new ArrayList<>();
        mList.add(new ScreenItemPHQ("Dalam 14 Hari terakhir, Apakah Anda Kurang Tertarik Atau Bergairah dalam melakukan Apapun ?"));
        mList.add(new ScreenItemPHQ("Dalam 14 Hari terakhir, Apakah Anda Merasa Murung, Muram Atau Putus Asa ?"));
        mList.add(new ScreenItemPHQ("Dalam 14 Hari terakhir, Apakah Anda Sulit Tidur atau Mudah Terbangun atau Selalu banyak tidur ?"));
        mList.add(new ScreenItemPHQ("Dalam 14 Hari terakhir, Apakah Anda Merasa Lelah Atau Kurang Bertenaga ?"));
        mList.add(new ScreenItemPHQ("Dalam 14 Hari terakhir, Apakah Anda Kurang nafsu makan Atau terlalu banyak makan ?"));
        mList.add(new ScreenItemPHQ("Dalam 14 Hari terakhir, Apakah Anda Kurang percaya diri atau merasa bahwa anda adalah orang yang gagal atau telah " + "mengecewakan diri sendiri atau keluarga ?"));
        mList.add(new ScreenItemPHQ("Dalam 14 Hari terakhir, Apakah Anda Sulit berkonsentrasi pada sesuatu misalnya membaca koran atau menonton televisi ?"));
        mList.add(new ScreenItemPHQ("Dalam 14 Hari terakhir, Apakah Anda Bergerak atau berbicara sangat lambat sehingga orang lain memperhatikannya. atau sebaliknya merasa resah " + "\n" + "atau gelisah sehingga anda sering bergerak dari biasannya ?"));
        mList.add(new ScreenItemPHQ("Dalam 14 Hari terakhir, Apakah Anda merasa lebih baik mati atau ingin melukai diri sendiri dengan cara apapun ?"));

        screenPager = findViewById(R.id.vpager);
        adapterPHQ = new AdapterPHQ(this, mList);
        screenPager.setAdapter(adapterPHQ);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);

        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = (Button) findViewById(R.id.btn_mulai);

        auth = FirebaseAuth.getInstance();



        //question1.setVisibility(View.VISIBLE);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int questionItem = 0;


                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    //boolean checked = ((RadioButton) view).isChecked();

                    if (position == 1) {

                        question1.setVisibility(View.GONE);
                        question2.setVisibility(View.VISIBLE);
                        question2.setAnimation(btnAnim);

                    } else if (position == 2) {
                        question2.setVisibility(View.GONE);
                        question3.setVisibility(View.VISIBLE);
                        question3.setAnimation(btnAnim);
                    } else if (position == 3) {
                        question3.setVisibility(View.GONE);
                        question4.setAnimation(btnAnim);
                        question4.setVisibility(View.VISIBLE);

                    } else if (position == 4) {
                        question4.setVisibility(View.GONE);
                        question5.setVisibility(View.VISIBLE);
                        question5.setAnimation(btnAnim);
                    } else if (position == 5) {
                        question5.setVisibility(View.GONE);
                        question6.setVisibility(View.VISIBLE);
                        question6.setAnimation(btnAnim);
                    } else if (position == 6) {
                        question6.setVisibility(View.GONE);
                        question7.setVisibility(View.VISIBLE);
                        question7.setAnimation(btnAnim);
                    } else if (position == 7) {
                        question7.setVisibility(View.GONE);
                        question8.setVisibility(View.VISIBLE);
                        question8.setAnimation(btnAnim);
                    } else if (position == 8) {
                        question8.setVisibility(View.GONE);
                        question9.setVisibility(View.VISIBLE);
                        question9.setAnimation(btnAnim);
                    } else if (position == 9) {
                        question9.setVisibility(View.GONE);
                        question9.setAnimation(btnAnim);
                    }


                        screenPager.setCurrentItem(position);
                        //Toast.makeText(DiagnosisPHQActivity.this, "Harap di Isi", Toast.LENGTH_SHORT).show();




                }




                if (position == mList.size()-1) {

                    // TODO : show the GETSTARTED Button and hide the indicator and the next button

                    loaddLastScreen();
                }

//                if (position == mList.size()-2) {
//                    loadQuestionSeven();
//                }
            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String operScore = Integer.toString(scoreChoice);
                String tingkat_depresi = "";
                String anjuran = "";

                if (scoreChoice >=1 && scoreChoice <= 4) {
                    tingkat_depresi = "Depresi Minimal";
                    anjuran = "Pasien tidak memerlukan terapi depresi";
                } else if (scoreChoice >= 5 && scoreChoice <= 9 ) {
                    tingkat_depresi = "Depresi Ringan";
                    anjuran = "Terapi berdasarkan penilaian klinis gejala yang dialami pasien";
                } else if (scoreChoice >= 10 && scoreChoice <= 14 ) {
                    tingkat_depresi = "Depresi Sedang";
                    anjuran = "Terapi berdasarkan penilaian klinis gejala yang dialami pasien";
                } else if (scoreChoice >= 15 && scoreChoice <= 19) {
                    tingkat_depresi = "Depresi Berat";
                    anjuran = "Terapi mengguakan anti depresan, psikoterapi atau kombinasi";
                } else if (scoreChoice >= 20 && scoreChoice <= 27) {
                    tingkat_depresi = "Depresi Berat";
                    anjuran = "Terapi mengunakan dengan atau tanpa psikoterapi";
                } else if (scoreChoice <= 0) {
                    tingkat_depresi = "Anda Tidak Mengalami Depresi";
                    anjuran = "Tetap Jaga Kesehatan";
                } else if (scoreChoice >= 27) {
                    tingkat_depresi = "Depresi Berat";
                    anjuran = "Terapi mengunakan dengan atau tanpa psikoterapi";
                }


                FirebaseUser firebaseUser = auth.getCurrentUser();
                assert firebaseUser != null;
                String userid = firebaseUser.getUid();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String date = format.format(new Date());
                reference = FirebaseDatabase.getInstance().getReference("Diagnosa").child(userid).child(date);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("username",userName);
                hashMap.put("uid",userid);
                hashMap.put("dateDiag", date);
                hashMap.put("skorDiag", operScore);
                hashMap.put("tingkatDiag", tingkat_depresi);
                hashMap.put("anjuranDiag", anjuran);


                reference.setValue(hashMap);

                Intent toResult = new Intent(getApplicationContext(), ResultPHQActivity.class);
                toResult.putExtra("hasilPHQ", operScore);
                toResult.putExtra("tingkatPHQ", tingkat_depresi);
                toResult.putExtra("anjuranPHQ", anjuran);
                toResult.putExtra("dateToday", date);
                startActivity(toResult);

                Toast.makeText(DiagnosisPHQActivity.this, "Ini Hanya Diagnosa Awal Tidak Perlu Panik atau semacamnya", Toast.LENGTH_LONG).show();

                finish();











            }
        });
    }



    private void loaddLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);

        //question9.setVisibility(View.VISIBLE);
        //question1.setVisibility(View.GONE);
        //tvSkip.setVisibility(View.INVISIBLE);

        // TODO : ADD an animation the getstarted button
        // setup animation




    }

    public void pertanyaan_1(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.tidak_pernah_1:
                if (checked) {
                    scoreChoice = scoreChoice + 0;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                    screenPager.setCurrentItem(position);
                }
                break;

            case R.id.beberapa_hari_1:
                if (checked) {
                    scoreChoice = scoreChoice + 1;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.separuh_waktu_1:
                if (checked) {
                    scoreChoice = scoreChoice + 2;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.hampir_setiap_hari_1:
                if (checked) {
                    scoreChoice = scoreChoice + 3;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
        }
    }

    public void pertanyaan_2(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.tidak_pernah_2:
                if (checked) {
                    scoreChoice = scoreChoice + 0;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;

            case R.id.beberapa_hari_2:
                if (checked) {
                    scoreChoice = scoreChoice + 1;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.separuh_waktu_2:
                if (checked) {
                    scoreChoice = scoreChoice + 2;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.hampir_setiap_hari_2:
                if (checked) {
                    scoreChoice = scoreChoice + 3;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
        }
    }

    public void pertanyaan_3(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.tidak_pernah_3:
                if (checked) {
                    scoreChoice = scoreChoice + 0;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();

                }
                break;

            case R.id.beberapa_hari_3:
                if (checked) {
                    scoreChoice = scoreChoice + 1;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.separuh_waktu_3:
                if (checked) {
                    scoreChoice = scoreChoice + 2;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.hampir_setiap_hari_3:
                if (checked) {
                    scoreChoice = scoreChoice + 3;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
        }
    }

    public void pertanyaan_4(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.tidak_pernah_4:
                if (checked) {
                    scoreChoice = scoreChoice + 0;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;

            case R.id.beberapa_hari_4:
                if (checked) {
                    scoreChoice = scoreChoice + 1;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.separuh_waktu_4:
                if (checked) {
                    scoreChoice = scoreChoice + 2;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.hampir_setiap_hari_4:
                if (checked) {
                    scoreChoice = scoreChoice + 3;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
        }
    }

    public void pertanyaan_5(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.tidak_pernah_5:
                if (checked) {
                    scoreChoice = scoreChoice + 0;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;

            case R.id.beberapa_hari_5:
                if (checked) {
                    scoreChoice = scoreChoice + 1;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.separuh_waktu_5:
                if (checked) {
                    scoreChoice = scoreChoice + 2;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.hampir_setiap_hari_5:
                if (checked) {
                    scoreChoice = scoreChoice + 3;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
        }
    }

    public void pertanyaan_6(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.tidak_pernah_6:
                if (checked) {
                    scoreChoice = scoreChoice + 0;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;

            case R.id.beberapa_hari_6:
                if (checked) {
                    scoreChoice = scoreChoice + 1;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.separuh_waktu_6:
                if (checked) {
                    scoreChoice = scoreChoice + 2;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.hampir_setiap_hari_6:
                if (checked) {
                    scoreChoice = scoreChoice + 3;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
        }
    }

    public void pertanyaan_7(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.tidak_pernah_7:
                if (checked) {
                    scoreChoice = scoreChoice + 0;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;

            case R.id.beberapa_hari_7:
                if (checked) {
                    scoreChoice = scoreChoice + 1;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.separuh_waktu_7:
                if (checked) {
                    scoreChoice = scoreChoice + 2;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.hampir_setiap_hari_7:
                if (checked) {
                    scoreChoice = scoreChoice + 3;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
        }
    }

    public void pertanyaan_8(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.tidak_pernah_8:
                if (checked) {
                    scoreChoice = scoreChoice + 0;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;

            case R.id.beberapa_hari_8:
                if (checked) {
                    scoreChoice = scoreChoice + 1;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.separuh_waktu_8:
                if (checked) {
                    scoreChoice = scoreChoice + 2;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
            case R.id.hampir_setiap_hari_8:
                if (checked) {
                    scoreChoice = scoreChoice + 3;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                }
                break;
        }
    }

    public void pertanyaan_9(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.tidak_pernah_9:
                if (checked) {
                    scoreChoice = scoreChoice + 0;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                    btnGetStarted.setVisibility(View.VISIBLE);
                    btnGetStarted.setAnimation(btnAnim);
                }
                break;

            case R.id.beberapa_hari_9:
                if (checked) {
                    scoreChoice = scoreChoice + 1;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                    btnGetStarted.setVisibility(View.VISIBLE);
                    btnGetStarted.setAnimation(btnAnim);
                }
                break;
            case R.id.separuh_waktu_9:
                if (checked) {
                    scoreChoice = scoreChoice + 2;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                    btnGetStarted.setVisibility(View.VISIBLE);
                    btnGetStarted.setAnimation(btnAnim);
                }
                break;
            case R.id.hampir_setiap_hari_9:
                if (checked) {
                    scoreChoice = scoreChoice + 3;
//                    Toast toast = Toast.makeText(DiagnosisPHQActivity.this, "Hasil" + scoreChoice, Toast.LENGTH_SHORT);
//                    toast.show();
                    btnGetStarted.setVisibility(View.VISIBLE);
                    btnGetStarted.setAnimation(btnAnim);
                }
                break;
        }
    }
}