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

import java.util.List;

/*
 * 类介绍：主界面适配器
 * 数据描述：
 * @mContext : 主界面给出的context
 * @save_List : 储物列表
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

        public ItemView(@NonNull View itemView) {
            super(itemView);
            this.type_img = (ImageView) itemView.findViewById(R.id.type_pic);
            this.name = (TextView) itemView.findViewById(R.id.item_name);
            this.else_day = (TextView) itemView.findViewById(R.id.left_num);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item,null);
        ItemView item = new ItemView(itemView);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){

    }

    @Override
    public int getItemCount() {
        if (save_List == null) {
            return 0;
        }

        return save_List.size();
    }
}
