package com.test.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CacheManager {

	public static final String READER_DIR = "ReaderService";
	public static final String READER_CACHE_DIR = READER_DIR + File.separator
			+ "ReaderCache";

	/*polnyiy_root.fb2*/
	private static boolean cacheExternalAvailable() {
		if (MemoryStatus.externalMemoryAvailable())
			return true;
		return false;
	}

	public static boolean prepareNeededDirs(String taskId) {
		if (cacheExternalAvailable()) {
			java.io.File f = null;
			f = new java.io.File(MemoryStatus.externalStorageDirectory()
					+ File.separator + READER_DIR);
			if (!f.exists())
				f.mkdirs();
			f = new java.io.File(MemoryStatus.externalStorageDirectory()
					+ File.separator + READER_CACHE_DIR + File.separator
					+ taskId);
			if (!f.exists())
				f.mkdirs();
			return true;
		}
		return false;
	}

	/*public static void createAndSaveFile(byte[] data) {
		String dir = MemoryStatus.externalStorageDirectory() + File.separator
				+ SONAR_CACHE_DIR + File.separator
				+ ApplicationManager.getInstance().getDetailedTask().Id;

		if (prepareNeededDirs(ApplicationManager.getInstance()
				.getDetailedTask().Id)) {
			File f = new File(dir + File.separator
					+ ApplicationManager.getInstance().getAttachment().Id
					+ ".cache");
			try {
				if (f.createNewFile()) {
					RandomAccessFile file = new RandomAccessFile(f, "rw");
					if (data != null)
						file.write(data);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
}
