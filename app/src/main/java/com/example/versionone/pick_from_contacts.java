package com.example.versionone;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class pick_from_contacts extends AppCompatActivity {

    private final int REQUEST_CODE = 99;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private Button sendSMS;
    private String web;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_from_contacts);


        Button pickcontact = findViewById(R.id.pick_contact);
        pickcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);


            }
        });
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String num = "";
                        if (Integer.valueOf(hasNumber) == 1) {
                            Cursor numbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (numbers.moveToNext()) {
                                num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                Toast.makeText(pick_from_contacts.this, "Number=" + num, Toast.LENGTH_LONG).show();
                                TextView mobilenum = findViewById(R.id.mobile);
                                mobilenum.setText(num);
                                String nameContact = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                                TextView namedisplay = findViewById(R.id.namedisplay);
                                namedisplay.setText(nameContact);
                            }
                        }
                    }
                    break;
                }

        }

        EditText smscopy = findViewById(R.id.copy);
        TextView mobileno = findViewById(R.id.mobile);


        String mobileforcopy = mobileno.getText().toString();
       String mob = mobileforcopy.replaceAll(" ", "");

        String blah = offers_lp.getData();
        String blah2 = blah.substring(24);



        String textcopy1 = "Hey, as discussed please see the below:" + blah2 + "?utm_source=" + mob;
        smscopy.setText(textcopy1);


        final TextView phoneNumber = (TextView) findViewById(R.id.mobile);
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("permission", "Permission already granted.");
            } else {
                requestPermission();
            }
        }

        final EditText smsText = (EditText) findViewById(R.id.copy);
        sendSMS = (Button) findViewById(R.id.sendSMS);
        sendSMS.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String sms = smsText.getText().toString();
                String phoneNum = phoneNumber.getText().toString();
                if (!TextUtils.isEmpty(sms) && !TextUtils.isEmpty(phoneNum)) {
                    if (checkPermission()) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNum, null, sms, null, null);
                    } else {
                        Toast.makeText(pick_from_contacts.this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(pick_from_contacts.this, Manifest.permission.SEND_SMS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(pick_from_contacts.this,
                            "Permission accepted", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(pick_from_contacts.this,
                            "Permission denied", Toast.LENGTH_LONG).show();
                    Button sendSMS = (Button) findViewById(R.id.sendSMS);
                    sendSMS.setEnabled(false);

                }
                break;
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