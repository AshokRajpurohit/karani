package com.ashok.friends.ankit.test;

import java.util.List;

public abstract class SingleLineAnalyzer {

	protected List<SingleLineAnalyzer> otherAnalyzers;

	public void setOtherAnalyzers(List<SingleLineAnalyzer> otherAnalyzers) {
		this.otherAnalyzers = otherAnalyzers;
	}

	public List<SingleLineAnalyzer> getOtherAnalyzers() {
		return this.otherAnalyzers;
	}

	public abstract boolean matches(String line);

	public abstract LineType getType();

}
