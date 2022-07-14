package com.nascodefy.emosianpkm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.nascodefy.emosianpkm.Adapter.ArticleAdapter;
import com.nascodefy.emosianpkm.Adapter.HistoryAdapter;
import com.nascodefy.emosianpkm.Adapter.UserAdapterContact;
import com.nascodefy.emosianpkm.Model.Article;
import com.nascodefy.emosianpkm.Model.History;
import com.nascodefy.emosianpkm.Model.User;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class HistoryPHQActivity extends AppCompatActivity {

    RecyclerView recyclerViewHistory;

    private HistoryAdapter historyAdapter;
    private List<History> mHistory;
    FirebaseUser fuser;
    Context mContext;
    FirebaseAuth auth;

    DatabaseReference reference;

    ProgressBar progressBar;

    private static final String TAG = "HistoryPHQActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_phq);

        recyclerViewHistory = findViewById(R.id.recycler_view_history);
        recyclerViewHistory.setHasFixedSize(true);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(HistoryPHQActivity.this));
        progressBar = findViewById(R.id.progress_bar_history);


        auth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.VISIBLE);
        readHistory();
        //Log.d(TAG, "ukuran array list: " + mHistory.size() );
    }

    private void readHistory() {
        final FirebaseUser firebaseUser = auth.getCurrentUser();
        final String userid = firebaseUser.getUid();
        mHistory = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Diagnosa").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    History  history = snapshot.getValue(History.class);

                    mHistory.add(history);

                }
                historyAdapter = new HistoryAdapter(HistoryPHQActivity.this, mHistory);
                recyclerViewHistory.setAdapter(historyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        progressBar.setVisibility(View.GONE);
    }
}