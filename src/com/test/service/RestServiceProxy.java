package com.test.service;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class RestServiceProxy {

	private final static int CONNECTION_TIMEOUT = 50000;
	private final static int SO_TIMEOUT = 50000;

	private final static String SCHEME_NAME = "http";
	private final static int SCHEME_PORT = 80;

	private DefaultHttpClient mHttpClient;

	public RestServiceProxy() {
		this(CONNECTION_TIMEOUT, SO_TIMEOUT);
	}

	public RestServiceProxy(int connectionTimeout, int soTimeout) {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme(SCHEME_NAME, PlainSocketFactory.getSocketFactory(), SCHEME_PORT));

		HttpParams connectionParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(connectionParams, connectionTimeout);
		HttpConnectionParams.setSoTimeout(connectionParams, soTimeout);
		ClientConnectionManager cm = new ThreadSafeClientConnManager(connectionParams, schemeRegistry);
		mHttpClient = new DefaultHttpClient(cm, connectionParams);
	}

	public HttpResponse webGet(String methodName, String login, String password) {
		HttpResponse response = null;
		String getUrl = String.format(Constants.URI_SCHEME, AppDataProvider.getInstance().getDomen(), methodName);

		HttpGet httpGet = new HttpGet(getUrl);
		httpGet.setHeader("Host", AppDataProvider.getInstance().getDomen());
		httpGet.setHeader("Authorization", Utils.getBase64Code(login, password));

		try {
			response = mHttpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public HttpResponse webInvoke(String methodName, String login, String password, String entity) {
		HttpResponse response = null;
		String getUrl = String.format(Constants.URI_SCHEME, AppDataProvider.getInstance().getDomen(), methodName);

		HttpPost httpPost = new HttpPost(getUrl);
		httpPost.setHeader("Host", AppDataProvider.getInstance().getDomen());
		httpPost.setHeader("Authorization", Utils.getBase64Code(login, password));
		try {

			httpPost.setEntity(new StringEntity(entity));
			response = mHttpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}
	

}
