package com.nathan.unionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static android.view.View.GONE;

public class InvitationActivity extends AppCompatActivity {

    //Logging
    private static final String TAG = "INVITATION_ACTIVITY";

    //Intent from activity
    private String mInviteCode;

    //For sending extras
    public static final String EXTRA_RSVP_OBJECT = "com.nathan.unionapp.rsvpobject";
    public static final String EXTRA_WEDDING_OBJECT = "com.nathan.unionapp.weddingobject";

    private static final int CONTACT_CODE = 1;

    //Variables I need
    private RSVPdata mRSVPResponse;
    private String mWeddingCode;
    private WeddingData mWeddingResponse;
    private String mBody;
    private RSVPdata mUpdateRSVPResponse;

    //View things
    private ProgressBar mLoading;
    private TextView mFullName;
    private TextView mRequest;
    private TextView mParticipantOne;
    private TextView mParticipantTwo;
    private TextView mAmp;
    private TextView mOnText;
    private TextView mDateText;
    private TextView mInText;
    private TextView mLocationText;
    private Button mAttendButton;
    private Button mDeclineButton;

    //Create HTTP Client
    private OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build();

    private ReservationService mReservationService = retrofit.create(ReservationService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);

        if (this.getIntent().getExtras() != null) {
            mInviteCode = getIntent().getStringExtra(MainActivity.EXTRA_INVITATION_CODE);
            Log.d(TAG, mInviteCode);
            getRSVPCall(mInviteCode);
        }
        else{
            Toast.makeText(InvitationActivity.this,"Unable to enter code", Toast.LENGTH_LONG).show();
            InvitationActivity.this.finish();
        }

        //View things
        mLoading = findViewById(R.id.progress_bar);
        mFullName = findViewById(R.id.full_name);
        mRequest = findViewById(R.id.request);
        mRequest.setVisibility(GONE);
        mParticipantOne = findViewById(R.id.participant_one);
        mAmp = findViewById(R.id.ampersand);
        mAmp.setVisibility(GONE);
        mParticipantTwo = findViewById(R.id.participant_two);
        mOnText = findViewById(R.id.on_text);
        mOnText.setVisibility(GONE);
        mDateText = findViewById(R.id.date_text);
        mInText = findViewById(R.id.in_text);
        mInText.setVisibility(GONE);
        mLocationText = findViewById(R.id.location_text);
        mAttendButton = findViewById(R.id.attend_button);
        mAttendButton.setVisibility(GONE);
        mDeclineButton = findViewById(R.id.decline_button);
        mDeclineButton.setVisibility(GONE);

        mAttendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //send this to an intent
            mBody = "{\"attending\":\"true\"}";
            RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json"), mBody);
            updateRSVPCall(mRequestBody);
            Toast.makeText(InvitationActivity.this,"Glad you can attend", Toast.LENGTH_LONG).show();
            gotoRegistry("attend");
            }
        });
        mDeclineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mBody = "{\"attending\":\"false\"}";
            RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json"), mBody);
            updateRSVPCall(mRequestBody);
            Toast.makeText(InvitationActivity.this,"Sorry you can\'t make it", Toast.LENGTH_LONG).show();
            InvitationActivity.this.finish();
            }
        });
    }

    private void getRSVPCall(String mInviteCode) {

        mReservationService.getRSVP(mInviteCode).enqueue(new Callback<RSVPdata>() {
            @Override
            public void onResponse(@NonNull Call<RSVPdata> call, @NonNull Response<RSVPdata> response) {
                mRSVPResponse = response.body();
                //if api response is valid
                if (mRSVPResponse != null && !mRSVPResponse.rsvpInfo.weddingID.equals("null")) {
                    mWeddingCode = mRSVPResponse.rsvpInfo.weddingID;
                    //Make the second api call
                    getWeddingCall(mWeddingCode);
                    Log.d("Response_body", mRSVPResponse.toString());
                }
                else {
                    Toast.makeText(InvitationActivity.this,"Unable to get RSVP information", Toast.LENGTH_LONG).show();
                    InvitationActivity.this.finish();
                }
            }
            @Override
            public void onFailure(Call<RSVPdata> call, Throwable t) {
                Log.e(TAG, "Error getting info", t);
                Toast.makeText(InvitationActivity.this,"Unable to get RSVP information", Toast.LENGTH_LONG).show();
                InvitationActivity.this.finish();
            }
        });
    }

    private void getWeddingCall(String mWeddingCode) {

        mReservationService.getWedding(mWeddingCode).enqueue(new Callback<WeddingData>() {
            @Override
            public void onResponse(@NonNull Call<WeddingData> call, @NonNull Response<WeddingData> response) {
                mWeddingResponse = response.body();
                //if api response is valid
                if (mWeddingResponse != null) {
                    Log.d("Wedding_Response_body", mWeddingResponse.toString());
                    setInvitation();
                }
                else {
                    Toast.makeText(InvitationActivity.this,"Unable to get wedding information", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<WeddingData> call, Throwable t) {
                Log.e(TAG, "Error getting info", t);
                Toast.makeText(InvitationActivity.this,"Unable to get wedding information", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateRSVPCall(RequestBody mBody) {

        mReservationService.updateRSVP(mRSVPResponse.id, mBody).enqueue(new Callback<RSVPdata>() {
            @Override
            public void onResponse(Call<RSVPdata> call, Response<RSVPdata> response) {
                mUpdateRSVPResponse = response.body();
                //if api response is valid
                if(mUpdateRSVPResponse != null) {
                    Log.e("UPDATE SUCCESSFUL?", mUpdateRSVPResponse.toString());
                    Toast.makeText(InvitationActivity.this,"RSVP Updated", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(InvitationActivity.this,"Unable to update RSVP information", Toast.LENGTH_LONG).show();
                    InvitationActivity.this.finish();
                }
            }
            @Override
            public void onFailure(Call<RSVPdata> call, Throwable t) {
                Log.e(TAG, "Error getting info", t);
                Toast.makeText(InvitationActivity.this,"Unable to update RSVP information", Toast.LENGTH_LONG).show();
                InvitationActivity.this.finish();
            }
        });
    }

    private void setInvitation(){
        mLoading.setVisibility(GONE);
        mFullName.setText(mRSVPResponse.rsvpInfo.firstName + " " + mRSVPResponse.rsvpInfo.lastName + ",");
        mRequest.setVisibility(View.VISIBLE);
        mAmp.setVisibility(View.VISIBLE);
        mParticipantOne.setText(mWeddingResponse.weddingInfo.participantOneName);
        mParticipantTwo.setText(mWeddingResponse.weddingInfo.participantTwoName);
        mOnText.setVisibility(View.VISIBLE);
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        String mDateString = "";
        try {
            mDateString = formatter.format(parser.parse(mWeddingResponse.weddingInfo.weddingDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mDateText.setText(mDateString);
        mInText.setVisibility(View.VISIBLE);
        mLocationText.setText(mWeddingResponse.weddingInfo.weddingLocation);
        mAttendButton.setVisibility(View.VISIBLE);
        mDeclineButton.setVisibility(View.VISIBLE);
    }

    private void gotoRegistry(String mAttending){
        Intent mRegistryIntent = new Intent(InvitationActivity.this, RegistryActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_REGISTRY", mWeddingResponse.weddingInfo.weddingRegistry);
        extras.putString("EXTRA_WEDDING_DATE", mWeddingResponse.weddingInfo.weddingDate);
        extras.putString("EXTRA_RSVP", mAttending);
        mRegistryIntent.putExtras(extras);
        startActivityForResult(mRegistryIntent, CONTACT_CODE);
    }
}
