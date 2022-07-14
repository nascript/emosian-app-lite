package com.nascodefy.emosianpkm.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.nascodefy.emosianpkm.ArticleViewActivity;
import com.nascodefy.emosianpkm.HistoryPHQActivity;
import com.nascodefy.emosianpkm.Model.Article;
import com.nascodefy.emosianpkm.Model.Journal;
import com.nascodefy.emosianpkm.Model.User;
import com.nascodefy.emosianpkm.R;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private Context mContext;
    private List<Article> mArticle;
    CardView cardViewHistory;
    ImageView imageViewFeel;

    public ArticleAdapter(Context mContext, List<Article> mArticle) {
        this.mContext = mContext;
        this.mArticle = mArticle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_article, parent, false);
        return new ArticleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Article article = mArticle.get(position);

        if (article.getImageURL().equals("default")){
            holder.imageArticle.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(article.getImageURL()).into(holder.imageArticle);
        }


        holder.titleArticle.setText(article.getArticleTitle());
        holder.titleDesc.setText(article.getArticleDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, article.getArticleTitle(), Toast.LENGTH_LONG).show();


                Intent intent = new Intent(mContext, ArticleViewActivity.class);
                intent.putExtra("img_article", article.getImageURL());
                intent.putExtra("title", article.getArticleTitle());
                intent.putExtra("description", article.getArticleDesc());
                mContext.startActivity(intent);

                //Intent toHistory = new Intent(mContext, HistoryPHQActivity.class);
                //mContext.startActivity(new Intent(mContext, ArticleViewActivity.class));
            }
        });


    }


    @Override
    public int getItemCount() {
        return mArticle.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleArticle, titleDesc;
        public ImageView imageArticle;
        DatabaseReference reference;

        public ViewHolder(View itemView) {
            super(itemView);
            imageArticle = itemView.findViewById(R.id.fb_image_article);
            titleArticle = (TextView) itemView.findViewById(R.id.fb_title_articles);
            titleDesc = itemView.findViewById(R.id.fbDescArticle);

//            dateFeel = itemView.findViewById(R.id.date_feel);
//            cardViewHistory = itemView.findViewById(R.id.indicator_color);
        }
    }
}
