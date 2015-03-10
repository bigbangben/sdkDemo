package com.demo;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.zhidian.issueSDK.ICallback;
import com.zhidian.issueSDK.ZDSDK;
import com.zhidian.issueSDK.model.UserInfoModel;
import com.zhidian.issueSDK.util.SDKLog;

public class WelComeActivity extends Activity implements OnClickListener {
	private static String TAG = "WelComeActivity";
	private boolean isLogout = false;
	public static String LOGOUT = "LOGOUT";
	private LoadingDialog mDialog;
	private ICallback callback = new ICallback() {

		@Override
		public void paySuccess(String orderid) {

		}

		@Override
		public void onError(int type, String message) {
			SDKLog.e(TAG, "onError >>>>>>>  " + message);// FIXME
			Toast.makeText(WelComeActivity.this, message, Toast.LENGTH_SHORT).show();
			if (mDialog.isShowing()) {
				mDialog.cancel();
			}
		}

		@Override
		public void logoutSuccess() {

		}

		@Override
		public void initSuccess() {
			if (mDialog.isShowing()) {
				mDialog.cancel();
			}
			Toast.makeText(WelComeActivity.this, "initSuccess",
					Toast.LENGTH_SHORT).show();
			mDialog.setMessage("登录中……");
			mDialog.show();
			ZDSDK.getInstance().sdkLogin(WelComeActivity.this, callback);
		}

		@Override
		public void loginSuccess(UserInfoModel userInfoModel) {
			if (mDialog.isShowing()) {
				mDialog.cancel();
			}
			Toast.makeText(WelComeActivity.this,
					"loginSuccess  " + userInfoModel.sessionId,
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(WelComeActivity.this, SetRoleActivity.class);
			WelComeActivity.this.startActivity(intent);
			finish();
		}

		@Override
		public void setGameInfoSuccess(String loginTime) {

		}

		@Override
		public void exitSuccess() {
			
		}

		@Override
		public void createRoleSuccess() {
			
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(ResUtil.getLayout(this, "activity_welcome"));
		isLogout = getIntent().getBooleanExtra(LOGOUT, false);
		// if (!isLogout) {
		// ZhiDianManager.init(this,ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE,mInitListener,
		// iLoginListener);
		// }
		Log.e("welcome", "+++++++++++ oncreate +++++++++++");
		mDialog = new LoadingDialog(this, "初始化中……");
		mDialog.show();
		ZDSDK.getInstance().sdkInit(this, callback);
		//findViewById(ResUtil.getId(this, "item_login")).setOnClickListener(this);
	

	}

	@Override
	public void onClick(View v) {
	/*	if (v.getId() == ResUtil.getId(this, "item_login")) {
			mDialog.setMessage("登录中……");
			mDialog.show();
			ZDSDK.getInstance().sdkLogin(this, callback);
		}*/
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		ZDSDK.getInstance().onSdkPause(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		ZDSDK.getInstance().onSdkStop(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ZDSDK.getInstance().onSdkResume(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
