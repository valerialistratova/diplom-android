package com.lera.lecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooserActivity extends AppCompatActivity {

    Button wind;
    Button onegin;
    Button air;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        wind = findViewById(R.id.wind);
        onegin = findViewById(R.id.onegin);
        air = findViewById(R.id.jane);

        wind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchReader("wind.txt");
            }
        });

        onegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchReader("onegin.txt");
            }
        });

        air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchReader("air.txt");
            }
        });

    }

    private void launchReader(String name) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}