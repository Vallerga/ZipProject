package com.zip_project.jsonparser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.zip_project.service.JsonParserTDD;

class IsJsonObjectTest {

	// is json object test
	@Test
	void testIsJsonObject_EmptyObject() {
		assertTrue(JsonParserTDD.isJsonObject("{}"));
	}

	@Test
	void testIsJsonObject_SingleAttribute() {
		assertTrue(JsonParserTDD.isJsonObject("{\"name\": \"John\"}"));
	}

	@Test
	void testIsJsonObject_MultipleAttributes() {
		assertTrue(JsonParserTDD.isJsonObject(
				"{\"name\": \"John\", \"age\": 30, \"isStudent\": true}"));
	}

	@Test
	void testIsJsonObject_NestedObject() {
		assertTrue(JsonParserTDD.isJsonObject(
				"{\"person\": {\"name\": \"John\", \"age\": 30}}"));
	}

	@Test
	void testIsJsonObject_NestedArray() {
		assertTrue(JsonParserTDD.isJsonObject("{\"numbers\": [1, 2, 3]}"));
	}

	@Test
	void testIsJsonObject_InvalidStructure_NoCurlyBraces() {
		assertFalse(JsonParserTDD.isJsonObject("\"name\": \"John\""));
	}

	@Test
	void testIsJsonObject_InvalidStructure_MultipleColons() {
		assertFalse(
				JsonParserTDD.isJsonObject("{\"name\": \"John\" : \"Doe\"}"));
	}

	@Test
	void testIsJsonObject_InvalidStructure_DuplicateAttributeNames() {
		assertFalse(JsonParserTDD
				.isJsonObject("{\"name\": \"John\", \"name\": \"Doe\"}"));
	}

	@Test
	void testIsJsonObject_InvalidStructure_ExtraComma() {
		assertFalse(JsonParserTDD.isJsonObject("{\"name\": \"John\",}"));
	}

	@Test
	void testIsJsonObject_InvalidStructure_NoColon() {
		assertFalse(JsonParserTDD.isJsonObject("{\"name\" \"John\"}"));
	}

	@Test
	void testIsJsonObject_InvalidStructure_EmptyAttributeName() {
		assertFalse(JsonParserTDD.isJsonObject("{\"\": \"value\"}"));
	}

	@Test
	void testIsJsonObject_InvalidStructure_MultipleValuesBeforeComma() {
		assertFalse(JsonParserTDD
				.isJsonObject("{\"name\": \"John\" \"Doe\", \"age\": 30}"));
	}

	@Test
	void testIsJsonObject_InvalidStructure_MultipleNamesBeforeComma() {
		assertFalse(JsonParserTDD
				.isJsonObject("{\"name\" \"surname\": \"John\", \"age\": 30}"));
	}

	@Test
	void testIsJsonObject_AttributeWithEscapeCharacters() {
		assertTrue(JsonParserTDD
				.isJsonObject("{\"escaped\": \"This is a \\\"test\\\".\"}"));
	}

	@Test
	void testIsJsonObject_AttributeWithSpecialCharacters() {
		assertTrue(JsonParserTDD.isJsonObject("{\"special\": \"New\nLine\"}"));
	}

	@Test
	void testIsJsonObject_NestedComplexObject() {
		assertTrue(JsonParserTDD.isJsonObject(
				"{\"person\": {\"name\": \"John\", \"details\": {\"age\": 30, \"isStudent\": true}}}"));
	}
}
