package com.nathan.unionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.OkHttpClient;
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

    //Variables I need
    private RSVPdata mRSVPResponse;
    private String mWeddingCode;
    private WeddingData mWeddingResponse;

    //View things
    private ProgressBar mLoading;
    private TextView mFullName;

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
            //Log.d(TAG, this.getIntent().getExtras().toString());
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

    private void setInvitation(){
        mLoading.setVisibility(GONE);
        mFullName.setText(mRSVPResponse.rsvpInfo.firstName + " " + mRSVPResponse.rsvpInfo.lastName + ",");
    }
}
