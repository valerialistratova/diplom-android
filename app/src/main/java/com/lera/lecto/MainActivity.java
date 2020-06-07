package com.lera.lecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView text;
    ScrollView scroll;

    int bookId = 1;
    int deviceId = 5;

    String url = "http://192.168.1.104:8080/save-progress-android";

    Runnable r;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager am = getAssets();
        text = findViewById(R.id.text);
        scroll = findViewById(R.id.scroller);
        r = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        float height = text.getHeight();
                        float currentScroll = scroll.getScrollY();
                        int percent = Math.round(currentScroll / height * 100);
                        RequestBody formBody = new FormBody.Builder()
                                .add("book_id", String.valueOf(bookId))
                                .add("device_id", String.valueOf(deviceId))
                                .add("datetime", new Timestamp(new Date().getTime()).toString())
                                .add("progress", String.valueOf(percent))
                                .build();
                        Request request = new Request.Builder()
                                .url(url)
                                .header("X-Requested-With", "XMLHttpRequest")
                                .post(formBody)
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("MY", e.toString());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.d("MY", "call resp");
                            }
                        });
                        Thread.sleep(60000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            String toOpen = getIntent().getStringExtra("name");
            assert toOpen != null;

            switch (toOpen) {
                case "air.txt":
                    bookId = 7;
                    break;
                case  "onegin.txt":
                    bookId = 3;
                    break;
                case "wind.txt":
                    bookId = 1;
                    break;
            }
            InputStream is = am.open(toOpen);
            int size = is.available();
            byte[] buffer = new byte[size];
            int len = is.read(buffer);
            String text = new String(buffer, 0, len, "windows-1251");
            this.text.setText(text);
            is.close();
            Thread t1 = new Thread(r);
            t1.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}