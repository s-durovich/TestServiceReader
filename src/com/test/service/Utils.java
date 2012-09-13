package com.test.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

public class Utils {

	public static String getBase64Code(String login, String pass) {
		String source = login + ":" + pass;
		String ret = "Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP); // UTF-8
																											// default
		return ret;
	}

	public static void appendLog(String text) {
		File logFile = new File("sdcard/servicelog.txt");
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			// BufferedWriter for performance, true to set append to file flag
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
			buf.append(text);
			buf.newLine();
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
	
	public static byte[] copyToBuffer(String filePath) {
	//	String filePath = MemoryStatus.externalStorageDirectory() + File.separator + CacheManager.READER_CACHE_DIR
	//			+ File.separator + "polnyiy_root.fb2";
		byte[] bytes = null;
		try {
			RandomAccessFile file = new RandomAccessFile(filePath, "r");
			try {
				int size = (int) file.length();
				bytes = new byte[size];
				file.readFully(bytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}
}
