package com.zz.guojin.outsorcingdemo.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.rest.Response;
import com.zz.guojin.outsorcingdemo.BaseActivity;
import com.zz.guojin.outsorcingdemo.R;
import com.zz.guojin.outsorcingdemo.utils.ActivityUtils;
import com.zz.guojin.outsorcingdemo.utils.ResultCode;
import com.zz.guojin.outsorcingdemo.utils.ToastUtils;
import com.zz.guojin.outsorcingdemo.utils.URLConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();
    private Context ctx;
    private Toolbar toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.forget_login)
    TextView forgetLogin;
    @BindView(R.id.regist_login)
    TextView registLogin;
    @BindView(R.id.login_login)
    TextView loginLogin;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void init() {
        ctx = this;
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public void initData() {
        initToolbar(toolbar, "登陆");
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.forget_login, R.id.regist_login, R.id.login_login})
    public void onClick(View view) {
     int  id = view.getId();
        switch (id) {
            case R.id.forget_login:
                //忘记密码
                Logger.d(TAG + "忘记密码");
                break;
            case R.id.regist_login:
                //注册
                Logger.d(TAG + "注册");
                break;
            case R.id.login_login:
                Logger.d(TAG + "登陆");
                if (TextUtils.isEmpty(etName.getText().toString().trim())) {
                    ToastUtils.showTextToast(ctx, "用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(etPwd.getText().toString().trim())) {
                    ToastUtils.showTextToast(ctx, "密码不能为空");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("", etName.getText().toString().trim());
                map.put("", etPwd.getText().toString().trim());
                requestNoHttpURL(TAG, URLConst.LOGIN, map, false, 1, 101);
                break;
        }

    }


    @Override
    public void doWhatForRequestResult(int what, Response<String> response) {
        super.doWhatForRequestResult(what, response);
        String info = response.get();
        try {
            JSONObject json = new JSONObject(info);
            String error = json.getString("error");
            String msg = json.getString("msg");
            ToastUtils.showTextToast(ctx, msg);
            if (ResultCode.LOAD_SUCCEED.equals(error)) {
                ActivityUtils.jumpToActivity(ctx, MainActivity.class);
            }

        } catch (JSONException e) {
            Logger.e(TAG + "登陆请求数据" + e.getMessage());
        }
    }

    @Override
    public void toStartProgressDialog() {
        toStartProgressDialog(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelBySign(TAG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
