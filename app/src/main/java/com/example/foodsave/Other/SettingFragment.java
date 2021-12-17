package com.example.foodsave.Other;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.foodsave.Activity_Main.HomeFragment;
import com.example.foodsave.Activity_Main.fragmentListener;
import com.example.foodsave.R;

public class SettingFragment extends Fragment{

    private fragmentListener activity;
    private Button small_button;
    private Button middle_button;
    private Button big_button;
    private Button huge_button;
    private Button back;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (fragmentListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.setting, container, false);
        activity.showTitle(getString(R.string.setting));
        small_button = (Button) view.findViewById(R.id.small_button);
        middle_button = (Button) view.findViewById(R.id.middle_button);
        big_button = (Button) view.findViewById(R.id.big_button);
        huge_button = (Button) view.findViewById(R.id.huge_button);
        back = view.findViewById(R.id.setting_back);
        buttonListener();
        return view;
    }

    public void buttonListener(){
        small_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setTextTheme(R.style.Small_textSize);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content,new SettingFragment()).commit();
            }
        });
        middle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setTextTheme(R.style.Middle_textSize);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content,new SettingFragment()).commit();
            }
        });
        big_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setTextTheme(R.style.Big_textSize);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content,new SettingFragment()).commit();
            }
        });
        huge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setTextTheme(R.style.Huge_textSize);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content,new SettingFragment()).commit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content,new HomeFragment()).commit();
            }
        });
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.i("set","stop");
    }
}
