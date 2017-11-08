package com.example.user.muxic;

/**
 * Created by Aman on 10/6/2016.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class choice extends AppCompatActivity {

    TextView send,recieve;
    int k = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

                send= (TextView) findViewById(R.id.send);
        recieve= (TextView) findViewById(R.id.recieve);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(choice.this,MusicActivity.class);
                startActivity(intent);
                finish();

            }
        });
        recieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(choice.this,bluetooth.class);
                startActivity(intent);
                finish();

            }
        });

        TextView exit = (TextView) findViewById(R.id.ex);
        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }




}
