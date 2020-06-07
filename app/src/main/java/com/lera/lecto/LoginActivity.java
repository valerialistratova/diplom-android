package com.lera.lecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button login;

    String url = "http://192.168.1.104:8080/login-android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailVal = email.getText().toString();
                String passwordVal = password.getText().toString();
                if (emailVal.length() > 0 && passwordVal.length() > 0) {
                    //TODO: send request
                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add("email", emailVal)
                            .add("password", passwordVal)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .header("X-Requested-With", "XMLHttpRequest")
                            .post(formBody)
                            .build();
                    try {
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.d("MY", e.toString());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                String jsonData = response.body().string();
                                try {
                                    JSONObject Jobject = new JSONObject(jsonData);
                                    Holder.getInstance().userId = Integer.parseInt(Jobject.get("userId").toString());
                                    Intent i = new Intent(LoginActivity.this, ChooserActivity.class);
                                    startActivity(i);
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    } catch (Exception e) {
                        Log.e("MY", e.toString());
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Потрібно ввести email та пароль!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}