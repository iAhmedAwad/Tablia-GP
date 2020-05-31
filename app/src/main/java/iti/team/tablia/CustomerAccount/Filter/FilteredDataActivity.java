package iti.team.tablia.CustomerAccount.Filter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.R;

public class FilteredDataActivity extends AppCompatActivity {

    public static final String INCOMING_CATEGORIES = "incoming_categories";
    private String choices;
    private FilteredDataActivityViewModel mModel;
    private ArrayList<String> arrayList;
    private RecyclerView xRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FilterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_data);
        xRecyclerView = findViewById(R.id.xRecyclerFiltered);
        mLayoutManager = new LinearLayoutManager(this);
        xRecyclerView.setHasFixedSize(true);
        xRecyclerView.setLayoutManager(mLayoutManager);
        init();
        initViewModel();
    }

    private void init() {
        Intent intent = getIntent();
        choices = intent.getStringExtra(INCOMING_CATEGORIES);
        if (choices != null) {
            String [] arr = choices.split(",");
            arrayList = new ArrayList<>();
            for (int i=0; i< arr.length; i++) {
                arrayList.add(arr[i]);
            }

        }
    }

    private void initViewModel() {
            mModel = ViewModelProviders.of(this).get(FilteredDataActivityViewModel.class);
            mModel.getFilteredData(arrayList).observe(FilteredDataActivity.this, new Observer<ArrayList<MenuPojo>>() {
                @Override
                public void onChanged(ArrayList<MenuPojo> menuPojos) {
                    mAdapter = new FilterAdapter(FilteredDataActivity.this, menuPojos);
                    xRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            });


    }
}
