package com.example.ideafoundation.meetaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ideafoundation.meetaapp.Adapter.ContactAdapter;
import com.example.ideafoundation.meetaapp.model.Contact;
import com.example.ideafoundation.meetaapp.util.DatabaseHandler;
import com.example.ideafoundation.meetaapp.util.NewDBHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ideafoundation on 03/02/17.
 */

public class Menu1 extends Fragment {
ListView contactList;
    private View view;
    //DatabaseHandler db;
    NewDBHandler ndb;
    ContactAdapter contactAdapter;
    ArrayList<Contact> contacts;
    EditText search_id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        view=inflater.inflate(R.layout.fragment_menu_1, container, false);
        contactList=(ListView)view.findViewById(R.id.contactList);
        search_id=(EditText)view.findViewById(R.id.search_id);
        ndb = new NewDBHandler(getActivity());
        contacts=new ArrayList<>();
        contacts = ndb.getAllContacts();


        for (Contact cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName()+",lat"+cn.getLatLng();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        contactAdapter=new ContactAdapter(contacts,getActivity());
        contactList.setAdapter(contactAdapter);
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),ShowMapActivity.class);
                intent.putExtra("list",contacts.get(position).getLatLng());
                intent.putExtra("title",contacts.get(position).getName());
                Log.e("values",""+contacts.get(position).getFirstWord());
                startActivity(intent);
            }
        });
        search_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = search_id.getText().toString().toLowerCase(Locale.getDefault());
                contactAdapter.filter(text);
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles

    }
}
