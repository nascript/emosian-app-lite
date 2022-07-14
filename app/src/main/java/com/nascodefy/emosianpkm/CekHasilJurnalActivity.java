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
import com.nascodefy.emosianpkm.Adapter.CekHasilJournalAdapter;
import com.nascodefy.emosianpkm.Model.History;
import com.nascodefy.emosianpkm.Model.Journal;

import java.util.ArrayList;
import java.util.List;


public class CekHasilJurnalActivity extends AppCompatActivity {

    RecyclerView recyclerViewCekJournal;

    private CekHasilJournalAdapter cekHasilJournalAdapter;
    private List<Journal> mJournal;
    FirebaseUser fuser;
    Context mContext;
    FirebaseAuth auth;

    DatabaseReference reference;

    ProgressBar progressBar;

    private static final String TAG = "CekJurnalDiagnosaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_hasil_jurnal);

        recyclerViewCekJournal = findViewById(R.id.recycler_view_cek_hasil_jurnal);
        recyclerViewCekJournal.setHasFixedSize(true);
        recyclerViewCekJournal.setLayoutManager(new LinearLayoutManager(CekHasilJurnalActivity.this));
        progressBar = findViewById(R.id.progress_bar_jurnal);


        auth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.VISIBLE);

        readCekHasil();
    }

    private void readCekHasil() {
        mJournal = new ArrayList<>();
        final String firebaseUser = auth.getUid();
        //final String userid = firebaseUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Journal");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    for ( DataSnapshot dateSnapshot : snapshot.getChildren() ){
                        Journal journal = dateSnapshot.getValue(Journal.class);
                        mJournal.add(journal);
                    }

                }
                cekHasilJournalAdapter = new CekHasilJournalAdapter(CekHasilJurnalActivity.this, mJournal);
                recyclerViewCekJournal.setAdapter(cekHasilJournalAdapter);

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}