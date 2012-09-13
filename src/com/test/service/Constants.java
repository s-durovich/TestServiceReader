package com.test.service;

public class Constants {
	public static final String LOGIN = "test@test.test";
	public static final String PASSWORD = "test";

	// public static final String SERVICE_URL =
	// "http://bookaz.jelastic.servint.net/cr-sync";
	//public static final String SERVICE_URL = "http://bookaz.jelastic.dogado.eu/cr-sync/";

	public static final String URI_SCHEME = "http://%s%s";

	public static final String DOMEN = "bookaz.jelastic.dogado.eu";
	public static final String METHOD_SYNC_BOOKMARK = "/cr-sync/sync/bookmark";
	public static final String METHOD_SYNC_UPLOAD = "/cr-sync/sync/upload";
	public static final String METHOD_SYNC_DOWNLOAD = "/cr-sync/sync/download";

	public static final String ERROR_BOOKMARK = "{\"error\":\"Book does not exist\"}";
}
