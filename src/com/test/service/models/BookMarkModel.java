package com.test.service.models;

import org.codehaus.jackson.annotate.JsonProperty;
import org.json.JSONException;
import org.json.JSONObject;

public class BookMarkModel {
	@JsonProperty("bookName")
	public String mBookName;

	@JsonProperty("bookPercent")
	public Integer mBookPercent;

	@JsonProperty("authorName")
	public String mAuthorName;

	@JsonProperty("comment")
	public String mComment;

	public BookMarkModel() {

	}

	public BookMarkModel(String bookName, Integer bookPercent) {
		mBookName = bookName;
		mBookPercent = bookPercent;
	}
	
	public BookMarkModel(JSONObject object) throws JSONException{
		parseJsonData(object);
	}

	private void parseJsonData(JSONObject json) throws JSONException {
		mBookName = json.getString("bookName");
		mBookPercent = json.getInt("bookPercent");
		mAuthorName = json.getString("authorName");
		mComment = json.getString("comment");
	}
}
