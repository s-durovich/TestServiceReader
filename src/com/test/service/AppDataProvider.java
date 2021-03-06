package com.test.service;

import com.test.service.models.BookMarkModel;
import com.test.service.models.FileModel;

public class AppDataProvider {

	private static AppDataProvider instance;

	private String mEmail;
	private String mPassword;
	private String mDomen;

	private FileModel mBook;
	private BookMarkModel mBookMark;
	public String book;

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

	public FileModel getBook() {
		return mBook;
	}

	public void setBook(FileModel mBook) {
		this.mBook = mBook;
	}

	public BookMarkModel getBookMark() {
		return mBookMark;
	}

	public void setBookMark(BookMarkModel mBookMark) {
		this.mBookMark = mBookMark;
	}
}
