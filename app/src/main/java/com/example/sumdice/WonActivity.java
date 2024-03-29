package com.example.sumdice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);

        // initialize ui
        Button btn_playAgain = findViewById(R.id.btn_playAgain);
        TextView txt_wonCaption = findViewById(R.id.txt_wonCaption);

        // receive bundle and WHO value
        Bundle bundle = getIntent().getExtras();
        String who = bundle.getString("WHO");

        // set caption of won activity
        txt_wonCaption.setText(who + " Won!");

        btn_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WonActivity.this, MainActivity.class);
                WonActivity.this.startActivity(intent);
                finish();
            }
        });
    }
}
