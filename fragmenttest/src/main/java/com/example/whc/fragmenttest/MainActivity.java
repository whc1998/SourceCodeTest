package com.example.whc.fragmenttest;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private String TAG="TEst";

    private FirstFragment firstFragment;
    private SecondFragment secondFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentFragment=new Fragment();
        fragmentManager=getSupportFragmentManager();

        firstFragment=new FirstFragment();
        secondFragment=new SecondFragment();

        findViewById(R.id.button_chang1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showFragment(firstFragment);
            }
        });

        findViewById(R.id.button_chang2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(secondFragment);
            }
        });

    }

    private void showFragment(Fragment fg) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //判断之前是否添加过
        if(!fg.isAdded()) {
            fragmentTransaction.hide(currentFragment)
                    .add(R.id.framelayout, fg, fg.getClass().getName());
            Log.d(TAG, "showFragment: add"+fg.toString());
        } else {
            fragmentTransaction.hide(currentFragment)
                    .show(fg);
            Log.d(TAG, "showFragment: show"+fg.toString());
        }

        //记录当前显示的fragment
        currentFragment = fg;

        fragmentTransaction.commit();

    }
}
