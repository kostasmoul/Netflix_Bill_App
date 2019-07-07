package com.example.netflixbillsplitter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FriendHandler extends SQLiteOpenHelper {

    //DB-related constants (DB name, version, tables etc)
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "netflixSplitterDB.db";
    public static final String TABLE_FRIENDS = "friends";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FRIENDNAME = "friend_name";
    public static final String TABLE_PAYMENTS = "payments";
    public static final String COLUMN_FRIENDID = "friend_id";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_DATE = "date";

    //Constructor
    public FriendHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    //Create the DB schema (table friends)

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FRIENDS_TABLE = "CREATE TABLE " +
                TABLE_FRIENDS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_FRIENDNAME + " TEXT" + ")";
        db.execSQL(CREATE_FRIENDS_TABLE);

        String CREATE_PAYMENTS_TABLE = "CREATE TABLE " +
                TABLE_PAYMENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_FRIENDID + " INTEGER," +
                COLUMN_MONTH +" TEXT," +
                COLUMN_DATE + " TEXT" + ")";
        db.execSQL(CREATE_PAYMENTS_TABLE);


    }

    //Update the DB schema: here I simply drop the table and recreate it
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENTS);
        onCreate(db);
    }

    //Method to add a friend in the DB
    public void addFriend(Friend friend) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FRIENDNAME, friend.get_friendName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_FRIENDS, null, values);
        db.close();
    }

    //Method to find a product in the DB based on its name
    public Friend findFriend(String friendname) {
        String query = "SELECT * FROM " + TABLE_FRIENDS + " WHERE " +
                COLUMN_FRIENDNAME + " = '" + friendname + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Friend friend = new Friend();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            friend.set_id(Integer.parseInt(cursor.getString(0)));
            friend.set_friendName(cursor.getString(1));
            cursor.close();
        } else {
            friend = null;
        }
        db.close();
        return friend;
    }

    public boolean deleteFriend(String friendname) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_FRIENDS + " WHERE " +
                COLUMN_FRIENDNAME + " = '" + friendname + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Friend friend = new Friend();
        if (cursor.moveToFirst()) {
            friend.set_id(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_FRIENDS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(friend.get_id()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public List<String> getNamesList(){
        String query = "SELECT "+ COLUMN_FRIENDNAME + " FROM "+ TABLE_FRIENDS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<String> names = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_FRIENDNAME));
                names.add(name);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return names;

    }

    public void addPayment(Payment payment){
        ContentValues values = new ContentValues();
        values.put(COLUMN_FRIENDID, payment.get_friendID());
        values.put(COLUMN_MONTH, payment.get_month());
        values.put(COLUMN_DATE, payment.get_date());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PAYMENTS, null, values);
        db.close();
    }

    public List<String> findPayments(int friendId) {
        String query = "SELECT "+ COLUMN_MONTH +", "+COLUMN_DATE + " FROM "+ TABLE_PAYMENTS + " WHERE " + COLUMN_FRIENDID + " = "+ friendId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<String> details = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                String month = cursor.getString(cursor.getColumnIndex(COLUMN_MONTH));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                String detail = month.concat(" at ").concat(date);
                details.add(detail);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return details;
    }

    public boolean payment_exists(int friendId, String my_month){
        String query = "SELECT * FROM " + TABLE_PAYMENTS + " WHERE " +
                COLUMN_FRIENDID + " = " + friendId +" AND " + COLUMN_MONTH + " = '" + my_month + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() == 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean name_exists(String name){
        String query = "SELECT * FROM " + TABLE_FRIENDS + " WHERE " +
                 COLUMN_FRIENDNAME + " = '" + name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() == 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean deletePayments(int friendId){
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_PAYMENTS + " WHERE " +
                COLUMN_FRIENDID + " = " + friendId ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Payment payment = new Payment();
        if (cursor.moveToFirst()){
            do{
                payment.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                payment.set_friendID(cursor.getInt(cursor.getColumnIndex(COLUMN_FRIENDID)));
                payment.set_month(cursor.getString(cursor.getColumnIndex(COLUMN_MONTH)));
                payment.set_date(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                db.delete(TABLE_PAYMENTS, COLUMN_ID + " = ?",
                        new String[] { String.valueOf(payment.get_id()) });
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }

    public boolean deletePayment(int friendId, String month, String date){
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_PAYMENTS + " WHERE " +
                COLUMN_FRIENDID + " = " + friendId +" AND " + COLUMN_MONTH + " = '" + month + "'" + " AND " + COLUMN_DATE + " = '" + date + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Payment payment = new Payment();
        if (cursor.moveToFirst()){
            do{
                payment.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                payment.set_friendID(cursor.getInt(cursor.getColumnIndex(COLUMN_FRIENDID)));
                payment.set_month(cursor.getString(cursor.getColumnIndex(COLUMN_MONTH)));
                payment.set_date(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                db.delete(TABLE_PAYMENTS, COLUMN_ID + " = ?",
                        new String[] { String.valueOf(payment.get_id()) });
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }




}
