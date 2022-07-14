package com.nascodefy.emosianpkm;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class AdapterPHQ  extends PagerAdapter {
    Context mContext;
    List<ScreenItemPHQ> mListScreen;


    public AdapterPHQ(Context mContext, List<ScreenItemPHQ> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.content_phq,null);


        TextView pertanyaan = layoutScreen.findViewById(R.id.pertanyaanphq);

        pertanyaan.setText(mListScreen.get(position).getQuestion());



        container.addView(layoutScreen);
        return layoutScreen;

    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
