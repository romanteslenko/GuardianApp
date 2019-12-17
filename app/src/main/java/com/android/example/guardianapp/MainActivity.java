package com.android.example.guardianapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {
    public static final String TAG = "GuardianApp";

    private ArticlesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new ArticlesAdapter(this, new ArrayList<Article>());
        ListView listView = findViewById(android.R.id.list);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = (Article) parent.getItemAtPosition(position);
                if (article != null) {
                    Uri uri = Uri.parse(article.getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @NonNull
    @Override
    public Loader<List<Article>> onCreateLoader(int id, @Nullable Bundle args) {
        String url = "https://content.guardianapis.com/search?section=football&page-size=10&show-fields=headline%2Cthumbnail&api-key=2e119e26-cf68-4292-8f7e-c7b73f66c25b";
        return new ArticlesLoader(this, url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Article>> loader, List<Article> data) {
        mAdapter.clear();
        if (data != null) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Article>> loader) {
        mAdapter.clear();
    }

    private static class ArticlesLoader extends AsyncTaskLoader<List<Article>> {
        private String mUrl;

        public ArticlesLoader(@NonNull Context context, String url) {
            super(context);
            mUrl = url;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Nullable
        @Override
        public List<Article> loadInBackground() {
            return QueryUtils.fetch(mUrl);
        }
    }
}
