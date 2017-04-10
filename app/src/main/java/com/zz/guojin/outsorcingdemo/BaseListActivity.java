package com.zz.guojin.outsorcingdemo;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zz.guojin.outsorcingdemo.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseListActivity extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener {
    protected Context context;
    protected Unbinder unbinder;

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
    Handler myHandler;
    protected  int number=10;
    protected int currentIndex =0;

    @Override
    public void init() {
        context = this;
        unbinder = ButterKnife.bind(this);
        ActivityUtils.add(this);
        myHandler = new Handler();

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_base_list;
    }

    @Override
    public void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recycler.setItemAnimator(new DefaultItemAnimator());
        swipe.setColorSchemeColors(getResources().getColor(R.color.blue),getResources().getColor(R.color.colorTheme));
        swipe.setOnRefreshListener(this);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        swipe.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    @Override
    public void initData() {
    //加载数据
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
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                currentIndex=0;
                initData();
            }
        });

    }


}
