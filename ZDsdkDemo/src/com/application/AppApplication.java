package com.application;

import android.app.Application;


public class AppApplication extends MarioPluginApplication {

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
