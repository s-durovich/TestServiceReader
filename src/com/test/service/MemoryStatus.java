package com.test.service;

public class MemoryStatus {

	public static boolean externalMemoryAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	public static String externalStorageDirectory() {
		if (externalMemoryAvailable())
			return android.os.Environment.getExternalStorageDirectory()
					.getPath();
		return null;
	}
}
