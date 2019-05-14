package com.pc.mimi.ui.mine;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivityCustomBinding;
import com.pc.mimi.util.CommonUtil;

import me.goldze.mvvmhabit.utils.KLog;

public class WebActivity extends SwipeBackActivity<ActivityCustomBinding, WebViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_custom;
    }

    private String url = "";
    private String title = "";

    @Override
    public void initParam() {
        title = getIntent().getExtras().getString("title", "");
        url = getIntent().getExtras().getString("url", "");
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle(title);
        CommonUtil.initTitleBar(this);
        binding.webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                KLog.d("---------load----------");

            }

            public void onReceivedTitle(WebView view, String title) {
                KLog.d("---title---->", title);
            }
        });
        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        binding.webView.loadUrl(url);
    }
}
