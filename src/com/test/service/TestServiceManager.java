package com.test.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.test.service.models.BookMarkModel;
import com.test.service.models.FileModel;

public class TestServiceManager {

	private static TestServiceManager instance;
	private RestServiceProxy mRestProxy;

	private int httpStatusCode;

	private TestServiceManager() {
		mRestProxy = new RestServiceProxy();
	}

	public static TestServiceManager getInstance() {
		if (instance == null)
			instance = new TestServiceManager();

		return instance;
	}

	public void finish() {
		instance = null;
	}

	public String setBookMark(String login, String password, BookMarkModel bookMark) {

		// String method = Constants.METHOD_SYNC_BOOKMARK + "?" + "bookName=" +
		// bookName + "&" + "bookPercent="
		// + bookPercent;
		String result = mRestProxy.webInvoke(Constants.METHOD_SYNC_BOOKMARK, login, password, bookMark);

		return result;
				/*getResponseEntity(response);*/
	}

	public String getBookMark(String login, String password) {
		HttpResponse response = mRestProxy.webGet(Constants.METHOD_SYNC_BOOKMARK, login, password);
		return getResponseEntity(response);
	}

	public boolean uploadBook(String login, String password, FileModel file) {

		String result = mRestProxy.webInvoke(Constants.METHOD_SYNC_UPLOAD, login, password, file);
		if (result.equals("{\"success\":\"Upload was finished successfully\"}"))
			return true;
		else
			return false;
	}

	public String downloadBook(String login, String password) {
		HttpResponse response = mRestProxy.webGet(Constants.METHOD_SYNC_DOWNLOAD, login, password);
		return getResponseEntity(response);
	}

	private String getResponseEntity(HttpResponse response) {
		String content = null;
		if (response != null) {
			httpStatusCode = response.getStatusLine().getStatusCode();
			if (httpStatusCode == HttpStatus.SC_OK) {
				try {
					content = EntityUtils.toString(response.getEntity());
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content;
	}
}
