package com.nascodefy.emosianpkm.FragmentsAdmin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nascodefy.emosianpkm.Adapter.ArticleAdapter;
import com.nascodefy.emosianpkm.Adapter.UserAdapterContact;
import com.nascodefy.emosianpkm.Model.Article;
import com.nascodefy.emosianpkm.Model.Chatlist;
import com.nascodefy.emosianpkm.Model.User;
import com.nascodefy.emosianpkm.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class ArticleFragment extends Fragment {

    EditText searchArticle;
    ProgressBar progressBar;

    private List<Article> mArticle;

    RecyclerView recyclerViewReadArticle;

    ArticleAdapter articleAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container,false);

//        imageViewfbArticle = view.findViewById(R.id.fb_image_article);
//        fbTitleArticle = view.findViewById(R.id.fb_title_article);
        //reference = FirebaseDatabase.getInstance().getReference("Artikel");

        recyclerViewReadArticle = view.findViewById(R.id.recycler_view_article);
        recyclerViewReadArticle.setHasFixedSize(true);
        recyclerViewReadArticle.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = view.findViewById(R.id.progress_bar_article);


        progressBar.setVisibility(View.VISIBLE);

        readArticle();


        searchArticle = view.findViewById(R.id.search_article);

        searchArticle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchArticleFb(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        return view;
    }

    private void searchArticleFb(String s) {
        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("Artikel").orderByChild("search")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mArticle.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Article article = snapshot.getValue(Article.class);

                        mArticle.add(article);

                }

                articleAdapter = new ArticleAdapter(getContext(), mArticle);
                recyclerViewReadArticle.setAdapter(articleAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readArticle() {
        mArticle = new ArrayList<>();

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Artikel");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Article article = snapshot.getValue(Article.class);

                    mArticle.add(article);
                    Log.d(TAG, "Data Terambil : " + article.getArticleTitle());

                }

                articleAdapter = new ArticleAdapter(getContext(), mArticle);
                recyclerViewReadArticle.setAdapter(articleAdapter);

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
