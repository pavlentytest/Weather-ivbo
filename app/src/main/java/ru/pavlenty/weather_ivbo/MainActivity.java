package ru.pavlenty.weather_ivbo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button2);
        tv = findViewById(R.id.textView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // вызов наследника AsyncTask
                MyDownload md = new MyDownload();
                md.execute();
            }
        });
    }
    private class MyDownload extends AsyncTask<Void,Void,String> {

        HttpURLConnection httpurl; // null

        @Override
        protected String doInBackground(Void... v) {
            try {
                URL url = new URL("http://api.weatherstack.com/current?access_key=c88c008b493b902d7d6c70b4da8fe193&query=Dubai");
                httpurl = (HttpURLConnection) url.openConnection();
                httpurl.setRequestMethod("GET");
                httpurl.connect();

                InputStream input = httpurl.getInputStream();
                Scanner scan = new Scanner(input);
                StringBuilder buffer = new StringBuilder();
                while(scan.hasNextLine()) {
                    buffer.append(scan.nextLine());
                }
                return buffer.toString();
            } catch (java.io.IOException e) {
                Log.e("RRRR",e.toString());
                //e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson g = new Gson();
            Weather weather = g.fromJson(s,Weather.class);
            int temp = weather.getCurrent().getTemperature();
            // weather.getLocation().getCountry();
            tv.setText(Integer.toString(temp));
        }
    }

}
