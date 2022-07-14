package com.nascodefy.emosianpkm.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nascodefy.emosianpkm.R;


public class ArticleFragment extends Fragment {

    Button addArticle;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);



        //addArticle = view.findViewById(R.id.btn_add_Article);

//        addArticle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent toAddArticle = new Intent(getActivity(), AddArticle.class);
//                startActivity(toAddArticle);
//            }
//        });

        return view;


    }


}