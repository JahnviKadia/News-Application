package com.mc2022.template;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BlankFragment extends Fragment {

    RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private ArrayList<News> newsArrayList;
    private static final String TAG = "Valid URL" ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView = v.findViewById(R.id.recview);
        newsArrayList = new ArrayList<>();
        newsAdapter = new NewsAdapter( newsArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(newsAdapter);
        if(checkNetworkConnection()){
            new DownloadData().execute();
        }
        return v;
    }

    private boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!= null;
    }

    class DownloadData extends AsyncTask<String, Integer, String> {
        final String[] url_1 = new String[1];
        StringBuilder sb;
        String result = " ";
        int counter = 0;
        int num;
        InputStream input;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... f_url) {
            URL url = null;
            try {
                for (num = counter; num <= 20; num++) {
                    url_1[0] = "https://petwear.in/mc2022/news/news_" + num + ".json";

                    News news = new News();
                    news.setNumber(num);

                    System.out.println(url_1[0]);
                    url = new URL(url_1[0]);
                    HttpURLConnection conection = (HttpURLConnection) url.openConnection();
                    conection.setRequestMethod("GET");
                    conection.connect();

                    if(conection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        input = new BufferedInputStream(url.openStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        sb = new StringBuilder();
                        String line = " ";
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + '\n');
                        }
                    }

                    result = sb.toString();
                    try {
                        JSONObject root = new JSONObject(result);
                        news.setTitle(root.getString("title"));
                        news.setBody(root.getString("body"));
                        news.setImageURL(root.getString("image-url"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    newsArrayList.add(news);
                    input.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace(); }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            newsAdapter.notifyDataSetChanged();
        }
    }
}