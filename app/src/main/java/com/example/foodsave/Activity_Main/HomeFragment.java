package com.example.foodsave.Activity_Main;

import android.os.Bundle;
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

import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private List<save_item> item_list;
    private List<save_Type> type_list;
    private List<String> type_name_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //初始化数据
        initData();
        //创建继承自header的view实例
        View view = inflater.inflate(R.layout.header,container,false);
        //获取两个Spinner实例
        Spinner mStatusSpinner = view.findViewById(R.id.mStatus);
        Spinner mTypeSpinner = view.findViewById(R.id.mTypes);
        //获取recyclerview实例
        RecyclerView recyclerView = view.findViewById(R.id.item_list);
        //创建新的适配器，导入初始化的项目及类型数据
        SaveAdapter adapter = new SaveAdapter(getContext(), item_list, type_list);
        //设置recyclerview为线性布局，方向为竖直（VERTICAL）
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,true));
        //添加项目间分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(this.getContext()), DividerItemDecoration.VERTICAL));
        //导入适配器进行显示
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        //判断类型列表是否为空，不为空则创建ArrayAdapter并将该适配器赋给 按类型划分下拉框
        if (type_name_list != null) {
            ArrayAdapter<String> type_adapter = new ArrayAdapter<>(this.getContext(),
                    android.R.layout.simple_list_item_1, type_name_list);
            mTypeSpinner.setAdapter(type_adapter);
        }
        //设置两个Spinner下拉框的监听器
        mTypeSpinner.setOnItemSelectedListener(this);
        mStatusSpinner.setOnItemSelectedListener(this);
        return view;
    }

    public void initData(){
        //获取数据库实例
        AppDatabase database = Room.databaseBuilder(Objects.requireNonNull(getActivity()).getApplicationContext(),AppDatabase.class,getResources().getString(R.string.Database_Name)).build();
        //数据库查询操作
        try {
            //创建查询线程，输入值为“All_Type"
            SearchThread searchThread = new SearchThread(database, getResources().getString(R.string.All_Type));
            //启动线程
            searchThread.start();
            //等待线程返回
            searchThread.join();
            //从查询线程获取查询到的数据
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
