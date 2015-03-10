package com.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zhidian.issueSDK.ICallback;
import com.zhidian.issueSDK.ZDSDK;
import com.zhidian.issueSDK.model.GameInfo;
import com.zhidian.issueSDK.model.UserInfoModel;
import com.zhidian.issueSDK.util.SDKLog;

public class MainActivity extends Activity implements OnClickListener {
	public static String loginTime = "";
	private EditText mPageEdit;
	private EditText mAreaEdit;
	private EditText mMonnyEdit;
	private EditText mNotesEdit;
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
			SDKLog.e("", "onError >>>>>>>  " + message);//FIXME
			Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

		}

		@Override
		public void logoutSuccess() {
			Toast.makeText(MainActivity.this, "logoutSuccess",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(MainActivity.this, WelComeActivity.class);
			intent.putExtra(WelComeActivity.LOGOUT, true);
			startActivity(intent);
			finish();
		}

		@Override
		public void initSuccess() {
		}

		@Override
		public void loginSuccess(UserInfoModel userInfoModel) {

		}

		@Override
		public void setGameInfoSuccess(String loginTime) {
			MainActivity.loginTime = loginTime;
			}

		@Override
		public void exitSuccess() {
			finish();
		}

		@Override
		public void createRoleSuccess() {
			Toast.makeText(MainActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
		}

	};
	private GameInfo gameInfo;
	private String notifyUrl;
	private String extInfo;
	private String cpOrderId;
	private LinearLayout mPayLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(ResUtil.getLayout(this, "activity_main"));
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
		findViewById(ResUtil.getId(this, "doneCusompay")).setOnClickListener(this);
		findViewById(ResUtil.getId(this, "logout")).setOnClickListener(this);
		findViewById(ResUtil.getId(this, "exit")).setOnClickListener(this);
		findViewById(ResUtil.getId(this, "doneNotes")).setOnClickListener(this);
		mPayLayout = (LinearLayout)findViewById(ResUtil.getId(this, "ll_customPay"));
		mMonnyEdit = (EditText) findViewById(ResUtil.getId(this, "customPaytEdit"));
		mNotesEdit = (EditText) findViewById(ResUtil.getId(this, "notes"));
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == ResUtil.getId(this, "doneCusompay")) {
			extInfo = "cusompay";
			mDialog = new LoadingDialog(this, "支付中……");
			mDialog.show();
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
