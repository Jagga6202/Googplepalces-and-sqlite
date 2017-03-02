package com.example.ideafoundation.meetaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ideafoundation.meetaapp.Adapter.ContactAdapter;
import com.example.ideafoundation.meetaapp.model.Contact;
import com.example.ideafoundation.meetaapp.util.NewDBHandler;

import java.util.ArrayList;

/**
 * Created by ideafoundation on 20/02/17.
 */

public class Menu2 extends Fragment {

    private View view;
    TextView select;
    //DatabaseHandler db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        view=inflater.inflate(R.layout.fragment_menu2, container, false);
      select=(TextView)view.findViewById(R.id.select_fragment);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles

    }
}
