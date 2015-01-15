package com.zhidian.gamesdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;

public class LoginActivity extends Activity {
	private EditText mNameText ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE) ;
		setContentView(R.layout.activity_login) ;
		mNameText = (EditText) findViewById(R.id.item_user_name);
		findViewById(R.id.item_ok).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				login() ;
			}
		});
	}
	private void login(){/*
		ZhiDianManager.cpLogin(this,mNameText.getText().toString().trim(), new ILoginListener() {
			
			@Override
			public void loginging() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void loginSuccess(String sessionId, String uid) {
				Toast.makeText(LoginActivity.this, sessionId+"-->"+uid, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				finish() ;
			}
			
			@Override
			public void loginFail() {
				// TODO Auto-generated method stub
				
			}
		});
	*/}
}
