package com.example.netflixbillsplitter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class FriendDetailsActivity extends AppCompatActivity {
    ListView lv;
    List<String> detailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);
        String friend_name = getIntent().getStringExtra("EXTRA_FRIEND_NAME");
        final FriendHandler fHandler = new FriendHandler(this, null, null, 1);
        final Friend my_friend = fHandler.findFriend(friend_name);
       detailsList = fHandler.findPayments(my_friend.get_id());


        lv = findViewById(R.id.detailListView);
       // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
        //        this,
         //       android.R.layout.simple_list_item_1, detailsList );
        CustomListAdapter whatever = new CustomListAdapter(this, detailsList);
        lv.setAdapter(whatever);

        lv.setLongClickable(true);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nameTextField = (TextView) view.findViewById(R.id.nameTextViewID);
                final String paymentString = nameTextField.getText().toString();

                //we split the string to get the month and date alone because currently its "month at date" in the list
                String[] arr = paymentString.split(" ");
                final String my_month = arr[0].concat(" ").concat(arr[1]);
                final String my_date = arr[3];


                AlertDialog.Builder alert = new AlertDialog.Builder(
                        FriendDetailsActivity.this);
                alert.setTitle("ALERT!");
                alert.setMessage("DELETE PAYMENT RECORD");
                alert.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        fHandler.deletePayment(my_friend.get_id(), my_month, my_date);
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
