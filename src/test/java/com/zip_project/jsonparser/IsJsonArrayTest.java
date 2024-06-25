package com.zip_project.jsonparser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.zip_project.service.JsonParserTDD;

class IsJsonArrayTest {

	@Test
	void testJsonArray() {
		assertTrue(JsonParserTDD.isJsonArray("[]"));
		assertTrue(JsonParserTDD.isJsonArray("     [   ]   "));
		assertTrue(JsonParserTDD.isJsonArray("[\n]"));
		assertFalse(JsonParserTDD.isJsonArray("["));
		assertFalse(JsonParserTDD.isJsonArray("]"));
	}

	@Test
	void testIsJsonArray_ValidArray() {
		assertTrue(JsonParserTDD.isJsonArray("[1, 2, 3]"));
	}

	@Test
	void testIsJsonArray_InvalidArray_UnmatchedBrackets() {
		assertFalse(JsonParserTDD.isJsonArray("[1, 2, 3"));
	}

	@Test
	void testIsJsonArray_ValidNestedArrays() {
		assertTrue(JsonParserTDD.isJsonArray("[1, [2, 3], 4]"));
	}

	@Test
	void testIsJsonArray_InvalidNestedArrays_UnmatchedBrackets() {
		assertFalse(JsonParserTDD.isJsonArray("[1, [2, 3], 4]]"));
	}

	@Test
	void testIsJsonArray_ValidEmptyArray() {
		assertTrue(JsonParserTDD.isJsonArray("[]"));
	}

	@Test
	void testIsJsonArray_InvalidArray_EmptyButMismatchedBrackets() {
		assertFalse(JsonParserTDD.isJsonArray("["));
	}

	@Test
	void testIsJsonArray_ValidArrayWithStrings() {
		assertTrue(JsonParserTDD.isJsonArray("[\"hello\", \"world\"]"));
	}

	@Test
	void testIsJsonArray_InvalidArrayWithUnmatchedStringQuotes() {
		assertFalse(JsonParserTDD.isJsonArray("[\"hello\", \"world]"));
	}

	@Test
	void testARRAYJSON_MultipleArrays() {
		assertFalse(JsonParserTDD.isJsonArray("[1, 2, 3] [4, 5, 6]"));
	}
}
