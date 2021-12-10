package com.example.foodsave.Activity_Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.foodsave.database.AppDatabase;
import com.example.foodsave.R;
import com.example.foodsave.database.entity.save_Type;
import com.example.foodsave.database.entity.save_item;

import java.util.List;

public class HomeFragment extends Fragment {
    private List<save_item> item_list;
    private List<save_Type> type_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        initdata();
        View view = inflater.inflate(R.layout.header,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.item_list);
        SaveAdapter adapter = new SaveAdapter(getActivity(), item_list, type_list);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void initdata(){
        AppDatabase database = Room.databaseBuilder(getActivity().getApplicationContext(),AppDatabase.class,getResources().getString(R.string.Database_Name)).build();
        try {
            SearchThread searchThread = new SearchThread(database, getResources().getString(R.string.All_Type));
            searchThread.start();
            searchThread.join();
            item_list = searchThread.getItem_list();
            type_list = searchThread.getType_list();
        }
        catch (Exception e){
        }
    }
}
