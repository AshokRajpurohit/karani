package com.ashok.friends.ankit.test;

import java.util.Map;

public abstract class FileAnalyzer {

	public abstract Map<String, Integer> analyseFileContent(String[] fileData);

	public abstract FileType getFileType();

	public abstract Map<LineType, SingleLineAnalyzer> getLineAnalyzers();

}
