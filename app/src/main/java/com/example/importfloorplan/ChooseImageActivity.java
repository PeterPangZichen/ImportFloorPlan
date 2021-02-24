package com.example.importfloorplan;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ChooseImageActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private RecycleViewAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);

        //List to save data
        mList = new ArrayList();
        mRecycleView = findViewById(R.id.rv_list);

        //Initialize data (For test)
        initData(mList);
        //Create layout manager
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //Create adapter
        mAdapter = new RecycleViewAdapter(mList);
        //Set layout manager
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        //Set adapter
        mRecycleView.setAdapter(mAdapter);
    }

    public void initData(List list) {
        for (int i = 1; i <= 40; i++) {
            list.add("Data " + i);
        }
    }
}