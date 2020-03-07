package com.ashok.friends.ankit.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JavaFileAnalyzer extends FileAnalyzer {

	@Override
	public FileType getFileType() {
		return FileType.JAVA;
	}

	private Map<LineType, SingleLineAnalyzer> lineAnalyzers = new LinkedHashMap<>();

	public JavaFileAnalyzer() {
		BlankLineAnalyzer blankLineAnalyzer = new BlankLineAnalyzer();
		LineWithCodeAnalyzer lineWithCodeAnalyzer = new LineWithCodeAnalyzer();
		SingleLineCommentAnalyzer commentAnalyzer = new SingleLineCommentAnalyzer();

		lineWithCodeAnalyzer.setOtherAnalyzers(Arrays.asList(blankLineAnalyzer, commentAnalyzer));

		getLineAnalyzers().put(LineType.BLANK, blankLineAnalyzer);
		getLineAnalyzers().put(LineType.SINGLE_LINE_COMMENT, commentAnalyzer);
		getLineAnalyzers().put(LineType.CODE, lineWithCodeAnalyzer);
	}

	@Override
	public Map<String, Integer> analyseFileContent(String[] fileData) {
		Map<LineType, SingleLineAnalyzer> lineAnalyzerMap = getLineAnalyzers();
		List<SingleLineAnalyzer> lineAnalyzers = lineAnalyzerMap.values().stream().collect(Collectors.toList());
		Map<String, Integer> result = new HashMap<>();
		for (String fileLine : fileData) {
			for (SingleLineAnalyzer lineAnalyzer : lineAnalyzers) {
				if (lineAnalyzer.matches(fileLine)) {
					LineType lineType = lineAnalyzer.getType();
					Integer lineTypeCount = result.get(lineType.name());
					if (lineTypeCount == null) {
						lineTypeCount = 0;
					}
					lineTypeCount++;
					result.put(lineType.name(), lineTypeCount);
					break;
				}
			}
		}
		result.put("Total", fileData.length);
		// System.out.println(result);
		return result;
	}

	@Override
	public Map<LineType, SingleLineAnalyzer> getLineAnalyzers() {
		return lineAnalyzers;
	}

}
