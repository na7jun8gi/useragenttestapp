package com.example.useragenttestapp;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MyWebView extends WebView{
	
	private Context mContext;
	
	public MyWebView(Context context) {
		super(context);
		mContext = context;

		webViewSetting();
	}

	private void webViewSetting() {
		WebSettings webSettings = getSettings();
		
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportMultipleWindows(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setUserAgentString(webSettings.getDefaultUserAgent(mContext) + "_USERAGNET_OVERRIED_TEST");
	}
}