package com.nascodefy.emosianpkm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nascodefy.emosianpkm.DiagnosisDetail;
import com.nascodefy.emosianpkm.Model.History;
import com.nascodefy.emosianpkm.Model.User;
import com.nascodefy.emosianpkm.R;

import java.util.List;

public class CekHasilDiagnosaAdapter extends RecyclerView.Adapter<CekHasilDiagnosaAdapter.ViewHolder> {

    private Context mContext;
    private List<History> mHistory;
    CardView cardViewHistory;

    public CekHasilDiagnosaAdapter(Context mContext, List<History> mHistory) {
        this.mContext = mContext;
        this.mHistory = mHistory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cek_hasil_content_view, parent, false);
        return new CekHasilDiagnosaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final History history = mHistory.get(position);
        DatabaseReference Userreference = FirebaseDatabase.getInstance().getReference("Users")
                .child(history.getUid());
       // final String userName = mUserName.get(position);
        holder.skorDiag.setText(history.getSkorDiag());
        holder.dateDiag.setText(history.getDateDiag());
        holder.userName.setText(history.getUsername());

        if (history.getTingkatDiag().equals("Depresi Berat")) {
            cardViewHistory.setCardBackgroundColor(Color.parseColor("#E36A6A"));

        }
        if (history.getTingkatDiag().equals("Depresi Ringan")) {
            cardViewHistory.setCardBackgroundColor(Color.parseColor("#F59D52"));

        }
        if (history.getTingkatDiag().equals("Depresi Minimal")) {
            cardViewHistory.setCardBackgroundColor(Color.parseColor("#7C8CEC"));

        }
        if (history.getTingkatDiag().equals("Depresi Sedang")) {
            cardViewHistory.setCardBackgroundColor(Color.parseColor("#D39BE4"));

        }

        if (history.getTingkatDiag().equals("Anda Tidak Mengalami Depresi")) {
            cardViewHistory.setCardBackgroundColor(Color.parseColor("#A1E49B"));

        }


        Userreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user.getImageURL().equals("default")){
                    holder.profileImage.setImageResource(R.drawable.img1);
                } else {
                    try{
                        Glide.with(mContext).load(user.getImageURL()).into(holder.profileImage);}
                    catch (Exception e){holder.profileImage.setImageResource(R.drawable.img1);}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mContext !=null) {
                    Intent intent = new Intent(mContext, DiagnosisDetail.class);
                    intent.putExtra("hasilPHQ", history.getSkorDiag());
                    intent.putExtra("tingkatPHQ", history.getTingkatDiag());
                    intent.putExtra("anjuranPHQ", history.getAnjuranDiag());
                    intent.putExtra("dateToday", history.getDateDiag());
                    intent.putExtra("username", history.getUsername());
                    mContext.startActivity(intent);
                } else{
                    Log.d("CekHasilDiagnosaAdapter", "context null ");
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return mHistory.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tingkatDiag, skorDiag, dateDiag,userName;
        public ImageView profileImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tingkatDiag = itemView.findViewById(R.id.cek_tingkatDiag);
            skorDiag = itemView.findViewById(R.id.cek_skorDiag);
            dateDiag = itemView.findViewById(R.id.cek_dateDiag);
            userName = itemView.findViewById(R.id.username_cek_hasil);
            cardViewHistory = itemView.findViewById(R.id.indicator_color);
            profileImage = itemView.findViewById(R.id.profile_image);
        }
    }
}
