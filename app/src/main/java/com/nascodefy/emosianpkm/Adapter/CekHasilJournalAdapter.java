package com.nascodefy.emosianpkm.Adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.nascodefy.emosianpkm.Model.History;
import com.nascodefy.emosianpkm.Model.Journal;
import com.nascodefy.emosianpkm.Model.User;
import com.nascodefy.emosianpkm.R;

import java.util.List;

public class CekHasilJournalAdapter extends RecyclerView.Adapter<CekHasilJournalAdapter.ViewHolder> {

    private Context mContext;
    private List<Journal> mJournal;
    CardView cardViewHistory;


    public CekHasilJournalAdapter(Context mContext, List<Journal> mJournal) {
        this.mContext = mContext;
        this.mJournal = mJournal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cek_jurnal_content_view, parent, false);
        return new CekHasilJournalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Journal journal = mJournal.get(position);
         DatabaseReference Userreference = FirebaseDatabase.getInstance().getReference("Users")
                 .child(journal.getId());
       // final String userName = mUserName.get(position);

        //holder.userName.setText(history.getUsername());

        holder.feelTitle.setText(journal.getTitleFeel());
        holder.dateFeel.setText(journal.getDateFeel());
        holder.username.setText(journal.getUsername());
        if (journal.getTitleFeel().equals("Senang")) {
            //cardViewHistory.setCardBackgroundColor(Color.parseColor("#E36A6A"));
            holder.imageViewFeel.setImageResource(R.drawable.emotsenyum);
        }
        if (journal.getTitleFeel().equals("Sedih")) {
//            cardViewHistory.setCardBackgroundColor(Color.parseColor("#F59D52"));
            holder.imageViewFeel.setImageResource(R.drawable.emotsad);

        }
        if (journal.getTitleFeel().equals("Marah")) {
//            cardViewHistory.setCardBackgroundColor(Color.parseColor("#7C8CEC"));
            holder.imageViewFeel.setImageResource(R.drawable.emotangry);
        }
        if (journal.getTitleFeel().equals("Bahagia")) {
//            cardViewHistory.setCardBackgroundColor(Color.parseColor("#D39BE4"));
            holder.imageViewFeel.setImageResource(R.drawable.emotlaugh);
        }
        if (journal.getTitleFeel().equals("Malas")) {
            holder.imageViewFeel.setImageResource(R.drawable.emotlazy);
        }

        if (journal.getTitleFeel().equals("Kecewa")) {
            holder.imageViewFeel.setImageResource(R.drawable.emotkecewa);
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
//                Intent intent = new Intent(mContext, Detail.class);
//                intent.putExtra("userid", history.getId());
//                mContext.startActivity(intent);
                Toast.makeText(mContext, journal.getDescFeel(), Toast.LENGTH_SHORT).show();


            }
        });
    }



    @Override
    public int getItemCount() {
        return mJournal.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView feelTitle, feelDesc, dateFeel,username;
        public ImageView imageViewFeel,profileImage;


        public ViewHolder(View itemView) {
            super(itemView);
            feelTitle = itemView.findViewById(R.id.cek_title_feel);
//            feelDesc = itemView.findViewById(R.id.cek_fe);
            dateFeel = itemView.findViewById(R.id.cek_dateFeel);
            imageViewFeel = itemView.findViewById(R.id.image_feel);
            profileImage = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.username_cek_hasil);
        }
    }
}
