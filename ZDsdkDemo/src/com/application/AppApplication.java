package com.application;

import com.baidu.gamesdk.BDGameApplication;

import android.app.Application;


public class AppApplication extends BDGameApplication {

	/**
	 * 是否测试模式
	 */
	public final static boolean DEBUG = false;

	@Override
	public void onCreate() {
		super.onCreate();
		if (!DEBUG) {
			// 捕获全局异常
			CrashHandler crashHandler = CrashHandler.getInstance();
			crashHandler.init(getApplicationContext());
		}
	}

}
