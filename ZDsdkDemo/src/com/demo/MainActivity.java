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
	private ICallback callback = new ICallback() {

		@Override
		public void paySuccess(String orderid) {

		}

		@Override
		public void onError(int type, String message) {
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
		setContentView(R.layout.activity_main);
		Bundle bundle = getIntent().getExtras();
		String roleName = bundle.getString("RoleName");
		gameInfo = new GameInfo();
		gameInfo.setRoleId("111111");
		gameInfo.setRoleLevel("11");
		gameInfo.setRoleName(roleName);
		gameInfo.setZoneId("1");
		gameInfo.setZoneName("big");
		ZDSDK.getInstance(this).setGameInfo(this, gameInfo, true, callback);
		cpOrderId = "353535";
		extInfo = "normal notes";
		notifyUrl = "http://zdsdktest.zhidian3g.cn:888/platform/callback";
		findViewById(R.id.createRole).setOnClickListener(this);
		findViewById(R.id.startUPPay).setOnClickListener(this);
		findViewById(R.id.doneCusompay).setOnClickListener(this);
		findViewById(R.id.cancleFloat).setOnClickListener(this);
		findViewById(R.id.testApi).setOnClickListener(this);
		findViewById(R.id.logout).setOnClickListener(this);
		findViewById(R.id.exit).setOnClickListener(this);
		findViewById(R.id.doneArea).setOnClickListener(this);
		findViewById(R.id.doneNotes).setOnClickListener(this);
		mPayLayout = (LinearLayout)findViewById(R.id.ll_customPay);
		mAreaEdit = (EditText) findViewById(R.id.area);
		mMonnyEdit = (EditText) findViewById(R.id.customPaytEdit);
		mNotesEdit = (EditText) findViewById(R.id.notes);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		
		case R.id.createRole:
			ZDSDK.getInstance(this).createRole(gameInfo, callback);

			break;
		case R.id.setGameInfo:
			ZDSDK.getInstance(this).setGameInfo(this, gameInfo, true, callback);

			break;
		case R.id.startUPPay:
			ZDSDK.getInstance(this).doPay(gameInfo, null, cpOrderId, extInfo, notifyUrl, callback);
			break;
		case R.id.doneCusompay:
			extInfo = "cusompay";
			ZDSDK.getInstance(this).doPay(gameInfo, mMonnyEdit.getText().toString(), cpOrderId, extInfo, notifyUrl, callback);
			 break;
		case R.id.logout:
			ZDSDK.getInstance(this).onSdkLogOut(this, gameInfo, callback);
			break;
		case R.id.exit:
			ZDSDK.getInstance(this).onSdkExit(gameInfo, callback);
			break;
		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	};
}
