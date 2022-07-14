package com.nascodefy.emosianpkm;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ArticleViewActivityCorausel extends AppCompatActivity {

    ImageView imageViewArticle;
    TextView title, descr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        //hilangin actionBar
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.AUTOFILL_FLAG_INCLUDE_NOT_IMPORTANT_VIEWS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        imageViewArticle = findViewById(R.id.img_article_view);
        title = findViewById(R.id.title_article_view);
        descr = findViewById(R.id.description_article_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int ima = extras.getInt("img_article");
            //String imageView = extras.getString("img_article");
            String gettitle = extras.getString("title");
            String getdesc = extras.getString("description");


            //Glide.with(ArticleViewActivityCorausel.this).load(imageView).into(imageViewArticle);
            imageViewArticle.setImageResource(ima);
            title.setText(gettitle);
            descr.setText(getdesc);
            //The key argument here must match that used in the other activity
        }
    }
}