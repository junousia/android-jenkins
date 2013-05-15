package com.ericsson.lmf.thejenkinsviewer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
	private ResponseReceiver receiver;
	
	@Override
	public void onCreate(Bundle args) {
		Log.d(TAG, "onCreate called");
		super.onCreate(args);
		setContentView(R.layout.main_layout);
		textView = (TextView)findViewById(R.id.jenkinsInfo);
		Button button = (Button) findViewById(R.id.jenkinsInfoButton);
		button.setOnClickListener(refreshListener);
		
		// Tying the Broadcast Receiver
		IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);
        
	}

	private OnClickListener refreshListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Log.d(TAG, "Refresh button clicked, starting service");
			Intent intent = new Intent(JenkinsActivity.this, JenkinsIntentService.class);
			startService(intent);
		}	
	};
	
	public class ResponseReceiver extends BroadcastReceiver {
		   public static final String ACTION_RESP =
		      "com.mamlambo.intent.action.MESSAGE_PROCESSED";
		   @Override
		    public void onReceive(Context context, Intent intent) {
		       String text = intent.getStringExtra(JenkinsIntentService.OUT_MSG);
		       textView.setText(text);
		    }
		}

}
