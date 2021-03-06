package com.zz.guojin.outsorcingdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.zz.guojin.outsorcingdemo.utils.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    protected Context ctx;
    protected RequestQueue queue;
    protected ProgressDialog pDialog;
    protected Request<String> request;
    protected Unbinder unbinder;
    protected String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  //控制屏幕的方向
        ActivityUtils.add(this);
        setContentView(getLayoutResId());
        unbinder = ButterKnife.bind(this);
        ctx = this;
        queue = NoHttp.newRequestQueue();
        init();
        initView();
        initData();
        initListener();

    }

    public abstract void init();

    public abstract int getLayoutResId();

    public abstract void initView();

    public abstract void initData();

    public abstract void initListener();

    public void initToolbar(Toolbar toolbar, String title) {
        initToolbar(toolbar, title, null, false);
    }

    public void initToolbar(Toolbar toolbar, String title, boolean isGoback) {
        initToolbar(toolbar, title, null, isGoback);

    }

    public void initToolbar(Toolbar toolbar, String title, String secondTitle, boolean isGoBack) {
        ImageView goback = (ImageView) toolbar.findViewById(R.id.img_back);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.title);
        TextView tvSecond = (TextView) toolbar.findViewById(R.id.second_title);
        tvTitle.setText(title);
        if (isGoBack) {
            goback.setVisibility(View.VISIBLE);
        } else {
            goback.setVisibility(View.GONE);
        }
        if (secondTitle == null) {
            tvSecond.setVisibility(View.GONE);
        } else {
            tvSecond.setVisibility(View.VISIBLE);
            tvSecond.setText(secondTitle);
        }

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    /**
     * @param tag           标记
     * @param url
     * @param params
     * @param isCache
     * @param requestMethod 0.get  1.post
     * @param what
     */
    public void requestNoHttpURL(String tag, String url,
                                 Map<String, Object> params,
                                 Boolean isCache,
                                 int requestMethod,
                                 int what) {
        if (requestMethod == 0) {
            request = NoHttp.createStringRequest(url, RequestMethod.GET);

        } else {
            request = NoHttp.createStringRequest(url, RequestMethod.POST);
        }
        request.setCancelSign(tag);
        request.setCacheKey(tag);
        if (isCache) {
            request.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        } else {
            request.setCacheMode(CacheMode.DEFAULT);
        }

        Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if (entry.getValue() instanceof String) {
                request.add(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue() instanceof File) {
                request.add(entry.getKey(), (File) entry.getValue());
            } else if (entry.getValue() instanceof Boolean) {
                request.add(entry.getKey(), (Boolean) entry.getValue());

            } else if (entry.getValue() instanceof Integer) {
                request.add(entry.getKey(), (Integer) entry.getValue());
            }

        }
        queue.add(what, request, responseListener);

    }

    OnResponseListener<String> responseListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            toStartProgressDialog();
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            doWhatForRequestResult(what, response);
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            Logger.e(getClass().getName() + "--onFailed---" + response.get());
        }

        @Override
        public void onFinish(int what) {
            stopProessDialog();
        }
    };

    public void doWhatForRequestResult(int what, Response<String> response) {
        String info = response.get();
        try {
            JSONObject jsonObject = new JSONObject(info);
            String error = jsonObject.getString("error");
            switch (error) {
                case "0":
                    //请求错误
                    String msg = jsonObject.getString("msg");
                    Logger.e(TAG + msg);

                    break;
                case "1":
//                        请求成功

                    break;
                case "3":
                    //系统故障
                    String msg1 = jsonObject.getString("msg");
                    Logger.e(TAG + msg1);

                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public abstract void toStartProgressDialog();

    public void toStartProgressDialog(boolean isLoaading) {
        if (isLoaading) {
            if (pDialog == null) {
                pDialog = ProgressDialog.show(ctx, null, "Loading...");
            }
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.isShowing();
            }
        }
    }

    public void stopProessDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.remove(this);
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.cancel();
            pDialog = null;
        }
        if (queue != null) {
            request.cancelBySign(getClass().getName());
            queue.cancelBySign(getClass().getName());
            queue = null;
        }
        unbinder.unbind();

    }
}
