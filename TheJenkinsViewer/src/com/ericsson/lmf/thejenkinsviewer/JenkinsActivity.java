package com.ericsson.lmf.thejenkinsviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class JenkinsActivity extends Activity {
	private static final String TAG = JenkinsActivity.class.getSimpleName();
	private TextView textView;
	private ListView listView;
	
	@Override
	public void onCreate(Bundle args) {
		Log.d(TAG, "onCreate called");
		super.onCreate(args);
		setContentView(R.layout.main_layout);
		textView = (TextView)findViewById(R.id.jenkinsInfo);
		listView = (ListView)findViewById(R.id.jenkinsList);
		listView.setVisibility(View.INVISIBLE);
		Button button = (Button) findViewById(R.id.jenkinsInfoButton);
		button.setOnClickListener(refreshListener);
	}

	private OnClickListener refreshListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Log.d(TAG, "Refresh button clicked, starting service");
			Intent intent = new Intent(JenkinsActivity.this, JenkinsIntentService.class);
			startService(intent);
			textView.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
		}
	};
}
