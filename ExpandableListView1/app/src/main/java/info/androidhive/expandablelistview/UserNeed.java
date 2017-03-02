package info.androidhive.expandablelistview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ideafoundation on 10/02/17.
 */

public class UserNeed extends Activity {

    ArrayList<Need> dataModels;
    ExpandableListView expListView;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataModels=(ArrayList<Need>)getIntent().getSerializableExtra("list");
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListView.setAdapter(new NewExpandleAdapter(this,dataModels,null));
    }
}
