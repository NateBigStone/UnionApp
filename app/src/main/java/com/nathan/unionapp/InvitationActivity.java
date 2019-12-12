package com.nathan.unionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class InvitationActivity extends AppCompatActivity {

    //Logging
    private static final String TAG = "INVITATION_ACTIVITY";

    //Intent from activity
    private String mInviteCode;

    //Variables I need
    private RSVPdata mRSVPResponse;
    private String mWeddingCode;

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
            InvitationActivity.this.finish();
        }
    }


    private void getRSVPCall(String mInviteCode) {

        mReservationService.getRSVP(mInviteCode).enqueue(new Callback<RSVPdata>() {
            @Override
            public void onResponse(@NonNull Call<RSVPdata> call, @NonNull Response<RSVPdata> response) {
                mRSVPResponse = response.body();
                //if api response is valid
                if (mRSVPResponse != null) {
                    mWeddingCode = mRSVPResponse.rsvpInfo.weddingID;
                    Log.d("Response_body", mRSVPResponse.toString());
                }
                else {
                    Toast.makeText(InvitationActivity.this,"Unable to get information", Toast.LENGTH_LONG).show();
                    InvitationActivity.this.finish();
                }
            }
            @Override
            public void onFailure(Call<RSVPdata> call, Throwable t) {
                Log.e(TAG, "Error getting info", t);
                Toast.makeText(InvitationActivity.this,"Unable to get information", Toast.LENGTH_LONG).show();
                InvitationActivity.this.finish();
            }
        });
    }
}
