package com.android.example.guardianapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ArticlesAdapter extends ArrayAdapter<Article> {
    public ArticlesAdapter(@NonNull Context context, @NonNull List<Article> articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.article, parent, false);
            holder = new ViewHolder();
            holder.thumbnail = convertView.findViewById(R.id.thumbnail);
            LinearLayout desctiption = convertView.findViewById(R.id.description);
            holder.title = desctiption.findViewById(R.id.title);
            holder.date = desctiption.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Article article = getItem(position);
        if (article != null && holder != null) {
            holder.thumbnail.setImageBitmap(article.getThumbnail());
            holder.title.setText(article.getTitle());
            holder.date.setText(article.getDate());
        }
        return convertView;
    }

    private static class ViewHolder {
        private ImageView thumbnail;
        private TextView title;
        private TextView date;
    }
}
