package com.test.service.models;

import org.codehaus.jackson.annotate.JsonProperty;

public class FileModel {

	@JsonProperty("fileName")
	public String fileName;

	@JsonProperty("fileExtension")
	public String extension;

	@JsonProperty("fileContent")
	public byte[] content;

	@JsonProperty("fileSize")
	public Long fileSize;
	
	public String book;
}
