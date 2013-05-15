package com.ericsson.lmf.thejenkinsviewer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.ericsson.lmf.thejenkinsviewer.JenkinsActivity.ResponseReceiver;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class JenkinsIntentService extends IntentService {
	private static final String TAG = JenkinsIntentService.class.getSimpleName();
	// private static final String URL = "https://dcpsupport.epk.ericsson.se/jenkins/view/Radiator%20Android%20App/api/xml";
	private static final String URL = "http://www.google.com";
	public static final String IN_MSG = "imsg";
	public static final String OUT_MSG = "omsg";
	
	public JenkinsIntentService() {
		super("JenkinsIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "onHandleIntent called");
		String result = getStuff();
		Log.v(TAG, "Got result: " + result);
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ResponseReceiver.ACTION_RESP);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(OUT_MSG, result);
		sendBroadcast(broadcastIntent);
	}

	private String getStuff() {
		Log.d(TAG, "Getting stuff from URL: " + URL);
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(URL); 
		HttpResponse response;
		String result = null;
		try {
			response = httpclient.execute(httpget);
			Log.v(TAG, response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result= convertStreamToString(instream);
				instream.close();
			}
		} catch (Exception e) {
			Log.e(TAG, "Failed to get stuff from Jenkins", e);
		}
		return result;
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
