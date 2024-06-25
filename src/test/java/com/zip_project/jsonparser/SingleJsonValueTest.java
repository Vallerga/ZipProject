package com.zip_project.jsonparser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.zip_project.service.JsonParserTDD;

class SingleJsonValueTest {

	@Test
	void testIsSingleJsonValue_ValidString() {
		assertTrue(JsonParserTDD.isSingleJsonValue("\"Hello, world!\""));
	}

	@Test
	void testIsSingleJsonValue_ValidNumber() {
		assertTrue(JsonParserTDD.isSingleJsonValue("42"));
	}

	@Test
	void testIsSingleJsonValue_ValidBoolean() {
		assertTrue(JsonParserTDD.isSingleJsonValue("true"));
	}

	@Test
	void testIsSingleJsonValue_ValidNull() {
		assertTrue(JsonParserTDD.isSingleJsonValue("null"));
	}

	@Test
	void testIsSingleJsonValue_ValidObject() {
		assertTrue(JsonParserTDD
				.isSingleJsonValue("{\"name\": \"John\", \"age\": 30}"));
	}

	@Test
	void testIsSingleJsonValue_ValidArray() {
		assertTrue(JsonParserTDD.isSingleJsonValue("[1, 2, 3]"));
	}

	@Test
	void testIsSingleJsonValue_InvalidMultipleValues() {
		assertFalse(JsonParserTDD.isSingleJsonValue("\"Hello\" \"World\""));
	}

	@Test
	void testIsSingleJsonValue_InvalidStringWithoutQuotes() {
		assertFalse(JsonParserTDD.isSingleJsonValue("Hello, world!"));
	}

	@Test
	void testIsSingleJsonValue_InvalidMultipleTypes() {
		assertFalse(JsonParserTDD
				.isSingleJsonValue("{\"name\": \"John\"} [1, 2, 3]"));
	}

	@Test
	void testIsSingleJsonValue_InvalidEmpty() {
		assertFalse(JsonParserTDD.isSingleJsonValue(""));
	}

}
