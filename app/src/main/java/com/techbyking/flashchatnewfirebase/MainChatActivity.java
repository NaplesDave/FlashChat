package com.techbyking.flashchatnewfirebase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainChatActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference   mDatabaseReference;  //For chat data
    private ChatListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        // TODO: Set up the display name and get the Firebase reference
        setupDisplayName(); // call the method to retrieve the locally stored username data


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        // Link the Views in the layout to the Java code
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);

        // TODO: Send the message when the "enter" button is pressed
        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // ENTER has been pressed on soft keyboard
                sendMessage(); // Call the send message method
                return true; // sends true back to indicate it has been handled.
            }
        });

        // TODO: Add an OnClickListener to the sendButton to send a message
        // They clicked the green Arrow image button on the screen to send the message
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    // TODO: Retrieve the display name from the Shared Preferences
    private void setupDisplayName(){
        // set up object to get data from CHAT_PREFS data stored item
        SharedPreferences prefs = getSharedPreferences(RegisterActivity.CHAT_PREFS, MODE_PRIVATE);
        // In that item look to get the name. If no name there return null
        mDisplayName = prefs.getString(RegisterActivity.DISPLAY_NAME_KEY, null);
        // check to see if a name is there or call it Anonymous
        if (mDisplayName == null) mDisplayName = "Anonymous";

    }


    private void sendMessage() {

        Log.d("FlashChat", "I sent something");
        // TODO: Grab the text the user typed in and push the message to Firebase
        String input = mInputText.getText().toString();
        if (!input.equals("")){ // If message is NOT empty send message
            // create the chat OBJECT to send to Firebase
            InstantMessage chat = new InstantMessage(input, mDisplayName);
            // Send the message to Firebase - child is the BRANCH in the database to store the messages
            // think JSON
            mDatabaseReference.child("messages").push().setValue(chat);
            // clear the message box since the message has just been sent.
            mInputText.setText("");


        }

    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.

        @Override
        public void onStart() {

        super.onStart();
        mAdapter = new ChatListAdapter(this, mDatabaseReference, mDisplayName);
        mChatListView.setAdapter(mAdapter);
        }

    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Firebase event listener on the adapter.
        mAdapter.cleanup();
    }

}
