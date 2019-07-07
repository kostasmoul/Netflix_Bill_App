package com.example.netflixbillsplitter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {
    public static final int MAKE_PAYMENT_REQUEST = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        List<String> friends_that_paid = new ArrayList<>();
        ListView lv;
        List<String> friends = new ArrayList<>();
        String[] monthName = { "January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December" };

        TextView monthDisplay = findViewById(R.id.monthDisplay);
        Calendar cal = Calendar.getInstance();
        String month = monthName[cal.get(Calendar.MONTH)];
        int year = cal.get(Calendar.YEAR);
        monthDisplay.setText(month +" "+ year);

        String current_payment = month.concat(" ").concat(Integer.toString(year));
        FriendHandler fHandler = new FriendHandler(this, null, null, 1);
        friends = fHandler.getNamesList();
        for(String friend_name : friends){
            Friend my_friend = fHandler.findFriend(friend_name);
            if(fHandler.payment_exists(my_friend.get_id(), current_payment)){
                if(!friends_that_paid.contains(my_friend.get_friendName())){
                    friends_that_paid.add(my_friend.get_friendName());
                }
            }
        }

        lv = findViewById(R.id.friendsPaidList);

        CustomListAdapter whatever = new CustomListAdapter(this, friends_that_paid);

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
        //        this,
        //        android.R.layout.simple_list_item_1, friends_that_paid );
        //lv.setAdapter(arrayAdapter);
        lv.setAdapter(whatever);



    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> friends_that_paid = new ArrayList<>();
        ListView lv;
        List<String> friends = new ArrayList<>();
        String[] monthName = { "January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December" };

        TextView monthDisplay = findViewById(R.id.monthDisplay);
        Calendar cal = Calendar.getInstance();
        String month = monthName[cal.get(Calendar.MONTH)];
        int year = cal.get(Calendar.YEAR);
        monthDisplay.setText(month +" "+ year);

        String current_payment = month.concat(" ").concat(Integer.toString(year));
        FriendHandler fHandler = new FriendHandler(this, null, null, 1);
        friends = fHandler.getNamesList();
        for(String friend_name : friends){
            Friend my_friend = fHandler.findFriend(friend_name);
            if(fHandler.payment_exists(my_friend.get_id(), current_payment)){
                if(!friends_that_paid.contains(my_friend.get_friendName())){
                    friends_that_paid.add(my_friend.get_friendName());
                }
            }
        }

        lv = findViewById(R.id.friendsPaidList);
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
        //        this,
        //        android.R.layout.simple_list_item_1, friends_that_paid );
        CustomListAdapter whatever = new CustomListAdapter(this, friends_that_paid);
        lv.setAdapter(whatever);
    }

    public void showFriends(View view) {
        Intent intent = new Intent(MainMenuActivity.this, FriendsActivity.class);
        startActivity(intent);
    }

    public void makePayment(View view) {
        Intent intent = new Intent(MainMenuActivity.this, AddPaymentActivity.class);
        startActivityForResult(intent, MAKE_PAYMENT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == MAKE_PAYMENT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                setContentView(R.layout.activity_main_menu);

                List<String> friends_that_paid = new ArrayList<>();
                ListView lv;
                List<String> friends = new ArrayList<>();
                String[] monthName = { "January", "February", "March", "April", "May", "June", "July",
                        "August", "September", "October", "November", "December" };

                TextView monthDisplay = findViewById(R.id.monthDisplay);
                Calendar cal = Calendar.getInstance();
                String month = monthName[cal.get(Calendar.MONTH)];
                int year = cal.get(Calendar.YEAR);
                monthDisplay.setText(month +" "+ year);

                String current_payment = month.concat(" ").concat(Integer.toString(year));
                FriendHandler fHandler = new FriendHandler(this, null, null, 1);
                friends = fHandler.getNamesList();
                for(String friend_name : friends){
                    Friend my_friend = fHandler.findFriend(friend_name);
                    if(fHandler.payment_exists(my_friend.get_id(), current_payment)){
                        if(!friends_that_paid.contains(my_friend.get_friendName())){
                            friends_that_paid.add(my_friend.get_friendName());
                        }

                    }
                }

                lv = findViewById(R.id.friendsPaidList);
                //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                //        this,
                 //       android.R.layout.simple_list_item_1, friends_that_paid );
                CustomListAdapter whatever = new CustomListAdapter(this, friends_that_paid);
                lv.setAdapter(whatever);
            }
        }
    }
}
