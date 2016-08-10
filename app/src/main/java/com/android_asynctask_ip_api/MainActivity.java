package com.android_asynctask_ip_api;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private class IpApiTask extends AsyncTask<String, String, String> {
        InputStream is = null;
        int len = 500;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null)
                Log.d(TAG, "onPostExecute: " + s);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL("http://ip-api.com/json");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000 /* milliseconds */);
                httpURLConnection.setConnectTimeout(15000 /* milliseconds */);
                httpURLConnection.setRequestMethod("GET");

                if (200 == httpURLConnection.getResponseCode()) {
                    return readInputStream(httpURLConnection.getInputStream());
                } else {
                    return "";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }
    }

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        (new IpApiTask()).execute();
    }

    public static String readInputStream(InputStream stream) throws IOException {
        try {
            int intChar;
            StringBuilder responseBuilder = new StringBuilder();
            while ((intChar = stream.read()) != -1) responseBuilder.append((char) intChar);
            return responseBuilder.toString();
        } finally {
            if (stream != null) stream.close();
        }
    }
}
