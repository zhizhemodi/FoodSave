package com.example.foodsave.Activity_Main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsave.R;
import com.example.foodsave.database.entity.save_Type;
import com.example.foodsave.database.entity.save_item;

import java.sql.Date;
import java.util.List;

/*
 * 类介绍：主界面适配器
 * 数据描述：
 * @mContext : 主界面给出的context
 * @save_List : 储物列表
 * @save_types: 储物类型列表
 */
public class SaveAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<save_item> save_List;
    private List<save_Type> save_types;

    public SaveAdapter(Context mContext, List<save_item> save_List, List<save_Type> save_types) {
        this.mContext = mContext;
        this.save_List = save_List;
        this.save_types = save_types;
    }

    //ItemView内部类
    public class ItemView extends RecyclerView.ViewHolder{
        private ImageView type_img;
        private TextView name;
        private TextView else_day;
        private save_item item_row;
        public ItemView(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Action
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item,parent,false);
        ItemView item = new ItemView(itemView);
        item.name = (TextView) itemView.findViewById(R.id.item_name);
        item.else_day = (TextView) itemView.findViewById(R.id.left_num);
        item.type_img = (ImageView) itemView.findViewById(R.id.type_pic);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        ItemView item = (ItemView) holder;
        //获取当前位置的储物实例
        save_item snow = save_List.get(position);
        //获取当前储物名称并显示
        item.name.setText(snow.getName());
        String print_left = null;
        //假如剩余时间小于0，在要显示的字符串前添加“过期 ”
        if (snow.getLeft_time() < 0) {
            print_left = "过期 ";
            print_left = print_left + LongToString(snow.getLeft_time());
        }
        else{
            print_left = LongToString(snow.getLeft_time());
        }
        //设置显示
        item.else_day.setText(print_left);
    }

    //将Long类型数据转换为时间
    private String LongToString(Long time){
        time = Math.abs(time / 1000);
        if (time < 60){
            return time + " 秒";
        }
        else if (time / 60 < 60){
            return time / 60 + " 分";
        }
        else if (time / 3600 < 24){
            return time / 3600 + " 小时";
        }
        else if (time / 86400 < 30) {
            return time / 86400 + " 天";
        }
        else if (time / 2592000 < 12){
            return time / 2592000 + " 月";
        }
        else{
            return  time / 31104000 + " 年";
        }
    }

    @Override
    public int getItemCount() {
        return save_List.size();
    }
}
