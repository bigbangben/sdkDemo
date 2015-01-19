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
			Toast.makeText(MainActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
			mPayLayout.setVisibility(View.VISIBLE);
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
		setContentView(R.layout.activity_main);
		gameInfo = new GameInfo();
		gameInfo.setRoleId("111111");
		gameInfo.setRoleLevel("11");
		gameInfo.setRoleName("qq");
		gameInfo.setZoneId("1111");
		gameInfo.setZoneName("big");
		cpOrderId = "353535";
		extInfo = "normal notes";
		notifyUrl = "http://zdsdktest.zhidian3g.cn:888/platform/callback";
		findViewById(R.id.createRole).setOnClickListener(this);
		findViewById(R.id.setGameInfo).setOnClickListener(this);
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
		// ZhiDianManager.showFloadButton(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		
		case R.id.createRole:
			// ZhiDianManager.payNormal(this,"normal notes");
			// new SMSPay().pay(this, "xxx", "2", "aaa");
			ZDSDK.getInstance(this).createRole(gameInfo, callback);
			break;
		case R.id.setGameInfo:
			// ZhiDianManager.payNormal(this,"normal notes");
			// new SMSPay().pay(this, "xxx", "2", "aaa");
			ZDSDK.getInstance(this).setGameInfo(this, gameInfo, true, callback);
			break;
		case R.id.startUPPay:
			// ZhiDianManager.payNormal(this,"normal notes");
			// new SMSPay().pay(this, "xxx", "2", "aaa");
			ZDSDK.getInstance(this).doPay(gameInfo, null, cpOrderId, extInfo, notifyUrl, callback);
			break;

		case R.id.cancleFloat:
			// ZhiDianManager.cancleFloadButton();
			break;

		case R.id.doneCusompay:
			/*
			 * ZhiDianManager.customPay(this,
			 * Integer.parseInt(mMonnyEdit.getText().toString()),"cusompay");
			 */
			extInfo = "cusompay";
			ZDSDK.getInstance(this).doPay(gameInfo, mMonnyEdit.getText().toString(), cpOrderId, extInfo, notifyUrl, callback);
			 break;
		case R.id.logout:
			// ZhiDianManager.logout(this, iLogOutListener);
			ZDSDK.getInstance(this).logOut(this, gameInfo, callback);
			break;
		case R.id.exit:
			// ZhiDianManager.exit(this, exitListener);
			ZDSDK.getInstance(this).exit(gameInfo, callback);
			break;
		case R.id.doneArea:
			// ZhiDianManager.setCpArea(mAreaEdit.getText().toString());
			// Toast.makeText(this, "分区设置成功", Toast.LENGTH_SHORT).show();
			break;
		case R.id.doneNotes:
			// Toast.makeText(this, "-->"+ZhiDianManager.notes,
			// Toast.LENGTH_SHORT).show();
			// ZhiDianManager.setNotes(mNotesEdit.getText().toString());
			// Toast.makeText(this,ZhiDianManager., Toast.LENGTH_SHORT).show();
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