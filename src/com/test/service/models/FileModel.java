package com.test.service.models;

import org.codehaus.jackson.annotate.JsonProperty;
import org.json.JSONException;
import org.json.JSONObject;

public class FileModel {

	@JsonProperty("fileName")
	public String fileName;

	@JsonProperty("fileExtension")
	public String extension;

	@JsonProperty("fileContent")
	public byte[] content;

	@JsonProperty("fileSize")
	public Long fileSize;

	public FileModel() {
		// TODO Auto-generated constructor stub
	}
	
	public FileModel(JSONObject json) throws JSONException {
		parseJsonData(json);
	}

	private void parseJsonData(JSONObject json) throws JSONException {
		fileName = json.getString("fileName");
		extension = json.getString("fileExtension");
		content = json.getString("fileContent").getBytes();
		fileSize = json.getLong("fileSize");
	}
}
