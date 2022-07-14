package com.nascodefy.emosianpkm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultPHQActivity extends AppCompatActivity {

    TextView resultPHQ, tingkatPHQ, anjuranPHQ, dateToday;
    String res;
    Button konsul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_phq);

        konsul = (Button) findViewById(R.id.konsul);
        resultPHQ = (TextView) findViewById(R.id.is_result_phq);
        tingkatPHQ = (TextView) findViewById(R.id.is_tingkat_phq);
        anjuranPHQ = (TextView) findViewById(R.id.is_anjuran);
        dateToday = (TextView) findViewById(R.id.date_today);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String hasil = extras.getString("hasilPHQ");
            String tingkat = extras.getString("tingkatPHQ");
            String anjuran = extras.getString("anjuranPHQ");
            String today_date = extras.getString("dateToday");

            resultPHQ.setText(hasil);
            tingkatPHQ.setText(tingkat);
            anjuranPHQ.setText(anjuran);
            dateToday.setText(today_date);
            //The key argument here must match that used in the other activity
        }

        konsul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultPHQActivity.this, MessageActivity.class);
                intent.putExtra("userid", "8PZSdw7y0fZmzEjCwL6K6ORuv4s2");
                startActivity(intent);
            }
        });




    }
}