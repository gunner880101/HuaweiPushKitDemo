package com.gunner.huaweipushkitdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "HuaweiPushKitDemo";

    TextView mTokenTv;
    Button mGetTokenBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTokenTv = findViewById(R.id.push_token_tv);
        mGetTokenBtn = findViewById(R.id.get_push_token_btn);
        mGetTokenBtn.setOnClickListener((view) -> {getPushToken();});
    }

    private void getPushToken (){
        new Thread(() -> {
            try{
                String appId = AGConnectServicesConfig.fromContext(MainActivity.this).getString("client/app_id");
                String token = HmsInstanceId.getInstance(MainActivity.this).getToken(appId, "HCM");
                Log.i(TAG, "get token:" + token);
                if (!TextUtils.isEmpty(token)){
                    showToken(token);
                    sendRegTokenToServer(token);
                }
            } catch (ApiException e) {
                Log.e(TAG, "get token failed, " + e);
            }
        }).start();
    }

    private void showToken(final String token){
        runOnUiThread(() -> mTokenTv.setText(token));
    }
    private void sendRegTokenToServer(String token){
        Log.i(TAG, "sendRegTokenToServer " + token);
    }
}