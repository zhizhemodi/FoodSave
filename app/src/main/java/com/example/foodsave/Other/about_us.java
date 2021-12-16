package com.example.foodsave.Other;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.foodsave.Activity_Main.HomeFragment;
import com.example.foodsave.Activity_Main.MainActivity;
import com.example.foodsave.Activity_Main.fragmentListener;
import com.example.foodsave.R;

public class about_us extends Fragment {

    private fragmentListener activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (fragmentListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.about_us,container,false);
        activity.showTitle(getString(R.string.about_us));
        TextView version = view.findViewById(R.id.app_version);
        version.setText(getVersion());
        Button back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content,new HomeFragment()).commit();
            }
        });
        return view;
    }

    public String getVersion(){
        PackageManager manager = this.getActivity().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getActivity().getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return this.getString(R.string.can_not_find_version_name);
        }
        String version = info.versionName;
        return this.getString(R.string.version_name) + version;
    }
}
