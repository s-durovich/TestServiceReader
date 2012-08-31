package com.test.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import com.test.service.models.FileModel;

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

	private class SyncBookmarkAsyncTask extends AsyncTask<FileModel, Void, Void> {
		ProgressBar syncProgress;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			syncProgress = (ProgressBar) findViewById(R.id.sync_progress);
			syncProgress.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(FileModel... params) {
			if (params != null && params[0] != null)
				/*
				 * mServiceManager.getBookMark(AppDataProvider.getInstance().
				 * getEmail(), AppDataProvider.getInstance() .getPassword());
				 * else
				 * mServiceManager.setBookMark(AppDataProvider.getInstance()
				 * .getEmail(), AppDataProvider.getInstance() .getPassword());
				 */

				mServiceManager.uploadBook(AppDataProvider.getInstance().getEmail(), AppDataProvider.getInstance()
						.getPassword(), params[0]);
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
		mStartFlag = false;
		// showSyncDataDialog();
		super.finish();
	}

	private void showSyncDataDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(R.string.dialog_message);

		builder.setPositiveButton(R.string.btn_sync_data, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				// startActivity(new Intent(
				// Settings.ACTION_WIFI_SETTINGS));
				// new SyncBookmarkAsyncTask().execute(mStartFlag);

				FileModel file = new FileModel();
				file.fileName = "polnyiy_root";
				file.extension = "fb2";
				file.fileSize = 623900.0;

				byte[] bytes = copyToBuffer();
				if (bytes != null) {
					file.content = bytes;

					new SyncBookmarkAsyncTask().execute(file);
				}
			}
		});
		builder.setNegativeButton(R.string.btn_not_sync, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				startActivity(new Intent(ReaderActivity.this, BrowserActivity.class));
			}
		});
		builder.setCancelable(true);

		mSyncDataDialog = builder.create();
		mSyncDataDialog.show();
	}

	private byte[] copyToBuffer() {
		String filePath = MemoryStatus.externalStorageDirectory() + File.separator + CacheManager.READER_CACHE_DIR
				+ File.separator + "polnyiy_root.fb2";
		byte[] bytes = null;
		try {
			RandomAccessFile file = new RandomAccessFile(filePath, "r");
			try {
				int size = (int) file.length();
				bytes = new byte[size];
				file.readFully(bytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}
}
