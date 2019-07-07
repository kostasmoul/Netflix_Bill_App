package com.example.netflixbillsplitter;

import java.util.Date;

public class Payment {
    private int _id;
    private int _friendID;
    private String _month;
    private String _date;

    public Payment(){
    }

    public Payment(int id, int friendID, String month, String date){
        this._id = id;
        this._friendID = friendID;
        this._month = month;
        this._date = date;
    }

    public Payment(int friendID, String month, String date){
        this._friendID = friendID;
        this._month = month;
        this._date = date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_friendID() {
        return _friendID;
    }

    public void set_friendID(int _friendID) {
        this._friendID = _friendID;
    }

    public String get_month() {
        return _month;
    }

    public void set_month(String _month) {
        this._month = _month;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }
}
