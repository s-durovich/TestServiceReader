package com.test.service;

import android.util.Base64;

public class Utils {

	public static String getBase64Code(String login, String pass) {
		String source = login + ":" + pass;
		String ret = "Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP); // UTF-8
																											// default
		return ret;
	}
}
