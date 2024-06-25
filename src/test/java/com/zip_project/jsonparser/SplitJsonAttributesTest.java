package com.zip_project.jsonparser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.zip_project.service.JsonParserTDD;

class SplitJsonAttributesTest {

	@Test
	void testSplitJsonAttributes_SimpleAttributes() {
		String input = "\"name\": \"John\", \"age\": 30, \"isStudent\": true";
		List<String> attributes = JsonParserTDD.splitJsonAttributes(input);
		assertEquals(3, attributes.size());
		assertTrue(attributes.contains("\"name\": \"John\""));
		assertTrue(attributes.contains("\"age\": 30"));
		assertTrue(attributes.contains("\"isStudent\": true"));
	}

	@Test
	void testSplitJsonAttributes_NestedObject() {
		String input = "\"person\": {\"name\": \"John\", \"age\": 30}, \"isStudent\": true";
		List<String> attributes = JsonParserTDD.splitJsonAttributes(input);
		assertEquals(2, attributes.size());
		assertTrue(attributes
				.contains("\"person\": {\"name\": \"John\", \"age\": 30}"));
		assertTrue(attributes.contains("\"isStudent\": true"));
	}

	@Test
	void testSplitJsonAttributes_NestedArray() {
		String input = "\"numbers\": [1, 2, 3], \"isStudent\": true";
		List<String> attributes = JsonParserTDD.splitJsonAttributes(input);
		assertEquals(2, attributes.size());
		assertTrue(attributes.contains("\"numbers\": [1, 2, 3]"));
		assertTrue(attributes.contains("\"isStudent\": true"));
	}

	@Test
	void testSplitJsonAttributes_SingleAttribute() {
		String input = "\"name\": \"John\"";
		List<String> attributes = JsonParserTDD.splitJsonAttributes(input);
		assertEquals(1, attributes.size());
		assertTrue(attributes.contains("\"name\": \"John\""));
	}

	@Test
	void testSplitJsonAttributes_EmptyInput() {
		String input = "";
		List<String> attributes = JsonParserTDD.splitJsonAttributes(input);
		assertEquals(0, attributes.size());
	}

}
