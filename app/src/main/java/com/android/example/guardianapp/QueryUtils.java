package com.android.example.guardianapp;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.android.example.guardianapp.MainActivity.TAG;

public class QueryUtils {

    @Nullable
    public static List<Article> fetch(String request) {
        URL url = strToUrl(request);
        String json = null;
        try {
            json = getResponse(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseJson(json);
    }

    @Nullable
    private static String getResponse(URL url) throws IOException {
        if (url != null) {
            HttpURLConnection httpConnection = null;
            InputStream inputStream = null;
            try {
                httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setConnectTimeout(10000);
                httpConnection.setReadTimeout(15000);
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
                int responseCode = httpConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpConnection.getInputStream();
                    return readInputStream(inputStream);
                } else {
                    Log.e(TAG, "Get response code " + responseCode + " from server");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (httpConnection != null) {
                    httpConnection.disconnect();
                }
            }
        }
        return null;
    }

    @Nullable
    private static String readInputStream(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } else {
            Log.e(TAG, "Input stream is empty");
            return null;
        }
    }

    @Nullable
    private static List<Article> parseJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        List<Article> articles = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String title = result.getString("webTitle");
                String date = result.getString("webPublicationDate");
                String url = result.getString("webUrl");
                JSONObject fields = result.getJSONObject("fields");
                // TODO: fetch actual bitmap
                String thumbnailUrl = fields.getString("thumbnail");
                Article article = new Article(title, date, url, null);
                articles.add(article);
            }
        } catch (JSONException | NullPointerException e) {
            Log.e(TAG, "Cannot parse server response as json");
            e.printStackTrace();
        }
        return articles;
    }

    private static URL strToUrl(String urlStr) {
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Cannot create URL from " + urlStr);
            e.printStackTrace();
        }
        return url;
    }
}
