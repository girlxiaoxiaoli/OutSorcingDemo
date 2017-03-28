package com.zz.guojin.outsorcingdemo.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/28.
 */

public class FragmentUtils {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private void switchFragment(Fragment fragment, FragmentManager manager,int contenerId){
        FragmentTransaction transaction = manager.beginTransaction();
        //先隐藏已经显示的fragment
        for (int i = 0; i < fragments.size(); i++) {
            if(!fragments.get(i).isHidden()){
                transaction.hide(fragments.get(i));
            }
        }
        //判断要显示的Fragment是否已经添加，添加过的话直接显示，否则先添加再显示
        if (!fragment.isAdded()) {
            transaction.add(contenerId, fragment).show(fragment).commit();
        } else {
            transaction.show(fragment).commit();
        }

    }
}
