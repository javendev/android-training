package com.javen.lock;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

import java.util.List;
import java.util.SortedMap;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * 获取栈顶程序的Task
 */
public class LockTask extends TimerTask {
	public static final String TAG = System.class.getSimpleName();
	private Context mContext;
	String testPackageName = "com.htc.notes";
	String testClassName = "com.htc.notes.collection.NotesGridViewActivity";

	private ActivityManager mActivityManager;

	private  UsageStatsManager mUsageStatsManager;

	public LockTask(Context context) {
		mContext = context;
		if (Build.VERSION.SDK_INT >= 21) {
			mUsageStatsManager= (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
		} else {
			mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		}

	}

	@Override
	public void run() {
//		ComponentName topActivity = mActivityManager.getRunningTasks(1).get(0).topActivity;
//		String packageName = topActivity.getPackageName();
//		String className = topActivity.getClassName();
//		Log.v(TAG, "packageName" + packageName);
//		Log.v(TAG, "className" + className);

		try {
			getProcess();
		} catch (Exception e) {
			e.printStackTrace();
		}

//		if (testPackageName.equals(packageName)
//				&& testClassName.equals(className)) {
//			Intent intent = new Intent();
//			intent.setClassName("com.example.locktest", "com.example.locktest.PasswordActivity");
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			mContext.startActivity(intent);
//		}
	}


	private String getProcess() throws Exception {
		if (Build.VERSION.SDK_INT >= 21) {
			return getProcessNew();
		} else {
			return getProcessOld();
		}
	}
	//API 21 and above
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private String getProcessNew() throws Exception {
		String topPackageName = null;
		long time = System.currentTimeMillis();
		List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);
		if (stats != null) {
			SortedMap<Long, UsageStats> runningTask = new TreeMap<Long,UsageStats>();
			for (UsageStats usageStats : stats) {
				runningTask.put(usageStats.getLastTimeUsed(), usageStats);
			}
			if (runningTask.isEmpty()) {
				return null;
			}
			topPackageName =  runningTask.get(runningTask.lastKey()).getPackageName();
			Log.v(TAG, "packageName" + topPackageName);
		}
		return topPackageName;
	}

	//API below 21
	@SuppressWarnings("deprecation")
	private String getProcessOld() throws Exception {
		String topPackageName = null;
		String topClassName = null;
		List<ActivityManager.RunningTaskInfo> runningTask = mActivityManager.getRunningTasks(1);
		if (runningTask != null) {
			ActivityManager.RunningTaskInfo taskTop = runningTask.get(0);
			ComponentName componentTop = taskTop.topActivity;
			topPackageName = componentTop.getPackageName();
			topClassName = componentTop.getClassName();
			Log.v(TAG, "packageName" + topPackageName);
			Log.v(TAG, "className" + topClassName);
		}
		return topPackageName;
	}

	/**
	 * 判断某个应用程序是 不是三方的应用程序
	 * @param info
	 * @return 如果是第三方应用程序则返回true，如果是系统程序则返回false
	 */
	private boolean filterApp(ApplicationInfo info) {
		if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
			return true;
		} else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
			return true;
		}
		return false;
	}
}