package com.example.whc.testanimation.HtmlTest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.whc.testanimation.MainActivity;
import com.example.whc.testanimation.R;

/**
 * Created by WSY on 2018/4/28.
 */

public class HtmlTest extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.htmltestview);

        webView=findViewById(R.id.webview);
        WebSettings mWebSettings = webView.getSettings();
        //启用JavaScript
        mWebSettings.setJavaScriptEnabled(true);
        //设置编码格式
        mWebSettings.setDefaultTextEncodingName("utf-8");

        webView.addJavascriptInterface(new JavaScriptObject(),"test");

        //调用本地html文件
        webView.loadUrl("file:///android_asset/demo.html");

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("javascript:callJavaScript('这是点击安卓端的Button后传过来的')");
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        //此处为 js 返回的结果
                    }
                });
            }
        });


        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(HtmlTest.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

        });

    }

    public class JavaScriptObject {

        @JavascriptInterface
        public void CallAndroid(String name){
            Toast.makeText(HtmlTest.this,name,Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(HtmlTest.this, MainActivity.class);
            startActivity(intent);

        }

    }

}
