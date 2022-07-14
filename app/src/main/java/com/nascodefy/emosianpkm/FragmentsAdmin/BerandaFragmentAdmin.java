package com.nascodefy.emosianpkm.FragmentsAdmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.nascodefy.emosianpkm.AddArticle;
import com.nascodefy.emosianpkm.ArticleViewActivity;
import com.nascodefy.emosianpkm.CekHasilDiagnosaActivity;
import com.nascodefy.emosianpkm.CekHasilJurnalActivity;
import com.nascodefy.emosianpkm.DiagnosisPHQActivity;
import com.nascodefy.emosianpkm.HistoryPHQActivity;
import com.nascodefy.emosianpkm.Model.User;
import com.nascodefy.emosianpkm.R;
import com.nascodefy.emosianpkm.TellMeActivity;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import de.hdodenhof.circleimageview.CircleImageView;

//import com.nascodefy.emosian.ConsultActivity;


public class BerandaFragmentAdmin extends Fragment {

    CardView diagnosaPHQ, historyPHQ, journal;

    CircleImageView image_profile;
    TextView username;
    DatabaseReference reference;
    FirebaseUser fuser;

    StorageReference storageReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beranda_admin, container,false);

        diagnosaPHQ = view.findViewById(R.id.to_consult);
        historyPHQ = view.findViewById(R.id.to_histories);
        image_profile = view.findViewById(R.id.profile_image);
        username = view.findViewById(R.id.username);
        journal = view.findViewById(R.id.to_check_jurnal);



        diagnosaPHQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toConsult = new Intent(getActivity(), CekHasilDiagnosaActivity.class);
                startActivity(toConsult);
            }
        });

        historyPHQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toArticle = new Intent(getActivity(), AddArticle.class);
                startActivity(toArticle);
            }
        });

        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toJurnal = new Intent(getActivity(), CekHasilJurnalActivity.class);
                startActivity(toJurnal);
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")){
                    image_profile.setImageResource(R.drawable.img1);
                } else {
                    try{Glide.with(getContext()).load(user.getImageURL()).into(image_profile);}
                    catch (Exception e){e.printStackTrace();}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        tellMe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent toTell = new Intent(getActivity(), TellMeActivity.class);
//                startActivity(toTell);
//            }
//        });


        return view;
    }

}
