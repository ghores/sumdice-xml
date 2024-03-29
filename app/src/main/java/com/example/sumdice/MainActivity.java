package com.example.sumdice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static int WIN_SCORE = 100;

    private int myScore = 0;
    private int pcScore = 0;
    private boolean isPcTurn = false;

    private TextView txt_side;
    private TextView txt_pcdetail;
    private TextView txt_mydetail;
    private TextView txt_myscore;
    private TextView txt_pcscore;

    private ImageView img_dice;

    private ArrayList<Integer> myNumbers;
    private ArrayList<Integer> pcNumbers;


    /** show proper caption based on turn */
    private void processSideCaption() {
        if (isPcTurn) {
            txt_side.setText("PC Turn.");
        } else {
            txt_side.setText("Your Turn. Click on dice to continue...");
        }
    }


    /** compute string presentation of detail dynamic array */
    private String computeDetailString(ArrayList<Integer> detailArray) {
        String detail = "";
        boolean isFirst = true;
        for (Integer myNumber: detailArray) {
            if (isFirst) {
                detail += myNumber;
                isFirst = false;
                continue;
            }

            detail += " + " + myNumber;
        }

        return detail;
    }


    /** show winner */
    private void win() {
        Intent intent = new Intent(MainActivity.this, WonActivity.class);

        if (isPcTurn) {
            intent.putExtra("WHO", "PC");
        } else {
            intent.putExtra("WHO", "You");
        }

        MainActivity.this.startActivity(intent);
        finish();
    }


    /** drop a real dice and process result */
    private void diceDrop() {
        // generate random number from 1 to 6 inclusive
        int number = (int) Math.floor(Math.random() * 6 + 1);

        // set dice graphical resource
        int res = 0;
        switch(number) {
            case 1: res = R.drawable.dice_1; break;
            case 2: res = R.drawable.dice_2; break;
            case 3: res = R.drawable.dice_3; break;
            case 4: res = R.drawable.dice_4; break;
            case 5: res = R.drawable.dice_5; break;
            case 6: res = R.drawable.dice_6; break;
        }
        img_dice.setImageResource(res);

        // compute total score and process ui
        if (isPcTurn) {
            pcNumbers.add(number);
            pcScore += number;
            txt_pcdetail.setText(computeDetailString(pcNumbers));
            txt_pcscore.setText("" + pcScore);
            if (pcScore >= WIN_SCORE) {
                win();
                return;
            }
        } else {
            myNumbers.add(number);
            myScore += number;
            txt_mydetail.setText(computeDetailString(myNumbers));
            txt_myscore.setText("" + myScore);
            if (myScore >= WIN_SCORE) {
                win();
                return;
            }
        }

        // let drop dice again for lucky player
        if (number != 6) {
            isPcTurn = !isPcTurn;
            processSideCaption();
        }

        // if player is pc, drop dice automatically
        if (isPcTurn) {
            diceDrop();
        }
    }


    /** initialize ui on startup */
    private void initializeUi() {
        // initialize variables
        txt_myscore = (TextView) findViewById(R.id.txt_myscore);
        txt_pcscore = (TextView) findViewById(R.id.txt_pcscore);
        txt_mydetail = (TextView) findViewById(R.id.txt_mydetail);
        txt_pcdetail = (TextView) findViewById(R.id.txt_pcdetail);
        txt_side = (TextView) findViewById(R.id.txt_side);
        img_dice = (ImageView) findViewById(R.id.img_dice);

        // initialize ui
        txt_myscore.setText("0");
        txt_pcscore.setText("0");
        txt_mydetail.setText("");
        txt_pcdetail.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize dynamic arrays
        myNumbers = new ArrayList<Integer>();
        pcNumbers = new ArrayList<Integer>();

        initializeUi();

        // drop dice automatically if this is pc turn
        isPcTurn = Math.random() >= 0.5;
        if (isPcTurn) {
            diceDrop();
        }

        processSideCaption();

        // click listener for dice view
        img_dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diceDrop();
            }
        });
    }
}

