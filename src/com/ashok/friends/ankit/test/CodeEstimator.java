package com.ashok.friends.ankit.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CodeEstimator {

	private static Map<FileType, FileAnalyzer> fileAnalyzers = new HashMap<>();

	public static void main(String[] args) throws Exception {
		populateFileAnalyzers();
		String inputFileName = "code_input.txt";
		String[] fileData = FileUtil.getDataFromFile(inputFileName);
		FileType fileType = FileType.JAVA;
		FileAnalyzer fileAnalyzer = fileAnalyzers.get(fileType);
		Map<String, Integer> lineTypesCount = fileAnalyzer.analyseFileContent(fileData);
		Set<String> keySet = lineTypesCount.keySet();
		lineTypesCount.entrySet();
		for (String lineType : keySet) {
			System.out.println(lineType + ":" + lineTypesCount.get(lineType));
		}
	}

	private static void populateFileAnalyzers() {
		fileAnalyzers.put(FileType.JAVA, new JavaFileAnalyzer());
	}
}
