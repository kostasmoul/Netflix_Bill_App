package com.example.netflixbillsplitter;

public class Friend {
    private int _id;
    private String _friendName;

    public Friend(){}

    public Friend(int id, String friendName){
        this._id = id;
        this._friendName = friendName;
    }

    public Friend(String friendName){
        this._friendName = friendName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_friendName() {
        return _friendName;
    }

    public void set_friendName(String _friendName) {
        this._friendName = _friendName;
    }
}
