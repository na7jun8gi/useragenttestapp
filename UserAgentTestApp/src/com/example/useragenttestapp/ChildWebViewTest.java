package com.example.useragenttestapp;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class ChildWebViewTest extends Activity {

	/**
	 * 
	 * Urls for each type of test cases.
	 *
	 */
	public static enum CHILD_WEBVIEW_TEST implements Serializable{
		USER_AGENT_TEST		("https://www.google.co.kr/?gfe_rd=ctrl&ei=GVYdU_PIDOSOiAfqqYGYDw&gws_rd=cr#newwindow=1&q=whatsmyuseragent",
				             "http://www.whatsmyuseragent.com");
		
		private String testUrl;
		
		CHILD_WEBVIEW_TEST(String url, String childUrl) {
			this.testUrl = url;
		}
		
		public String getTestUrl() {
			return this.testUrl;
		}
	};
	
	CHILD_WEBVIEW_TEST type = null;
	
	RelativeLayout mLayout;
	RelativeLayout mSubLayout;
	WebView mParentWebView;
	WebView mChiildWebView;
	
	EditText mInputUrlEditText;
	
	Context mContext;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Test Type selected form Main Activity
		type = (CHILD_WEBVIEW_TEST)getIntent().getSerializableExtra("type");
		
		
		setContentView(R.layout.child_webview);
		
		mLayout = (RelativeLayout)findViewById(R.id.layout);
		mInputUrlEditText = (EditText)findViewById(R.id.url_text);
		mInputUrlEditText.setInputType(0);
		mSubLayout = (RelativeLayout)findViewById(R.id.sub_layout);
		
		mParentWebView = new MyWebView(this);
		
		mParentWebView.setWebViewClient(new WebViewClient());
		mParentWebView.setWebChromeClient(new WebChromeClientExample());
		
		mParentWebView.loadUrl(type.getTestUrl());
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		
		mLayout.addView(mParentWebView, params);
		
	}

	@Override
	public void onBackPressed() {
		
		if(mSubLayout.getVisibility() == View.VISIBLE) {
			if(mChiildWebView.canGoBack() == true) {
				mChiildWebView.goBack();
				return;
			} else {
				mSubLayout.removeView(mChiildWebView);
				mSubLayout.setVisibility(View.GONE);
				mChiildWebView.destroy();
				return;
			}
		} else {
			if(mParentWebView.canGoBack() == true) {
				mParentWebView.goBack();
				return;
			} else {
				mParentWebView.destroy();
			}
		}
		
		super.onBackPressed();
	}
	
	/************************************ WebChromeClient****************************************/
	private class WebChromeClientExample extends WebChromeClient
	{
		@Override
		public boolean onCreateWindow(WebView view, boolean isDialog,
				boolean isUserGesture, Message resultMsg) {

			mChiildWebView = new MyWebView(view.getContext());
			mChiildWebView.setWebViewClient(new WebViewClient());
			mChiildWebView.setWebChromeClient(new WebChromeClientExample());
			
			mSubLayout.setVisibility(View.VISIBLE);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			mSubLayout.addView(mChiildWebView, params);

			WebView.WebViewTransport transport = (WebView.WebViewTransport)resultMsg.obj;
			transport.setWebView(mChiildWebView);
			resultMsg.sendToTarget();
			
			return true;
		}
		
		@Override
		public void onCloseWindow(WebView window) {
			
			mSubLayout.removeView(mChiildWebView);
			mSubLayout.setVisibility(View.GONE);
			
			super.onCloseWindow(window);
		}
	}
}