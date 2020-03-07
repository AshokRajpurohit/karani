package com.ashok.friends.ankit.test;

public class LineWithCodeAnalyzer extends SingleLineAnalyzer {

	@Override
	public boolean matches(String line) {
		if (otherAnalyzers != null) {
			for (SingleLineAnalyzer singleLineAnalyzer : otherAnalyzers) {
				if (singleLineAnalyzer.matches(line)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public LineType getType() {
		return LineType.CODE;
	}
}
