package com.ashok.friends.ankit.test;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileUtil {

	private static String newLine = System.getProperty("line.separator");

	public static String[] getDataFromFile(String fileName) throws Exception {
		StringBuilder data = new StringBuilder();
		data = getFileData(fileName);
		String lines[] = data.toString().split(newLine);
		return lines;
	}

	public static StringBuilder getFileData(String fileName) throws Exception {
		StringBuilder data = new StringBuilder();
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String temp = br.readLine();
		while (null != temp) {
			data.append(temp);
			data.append(newLine);
			temp = br.readLine();
		}
		br.close();
		fr.close();
		return data;
	}

}
