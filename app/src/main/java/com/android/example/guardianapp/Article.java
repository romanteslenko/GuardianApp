package com.android.example.guardianapp;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Article {
    private static final String GUARDIAN_MAIN_PAGE = "https://www.theguardian.com/";
    private String mTitle;
    private String mDate;
    private String mUrl;
    private Bitmap mThumbnail;

    public Article(String title, String date, String url, Bitmap thumbnail) {
        this.mTitle = title;
        this.mDate = date;
        this.mUrl = url;
        this.mThumbnail = thumbnail;
    }

    @NonNull
    public String getTitle() {
        return mTitle != null ? mTitle : "";
    }

    @NonNull
    public String getDate() {
        return mDate != null ? mDate : "";
    }

    @NonNull
    public String getUrl() {
        return mUrl != null ? mUrl : GUARDIAN_MAIN_PAGE;
    }

    @Nullable
    public Bitmap getThumbnail() {
        return mThumbnail;
    }
}
