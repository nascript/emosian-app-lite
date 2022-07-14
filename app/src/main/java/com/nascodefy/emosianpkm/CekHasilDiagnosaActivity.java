package com.nascodefy.emosianpkm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nascodefy.emosianpkm.Adapter.CekHasilDiagnosaAdapter;
import com.nascodefy.emosianpkm.Adapter.HistoryAdapter;
import com.nascodefy.emosianpkm.Model.History;
import com.nascodefy.emosianpkm.Model.User;

import java.util.ArrayList;
import java.util.List;

public class CekHasilDiagnosaActivity extends AppCompatActivity {

    RecyclerView recyclerViewCekHasil;

    private CekHasilDiagnosaAdapter cekHasilDiagnosaAdapter;
    private List<History> mHistory;
    FirebaseUser fuser;
    Context mContext;
    FirebaseAuth auth;

    DatabaseReference reference;

    ProgressBar progressBar;

    private static final String TAG = "CekHasilDiagnosaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_hasil_diagnosa);

        recyclerViewCekHasil = findViewById(R.id.recycler_view_cek_hasil_diagnosa);
        recyclerViewCekHasil.setHasFixedSize(true);
        recyclerViewCekHasil.setLayoutManager(new LinearLayoutManager(CekHasilDiagnosaActivity.this));
        progressBar = findViewById(R.id.progress_bar_history);


        auth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.VISIBLE);

        readCekHasil();
    }

    private void readCekHasil() {
        mHistory = new ArrayList<>();
        final String firebaseUser = auth.getUid();
        //final String userid = firebaseUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Diagnosa");

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {



                    for ( DataSnapshot dateSnapshot : snapshot.getChildren() ){
                        History history = dateSnapshot.getValue(History.class);
                        mHistory.add(history);
                        //Log.d(TAG, "Ambil Data: " + mHistory.add(history));
                    }

                }
                cekHasilDiagnosaAdapter = new CekHasilDiagnosaAdapter(CekHasilDiagnosaActivity.this, mHistory);
                recyclerViewCekHasil.setAdapter(cekHasilDiagnosaAdapter);

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}