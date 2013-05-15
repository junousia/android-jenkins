package com.ericsson.lmf.thejenkinsviewer;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class JenkinsIntentService extends IntentService {
	private static final String TAG = JenkinsIntentService.class.getSimpleName();

	public JenkinsIntentService() {
		super("JenkinsIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "onHandleIntent called");
	}

}
