package com.nathan.unionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //GAUT63EA
    //https://union-rsvp.firebaseapp.com/api/v1/rsvps/DANI93E1

    private Button mButton;

    //Logging thing
    private static final String TAG = "RESULT_RSVP";
    //API Response
    private RSVPdata mRSVPResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mReservationCode.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Enter a Code", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                hideKeyboard();
                getRSVPCall();
//                mReservationCode.setText(R.string.empty);

            }
        });
    }

    private void getRSVPCall() {

        //Retrofit Debugging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl()
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();

        ReservationService mReservationService = retrofit.create(ReservationService.class);

        String mCode = "DANI93E1";

        mReservationService.getRSVP(mCode).enqueue(new Callback<RSVPdata>() {
            @Override
            public void onResponse(@NonNull Call<RSVPdata> call, @NonNull Response<RSVPdata> response) {
                mRSVPResponse = response.body();
                Log.d("Response_body", mRSVPResponse.toString());
                //if api response is valid
                if (mRSVPResponse != null) {
                    //Set the texts
                    //setTexts(mSymbolResponse);
                }
                else {
                    Toast.makeText(MainActivity.this,"Unable to get information", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<RSVPdata> call, Throwable t) {
                Log.e(TAG, "Error getting info", t);
                //Toast.makeText(getContext(),"Unable to get information", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void hideKeyboard() {
        View mainView = findViewById(android.R.id.content);
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(mainView.getWindowToken(), 0);
    }
}
