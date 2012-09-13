package com.test.service;

import com.test.service.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class LoginActivity extends Activity {

	private EditText mEmailEdit;
	private EditText mPasswordEdit;
	private EditText mURLEdit;
	private Button mLoginButton;

	private AppDataProvider mDataProvider;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mDataProvider = AppDataProvider.getInstance();

		mLoginButton = (Button) findViewById(R.id.login_button);
		mLoginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDataProvider.setEmail(mEmailEdit.getText().toString());
				mDataProvider.setPassword(mPasswordEdit.getText().toString());
				mDataProvider.setDomen(mURLEdit.getText().toString());

				if (Utils.isOnline(LoginActivity.this)) {
					startActivity(new Intent(LoginActivity.this, ReaderActivity.class));
					finish();
				} else
					Toast.makeText(LoginActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();

			}
		});

		mEmailEdit = (EditText) findViewById(R.id.email_edit);
		mPasswordEdit = (EditText) findViewById(R.id.password_edit);
		mURLEdit = (EditText) findViewById(R.id.url_edit);
		mURLEdit.setText(Constants.DOMEN);
		// Temporally
		mEmailEdit.setText(Constants.LOGIN);
		mPasswordEdit.setText(Constants.PASSWORD);
	}
}
