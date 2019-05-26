package com.example.versionone;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class type_number_in extends AppCompatActivity {

    private EditText txtMobile;
    private EditText venueName;
    private EditText customerName;
    private EditText txtMessage1;
    private Button btnSms;
    private Button mAddToDB;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    private static final String TAG = "AddToDatabase";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_number_in);


        txtMobile = (EditText)findViewById(R.id.mblTxt);
        venueName = (EditText)findViewById(R.id.venuename);
        customerName = (EditText)findViewById(R.id.custname);
        txtMessage1 = (EditText)findViewById(R.id.msgTxt);
        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        final String finalurl = globalClass.getUrlcolleccted();





        txtMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String mobnum = txtMobile.getText().toString();

                String finalurl1 = finalurl.substring(24);
                txtMessage1.setText(String.format("Hey, as discussed please see the below: %s?utm_source=%s", finalurl1, mobnum));
            }
        });
        btnSms = (Button) findViewById(R.id.btnSend);



                /*
                 * Code you want to run on the thread goes here
                 */
                mAuth = FirebaseAuth.getInstance();
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                myRef = mFirebaseDatabase.getReference();
                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            // User is signed in
                            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                        } else {
                            // User is signed out
                            Log.d(TAG, "onAuthStateChanged:signed_out");

                        }
                        // ...
                    }
                };

                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        Object value = dataSnapshot.getValue();
                        Log.d(TAG, "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });



// save data to fire base and send sms
                btnSms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "onClick: Attempting to add object to database.");
                        Date currentTime = Calendar.getInstance().getTime();
                        String datetime = currentTime.toString();
                        String VENUENAME = venueName.getText().toString();
                        String mobile = txtMobile.getText().toString();
                        String customername = customerName.getText().toString();
                        FirebaseUser userin = mAuth.getCurrentUser();
                        String userinfo = userin.getUid();
                        GlobalClass globalClass = (GlobalClass) getApplicationContext();
                        String finalurlfordb = globalClass.getUrlcolleccted();
                        String urlcollected = finalurlfordb.substring(41);
                        String mob = urlcollected.replaceAll("/", ":");
                        String namemob;
                        namemob = userinfo+";"+datetime+";"+VENUENAME+";"+mobile+";"+customername+";"+mob;
                        if(!mobile.equals("")){
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userID = user.getUid();
                            myRef.child(userID).child("Customer Record").child(namemob).setValue("true");
                            try{
                                SmsManager smgr = SmsManager.getDefault();
                                smgr.sendTextMessage(txtMobile.getText().toString(),null,txtMessage1.getText().toString(),null,null);
                                Toast.makeText(type_number_in.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();

                            }
                            catch (Exception e){
                                Toast.makeText(type_number_in.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Write your code here
        Intent intent = new Intent(getApplicationContext(), choose_send_option.class);
        startActivity(intent);


        }
    }
