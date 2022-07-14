package com.nascodefy.emosianpkm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class TellMeActivity extends AppCompatActivity {

    Spinner spinnerFeel;
    TextView descFeel;
    Button btn_submit_feel;

    String dateToday;
    Calendar calender;
    SimpleDateFormat simpleDateFormat;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String userid;

    DatabaseReference reference;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_me);


        calender = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("EEEEEE, MM.dd.yyyy" , Locale.getDefault());
        dateToday = simpleDateFormat.format(calender.getTime());

        auth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference("Journal");

        spinnerFeel = findViewById(R.id.spinner_feel);
        ArrayAdapter<CharSequence> feelItem = ArrayAdapter.createFromResource(this, R.array.feel,
                R.layout.support_simple_spinner_dropdown_item);
        spinnerFeel.setAdapter(feelItem);

        descFeel = findViewById(R.id.feel_desc);
        btn_submit_feel = findViewById(R.id.btn_submit_feel);

        btn_submit_feel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBar.setTitle("Proses Pengiriman..");
                loadingBar.setMessage("Mohon Tunggu Beberapa Saat");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();

                final String txt_descfeel = descFeel.getText().toString();
                final String txt_feetTitle = spinnerFeel.getSelectedItem().toString();

                if (TextUtils.isEmpty(txt_descfeel) || TextUtils.isEmpty(txt_feetTitle)) {
                    Toast.makeText(TellMeActivity.this, "Harap Semua Kolom Di Isi", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                } else {
                    addJournal(txt_descfeel, txt_feetTitle);
                }
            }
        });

    }

    private void addJournal(String txt_descfeel, String txt_feetTitle) {

        FirebaseUser firebaseUser = auth.getCurrentUser();
        assert firebaseUser != null;
        String userid = firebaseUser.getUid();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = format.format(new Date());
        reference = FirebaseDatabase.getInstance().getReference("Journal").child(userid).child(date);



        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", userid);
        hashMap.put("titleFeel", txt_feetTitle);
        hashMap.put("descFeel", txt_descfeel);
        hashMap.put("dateFeel", dateToday);

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    loadingBar.dismiss();
                    Toast.makeText(TellMeActivity.this, "Jurnal Terkirim", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}