package com.demo;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.zhidian.issueSDK.ICallback;
import com.zhidian.issueSDK.ZDSDK;
import com.zhidian.issueSDK.model.GameInfo;
import com.zhidian.issueSDK.model.UserInfoModel;

public class SetRoleActivity extends Activity implements OnClickListener {
	
    private LoadingDialog mDialog;
	private ICallback callback = new ICallback() {

		@Override
		public void setGameInfoSuccess(String loginTime) {
			Intent intent = new Intent(SetRoleActivity.this, MainActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("RoleName", edRoleName.getText().toString().trim());
			intent.putExtras(bundle);
			SetRoleActivity.this.startActivity(intent);
			finish();
		}

		@Override
		public void paySuccess(String orderid) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onError(int type, String message) {
			switch (type) {
			case ICallback.CREATE_ROLE:
				mDialog.dismiss();
				Toast.makeText(SetRoleActivity.this, message, Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}

		@Override
		public void logoutSuccess() {
			// TODO Auto-generated method stub

		}

		@Override
		public void loginSuccess(UserInfoModel userInfoModle) {
			// TODO Auto-generated method stub

		}

		@Override
		public void initSuccess() {
			// TODO Auto-generated method stub

		}

		@Override
		public void exitSuccess() {
			// TODO Auto-generated method stub

		}

		@Override
		public void createRoleSuccess() {
			if (mDialog.isShowing()) {
				mDialog.cancel();
			}
			Toast.makeText(SetRoleActivity.this, "创建成功", Toast.LENGTH_SHORT)
					.show();
		}
	};
	private EditText edRoleName;
	private GameInfo gameInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(ResUtil.getLayout(this, "activity_role"));
		findViewById(ResUtil.getId(this, "item_begin")).setOnClickListener(this);
		findViewById(ResUtil.getId(this, "item_create")).setOnClickListener(this);
		edRoleName = (EditText) findViewById(ResUtil.getId(this, "role_name"));
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == ResUtil.getId(this, "item_begin")) {
			Intent intent = new Intent(SetRoleActivity.this, MainActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("RoleName", edRoleName.getText().toString().trim());
			intent.putExtras(bundle);
			SetRoleActivity.this.startActivity(intent);
			finish();
		}else if (v.getId() == ResUtil.getId(this, "item_create")) {
			mDialog = new LoadingDialog(this, "创建中……");
			mDialog.show();
			gameInfo = new GameInfo();
			gameInfo.setRoleId("111111");
			gameInfo.setRoleLevel("11");
			gameInfo.setRoleName(edRoleName.getText().toString().trim());
			gameInfo.setZoneId("1111");
			gameInfo.setZoneName("big");
			ZDSDK.getInstance().createRole(this, gameInfo, callback);
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
	}

}
