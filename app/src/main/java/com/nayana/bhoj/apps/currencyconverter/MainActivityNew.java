package com.nayana.bhoj.apps.currencyconverter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by supuser on 16/2/17.
 */

public class MainActivityNew extends AppCompatActivity implements AbsListView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_n);

        //getSupportActionBar().hide();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setElevation(0);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        ArrayAdapter<String> demoAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);

        for (Demo demo : Demo.values()) {
            demoAdapter.add(getString(demo.titleResId));
        }

        listView.setAdapter(demoAdapter);

        //Demo demo = Demo.values()[12];
        //demo.startActivity(this);
        //finish();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Demo demo = Demo.values()[position];
        //demo.startActivity(this);
    }

}
