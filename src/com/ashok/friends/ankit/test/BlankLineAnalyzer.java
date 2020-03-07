package com.ashok.friends.ankit.test;

public class BlankLineAnalyzer extends SingleLineAnalyzer {

	@Override
	public boolean matches(String line) {
		return line == null || line.trim().length() == 0;
	}

	@Override
	public LineType getType() {
		return LineType.BLANK;
	}
}
