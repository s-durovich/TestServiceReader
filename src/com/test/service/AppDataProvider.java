package com.test.service;

public class AppDataProvider {
	private static AppDataProvider instance;

	private String mEmail;
	private String mPassword;
	private String mDomen;

	private AppDataProvider() {

	}

	public static AppDataProvider getInstance() {
		if (instance == null)
			instance = new AppDataProvider();

		return instance;
	}

	public void finish() {
		instance = null;
	}

	public String getDomen() {
		return mDomen;
	}

	public void setDomen(String mDomen) {
		this.mDomen = mDomen;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String mPassword) {
		this.mPassword = mPassword;
	}
}
