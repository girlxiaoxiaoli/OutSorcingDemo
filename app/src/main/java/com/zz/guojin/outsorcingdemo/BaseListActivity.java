package com.zz.guojin.outsorcingdemo;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseListActivity extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener{
    protected Context context;
    protected Unbinder unbinder;
    protected int number = 8;
    protected int currentIndex = 0;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.second_title)
    TextView secondTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.content_base_list)
    LinearLayout contentBaseList;

    @Override
    public void init() {
        context = this;
        unbinder = ButterKnife.bind(this);

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_base_list;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {


    }

    @Override
    public void initListener() {
        swipe.setOnRefreshListener(this);

    }

    @Override
    public void toStartProgressDialog() {

    }

    @Override
    public void onRefresh() {

    }


}
