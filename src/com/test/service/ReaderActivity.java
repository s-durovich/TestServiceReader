package com.test.service;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class ReaderActivity extends Activity {

	private TestServiceManager mServiceManager;
	private Boolean mStartFlag = true;

	private AlertDialog mSyncDataDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reader_layout);
		mServiceManager = TestServiceManager.getInstance();
		showSyncDataDialog();
	}

	private class SyncBookmarkAsyncTask extends AsyncTask<Boolean, Void, Void> {
		ProgressBar syncProgress;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			syncProgress = (ProgressBar) findViewById(R.id.sync_progress);
			syncProgress.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Boolean... params) {
			if (params != null && params[0] == true)
				mServiceManager.getBookMark(AppDataProvider.getInstance()
						.getEmail(), AppDataProvider.getInstance()
						.getPassword());
			else
				mServiceManager.setBookMark(AppDataProvider.getInstance()
						.getEmail(), AppDataProvider.getInstance()
						.getPassword());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			syncProgress.setVisibility(View.GONE);
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		mStartFlag = false;
		showSyncDataDialog();
	}

	private void showSyncDataDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(R.string.dialog_message);

		builder.setPositiveButton(R.string.btn_sync_data,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						// startActivity(new Intent(
						// Settings.ACTION_WIFI_SETTINGS));
						new SyncBookmarkAsyncTask().execute(mStartFlag);
					}
				});
		builder.setNegativeButton(R.string.btn_not_sync,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						startActivity(new Intent(ReaderActivity.this,
								BrowserActivity.class));
					}
				});
		builder.setCancelable(true);

		mSyncDataDialog = builder.create();
		mSyncDataDialog.show();
	}
}
