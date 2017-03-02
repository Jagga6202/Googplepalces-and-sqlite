package com.example.ideafoundation.meetaapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideafoundation.meetaapp.model.DefaultNeed;
import com.example.ideafoundation.meetaapp.model.Need;
import com.example.ideafoundation.meetaapp.model.User;
import com.example.ideafoundation.meetaapp.util.LoginHandler;
import com.example.ideafoundation.meetaapp.util.NewDBHandler;
import com.example.ideafoundation.meetaapp.util.UserNeedHandler;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ideafoundation on 31/01/17.
 */

public class CompleteProfileActivity extends AppCompatActivity implements View.OnClickListener,Validator.ValidationListener{
    TextView name,email;
    Validator validator;
    Toolbar mToolbar;
     @NotEmpty
     @Length(min = 6)
     @Order(value = 6)
    EditText edit_name;

    @NotEmpty
    @Email
    EditText edit_email;

    Button submit;

    LoginHandler loginHandler;
    SharedPreferences sharedpreferences;
    UserNeedHandler handler;
    ArrayList<DefaultNeed> dataModels;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_profile_activity);
        validator=new Validator(this);
        edit_name=(EditText)findViewById(R.id.edit_name);
        edit_email=(EditText)findViewById(R.id.edit_email);

        dataModels=new ArrayList<>();
        dataModels.add(new DefaultNeed(0,"accounting"));
        dataModels.add(new DefaultNeed(1,"airport"));
        dataModels.add(new DefaultNeed(2,"amusement_park"));
        dataModels.add(new DefaultNeed(3,"aquarium"));
        dataModels.add(new DefaultNeed(4,"art_gallery"));
        dataModels.add(new DefaultNeed(5,"atm"));
        dataModels.add(new DefaultNeed(6,"bakery"));
        dataModels.add(new DefaultNeed(7,"bank"));
        dataModels.add(new DefaultNeed(8,"bar"));
        dataModels.add(new DefaultNeed(9,"beauty_salon"));
        dataModels.add(new DefaultNeed(10,"bicycle_store"));
        dataModels.add(new DefaultNeed(11,"book_store"));
        dataModels.add(new DefaultNeed(12,"bowling_alley"));
        dataModels.add(new DefaultNeed(13,"bus_station"));
        dataModels.add(new DefaultNeed(14,"cafe"));
        dataModels.add(new DefaultNeed(15,"campground"));
        dataModels.add(new DefaultNeed(16,"car_dealer"));
        dataModels.add(new DefaultNeed(17,"car_rental"));
        dataModels.add(new DefaultNeed(18,"car_wash"));
        dataModels.add(new DefaultNeed(19,"casino"));
        dataModels.add(new DefaultNeed(20,"cemetery"));
        dataModels.add(new DefaultNeed(21,"church"));
        dataModels.add(new DefaultNeed(22,"city_hall"));
        dataModels.add(new DefaultNeed(23,"clothing_store"));
        dataModels.add(new DefaultNeed(24,"convenience_store"));
        dataModels.add(new DefaultNeed(25,"courthouse"));
        dataModels.add(new DefaultNeed(26,"dentist"));
        dataModels.add(new DefaultNeed(27,"department_store"));
        dataModels.add(new DefaultNeed(28,"doctor"));
        dataModels.add(new DefaultNeed(29,"electrician"));
        dataModels.add(new DefaultNeed(30,"electronics_store"));
        dataModels.add(new DefaultNeed(31,"embassy"));
        dataModels.add(new DefaultNeed(32,"fire_station"));
        dataModels.add(new DefaultNeed(33,"florist"));
        dataModels.add(new DefaultNeed(34,"funeral_home"));
        dataModels.add(new DefaultNeed(35,"furniture_store"));
        dataModels.add(new DefaultNeed(36,"gas_station"));
        dataModels.add(new DefaultNeed(37,"gym"));
        dataModels.add(new DefaultNeed(38,"hair_care"));
        dataModels.add(new DefaultNeed(39,"hardware_store"));
        dataModels.add(new DefaultNeed(40,"hindu_temple"));
        dataModels.add(new DefaultNeed(41,"home_goods_store"));
        dataModels.add(new DefaultNeed(42,"hospital"));
        dataModels.add(new DefaultNeed(43,"insurance_agency"));
        dataModels.add(new DefaultNeed(44,"jewelry_store"));
        dataModels.add(new DefaultNeed(45,"laundry"));
        dataModels.add(new DefaultNeed(46,"lawyer"));
        dataModels.add(new DefaultNeed(47,"library"));
        dataModels.add(new DefaultNeed(48,"liquor_store"));
        dataModels.add(new DefaultNeed(49,"local_government_office"));
        dataModels.add(new DefaultNeed(50,"locksmith"));
        dataModels.add(new DefaultNeed(51,"lodging"));
        dataModels.add(new DefaultNeed(52,"meal_delivery"));
        dataModels.add(new DefaultNeed(53,"meal_takeaway"));
        dataModels.add(new DefaultNeed(54,"mosque"));
        dataModels.add(new DefaultNeed(55,"movie_rental"));
        dataModels.add(new DefaultNeed(56,"movie_theater"));
        dataModels.add(new DefaultNeed(57,"moving_company"));
        dataModels.add(new DefaultNeed(58,"museum"));
        dataModels.add(new DefaultNeed(59,"night_club"));
        dataModels.add(new DefaultNeed(60,"painter"));
        dataModels.add(new DefaultNeed(61,"park"));
        dataModels.add(new DefaultNeed(62,"parking"));
        dataModels.add(new DefaultNeed(63,"pet_store"));
        dataModels.add(new DefaultNeed(64,"pharmacy"));
        dataModels.add(new DefaultNeed(65,"physiotherapist"));
        dataModels.add(new DefaultNeed(66,"plumber"));
        dataModels.add(new DefaultNeed(67,"police"));
        dataModels.add(new DefaultNeed(68,"post_office"));
        dataModels.add(new DefaultNeed(69,"real_estate_agency"));
        dataModels.add(new DefaultNeed(70,"restaurant"));
        dataModels.add(new DefaultNeed(71,"roofing_contractor"));
        dataModels.add(new DefaultNeed(72,"rv_park"));
        dataModels.add(new DefaultNeed(73,"school"));
        dataModels.add(new DefaultNeed(74,"shoe_store"));
        dataModels.add(new DefaultNeed(75,"shopping_mall"));
        dataModels.add(new DefaultNeed(76,"spa"));
        dataModels.add(new DefaultNeed(77,"stadium"));
        dataModels.add(new DefaultNeed(78,"storage"));
        dataModels.add(new DefaultNeed(79,"store"));
        dataModels.add(new DefaultNeed(80,"subway_station"));
        dataModels.add(new DefaultNeed(81,"synagogue"));
        dataModels.add(new DefaultNeed(82,"taxi_stand"));
        dataModels.add(new DefaultNeed(83,"train_station"));
        dataModels.add(new DefaultNeed(84,"transit_station"));
        dataModels.add(new DefaultNeed(85,"travel_agency"));
        dataModels.add(new DefaultNeed(86,"university"));
        dataModels.add(new DefaultNeed(87,"veterinary_care"))	;
        dataModels.add(new DefaultNeed(88,"zoo"));
        TextInputLayout lNameLayout = (TextInputLayout) findViewById(R.id
                .fNameLayout);
        TextInputLayout emailLayout = (TextInputLayout) findViewById(R.id
                .femailLayout);
        sharedpreferences = getSharedPreferences("logindata", Context.MODE_PRIVATE);
        submit=(Button)findViewById(R.id.submit);
        loginHandler=new LoginHandler(this);
        handler=new UserNeedHandler(this);

        submit.setOnClickListener(this);
        //final String email = edit_email.getText().toString().trim();
        validator.setValidationListener(this);
        initToolbar();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.submit:
                validator.validate();
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
        loginHandler.insertEntry(new User(edit_name.getText().toString(),edit_email.getText().toString()));
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("username",edit_name.getText().toString().trim());
        editor.putString("useremail",edit_email.getText().toString().trim());
        editor.commit();
        Log.e("password",""+loginHandler.getSinlgeEntry(edit_name.getText().toString()));
        handler.allpalces(dataModels);
        Intent intent=new Intent(CompleteProfileActivity.this,CreateNeedActivity.class);
        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    void initToolbar(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        TextView header=(TextView)mToolbar.findViewById(R.id.header);
        header.setText("CompleteProfile");
    }
}
