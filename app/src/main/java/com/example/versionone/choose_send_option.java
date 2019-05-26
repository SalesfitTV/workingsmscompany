package com.example.versionone;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class choose_send_option extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_send_option);

        Button enterbutton = findViewById(R.id.newnumbtn);
        enterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), type_number_in.class);
                startActivity(intent);
            }
        });

        Button choosebutton = findViewById(R.id.choosebtn);
        choosebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), pick_from_contacts.class);
                startActivity(intent);
            }
        });



}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Write your code here
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
}}
