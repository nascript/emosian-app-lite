package com.nascodefy.emosianpkm.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.nascodefy.emosianpkm.DialogPopUp;
import com.nascodefy.emosianpkm.MessageActivity;
import com.nascodefy.emosianpkm.Model.History;
import com.nascodefy.emosianpkm.R;
import com.nascodefy.emosianpkm.ResultPHQActivity;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context mContext;
    private List<History> mHistory;
    CardView cardViewHistory;

    public HistoryAdapter(Context mContext, List<History> mHistory) {
        this.mContext = mContext;
        this.mHistory = mHistory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_content_view, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final History history = mHistory.get(position);
        holder.tingkatDiag.setText(history.getTingkatDiag());
        holder.skorDiag.setText(history.getSkorDiag());
        holder.dateDiag.setText(history.getDateDiag());

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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, Detail.class);
//                intent.putExtra("userid", history.getId());
//                mContext.startActivity(intent);
                //Toast.makeText(mContext, "Bisa Di Klik " + position, Toast.LENGTH_SHORT).show();
                Intent toResult = new Intent(mContext, ResultPHQActivity.class);
                toResult.putExtra("hasilPHQ", history.getSkorDiag());
                toResult.putExtra("tingkatPHQ", history.getTingkatDiag());
                toResult.putExtra("anjuranPHQ", history.getAnjuranDiag());
                toResult.putExtra("dateToday", history.getDateDiag() );
                mContext.startActivity(toResult);


            }
        });
    }



    @Override
    public int getItemCount() {
        return mHistory.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tingkatDiag, skorDiag, dateDiag;

        public ViewHolder(View itemView) {
            super(itemView);
            tingkatDiag = itemView.findViewById(R.id.hist_tingkatDiag);
            skorDiag = itemView.findViewById(R.id.hist_skorDiag);
            dateDiag = itemView.findViewById(R.id.hist_dateDiag);
            cardViewHistory = itemView.findViewById(R.id.indicator_color);
        }
    }
}
