package com.zip_project;

import java.util.List;

public class TestData {
	private final List<String> input;
	private final int expectedSize;

	public TestData(List<String> input, int expectedSize) {
		this.input = input;
		this.expectedSize = expectedSize;
	}

	public List<String> getInput() {
		return input;
	}

	public int getExpectedSize() {
		return expectedSize;
	}
}