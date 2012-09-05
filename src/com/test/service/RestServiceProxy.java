package com.test.service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

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
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import android.util.Log;

public class RestServiceProxy {

	private final static int CONNECTION_TIMEOUT = 90000;
	private final static int SO_TIMEOUT = 90000;

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

	/*
	 * public HttpResponse webInvoke(String methodName, String login, String
	 * password, Object entity) { HttpResponse response = null; String getUrl =
	 * String.format(Constants.URI_SCHEME,
	 * AppDataProvider.getInstance().getDomen(), methodName);
	 * 
	 * HttpPost httpPost = new HttpPost(getUrl); httpPost.setHeader("Host",
	 * AppDataProvider.getInstance().getDomen());
	 * httpPost.setHeader("Authorization", Utils.getBase64Code(login,
	 * password)); try {
	 * 
	 * httpPost.setEntity(new StringEntity(entity)); response =
	 * mHttpClient.execute(httpPost); } catch (ClientProtocolException e) {
	 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * return response; }
	 */

	public String webInvoke(String methodName, String login, String password, Object param) {
		StringWriter sw = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		MappingJsonFactory jsonFactory = new MappingJsonFactory();
		String jsonString = "";
		JsonGenerator jsonGenerator;
		try {
			jsonGenerator = jsonFactory.createJsonGenerator(sw);
			mapper.writeValue(jsonGenerator, param);
			jsonString = new String(sw.getBuffer());
			sw.close();
			sw = null;
			mapper = null;
			jsonFactory = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return webInvoke(methodName, login, password, jsonString, "application/json");
	}

	private String webInvoke(String methodName, String login, String password, String data, String contentType) {
		Utils.appendLog(data);
		HttpResponse response = null;
		String ret = null;

		String url = String.format(Constants.URI_SCHEME, AppDataProvider.getInstance().getDomen(), methodName);

		HttpPost httpPost = new HttpPost(url);
		// mHttpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
		// CookiePolicy.RFC_2109);

		httpPost.setHeader("Content-Type", contentType);

		//httpPost.setHeader("Host", Constants.DOMEN);
		//httpPost.setHeader("Host", "bookaz.jelastic.servint.net");
		httpPost.setHeader("Authorization", Utils.getBase64Code(login, password));
		httpPost.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.83 Safari/537.1");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");
		httpPost.setHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpPost.setHeader("Accept-Language", "en-US,en;q=0.8");
		try {
			httpPost.setEntity(new StringEntity(data));
		} catch (UnsupportedEncodingException e1) { // TODO Auto-generated catch
													// block
													// e1.printStackTrace();
		}

		// ByteArrayEntity(data.getBytes())
		try {
			response = mHttpClient.execute(httpPost);
			if (response != null) {
				ret = EntityUtils.toString(response.getEntity());
				Log.d("attachment", ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{}";
		}
		return (ret == null) ? "{}" : ret;
	}

}
