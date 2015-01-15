package com.application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * 
 * @author user
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";

	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler实例
	private static CrashHandler INSTANCE = new CrashHandler();
	/**
	 * 错误日志路径
	 */
	private String mLogPath;

	// 用于格式化日期,作为日志文件名的一部分
	@SuppressLint("SimpleDateFormat")
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context) {
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
		/*	Intent intent = new Intent(TAApplication.getApplication(),
					CrashHandleService.class);
			intent.putExtra("logpath", mLogPath);
			PendingIntent restartIntent = PendingIntent.getService(
					TAApplication.getApplication(), 0, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			// 退出程序
			AlarmManager mgr = (AlarmManager) TAApplication.getApplication()
					.getSystemService(Context.ALARM_SERVICE);
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
					restartIntent); // 1秒钟后重启应用
					
*/			
			saveCrashInfo2File(ex);
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return false;
		}
		// 保存日志文件
		saveCrashInfo2File(ex);
		return true;
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	private void saveCrashInfo2File(Throwable ex) {
		StringBuffer sb = new StringBuffer();

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
        //SDKLog.e("AppApplicatin", sb);
        saveByteToFile(sb.toString(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhidian/a/b/Exception.txt");
        saveByteToFile("*****************", Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhidian/a/b/Exception.txt");
        saveByteToFile(" *****next*****", Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhidian/a/b/Exception.txt");
        saveByteToFile("*****************", Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhidian/a/b/Exception.txt");
		/*long timestamp = System.currentTimeMillis();
		String time = formatter.format(new Date());
		mLogPath = PathConstant.CRASH_LOG_PATH + "/crash-" + time + "-"
				+ timestamp + ".log";
		FileUtil.saveByteToFile(sb.toString().getBytes(), mLogPath);
		return mLogPath;*/
	}
	/**
	 * 保存数据到指定文件
	 */
	public static boolean saveByteToFile(String configString,
			final String filePathName) {
		boolean result = false;
		try {
			File newFile = createNewFile(filePathName, true);
				FileOutputStream fileOutputStream = new FileOutputStream(newFile,true);
				Writer writer = new OutputStreamWriter(fileOutputStream);
				BufferedWriter bw = new BufferedWriter(writer);
				bw.write(configString);
				bw.flush();
				bw.close();
			/*	FileOutputStream fileOutputStream = new FileOutputStream(newFile,true);
			fileOutputStream.write(byteData);
			fileOutputStream.write("\r\n".getBytes());
			fileOutputStream.flush();
			fileOutputStream.close();*/
			result = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static File createNewFile(String path, boolean append) {
		File newFile = new File(path);
		if (!append) {
			if (newFile.exists()) {
				newFile.delete();
			}
		}
		if (!newFile.exists()) {
			try {
				File parent = newFile.getParentFile();
				if (parent != null && !parent.exists()) {
					parent.mkdirs();
				}
				newFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return newFile;
	}
}
