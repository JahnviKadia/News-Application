package com.mc2022.template;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BlankFragment extends Fragment {

    RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private ArrayList<News> newsArrayList;
    private static final String TAG = "Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView = v.findViewById(R.id.recview);
        newsArrayList = new ArrayList<>();
        newsAdapter = new NewsAdapter( newsArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.addItemDecoration(dividerItemDecoration);

        new FetchData().execute();

        return v;
    }

    class FetchData extends AsyncTask<String, Void, String> {

        private InputStream inputStream;
        private BufferedReader bufferedReader;
        private HttpURLConnection connection;
        private static final String DEBUG_TAG = "NetworkStatusExample";
        private  int counter = 0;
        private int num;
        final String[] url_1 = new String[1];
        private String result;
        ImageView imageView;
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ConnectivityManager connMng = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connMng.getActiveNetworkInfo();
            boolean connected = netInfo.isConnected();
            boolean isWiFiConnected=(netInfo.getType()==ConnectivityManager.TYPE_WIFI);
            boolean isMobileConnected=(netInfo.getType()==ConnectivityManager.TYPE_MOBILE);
            Log.d(DEBUG_TAG, "WIFi connected="+isWiFiConnected);
            Log.d(DEBUG_TAG,"Mobile data Connected"+isMobileConnected);

            if(!connected)
                cancel(true);

        }

        @Override
        protected String doInBackground(String... voids) {

            StringBuilder stringBuilder = null;

            URL url = null;
            try {
                for (num = counter; num <= 20; num++) {
                    url_1[0] = "https://petwear.in/mc2022/news/news_" + num + ".json";
                    News news = new News();
                    news.setNumber(num);

                    System.out.println(url_1[0]);
                    url = new URL(url_1[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    // Thread.sleep(1000);
                    Log.i("TAG", "status code: " + connection.getResponseCode());

                    //Reading Response
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        inputStream = connection.getInputStream();
                        //Reading data from InputStream
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                    }
                    result = stringBuilder.toString();

                    try {
                        JSONObject root = new JSONObject(result);
                       news.setTitle(root.getString("title"));
                        news.setBody(root.getString("body"));
                        news.setImageURL(root.getString("image-url"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                   newsArrayList.add(news);

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(TAG, "error: " + e.getMessage());
            } finally {
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();

                    if (inputStream != null)
                        inputStream.close();

                    if(connection != null)
                        connection.disconnect();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (stringBuilder != null) {
                return stringBuilder.toString();
            }
            return "no connection";
        }
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            newsAdapter.notifyDataSetChanged();
        }
    }
}