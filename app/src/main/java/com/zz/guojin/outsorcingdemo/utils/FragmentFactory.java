package com.zz.guojin.outsorcingdemo.utils;

import android.support.v4.app.Fragment;

import com.yanzhenjie.nohttp.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xi on 2016/7/5.
 * 生产Fragment的工厂类
 */
public class FragmentFactory {

    public static Map<Integer, Fragment> getFragmentMap() {
        return fragmentMap;
    }

    private static Map<Integer, Fragment> fragmentMap = new HashMap<>();

    private static FragmentFactory fragmentFactory = null;
    public static FragmentFactory getInstance(){
        if (fragmentFactory == null){
            fragmentFactory = new FragmentFactory();
        }
        return fragmentFactory;
    }

    /***
     * 生产Fragment
     *
     * @param position
     * @return
     */
    public static Fragment create(int position,Fragment fragment) {
        // 先在缓存中取
        if (fragmentMap.get(position) == null){
            Logger.e("position ："+position+" 工厂里面打印的  当前为空");
        }
        fragment = fragmentMap.get(position);
        //取不到 就重新创建
        if (fragment == null) {
//            if (position == 0) {
//                fragment = new Fragment1();
//            } else if (position == 1) {
//                fragment = new Fragment2();
//            } else if (position == 2) {
//                fragment = new Fragment3();
//            } else if (position == 3) {
//                fragment = new Fragment4();
//            } else if (position == 4){
//                fragment = new Fragment5();
//            }
            // 缓存Fragment对象
            if (fragment != null) {
                fragmentMap.put(position, fragment);
            }
        }
        return fragment;
    }
}
