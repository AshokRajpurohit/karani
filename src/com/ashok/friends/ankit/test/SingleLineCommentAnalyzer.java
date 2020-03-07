package com.ashok.friends.ankit.test;

public class SingleLineCommentAnalyzer extends SingleLineAnalyzer {

	@Override
	public boolean matches(String line) {
		if (line == null) {
			return false;
		}
		line = line.trim();
		if (line.startsWith("//") || (line.startsWith("/*") && line.endsWith("*/"))) {
			return true;
		}
		return false;
	}

	@Override
	public LineType getType() {
		return LineType.SINGLE_LINE_COMMENT;
	}

}
