package com.example.foodsave.Activity_Main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.foodsave.database.AppDatabase;
import com.example.foodsave.R;
import com.example.foodsave.database.entity.save_Type;
import com.example.foodsave.database.entity.save_item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener, AdapterListener{
    private List<save_item> item_list;
    private List<save_Type> type_list;
    private List<String> type_name_list;
    private RecyclerView recyclerView;
    private fragmentListener activity;

    private TextView left_time;
    private TextView create_date;
    private TextView save_place;
    private SaveAdapter adapter;

    LocalBroadcastManager localBroadcastManager;
    IntentFilter intentFilter;
    BroadcastReceiver broadcastReceiver;

    //获取主Activity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (fragmentListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //初始化数据
        initData();
        //创建继承自header的view实例
        View view = inflater.inflate(R.layout.header,container,false);

        detailBoardCast();

        //设置主Activity的Title
        activity.showTitle(getString(R.string.main_activity_title));

        //获取三个TextView
        left_time = view.findViewById(R.id.bigger_num);
        create_date = view.findViewById(R.id.make_date);
        save_place = view.findViewById(R.id.save_place);

        //获取两个Spinner实例
        Spinner mStatusSpinner = view.findViewById(R.id.mStatus);
        Spinner mTypeSpinner = view.findViewById(R.id.mTypes);

        //获取recyclerview实例
        recyclerView = view.findViewById(R.id.item_list);

        //创建新的适配器，导入初始化的项目及类型数据
        adapter = new SaveAdapter(getContext(), item_list, type_list);

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
        if (item_list.size() > 0){
            detailSetter(item_list.get(0));
        }

        //设置两个Spinner下拉框的监听器
        mTypeSpinner.setOnItemSelectedListener(this);
        mStatusSpinner.setOnItemSelectedListener(this);
        return view;
    }

    public void initData(){
        //获取数据库实例
        AppDatabase database = Room.databaseBuilder(Objects.requireNonNull(getActivity()).getApplicationContext(),
                AppDatabase.class,getResources().getString(R.string.Database_Name)).build();
        //数据库查询操作
        try {
            String[] status = getResources().getStringArray(R.array.status);
            //创建查询线程，输入值为“All_Type"
            SearchThread searchThread = new SearchThread(database, getResources().getString(R.string.All_Type), status);
            //启动线程
            searchThread.start();
            //等待线程返回
            searchThread.join();
            //从查询线程获取查询到的数据
            item_list = searchThread.getItem_list();
            type_list = searchThread.getType_list();
            type_name_list = searchThread.getType_name_list();
            //在数组首位添加“全部类型”选项,这样初始显示为“全部类型”
            type_name_list.add(0,getString(R.string.ch_All_Type));

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
     * 选中监听器返回函数
     * 输入数据:
     * @adapterView: 事件发生的View，可通过该实例的getId()方法判断哪一个Spinner发生了选择事件
     * @view: 主界面窗口
     * @i: 被选中项的id
     * @l: 同i
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String get = null;
        String[] status = getResources().getStringArray(R.array.status);
        switch (adapterView.getId()){
            case R.id.mStatus:
                //TODO 按状态进行数据查询
                get = status[i]; //当前选中的状态
                if (get.equals(status[0])){
                    adapter = new SaveAdapter(getContext(), item_list, type_list);
                }
                else {
                    List<save_item> some = new ArrayList<save_item>();
                    for(int j = 0;j < item_list.size();j++){
                        if (item_list.get(j).getStatus().equals(get)){
                            some.add(item_list.get(j));
                        }
                    }
                    adapter = new SaveAdapter(getContext(), some, type_list);
                }
                recyclerView.setAdapter(adapter);
                break;
            case R.id.mTypes:
                get = type_name_list.get(i); //当前选中的类型
                //这里将ch_All_Type转换为All_Type进行数据搜索
                if (get.equals(getString(R.string.ch_All_Type))){
                    get = getString(R.string.All_Type);
                }
                AppDatabase database = Room.databaseBuilder(Objects.requireNonNull(getActivity()).getApplicationContext(),
                        AppDatabase.class,getResources().getString(R.string.Database_Name)).build();
                SearchThread searchThread = new SearchThread(database, get, status);
                searchThread.start();
                try {
                    searchThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                item_list = searchThread.getItem_list();
                //创建新的适配器，导入初始化的项目及类型数据
                adapter = new SaveAdapter(getContext(), item_list, type_list);
                //设置recyclerview为线性布局，方向为竖直（VERTICAL）
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,true));
                //导入适配器进行显示
                recyclerView.setAdapter(adapter);
                recyclerView.setNestedScrollingEnabled(false);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /*
     * 设置详细信息
     */
    @Override
    public void detailSetter(save_item selected) {
        String print_left = null;
        Long le = selected.getSave_Len() - selected.getLeft_time();
        if (le < 0) {
            print_left = "过期 ";
            print_left = print_left + SaveAdapter.LongToString(le);
        }
        else{
            print_left = SaveAdapter.LongToString(le);
        }
        left_time.setText(print_left);
        create_date.setText(selected.getCreate_Date().toString());
        save_place.setText(selected.getSave_Place());
    }

    /*
     * 注册获取详细信息的广播
     */
    public void detailBoardCast(){
        //广播接收
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intentFilter = new IntentFilter();
        intentFilter.addAction("Select_One");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int id = intent.getIntExtra("select_id", 0);
                detailSetter(item_list.get(id));
            }
        };
        localBroadcastManager.registerReceiver(broadcastReceiver,intentFilter);
    }
}
