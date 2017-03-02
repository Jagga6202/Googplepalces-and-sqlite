package info.androidhive.expandablelistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by ideafoundation on 09/02/17.
 */

public class CreateNeedActivity extends Activity {
    ArrayList<Need> dataModels;
    HashMap<Need, List<String>> _listDataChild;
    private static NeedTableAdapter adapter;
    ListView listview;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        initview();
    }
    public void initview() {
        listview = (ListView) findViewById(R.id.listview);

        submit=(Button)findViewById(R.id.submit);
        dataModels = new ArrayList<Need>();
        dataModels.add(new Need("accounting"));
        dataModels.add(new Need("airport"));
        dataModels.add(new Need("amusement_park"));
        dataModels.add(new Need("aquarium"));
        dataModels.add(new Need("art_gallery"));
        dataModels.add(new Need("atm"));
        dataModels.add(new Need("bakery"));
        dataModels.add(new Need("bank"));
        dataModels.add(new Need("bar"));
        dataModels.add(new Need("beauty_salon"));
        dataModels.add(new Need("bicycle_store"));
        dataModels.add(new Need("book_store"));
        dataModels.add(new Need("bowling_alley"));
        dataModels.add(new Need("bus_station"));
        dataModels.add(new Need("cafe"));
        dataModels.add(new Need("campground"));
        dataModels.add(new Need("car_dealer"));
        dataModels.add(new Need("car_rental"));
        dataModels.add(new Need("car_wash"));
        dataModels.add(new Need("casino"));
        dataModels.add(new Need("cemetery"));
        dataModels.add(new Need("church"));
        dataModels.add(new Need("city_hall"));
        dataModels.add(new Need("clothing_store"));
        dataModels.add(new Need("convenience_store"));
        dataModels.add(new Need("courthouse"));
        dataModels.add(new Need("dentist"));
        dataModels.add(new Need("department_store"));
        dataModels.add(new Need("doctor"));
        dataModels.add(new Need("electrician"));
        dataModels.add(new Need("electronics_store"));
        dataModels.add(new Need("embassy"));
        dataModels.add(new Need("fire_station"));
        dataModels.add(new Need("florist"));
        dataModels.add(new Need("funeral_home"));
        dataModels.add(new Need("furniture_store"));
        dataModels.add(new Need("gas_station"));
        dataModels.add(new Need("gym"));
        dataModels.add(new Need("hair_care"));
        dataModels.add(new Need("hardware_store"));
        dataModels.add(new Need("hindu_temple"));
        dataModels.add(new Need("home_goods_store"));
        dataModels.add(new Need("hospital"));
        dataModels.add(new Need("insurance_agency"));
        dataModels.add(new Need("jewelry_store"));
        dataModels.add(new Need("laundry"));
        dataModels.add(new Need("lawyer"));
        dataModels.add(new Need("library"));
        dataModels.add(new Need("liquor_store"));
        dataModels.add(new Need("local_government_office"));
        dataModels.add(new Need("locksmith"));
        dataModels.add(new Need("lodging"));
        dataModels.add(new Need("meal_delivery"));
        dataModels.add(new Need("meal_takeaway"));
        dataModels.add(new Need("mosque"));
        dataModels.add(new Need("movie_rental"));
        dataModels.add(new Need("movie_theater"));
        dataModels.add(new Need("moving_company"));
        dataModels.add(new Need("museum"));
        dataModels.add(new Need("night_club"));
        dataModels.add(new Need("painter"));
        dataModels.add(new Need("park"));
        dataModels.add(new Need("parking"));
        dataModels.add(new Need("pet_store"));
        dataModels.add(new Need("pharmacy"));
        dataModels.add(new Need("physiotherapist"));
        dataModels.add(new Need("plumber"));
        dataModels.add(new Need("police"));
        dataModels.add(new Need("post_office"));
        dataModels.add(new Need("real_estate_agency"));
        dataModels.add(new Need("restaurant"));
        dataModels.add(new Need("roofing_contractor"));
        dataModels.add(new Need("rv_park"));
        dataModels.add(new Need("school"));
        dataModels.add(new Need("shoe_store"));
        dataModels.add(new Need("shopping_mall"));
        dataModels.add(new Need("spa"));
        dataModels.add(new Need("stadium"));
        dataModels.add(new Need("storage"));
        dataModels.add(new Need("store"));
        dataModels.add(new Need("subway_station"));
        dataModels.add(new Need("synagogue"));
        dataModels.add(new Need("taxi_stand"));
        dataModels.add(new Need("train_station"));
        dataModels.add(new Need("transit_station"));
        dataModels.add(new Need("travel_agency"));
        dataModels.add(new Need("university"));
        dataModels.add(new Need("veterinary_care"));
        dataModels.add(new Need("zoo"));
        adapter = new NeedTableAdapter(dataModels, this);
        listview.setAdapter(adapter);
       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ArrayList<Need> actorList = ((NeedTableAdapter)listview.getAdapter()).getSelectActorList();
               Toast.makeText(CreateNeedActivity.this,""+actorList.size(),Toast.LENGTH_LONG).show();
               Intent intent=new Intent(CreateNeedActivity.this, UserNeed.class);
               intent.putExtra("list",actorList);
               startActivity(intent);
           }
       });

    }

}
