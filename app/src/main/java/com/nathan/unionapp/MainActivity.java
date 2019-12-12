package com.nathan.unionapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //GAUT63EA
    //rsvps/DANI93E1
    //wedding/COST29BB

    //TODO: Add image and meal Choices to wedding schema
    //TODO: Enter Reservation code
    //TODO: INPUT validation on code
    //TODO: loading bar while the call is made
    //TODO: CREATE AN ARRAY OF PRESENT QUESTIONS
    //TODO: CREATE AN ARRAY OF NULL QUESTIONS
    //TODO: VERIFY THE USER IF NOT KICK BACK TO BEGINNING
    //TODO: Verify Wedding If not error fragment
    //TODO: if the user and wedding exists tell them if they are in the wedding party
    //TODO: VERIFY INFO AND SAVE
    //TODO: PROGRESS BAR for form
    //TODO: FANCY INFO FOR WEDDING: MAP INTENT, EMAIL ORGANIZER, DATE COUNTDOWN

    private EditText mCodeText;
    private Button mRSVPButton;
    private String mRSVPCode;

    public static final String EXTRA_INVITATION_CODE = "com.nathan.unionapp.invitationcode";
    private static final int INVITATION_REQUEST_CODE = 0;

    //Logging thing
    private static final String TAG = "RESULT_RSVP";
    //API Response
    private RSVPdata mRSVPResponse;
    private WeddingData mWeddingResponse;
    private String mWeddingCode;

    private static final String[] INFO = {
            "Is this your name?",
            "Are you able to attend the Costanza wedding?",
            "Is this your contact info?",
            "What meal would you like?",
            "Are you bringing a guest?",
            "Enter/Verify Guest/'s name",
            "Guest Meal Choice"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCodeText = findViewById(R.id.invite_code);
        mRSVPButton = findViewById(R.id.rsvp_button);

        mRSVPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRSVPCode = mCodeText.getText().toString().trim().toUpperCase();
                if (mRSVPCode.length() != 8) {
                    Toast.makeText(MainActivity.this, "Please Enter a Valid Invite Code", Toast.LENGTH_SHORT).show();
                    return;
                }
                hideKeyboard();
                mCodeText.setText(""); //TODO: proper clear this
                //send this to an intent
                Intent mInvitationIntent = new Intent(MainActivity.this, InvitationActivity.class);
                mInvitationIntent.putExtra(EXTRA_INVITATION_CODE, mRSVPCode);
                startActivityForResult(mInvitationIntent, INVITATION_REQUEST_CODE);
            }
        });

//        mWeddingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                if (mReservationCode.isEmpty()) {
////                    Toast.makeText(MainActivity.this, "Enter a Code", Toast.LENGTH_SHORT).show();
////                    return;
////                }
//                hideKeyboard();
//                getWeddingCall(mWeddingCode);
////                mReservationCode.setText(R.string.empty);
//
//            }
//        });
    }



 //   private void getWeddingCall(String mWeddingCode) {
//
//        //Retrofit Debugging (Currently not used)
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .build();
//
//        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BuildConfig.BASE_URL)
//                .addConverterFactory(JacksonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        ReservationService mReservationService = retrofit.create(ReservationService.class);
//
//        mReservationService.getWedding(mWeddingCode).enqueue(new Callback<WeddingData>() {
//            @Override
//            public void onResponse(@NonNull Call<WeddingData> call, @NonNull Response<WeddingData> response) {
//                mWeddingResponse = response.body();
//                //if api response is valid
//                if (mWeddingResponse != null) {
//                    Log.d("Wedding_Response_body", mWeddingResponse.toString());
//                    //Do things
//                }
//                else {
//                    Toast.makeText(MainActivity.this,"Unable to get information", Toast.LENGTH_LONG).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<WeddingData> call, Throwable t) {
//                Log.e(TAG, "Error getting info", t);
//                Toast.makeText(MainActivity.this,"Unable to get information", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    private void hideKeyboard() {
        View mainView = findViewById(android.R.id.content);
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(mainView.getWindowToken(), 0);
    }
}
