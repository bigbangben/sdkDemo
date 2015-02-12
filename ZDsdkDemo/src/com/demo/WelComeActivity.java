package com.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.zhidian.amber.mi.R;
import com.zhidian.issueSDK.ICallback;
import com.zhidian.issueSDK.ZDSDK;
import com.zhidian.issueSDK.model.InitInfo;
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
		}

		@Override
		public void loginSuccess(UserInfoModel userInfoModel) {
			if (mDialog.isShowing()) {
				mDialog.cancel();
			}
			Toast.makeText(WelComeActivity.this,
					"loginSuccess  " + userInfoModel.sessionId,
					Toast.LENGTH_SHORT).show();
			SDKLog.e(TAG, " id  == " + userInfoModel.id + " ; userName  == " + userInfoModel.userName);
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
	private InitInfo initInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		isLogout = getIntent().getBooleanExtra(LOGOUT, false);
		// if (!isLogout) {
		// ZhiDianManager.init(this,ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE,mInitListener,
		// iLoginListener);
		// }
		Log.e("welcome", "+++++++++++ oncreate +++++++++++");
		mDialog = new LoadingDialog(this, "初始化中……");
		mDialog.show();
		ZDSDK.getInstance().sdkInit(this, callback);
		Button button = (Button) findViewById(R.id.item_login);
		Log.e("welcome", "++++++   activity_welcome = "+ R.layout.activity_welcome +"  +++++++");
		Log.e("welcome", "++++++   item_login = "+ R.id.item_login +"  +++++++");
		if (button != null) {
			Log.e("welcome", "+++++++++++ button != null +++++++++++");
			button.setOnClickListener(this);
		}else {
			Log.e("welcome", "+++++++++++ button == null +++++++++++");
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.item_login:
			// ZhiDianManager.showLogin(this, iLoginListener);
			mDialog.setMessage("登录中……");
			mDialog.show();
			ZDSDK.getInstance().sdkLogin(this, callback);
			break;

		default:
			break;
		}

	}

}
