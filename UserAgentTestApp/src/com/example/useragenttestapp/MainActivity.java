package com.example.useragenttestapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;


public class MainActivity extends Activity implements android.view.View.OnClickListener{

	Button mUseragentIssueButton;
	Button mShouldoverrideurlloadingIssueButton;
	Button mJavascriptInterfaceIssueButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.useragent_button).setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		
		Intent intent = new Intent();
		
		switch(v.getId()) {
			/*
			 * It is case regarding to the user-agent of a child-webview
			 */
			case R.id.useragent_button:
				intent.setClass(this, ChildWebViewTest.class);
				intent.putExtra("type", ChildWebViewTest.CHILD_WEBVIEW_TEST.USER_AGENT_TEST);
				startActivity(intent);
				break;
		}
	}
}