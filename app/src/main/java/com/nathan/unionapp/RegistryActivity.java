package com.nathan.unionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RegistryActivity extends AppCompatActivity {


    private TextView mThankYou;
    private TextView mDateString;
    private Button mRegistryButton;

    private String mFirstName;
    private String mWeddingDate;
    private String mRegistryWebsite;
    private String mDateLongString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        mThankYou = findViewById(R.id.thank_you);
        mDateString = findViewById(R.id.date_text);
        mRegistryButton = findViewById(R.id.registry_button);

        Bundle extras = getIntent().getExtras();
        mFirstName = extras.getString("EXTRA_FIRST_NAME");
        mWeddingDate = extras.getString("EXTRA_WEDDING_DATE");
        mRegistryWebsite = extras.getString("EXTRA_REGISTRY");
        getDifferenceDays(mWeddingDate);
        mThankYou.setText(mFirstName + ", " + getString(R.string.thank_you_text));
        mDateString = findViewById(R.id.date_string);
        mDateString.setText(getString(R.string.date_string, mDateLongString));

        mRegistryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                mWebLink.setData(Uri.parse(mRegistryWebsite));
                startActivity(mWebLink);
            }
        });
    }

    public void getDifferenceDays(String mWeddingDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date mDateTime = null;
        try {
            mDateTime = formatter.parse(mWeddingDate);
            Log.e("TIME_PARSED", mDateTime.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long mDiff = mDateTime.getTime() - System.currentTimeMillis();
        long mDiffString =  TimeUnit.DAYS.convert(mDiff, TimeUnit.MILLISECONDS);
        mDateLongString = Long.toString(mDiffString);
        Log.e("TIME_DIFFERENCE", mDateLongString);
    }
}
