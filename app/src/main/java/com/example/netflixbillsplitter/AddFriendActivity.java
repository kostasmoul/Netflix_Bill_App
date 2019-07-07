package com.example.netflixbillsplitter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddFriendActivity extends AppCompatActivity {
    EditText friendNameBox;
    public static final int ADD_FRIEND_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        friendNameBox = findViewById(R.id.friendName);

    }

    public void addNewFriend(View view) {
        FriendHandler fHandler = new FriendHandler(this, null, null, 1);
        String friendName = friendNameBox.getText().toString();
        if(!friendName.equals("") && fHandler.name_exists(friendName) == false){
            Friend myFriend = new Friend(friendName);
            fHandler.addFriend(myFriend);
            friendNameBox.setText("");
            setResult(FriendsActivity.RESULT_OK);
            finish();
        }else{
            Context context = getApplicationContext();
            CharSequence text = "NAME ALREADY EXISTS!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
}
