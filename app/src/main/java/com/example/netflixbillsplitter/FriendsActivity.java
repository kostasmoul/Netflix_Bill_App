package com.example.netflixbillsplitter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FriendsActivity extends AppCompatActivity {
    ListView friendNamesList;
    List<String> dbNamesList;
    public static final int ADD_FRIEND_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);


        friendNamesList = findViewById(R.id.allFriends);
        final FriendHandler fHandler = new FriendHandler(this, null, null, 1);
        dbNamesList = fHandler.getNamesList();

       // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
        //        this,
        //        android.R.layout.simple_list_item_1, dbNamesList );
        CustomListAdapter whatever = new CustomListAdapter(this, dbNamesList);
        friendNamesList.setAdapter(whatever);

        friendNamesList.setClickable(true);
        friendNamesList.setLongClickable(true);
        friendNamesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nameTextField = (TextView) view.findViewById(R.id.nameTextViewID);
                String friend_name = nameTextField.getText().toString();
                Intent intent = new Intent(FriendsActivity.this, FriendDetailsActivity.class);
                intent.putExtra("EXTRA_FRIEND_NAME", friend_name);
                startActivity(intent);


            }
        });
        friendNamesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nameTextField = (TextView) view.findViewById(R.id.nameTextViewID);
                final String friend_name = nameTextField.getText().toString();

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        FriendsActivity.this);
                alert.setTitle("ALERT!");
                alert.setMessage("DELETE FRIEND");
                alert.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Friend my_friend = fHandler.findFriend(friend_name);
                        fHandler.deletePayments(my_friend.get_id());
                        fHandler.deleteFriend(friend_name);

                        dialog.dismiss();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();

                return true;
            }
        });



    }


    public void goToAddFriend(View view) {
        Intent intent = new Intent(FriendsActivity.this, AddFriendActivity.class);
        startActivityForResult(intent, ADD_FRIEND_REQUEST);
        //startActivity(intent);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == ADD_FRIEND_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                setContentView(R.layout.activity_friends);
                friendNamesList = findViewById(R.id.allFriends);
                final FriendHandler fHandler = new FriendHandler(this, null, null, 1);
                dbNamesList = fHandler.getNamesList();

                //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                 //       this,
                 //       android.R.layout.simple_list_item_1, dbNamesList );
                CustomListAdapter whatever = new CustomListAdapter(this, dbNamesList);
                friendNamesList.setAdapter(whatever);
                friendNamesList.setClickable(true);
                friendNamesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView nameTextField = (TextView) view.findViewById(R.id.nameTextViewID);
                        String friend_name = nameTextField.getText().toString();
                        Intent intent = new Intent(FriendsActivity.this, FriendDetailsActivity.class);
                        intent.putExtra("EXTRA_FRIEND_NAME", friend_name);
                        startActivity(intent);

                    }
                });

                friendNamesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView nameTextField = (TextView) view.findViewById(R.id.nameTextViewID);
                        final String friend_name = nameTextField.getText().toString();

                        AlertDialog.Builder alert = new AlertDialog.Builder(
                                FriendsActivity.this);
                        alert.setTitle("ALERT!");
                        alert.setMessage("DELETE FRIEND");
                        alert.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Friend my_friend = fHandler.findFriend(friend_name);
                                fHandler.deletePayments(my_friend.get_id());
                                fHandler.deleteFriend(friend_name);

                                dialog.dismiss();
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });
                        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });

                        alert.show();

                        return true;
                    }
                });


            }
        }
    }
}
