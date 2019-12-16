package com.nathan.unionapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //rsvps/GAUT63EA
    //rsvps/DANI93E1
    //wedding/COST29BB

    //TODO: CREATE AN ARRAY OF PRESENT QUESTIONS
    //TODO: CREATE AN ARRAY OF NULL QUESTIONS
    //TODO: if the user and wedding exists tell them if they are in the wedding party
    //TODO: VERIFY INFO AND SAVE
    //TODO: FANCY INFO FOR WEDDING: MAP INTENT, EMAIL ORGANIZER, DATE COUNTDOWN

    private EditText mCodeText;
    private Button mRSVPButton;
    private String mRSVPCode;

    public static final String EXTRA_INVITATION_CODE = "com.nathan.unionapp.invitationcode";
    private static final int INVITATION_REQUEST_CODE = 0;

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
                mCodeText.setText(""); //TODO: proper clear this & have a listener make it to upper
                //send this to an intent
                Intent mInvitationIntent = new Intent(MainActivity.this, InvitationActivity.class);
                mInvitationIntent.putExtra(EXTRA_INVITATION_CODE, mRSVPCode);
                startActivityForResult(mInvitationIntent, INVITATION_REQUEST_CODE);
            }
        });
    }

    private void hideKeyboard() {
        View mainView = findViewById(android.R.id.content);
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(mainView.getWindowToken(), 0);
    }
}
