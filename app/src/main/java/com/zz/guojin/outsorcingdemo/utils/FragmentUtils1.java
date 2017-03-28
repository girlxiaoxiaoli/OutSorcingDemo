package com.zz.guojin.outsorcingdemo.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

//import myapp.first.myapplication.R;

/**
 * Created by Administrator on 2016/10/7 0007.
 */

public class FragmentUtils1 {

    public static Fragment preFragment;
    public static Fragment preMainFragment;

    public static List<Fragment> listFragment = new ArrayList<>();

    public static Fragment getPreFragment() {
        return preFragment;
    }

    public static void setCurrentFragment(Fragment currentFragment){
        preFragment = currentFragment;
    }

    public static void show(FragmentManager fragmentManager, int containerID, Fragment showFragment, int flag){
//        if (!listFragment.contains(showFragment)){
//            listFragment.add(showFragment);
//        }
        if (preFragment == null){
            show(fragmentManager,containerID,showFragment,null,flag);
        } else{
            show(fragmentManager,containerID,showFragment,preFragment,flag);
        }
    }

    public static void show(FragmentManager fragmentManager, int containerID, Fragment showFragment, Fragment hideFragment, int flag){
        FragmentTransaction transition = fragmentManager.beginTransaction();
        switch (flag){
            case 0:
//                transition.setCustomAnimations(R.anim.fragment_slide_left_enter,
//                R.anim.fragment_slide_left_exit);

                break;
            case -1:

                break;
            case 1:

                break;
            case 2:
//                transition.setCustomAnimations(R.anim.anim_bottom_in,
//                    R.anim.anim_top_out);
                break;
            case 3:
//                transition.setCustomAnimations(R.anim.anim_top_in,
//                        R.anim.anim_bottom_out);
                break;
        }
        if (!showFragment.isAdded()){
            transition.add(containerID,showFragment);
//            transition.add(containerID,showFragment).addToBackStack(null);
        }

        if (hideFragment == null)
            transition.show(showFragment);
        else
            transition.hide(hideFragment).hide(preFragment).show(showFragment);
        transition.commitAllowingStateLoss();
        preFragment = showFragment;
    }


    public static void showFragment(FragmentManager fragmentManager, int containerID, Fragment showFragment, boolean isMainFragment){
        listFragment.add(showFragment);
        if (preMainFragment == null){
            showFragment(fragmentManager,containerID,showFragment,null,isMainFragment);
        }else{
            showFragment(fragmentManager,containerID,showFragment,preMainFragment,isMainFragment);
        }
    }

    public static void showFragment(FragmentManager fragmentManager, int containerID, Fragment showFragment, Fragment hideFragment, boolean isMainFragment){
        FragmentTransaction transition = fragmentManager.beginTransaction();
        if (!showFragment.isAdded()){
            transition.add(containerID,showFragment);
//            transition.add(containerID,showFragment).addToBackStack(null);
        }
        if (hideFragment == null){
            transition.show(showFragment);
        } else{
            transition.hide(preMainFragment).show(showFragment);
            if (preMainFragment.isVisible()){
                transition.hide(preMainFragment);
//                transition.hide(preFragment);
            }
            if (preFragment.isVisible()){
                transition.hide(preFragment);
            }
        }

        if (isMainFragment){
            transition.commit();
        }else {
            transition.addToBackStack(null).commit();
        }

        preMainFragment = showFragment;
    }

    public static void exitBackStack(FragmentManager fragmentManager){
        fragmentManager.popBackStack();
        listFragment.remove(listFragment.size()-1);
        preMainFragment = listFragment.get(listFragment.size()-1);

    }


}
