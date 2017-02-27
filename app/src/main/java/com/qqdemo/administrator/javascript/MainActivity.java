package com.qqdemo.administrator.javascript;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.button)
    Button mButton;
    @InjectView(R.id.button2)
    Button mButton2;
    @InjectView(R.id.webview)
    WebView mWebview;
    @InjectView(R.id.activity_main)
    LinearLayout mActivityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.loadUrl("file:///android_asset/index.html");
        mWebview.addJavascriptInterface(MainActivity.this,"android");
    }

    @OnClick({R.id.button, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                mWebview.loadUrl("javascript:javacalljs()");
                break;
            case R.id.button2:
                mWebview.loadUrl("javascript:javasendjs()");
                break;
        }
    }
    @JavascriptInterface
    public void call(final String text){
        Intent i=new Intent();
        i.setAction(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:"+text));
        startActivity(i);
        };

    @JavascriptInterface
    public void send(String num,String message){
        num=num.trim();
        SmsManager manager=SmsManager.getDefault();
        ArrayList<String> list=manager.divideMessage(message);
        for(String msg:list){
            manager.sendTextMessage(num,null,msg,null,null);
        }
        Toast.makeText(MainActivity.this, "发送完毕", Toast.LENGTH_SHORT).show();

    };
};
