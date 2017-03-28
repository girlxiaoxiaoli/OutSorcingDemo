package com.zz.guojin.outsorcingdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ActivityUtils {

	public static List<Activity> mActivities = new ArrayList<Activity>();

	public static void exitAll() {
		for (Activity activity : mActivities) {
			activity.finish();
		}
	}

	public static void add(Activity activity) {
		if (!mActivities.contains(activity)) {
			mActivities.add(activity);
		}
	}

	public static void remove(Activity activity){
		if (mActivities.contains(activity))
			mActivities.remove(activity);
	}

	public static void jumpToActivity(Context context, Class<? extends Activity> activityClzz, Bundle bundle){
		Intent intent = new Intent(context,activityClzz);
		if (bundle != null)
			intent.putExtras(bundle);
		context.startActivity(intent);
	}

	public static void startActivityForResult(Activity activity, Class<? extends Activity> activityClzz, int requestCode, Bundle bundle){
		Intent intent = new Intent(activity,activityClzz);
		if (bundle != null)
			intent.putExtras(bundle);
		activity.startActivityForResult(intent,requestCode);
	}

}
