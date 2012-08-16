package com.test.service.models;

public class BookMarkModel {
	private String mBookId;
	private String mBookName;
	private String mAuthorId;
	private String mAuthorName;
	private Integer mBookPercent;
	private String mComment;

	public BookMarkModel() {

	}

	public BookMarkModel(String bookId, String bookName, String authorId, String authorName, Integer bookPercent,
			String comment) {
		mBookId = bookId;
		mBookName = bookName;
		mAuthorId = authorId;
		mAuthorName = authorName;
		mBookPercent = bookPercent;
		mComment = comment;
	}

	public String getBookId() {
		return mBookId;
	}

	public void setBookId(String mBookId) {
		this.mBookId = mBookId;
	}

	public String getBookName() {
		return mBookName;
	}

	public void setBookName(String mBookName) {
		this.mBookName = mBookName;
	}

	public String getAuthorId() {
		return mAuthorId;
	}

	public void setAuthorId(String mAuthorId) {
		this.mAuthorId = mAuthorId;
	}

	public String getAuthorName() {
		return mAuthorName;
	}

	public void setAuthorName(String mAuthorName) {
		this.mAuthorName = mAuthorName;
	}

	public Integer getBookPercent() {
		return mBookPercent;
	}

	public void setBookPercent(Integer mBookPercent) {
		this.mBookPercent = mBookPercent;
	}

	public String getComment() {
		return mComment;
	}

	public void setComment(String mComment) {
		this.mComment = mComment;
	}
}
