package com.nascodefy.emosianpkm.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.nascodefy.emosianpkm.Model.History;
import com.nascodefy.emosianpkm.Model.Journal;
import com.nascodefy.emosianpkm.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {

    private Context mContext;
    private List<Journal> mJournal;
    CardView cardViewHistory;
    ImageView imageViewFeel;

    public JournalAdapter(Context mContext, List<Journal> mJournal) {
        this.mContext = mContext;
        this.mJournal = mJournal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_item_journal, parent, false);
        return new JournalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Journal journal = mJournal.get(position);
        //holder.imageFeel.setText(journal.getTingkatDiag());
        holder.textViewFeel.setText(journal.getTitleFeel());
        holder.dateFeel.setText(journal.getDateFeel());

        if (journal.getTitleFeel().equals("Senang")) {
            //cardViewHistory.setCardBackgroundColor(Color.parseColor("#E36A6A"));
            imageViewFeel.setImageResource(R.drawable.emotsenyum);
        }
        if (journal.getTitleFeel().equals("Sedih")) {
//            cardViewHistory.setCardBackgroundColor(Color.parseColor("#F59D52"));
            imageViewFeel.setImageResource(R.drawable.emotsad);

        }
        if (journal.getTitleFeel().equals("Marah")) {
//            cardViewHistory.setCardBackgroundColor(Color.parseColor("#7C8CEC"));
            imageViewFeel.setImageResource(R.drawable.emotangry);
        }
        if (journal.getTitleFeel().equals("Bahagia")) {
//            cardViewHistory.setCardBackgroundColor(Color.parseColor("#D39BE4"));
            imageViewFeel.setImageResource(R.drawable.emotlaugh);
        }
        if (journal.getTitleFeel().equals("Malas")) {
            imageViewFeel.setImageResource(R.drawable.emotlazy);
        }

        if (journal.getTitleFeel().equals("Kecewa")) {
            imageViewFeel.setImageResource(R.drawable.emotkecewa);
        }

//        if (journal.getTingkatDiag().equals("Anda Tidak Mengalami Depresi")) {
//            cardViewHistory.setCardBackgroundColor(Color.parseColor("#A1E49B"));
//
//        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, Detail.class);
//                intent.putExtra("userid", history.getId());
//                mContext.startActivity(intent);
                Toast.makeText(mContext, "Karena : " +"\n"+ journal.getDescFeel(), Toast.LENGTH_LONG).show();
                //openBottomSheet();


            }
        });
    }

//    public void openBottomSheet() {
//
//
//        View view = getLayoutInflater().inflate(R.layout.sheet_bottom_layout, null);
//        calender = Calendar.getInstance();
//        simpleDateFormat = new SimpleDateFormat("EEEEEE, MM.dd.yyyy" , Locale.getDefault());
//        dateToday = simpleDateFormat.format(calender.getTime());
//
//        auth = FirebaseAuth.getInstance();
//        reference = FirebaseDatabase.getInstance().getReference("Journal");
//        descFeel = view.findViewById(R.id.feel_desc);
//
//        final Spinner spin1 = (Spinner) view.findViewById(R.id.spin1);
//        //Spinner spin2 = (Spinner) view.findViewById(R.id.spin2);
//        //ListView catList = (ListView) view.findViewById(R.id.listItems);
//        loadingBar = new ProgressDialog(JournalAdapter.this);
//        Button btnDone = (Button) view.findViewById(R.id.btnDone);
//
//        final Dialog mBottomSheetDialog = new Dialog(getActivity(),
//                R.style.MaterialDialogSheet);
//        mBottomSheetDialog.setContentView(view);
//        mBottomSheetDialog.setCancelable(true);
//        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        mBottomSheetDialog.getWindow().setGravity(Gravity.TOP);
//        mBottomSheetDialog.show();
//
//        String items[] = {"Senang", "Sedih", "Marah", "Kecewa", "Bahagia", "Malas"};
//
//        spin1.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, items));
//
//        btnDone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadingBar.setTitle("Proses Pengiriman..");
//                loadingBar.setMessage("Mohon Tunggu Beberapa Saat");
//                loadingBar.setCanceledOnTouchOutside(true);
//                loadingBar.show();
//
//                final String txt_descfeel = descFeel.getText().toString();
//                final String txt_feetTitle = spin1.getSelectedItem().toString();
//
//                if (TextUtils.isEmpty(txt_descfeel) || TextUtils.isEmpty(txt_feetTitle)) {
//                    Toast.makeText(getActivity(), "Harap Semua Kolom Di Isi", Toast.LENGTH_SHORT).show();
//                    loadingBar.dismiss();
//                } else {
//                    addJournal(txt_descfeel, txt_feetTitle);
//                }
//
//
//                mBottomSheetDialog.dismiss();
//            }
//        });
//
//    }


    @Override
    public int getItemCount() {
        return mJournal.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewFeel, dateFeel;
        public ImageView profileImage;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewFeel = itemView.findViewById(R.id.image_feel);
            textViewFeel = itemView.findViewById(R.id.text_feel);
            dateFeel = itemView.findViewById(R.id.date_feel);
            cardViewHistory = itemView.findViewById(R.id.indicator_color);

        }
    }
}
