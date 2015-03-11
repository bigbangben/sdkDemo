package com.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhidian.issueSDK.ICallback;
import com.zhidian.issueSDK.ZDSDK;
import com.zhidian.issueSDK.model.GameInfo;
import com.zhidian.issueSDK.model.UserInfoModel;
import com.zhidian.issueSDK.util.SDKLog;

public class WelComeActivity extends Activity implements OnClickListener {
	private static String TAG = "WelComeActivity";
	private LoadingDialog mDialog;
	private ICallback callback = new ICallback() {

		@Override
		public void paySuccess(String orderid) {
			if (mDialog.isShowing()) {
				mDialog.cancel();
			}
		}

		@Override
		public void onError(int type, String message) {
			if (mDialog.isShowing()) {
				mDialog.cancel();
			}
			SDKLog.e(TAG, "onError >>>>>>>  " + message);// FIXME
			Toast.makeText(WelComeActivity.this, message, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void logoutSuccess() {
			welcomePage.setVisibility(View.VISIBLE);
			mainPage.setVisibility(View.GONE);
			ZDSDK.getInstance().sdkInit(WelComeActivity.this, callback);
		}

		@Override
		public void initSuccess() {
			if (mDialog.isShowing()) {
				mDialog.cancel();
			}
			//Toast.makeText(WelComeActivity.this, "initSuccess",Toast.LENGTH_SHORT).show();
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
			welcomePage.setVisibility(View.GONE);
			mainPage.setVisibility(View.VISIBLE);
			setGameInfo();
		}

		@Override
		public void setGameInfoSuccess(String loginTime) {

		}

		@Override
		public void exitSuccess() {
			finish();
		}

		@Override
		public void createRoleSuccess() {
			
		}

	};
	private RelativeLayout welcomePage, mainPage;
	private GameInfo gameInfo;
	private String cpOrderId;
	private String extInfo;
	private String notifyUrl;
	private LinearLayout mPayLayout;
	private EditText mMonnyEdit;
	private EditText mNotesEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(ResUtil.getLayout(this, "activity_welcome"));
		Log.e("welcome", "+++++++++++ oncreate +++++++++++");
		welcomePage = (RelativeLayout) findViewById(ResUtil.getId(this, "welcome_page"));
		mainPage = (RelativeLayout) findViewById(ResUtil.getId(this, "main_page"));
		findViewById(ResUtil.getId(this, "doneCusompay")).setOnClickListener(this);
		findViewById(ResUtil.getId(this, "logout")).setOnClickListener(this);
		findViewById(ResUtil.getId(this, "exit")).setOnClickListener(this);
		findViewById(ResUtil.getId(this, "doneNotes")).setOnClickListener(this);
		mPayLayout = (LinearLayout)findViewById(ResUtil.getId(this, "ll_customPay"));
		mMonnyEdit = (EditText) findViewById(ResUtil.getId(this, "customPaytEdit"));
		mNotesEdit = (EditText) findViewById(ResUtil.getId(this, "notes"));
		mDialog = new LoadingDialog(this, "初始化中……");
		mDialog.show();
		ZDSDK.getInstance().sdkInit(this, callback);
		//findViewById(ResUtil.getId(this, "item_login")).setOnClickListener(this);
	

	}

	protected void setGameInfo() {
		gameInfo = new GameInfo();
		gameInfo.setRoleId("111111");
		gameInfo.setRoleLevel("11");
		gameInfo.setRoleName("qq");
		gameInfo.setZoneId("1111");
		gameInfo.setZoneName("big");
		cpOrderId = "353535";
		extInfo = "normal notes";
		notifyUrl = "http://zdsdktest.zhidian3g.cn/platform/callback";
		ZDSDK.getInstance().setGameInfo(this, gameInfo, true, callback);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == ResUtil.getId(this, "doneCusompay")) {
			extInfo = "cusompay";
			ZDSDK.getInstance().doPay(this, gameInfo, mMonnyEdit.getText().toString(), cpOrderId, extInfo, notifyUrl, callback);
		}else if (v.getId() == ResUtil.getId(this, "logout")) {
			ZDSDK.getInstance().onSdkLogOut(this, gameInfo, callback);
		}else if (v.getId() == ResUtil.getId(this, "exit")) {
			ZDSDK.getInstance().onSdkExit(this, gameInfo, callback);
		}
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
		ZDSDK.getInstance().onSdkDestory();
	}

}
