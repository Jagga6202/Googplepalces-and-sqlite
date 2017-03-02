package com.example.ideafoundation.meetaapp.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ideafoundation on 06/02/17.
 */

public class Contact {

    //private variables
    int _id;
    String _name;
    String _phone_number;

    public Contact(String _name, String latLng, String firstWord) {
        this._name = _name;
        this.latLng = latLng;
        this.firstWord = firstWord;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    String firstWord;

    public Contact(String _name, String latLng) {
        this._name = _name;
        this.latLng = latLng;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }

    String latLng;

    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id, String name, String _phone_number){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
    }


    public Contact(String name){
        this._name = name;

    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber(){
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }
}
