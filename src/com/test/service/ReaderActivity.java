package com.test.service;

import java.util.Random;

import com.test.service.models.FileModel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ReaderActivity extends Activity {

	private TestServiceManager mServiceManager;
	private Boolean mStartFlag = true;
	private TextView mTextViewBook;

	private AlertDialog mSyncDataDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reader_layout);
		mTextViewBook = (TextView) findViewById(R.id.textBook);
		mServiceManager = TestServiceManager.getInstance();
		showSyncDataDialog();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (AppDataProvider.getInstance().getBook() != null && AppDataProvider.getInstance().getBook().book != null)
			mTextViewBook.setText(AppDataProvider.getInstance().getBook().book);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		mStartFlag = false;
		showSyncDataDialog();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

	private class SyncBookmarkAsyncTask extends AsyncTask<FileModel, Void, String> {
		ProgressBar syncProgress;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			syncProgress = (ProgressBar) findViewById(R.id.sync_progress);
			syncProgress.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(FileModel... params) {
			// if (params != null && params[0] != null)

			if (mStartFlag)
				return mServiceManager.getBookMark(AppDataProvider.getInstance().getEmail(), AppDataProvider
						.getInstance().getPassword());
			else {
				if (AppDataProvider.getInstance().getBook() != null) {
					Random generator = new Random();
					int percent = generator.nextInt(100);
					return mServiceManager.setBookMark(AppDataProvider.getInstance().getEmail(), AppDataProvider
							.getInstance().getPassword(), AppDataProvider.getInstance().getBook().fileName, percent);
				} else
					return null;
			}
			/*
			 * mServiceManager.setBookMark(AppDataProvider.getInstance()
			 * .getEmail(), AppDataProvider.getInstance() .getPassword());
			 */
			// mServiceManager.uploadBook(AppDataProvider.getInstance().getEmail(),
			// AppDataProvider.getInstance()
			// .getPassword(), params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result != null && result.equals(Constants.ERROR_BOOKMARK)) {
				if (mStartFlag) {
					mTextViewBook.setText(result);
					showOpenBookDialog();
					syncProgress.setVisibility(View.GONE);
				} else {
					// TODO ADD upload file request
				}
			} else {

			}
		}
	}

	private void showSyncDataDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(R.string.dialog_message);

		builder.setPositiveButton(R.string.btn_sync_data, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				if (Utils.isOnline(ReaderActivity.this))
					new SyncBookmarkAsyncTask().execute();
				else
					Toast.makeText(ReaderActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
				// startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
				// new SyncBookmarkAsyncTask().execute(mStartFlag);

				/*
				 * FileModel file = new FileModel(); file.fileName =
				 * "text_book"; file.extension = "rtf"; file.fileSize =
				 * 610000.0; byte[] bytes = copyToBuffer(); if (bytes != null) {
				 * file.content = bytes; new
				 * SyncBookmarkAsyncTask().execute(file); }
				 */
			}
		});
		builder.setNegativeButton(R.string.btn_cansel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				// startActivity(new Intent(ReaderActivity.this,
				// BrowserActivity.class));
			}
		});
		builder.setCancelable(true);

		mSyncDataDialog = builder.create();
		mSyncDataDialog.show();
	}

	private void showOpenBookDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(R.string.dialog_open_book_message);
		builder.setPositiveButton(R.string.btn_open_book, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				startActivity(new Intent(ReaderActivity.this, BrowserActivity.class));
			}
		});
		builder.setNegativeButton(R.string.btn_cansel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setCancelable(true);

		mSyncDataDialog = builder.create();
		mSyncDataDialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		startActivity(new Intent(ReaderActivity.this, BrowserActivity.class));
		return super.onMenuItemSelected(featureId, item);
	}
}
