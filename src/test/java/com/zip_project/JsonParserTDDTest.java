package com.zip_project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.zip_project.service.JsonParserTDD;

@SpringBootTest
class JsonParserTDDTest {

	@Test
	void testNullString() {
		assertTrue(JsonParserTDD.isJsonNull(null));
	}

	@Test
	void testEmptyString() {
		assertTrue(JsonParserTDD.isEmptyString(""));
		assertTrue(JsonParserTDD.isEmptyString("    "));
		assertTrue(JsonParserTDD.isEmptyString("\n"));
	}

	@Test
	void testisDoubleQuotesString() {
		assertTrue(JsonParserTDD.isDoubleQuotesString("\"\""));
		assertTrue(JsonParserTDD.isDoubleQuotesString("\"    \""));
		assertTrue(JsonParserTDD.isDoubleQuotesString("     \"\"    "));
		assertTrue(JsonParserTDD.isDoubleQuotesString("\n\"\n\"\n"));
	}

	@Test
	void testStringWithDoubleQuotes() {
		assertTrue(JsonParserTDD.isJsonString("\"\""));
		assertTrue(JsonParserTDD.isJsonString("\"    \""));
		assertTrue(JsonParserTDD.isJsonString("     \"\"    "));
		assertTrue(JsonParserTDD.isJsonString("\n\"\n\"\n"));
	}
	
	 @Test
	    void testJsonString() {
	        assertTrue(JsonParserTDD.isJsonString("\"hello\""));
	        assertTrue(JsonParserTDD.isJsonString("\"  hello  \""));
	        assertTrue(JsonParserTDD.isJsonString("\"\\\"\\\"\""));
	        assertTrue(JsonParserTDD.isJsonString("\"\\t\\n\\r\""));
	        assertFalse(JsonParserTDD.isJsonString("hello"));
	        assertFalse(JsonParserTDD.isJsonString("  hello  "));
	        assertFalse(JsonParserTDD.isJsonString("\""));
	        assertFalse(JsonParserTDD.isJsonString("\"hello"));
	        assertFalse(JsonParserTDD.isJsonString("hello\""));
	    }

	@Test
	void testJsonObject() {
		assertTrue(JsonParserTDD.isJsonObject("{}"));
		assertTrue(JsonParserTDD.isJsonObject("     {   }   "));
		assertTrue(JsonParserTDD.isJsonObject("{\n}"));
		assertFalse(JsonParserTDD.isJsonObject("{"));
		assertFalse(JsonParserTDD.isJsonObject("}"));
	}

	@Test
	void testJsonArray() {
		assertTrue(JsonParserTDD.isJsonArray("[]"));
		assertTrue(JsonParserTDD.isJsonArray("     [   ]   "));
		assertTrue(JsonParserTDD.isJsonArray("[\n]"));
		assertFalse(JsonParserTDD.isJsonArray("["));
		assertFalse(JsonParserTDD.isJsonArray("]"));
	}

	@Test
	void testJsonLong() {
		assertTrue(JsonParserTDD.isJsonLong("123456789"));
		assertTrue(JsonParserTDD.isJsonLong("  123456789  "));
		assertFalse(JsonParserTDD.isJsonLong("123.45"));
		assertFalse(JsonParserTDD.isJsonLong("abc"));
	}

	@Test
	void testJsonInteger() {
		assertTrue(JsonParserTDD.isJsonNumber("12345"));
		assertTrue(JsonParserTDD.isJsonNumber("  12345  "));
		assertFalse(JsonParserTDD.isJsonNumber("123.45"));
		assertFalse(JsonParserTDD.isJsonNumber("abc"));
	}

	@Test
	void testJsonDouble() {
		assertTrue(JsonParserTDD.isJsonDouble("123.45"));
		assertTrue(JsonParserTDD.isJsonDouble("  123.45  "));
		assertFalse(JsonParserTDD.isJsonDouble("12345"));
		assertFalse(JsonParserTDD.isJsonDouble("abc"));
	}

	@Test
	void testJsonBoolean() {
		assertTrue(JsonParserTDD.isJsonBoolean("true"));
		assertTrue(JsonParserTDD.isJsonBoolean("false"));
		assertTrue(JsonParserTDD.isJsonBoolean("  true  "));
		assertTrue(JsonParserTDD.isJsonBoolean("  false  "));
		assertFalse(JsonParserTDD.isJsonBoolean("yes"));
		assertFalse(JsonParserTDD.isJsonBoolean("no"));
	}
	
	@Test
    void testIsJsonAttribute() {
	 assertTrue(JsonParserTDD.isJsonAttribute("\"name\": \"John\""));
     assertTrue(JsonParserTDD.isJsonAttribute("\"age\": 30"));
     assertTrue(JsonParserTDD.isJsonAttribute("\"isStudent\": true"));
     assertTrue(JsonParserTDD.isJsonAttribute("\"balance\": 123.45"));
     assertTrue(JsonParserTDD.isJsonAttribute("\"children\": null"));
     assertTrue(JsonParserTDD.isJsonAttribute("\"address\": {\"city\": \"New York\"}"));
     assertTrue(JsonParserTDD.isJsonAttribute("\"numbers\": [1, 2, 3]"));

     assertFalse(JsonParserTDD.isJsonAttribute("name: \"John\"")); 
     assertFalse(JsonParserTDD.isJsonAttribute("\"name\" \"John\""));
//     assertFalse(JsonParserTDD.isJsonAttribute("\"name\": \"John\" \"Doe\""));
     assertFalse(JsonParserTDD.isJsonAttribute("\"name\": "));
     assertFalse(JsonParserTDD.isJsonAttribute(": \"John\""));
     assertFalse(JsonParserTDD.isJsonAttribute("\"name: \"John\""));
	}
}
