package com.example.foodsave.Activity_Main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.foodsave.database.AppDatabase;
import com.example.foodsave.R;
import com.example.foodsave.database.entity.save_Type;
import com.example.foodsave.database.entity.save_item;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private List<save_item> item_list;
    private List<save_Type> type_list;
    private List<String> type_name_list;
    private Spinner mTypeSpinner = null;
    private Spinner mStatusSpinner = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        initData();
        View view = inflater.inflate(R.layout.header,container,false);
        mStatusSpinner = (Spinner) view.findViewById(R.id.mStatus);
        mTypeSpinner = (Spinner) view.findViewById(R.id.mTypes);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.item_list);
        SaveAdapter adapter = new SaveAdapter(getContext(), item_list, type_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,true));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        if (type_name_list != null) {
            ArrayAdapter<String> type_adapter = new ArrayAdapter<String>(this.getContext(),
                    android.R.layout.simple_list_item_1, type_name_list);
            mTypeSpinner.setAdapter(type_adapter);
        }
        mTypeSpinner.setOnItemSelectedListener(this);
        mStatusSpinner.setOnItemSelectedListener(this);
        return view;
    }

    public void initData(){
        AppDatabase database = Room.databaseBuilder(getActivity().getApplicationContext(),AppDatabase.class,getResources().getString(R.string.Database_Name)).build();

        try {
            SearchThread searchThread = new SearchThread(database, getResources().getString(R.string.All_Type));
            searchThread.start();
            searchThread.join();
            item_list = searchThread.getItem_list();
            type_list = searchThread.getType_list();
            type_name_list = searchThread.getType_name_list();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
