package com.example.netflixbillsplitter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddPaymentActivity extends AppCompatActivity {
    List<String> dbNamesList;
    RadioGroup friendsGroup;
    Spinner monthSpinner;
    List<String> months =  new ArrayList<String>();
    FriendHandler fHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        fHandler = new FriendHandler(this, null, null, 1);

        friendsGroup = findViewById(R.id.friendsGroup);
        monthSpinner = findViewById(R.id.monthSpinner);
        dbNamesList = fHandler.getNamesList();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        months.add("January " + Integer.toString(year));
        months.add("February " + Integer.toString(year));
        months.add("March " + Integer.toString(year));
        months.add("April " + Integer.toString(year));
        months.add("May " + Integer.toString(year));
        months.add("June " + Integer.toString(year));
        months.add("July " + Integer.toString(year));
        months.add("August " + Integer.toString(year));
        months.add("September " + Integer.toString(year));
        months.add("October " + Integer.toString(year));
        months.add("November " + Integer.toString(year));
        months.add("December " + Integer.toString(year));


        for( String name : dbNamesList){
            RadioButton rdbtn = new RadioButton(this);
            rdbtn.setId(View.generateViewId());
            rdbtn.setText(name);
            rdbtn.setTextSize(24);

            rdbtn.setGravity(Gravity.CENTER_HORIZONTAL);
            friendsGroup.addView(rdbtn);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, months);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);


    }
    public void addPaymentDB(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.friendsGroup);

        if (radioGroup.getCheckedRadioButtonId() == -1)
        {
            Context context = getApplicationContext();
            CharSequence text = "SELECT A FRIEND!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else
        {
            // one of the radio buttons is checked
            int checkedId = friendsGroup.getCheckedRadioButtonId();
            RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
            String selected_name = checkedRadioButton.getText().toString();
            Friend my_friend = fHandler.findFriend(selected_name);


            String selected_month = monthSpinner.getSelectedItem().toString();

            String current_date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            //check if payment for that month already exists in the database for that user
            boolean exists;
            exists = fHandler.payment_exists(my_friend.get_id(), selected_month);
            if(exists == true){
                Context context = getApplicationContext();
                CharSequence text = "PAYMENT FOR THAT MONTH EXISTS!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }else{

                Payment my_payment = new Payment(my_friend.get_id(), selected_month, current_date);
                fHandler.addPayment(my_payment);
                setResult(MainMenuActivity.RESULT_OK);
                finish();
            }

        }




    }
}
