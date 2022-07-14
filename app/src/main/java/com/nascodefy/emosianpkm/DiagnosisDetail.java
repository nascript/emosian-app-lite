package com.nascodefy.emosianpkm;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DiagnosisDetail extends AppCompatActivity {
    TextView resultPHQ, tingkatPHQ, anjuranPHQ, dateToday,userName;
    String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosis_detail);


        resultPHQ = (TextView) findViewById(R.id.is_result_phq);
        tingkatPHQ = (TextView) findViewById(R.id.is_tingkat_phq);
        anjuranPHQ = (TextView) findViewById(R.id.is_anjuran);
        dateToday = (TextView) findViewById(R.id.date_today);
        userName = (TextView) findViewById(R.id.username);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String hasil = extras.getString("hasilPHQ");
            String tingkat = extras.getString("tingkatPHQ");
            String anjuran = extras.getString("anjuranPHQ");
            String today_date = extras.getString("dateToday");
            String username = extras.getString("username");

            resultPHQ.setText(hasil);
            tingkatPHQ.setText(tingkat);
            anjuranPHQ.setText(anjuran);
            dateToday.setText(today_date);
            userName.setText(username);
            //The key argument here must match that used in the other activity
        }




    }

}
